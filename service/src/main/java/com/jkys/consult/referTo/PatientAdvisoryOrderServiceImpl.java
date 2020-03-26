package com.jkys.consult.referTo;

import com.jkys.consult.referTo.bean.AdvisoryStatus;
import com.jkys.consult.referTo.bean.OrderInfoDO;
import com.jkys.consult.referTo.bean.OrderType;
import com.jkys.consult.referTo.bean.advisory.OrderInfoModel;
import com.jkys.consult.referTo.bean.advisory.OrderResult;
import com.jkys.consult.referTo.bean.request.OrderInfoRequest;
import com.jkys.consult.utils.JsonUtil;
import com.jkys.phobos.ApiGateway;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.domain.user.UserExtraInfo;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author ks
 * @since 2018/8/31
 **/
public class PatientAdvisoryOrderServiceImpl implements PatientAdvisoryOrderService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PatientAdvisoryOrderMapper patientAdvisoryOrderMapper;
    @Resource
    private UserCenterUserService userCenterUserService;
    @Resource
    private PatientAdvisoryInfoMapper patientAdvisoryInfoMapper;
    @Resource
    private PatientAdvisoryLogic patientAdvisoryLogic;
    private final String[] column = {"hospital"};

    public List<OrderInfoModel> queryPatientInfoOrderList(OrderInfoRequest request) {
        if (ApiGateway.getUserId() == null || request.getStatus() == null) {
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        log.info("病人：咨询订单 请求参数：{}", JsonUtil.GsonString(request));

        List<OrderInfoModel> models = new ArrayList<>();
        Long userId = ApiGateway.getUserId();
        List<OrderInfoDO> orders = patientAdvisoryOrderMapper.queryPatientInfoOrderList(userId, request.getStatus(),
                request.getOffSet(), request.getLimit());
        if (orders == null || orders.isEmpty()) {
           return  new ArrayList<>();
        }
        List<Long> userIds = orders.stream().map(OrderInfoDO::getDoctorId).collect(Collectors.toList());
        BaseUserInfo user;
        UserExtraInfo userExtra;
        Map<Long, BaseUserInfo> users = userCenterUserService.findUserByIds(userIds);
        Map<Long, UserExtraInfo> userExtras = userCenterUserService.findUserExtraByUserIds(userIds, column);
        if (users != null && !users.isEmpty() && userExtras != null && !userExtras.isEmpty()) {
            for (OrderInfoDO o : orders) {
                OrderInfoModel model = new OrderInfoModel();
                user = users.get(o.getDoctorId());
                userExtra = userExtras.get(o.getDoctorId());
                model.setDoctorName(user.getName() != null ? user.getName() : user.getNickname());
                model.setDescribes(o.getDescribes());
                model.setStartTime(o.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                model.setAmount(o.getAmount());
                model.setStatus(o.getStatus());
                model.setAvatar(user.getAvatar());
                model.setDoctorId(o.getDoctorId());
                model.setId(o.getId());
                if (userExtra.getPairs() != null && userExtra.getPairs().get("hospital") != null) {
                    model.setHospital(userExtra.getPairs().get("hospital").toString());
                }
                try {
                    model.setStatusMsg(AdvisoryStatus.getEnum(o.getStatus()).getDescribe());
                }catch (Exception e){
                    log.warn("AdvisoryStatus:{}",o.getStatus(),e);
                }
                models.add(model);
            }
            return models;
        }
        return new ArrayList<>();
    }


    public OrderResult queryDoctorInfoOrderList(OrderInfoRequest request) {
        if (ApiGateway.getUserId() == null || request.getStatus() == null || StringUtils.isEmpty(request.getDate())) {
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        log.info("医生：我的订单 请求参数：{}", JsonUtil.GsonString(request));

        List<OrderInfoModel> models = new ArrayList<>();
        OrderResult result = new OrderResult();
        Long userId = ApiGateway.getUserId();
        String startDate = null/* = DateUtils.getFirstDayOfMonth(request.getDate())*/;
        String endDate = null/* = DateUtils.getFirstDayOfNextMonth(request.getDate())*/;
        result.setSumMap(patientAdvisoryOrderMapper.sumDoctorInfoOrder(userId,startDate,endDate));
        List<OrderInfoDO> orders = patientAdvisoryOrderMapper.queryDoctorInfoOrderList(userId, request.getStatus(),
                request.getOffSet(), request.getLimit(),startDate,endDate);
        if (orders == null || orders.isEmpty()) {
            result.setOrderList(new ArrayList<>());
            return result;
        }

        for (OrderInfoDO o : orders) {
            OrderInfoModel model = new OrderInfoModel();
            model.setDescribes(o.getDescribes());
            model.setStartTime(o.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            model.setAmount(o.getAmount());
            model.setPatientId(o.getPatientId());
            model.setDoctorId(o.getDoctorId());
            try {
                model.setStatusMsg(AdvisoryStatus.getEnum(o.getStatus()).getDescribe());
            }catch (Exception e){
                log.warn("AdvisoryStatus:{}",o.getStatus(),e);
            }
            model.setStatus(o.getStatus());
            model.setId(o.getId());
            models.add(model);
        }
        result.setOrderList(models);
        return result;
    }

    @Override
    public OrderInfoModel queryOrderOne(String orderNum, String type) {
        if (StringUtils.isEmpty(orderNum) || StringUtils.isEmpty(type)) {
            return null;
        }
        PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findLastPayOrder(orderNum, type);
        if (order == null) {
            return null;
        }
        OrderInfoModel model = new OrderInfoModel();
        BeanUtils.copyProperties(order, model);
        return model;
    }

    @Override
    public Boolean backOrder(String orderNum) {
        PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findLastPayOrder(orderNum, OrderType.PAY.name());
        if (order == null) {
            return false;
        }
        PatientAdvisoryInfo info = patientAdvisoryInfoMapper.findByOrder(orderNum);
        if (info == null) {
            return false;
        }
        patientAdvisoryInfoMapper.updateStatus(AdvisoryStatus.REFUND.getCode(), info.getId());
        PatientAdvisoryOrder backOrder = patientAdvisoryLogic.backOrderCoin(order);
        Boolean response = patientAdvisoryLogic.isIncreaseCoin(backOrder);
        if (response) {
            patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.REFUND.getCode(), backOrder.getBizCode());
        } else {
            patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.REFUND_FAIL.getCode(), backOrder.getBizCode());
        }
        return response;
    }
}
