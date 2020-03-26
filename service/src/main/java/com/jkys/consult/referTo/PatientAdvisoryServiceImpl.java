package com.jkys.consult.referTo;

import com.alibaba.fastjson.JSONObject;
import com.jkys.coincenter.enums.SourceEnum;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.request.CoinCenterPatientRequest;
import com.jkys.coincenter.rpc.response.CoinCenterPatientResponse;
import com.jkys.coincenter.rpc.response.CoinRechargeResponse;
import com.jkys.consult.referTo.bean.AdvisoryStatus;
import com.jkys.consult.referTo.bean.Constants;
import com.jkys.consult.referTo.bean.OrderType;
import com.jkys.consult.referTo.bean.advisory.AdvisoryQuery;
import com.jkys.consult.referTo.bean.advisory.CoinOrderModel;
import com.jkys.consult.referTo.bean.request.AdvisoryRequest;
import com.jkys.consult.referTo.bean.response.BaseResponse;
import com.jkys.im.client.bean.UserSendAction;
import com.jkys.im.client.enumeration.MessageType;
import com.jkys.phobos.ApiGateway;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.uenum.UserTypeEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author yangZh
 * @since 2018/7/13
 **/
public class PatientAdvisoryServiceImpl implements PatientAdvisoryService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PatientAdvisoryInfoMapper patientAdvisoryInfoMapper;
    @Resource
    private PatientAdvisoryOrderMapper patientAdvisoryOrderMapper;
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private CoinCenterPatientService coinCenterPatientService;
    @Resource
    private PatientAdvisoryLogic patientAdvisoryLogic;
//    @Resource
//    private RedisService redisService;
//    @Resource
//    private KafkaServer kafkaServer;
    @Resource
    private UserCenterUserService userCenterUserService;
//    @Resource
//    private PrescriptionDataRpcService prescriptionDataRpcService;
//    @Resource
//    private SpecialAdvisoryService specialAdvisoryService;
//    @Resource
//    private MemberAdvisoryRpcService memberAdvisoryRpcService;

    private static final Integer REDIS_TIMEOUT = 10;//重复发起支付间隔 10秒
    private static final Long PAY_TIMEOUT = 1000L; //自循环间隔时间 毫秒

    /**
     * 可重入方法,无事务，多次支付优先查找是否已支付糖币成功
     *
     * @param request 使用 医生id  病人id
     */
    @Override
    public CoinOrderModel payGo(AdvisoryRequest request) {
        Long patientId = ApiGateway.getUserId();
        log.info("确认支付 请求开始，patientId:{},doctorId:{}", patientId, request.getDoctorId());
//        if (request.getDoctorId() == null || patientId == null) {
//            log.warn("咨询订单确认支付：参数异常！user:{}", patientId);
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
//        }
//
//        if (request.getPatientId() != null && !patientId.equals(request.getPatientId())) {
//            throw new ShineServiceException(ExceptionCode.INVALID_USER_STATUS);
//        }
//        //支付频率
//        if (redisService.get(patientId.toString()) != null && !request.getReTry()) {
//            throw new ShineServiceException(ExceptionCode.ORDER_PAY_BUSY);
//        }
//        redisService.set(patientId.toString(), patientId.toString(), REDIS_TIMEOUT);
        PatientAdvisoryInfo info = patientAdvisoryInfoMapper.findByDocAndPatient(request.getDoctorId(), patientId);
        if (info == null) {
//            throw new ShineServiceException(ExceptionCode.INVALID_ADVISORY);
        }
        Integer price = patientAdvisoryLogic.doctorPrice(request.getDoctorId());
        CoinOrderModel model = new CoinOrderModel();
        boolean success = false;
        PatientAdvisoryOrder order = null;
//        synchronized (request.getDoctorId()) {
        try {
            String orderNum;
            orderNum = info.getOrderNum();
            if (!info.getStatus().equals(AdvisoryStatus.INIT.getCode())) {
//                throw new ShineServiceException(ExceptionCode.USING_ADVISORY);
            }
            order = patientAdvisoryOrderMapper.findLastPayOrder(orderNum, OrderType.PAY.name());
            if (order != null) { //3种情况 已支付，支付失败，未发起支付（同订单再发起）
                model.setAmount(order.getAmount());
                if (order.getStatus() > OrderStatus.PAY_FAIL.getCode()) {
//                    throw new ShineServiceException(ExceptionCode.ORDER_BACK);
                }
                if (order.getStatus() == OrderStatus.PAY_SUCCESS.getCode()) {//已支付成功过
                    long expire = System.currentTimeMillis() + Constants.EXPIRE_TIME;
                    patientAdvisoryInfoMapper.updateOrderStatusStart(info.getId(), expire);
                    model.setBizCode(order.getBizCode());
//                    model.setId(order.getId());
                    model.setCoinPay(1);
                    model.setSuccess(true);
                    return model;
                }
            } else {//无支付订单，新生成
                log.info("确认支付 订单生成，orderNum：{},patientId:{},doctorId:{}", orderNum, patientId, request.getDoctorId());

                order = patientAdvisoryLogic.initOrder(request.getDoctorId(), patientId, orderNum,
                        OrderStatus.INIT.getCode(), OrderType.PAY.name());

            }
            if (order == null) {
//                throw new ShineServiceException(ExceptionCode.TRY_AGAIN);
            }

//            Integer price = order.getAmount();
            Integer memberFree = 0;
            if (userCenterUserService.isMember(patientId)) {
//                memberFree = memberAdvisoryRpcService.getFreeAdvisoryByDocId(patientId,request.getDoctorId());
                if (memberFree !=null && memberFree > 0) {
                    price = 0;
                }
            }
            model.setAmount(price);
            int times = 0;
            while (times < 3) {
                times++;
                //处理云币余额扣款
                Integer coin = coinCount(patientId);
                model.setCoin(coin);
                if (coin < price) {
                    model.setCoinPay(2);//余额不足
                }
                if (coin >= price) {
                    if (!patientAdvisoryLogic.isDecreasePatientCoin(order, price)) {
//                        throw new ShineServiceException(ExceptionCode.COIN_FAIL);
                    }
                    model.setCoinPay(1);
                    success = true;
                    model.setCoin(coin - price);
                    break;
                }
                if (!request.getReTry()) {
                    break;
                }
//                Thread.sleep(PAY_TIMEOUT);
            }

            if (success) {
//                if (memberFree !=null && memberFree > 0) {
//                  boolean member=  memberAdvisoryRpcService.addFreeAdvisory(info.getId(),patientId,info.getDoctorId(), request.getClient());
//                  if(!member){
//                      throw new ShineServiceException("请检查您的会员咨询权限或退出重试!");
//                  }
//                }
                patientAdvisoryInfoMapper.updateOrderStatusStart(info.getId(),
                        System.currentTimeMillis() + Constants.EXPIRE_TIME);
                patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.PAY_SUCCESS.getCode(), order.getBizCode());


                chatMessageService.sendOrderMessage(info.getPatientId(), info.getDoctorId(), info.getId());
                model.setHasSent(Boolean.TRUE);

            }
//        } catch (ShineServiceException s) {
//            log.warn("确认支付 发生错误，doctorId:{},patientId:{},{}", request.getDoctorId(), patientId, s.getMessage());
//            throw s;
//        } catch (Exception e) {
//            log.error("确认支付 订单异常，patient= {}", patientId, e);
//            throw new ShineServiceException(ExceptionCode.FAIL);
        } finally {
            if (model.getCoinPay() == 0 && order != null) {
                log.info("确认支付 支付失败，doctorId:{},patientId:{},orderNum:{}", request.getDoctorId(), patientId,
                        order.getOrderNum());
                patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.PAY_FAIL.getCode(), order.getBizCode());

            }
        }
//        }
//        model.setId(order.getId());
//        model.setOrderId(order.getId());
        model.setInfoId(info.getId());
        model.setBizCode(order.getBizCode());
        model.setSuccess(true);
        log.info("确认支付 返回结果，result:{}", JSONObject.toJSONString(model));
        return model;
    }

    //无事务处理逻辑
    public CoinOrderModel changeDoctor(AdvisoryRequest request) {
        Long patientId = ApiGateway.getUserId();
        if (request.getDoctorId() == null || patientId == null) {
            log.warn("更换医生：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        request.setPatientId(patientId);
        PatientAdvisoryInfo newDoctorInfo = patientAdvisoryInfoMapper.findByDocAndPatient(request.getNewDoctorId(),
                request.getPatientId());
        if (newDoctorInfo != null && newDoctorInfo.getStatus() > 1) {//已经在咨询的返回，初始状态的再进支付
            log.info("更换医生 新的医生咨询状态错误:{},{}", request.getNewDoctorId(), request.getPatientId());
//            throw new ShineServiceException(ExceptionCode.USING_ADVISORY);
        }
        PatientAdvisoryInfo oldInfo = patientAdvisoryInfoMapper.findByDocAndPatient(request.getDoctorId(),
                request.getPatientId());
        //如果是录入更新，必须先录入信息
        if (newDoctorInfo == null && oldInfo == null) {
//            throw new ShineServiceException(ExceptionCode.INVALID_ADVISORY);
        }
        if (newDoctorInfo == null) {
            try {
                PatientAdvisoryInfo newInfo = new PatientAdvisoryInfo();
                BeanUtils.copyProperties(oldInfo, newInfo);
                String orderNum = patientAdvisoryLogic.genOrderNum(OrderType.PAY.name());
                newInfo.setOrderNum(orderNum);
                newInfo.setStartTime(null);
                newInfo.setExpireTime(null);
                newInfo.setEndTime(null);
                newInfo.setStatus(AdvisoryStatus.INIT.getCode());
                newInfo.setDoctorId(request.getNewDoctorId());
                newInfo.setBeforeInfoId(oldInfo.getId());
//                newInfo.setAdvisoryType(AdvisoryType.PATIENT.getCode());
                patientAdvisoryInfoMapper.initInfo(newInfo);
                patientAdvisoryLogic.initOrder(request.getNewDoctorId(), request.getPatientId(), orderNum,
                        OrderStatus.INIT.getCode(), OrderType.PAY.name());

            } catch (Exception e) {
                log.error("完善咨询订单用户信息：信息录入失败 {}", patientId, e);
//                throw new ShineServiceException(ExceptionCode.FAIL);
            }
        }

        //退款
        if (oldInfo != null) {
            PatientAdvisoryOrder oldOrder = patientAdvisoryOrderMapper.findByOrderAndType(oldInfo.getOrderNum(),
                    OrderType.PAY.name());
            Boolean result = patientAdvisoryLogic.isRefund(oldInfo, oldOrder);
            if (!result) {
//                throw new ShineServiceException(ExceptionCode.TRY_AGAIN);
            }
            chatMessageService.sendFinishAdvisoryMessage(oldInfo.getDoctorId(), oldInfo.getPatientId(),
                    Constants.FINISH_TEXT2, MessageType.Finish.name());
        }

        request.setDoctorId(request.getNewDoctorId());
        return payGo(request);
    }


    @Override
    public BaseResponse updateOrderOver(Long patientId, Long doctorId) {
        log.info("更新咨询会话结束 请求开始，patientId:{},doctorId:{}", patientId, doctorId);
        Long userId = ApiGateway.getUserId();
        if (patientId == null || doctorId == null || userId == null) {
            log.warn("更新咨询会话结束：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        if (patientId == 0 && !doctorId.equals(userId)) {//微信是病人发起的结束，只有token
            patientId = userId;
        }
        PatientAdvisoryInfo advisory;
//        if (SpecialDoctorHandler.isSpecialDoctor(doctorId)) {
//            log.info("特殊专家组特殊处理");
//            advisory = patientAdvisoryInfoMapper.findByDoctorsAndPatient(SpecialDoctorHandler.allSpecialDoctor(),
//                    patientId);
//        } else {
            advisory = patientAdvisoryInfoMapper.findByDocAndPatient(doctorId, patientId);
//        }
        boolean patientAdvisory = false;
        BaseUserInfo patient = userCenterUserService.findUserById(patientId);
        if (patient != null && patient.getUtype().equals(UserTypeEnum.PATIENT.getValue())) {
            patientAdvisory = true;
        }

        stepOver(userId, advisory, doctorId.equals(userId), patientAdvisory);
        return new BaseResponse();
    }

    @Override
    public BaseResponse finishAdvisoryByGroup(AdvisoryRequest request) {
        log.info("更新咨询会话结束 请求开始，imGroupId:{},patientId:{}", request.getImGroupId(), request.getPatientId());
        Long userId = ApiGateway.getUserId();
        if (userId == null) {
            log.warn("更新咨询会话结束：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        BaseUserInfo user = userCenterUserService.findUserById(userId);
        if (user == null) {
//            throw new ShineServiceException(ExceptionCode.USER_NOT_EXIST);
        }
        boolean isDoctor = false;//是否为医生结束
        if (user.getUtype().equals(UserTypeEnum.DOCTOR.getValue())) {
            isDoctor = true;
        }

        Long patientId = request.getPatientId();
        if (patientId != null && patientId == 0 && !isDoctor) {//微信是病人发起的结束，只有token
            patientId = userId;
        }

        boolean patientAdvisory = false;//如果是掌糖的付费咨询（客户端要求兼容下）
        if (patientId != null) {
            BaseUserInfo patient = userCenterUserService.findUserById(patientId);
            if (patient != null && patient.getUtype().equals(UserTypeEnum.PATIENT.getValue())) {
                patientAdvisory = true;
            }
        }

        AdvisoryQuery query = new AdvisoryQuery();
        if (patientAdvisory && isDoctor) {//如果是掌糖的付费咨询且是医生发起
            query.setDoctorId(userId);
            query.setPatientId(patientId);
        } else {//药店的咨询
            if (request.getImGroupId() == null || (isDoctor && request.getImGroupId() < 0)) {
//                throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
            }
            query.setImGroupId(request.getImGroupId());
            if (isDoctor) {
                query.setDoctorId(userId);
            } else {
                query.setPatientId(userId);
            }

        }
        PatientAdvisoryInfo advisory = patientAdvisoryInfoMapper.findByQuery(query);

        stepOver(userId, advisory, isDoctor, patientAdvisory);
        return new BaseResponse();
    }


    private void stepOver(Long userId, PatientAdvisoryInfo advisory, boolean isDoctor, boolean patientAdvisory) {

        if (!patientAdvisory && isStoreAdvisoryOver(advisory, isDoctor)) {
            log.info("药店咨询结束请求：userId:{}", userId);
            return;
        }

        if (advisory == null || advisory.getStartTime() == null) {
            log.warn("更新咨询会话结束：没有可结束的咨询，请勿重复点击 user:{}", userId);
//            throw new ShineServiceException(ExceptionCode.OVER_ADVISORY);
        }

        if (!userId.equals(advisory.getDoctorId()) && !userId.equals(advisory.getPatientId())) {
            log.warn("用户无权结束咨询：userId:{}", userId);
//            throw new ShineServiceException(ExceptionCode.OVER_ADVISORY);
        }

        isPatientAdvisoryOver(advisory);
    }


    private void isPatientAdvisoryOver(PatientAdvisoryInfo advisory) {
        if (advisory.getAdvisoryType() != AdvisoryType.PATIENT.getCode()) {//判定为付费咨询的，出去
//            throw new ShineServiceException("非付费咨询会话，请检查");
        }
        if (patientAdvisoryInfoMapper.updateOrderStatusOver(AdvisoryStatus.OVER.getCode(), advisory.getDoctorId(),
                advisory.getPatientId()) <=
                0) {
//            throw new ShineServiceException(ExceptionCode.FAIL);
        }
        //最后做im消息通知
        chatMessageService.sendFinishAdvisoryMessage(advisory.getDoctorId(), advisory.getPatientId(),
                Constants.FINISH_TEXT, MessageType.Finish.name());
//        kafkaServer.sendMsg(advisory);
        //todo 超过20191101号删除
//        specialAdvisoryService.delKey(advisory.getDoctorId(),advisory.getPatientId());
    }


    private boolean isStoreAdvisoryOver(PatientAdvisoryInfo info, boolean isDoctor) {
        if (info == null || !info.getStatus().equals(AdvisoryStatus.BEGIN.getCode())) {
            if (isDoctor) {
//                throw new ShineServiceException(ExceptionCode.OVER_ADVISORY);
            }
            return true;
        }
        if (info.getAdvisoryType() == AdvisoryType.PATIENT.getCode()) {//判定为付费咨询的，出去
            return false;
        }

        log.info("结束咨询：药店咨询结束请求：doc:{}，patient:{},isDoctor:{}", info.getDoctorId(), info.getPatientId());
        if (isDoctor && info.getAdvisoryType() != AdvisoryType.VIDEO.getCode()) {
//            throw new ShineServiceException("非视频咨询，不可结束");
        }

//        List<PrescriptionDTO> prList = prescriptionDataRpcService.findListByAdvisory(info.getId());
        List prList = new ArrayList();
//        if (!CollectionUtils.isEmpty(prList)) {
//            List<PrescriptionDTO> cancelList = prList.stream().filter(p -> p.getDoctorAuditStatus() ==
//                    0).collect(Collectors.toList());
//            if (!CollectionUtils.isEmpty(cancelList)) {
//                List<Long> ids = cancelList.stream().map(PrescriptionDTO::getId).collect(Collectors.toList());
//                log.info("结束咨询：待处理处方：id:{}", ids);
//                if (isDoctor) {//如果是医生的，必须处理完处方
//                    throw new ShineServiceException("请先处理完相关处方");
//                }
//                Boolean cancel = prescriptionDataRpcService.cancelPrescription(ids);
//                if (!cancel) {
//                    throw new ShineServiceException("结束咨询失败，请重试");
//                }
//            }
//        }
        //就算没有处方单，咨询也要结束
        PatientAdvisoryInfo update = new PatientAdvisoryInfo();
        update.setStatus(AdvisoryStatus.OVER.getCode());
        update.setId(info.getId());
        update.setEndTime(new Date());
        if (patientAdvisoryInfoMapper.updateOrderUserInfo(update) <= 0) {
//            throw new ShineServiceException("结束失败");
        }

        String text = info.getAdvisoryType() == AdvisoryType.VIDEO.getCode() ? "视频问诊已结束" : "咨询已结束";
        if (!CollectionUtils.isEmpty(prList)) {
            if (info.getAdvisoryType() == AdvisoryType.VIDEO.getCode()) {
                chatMessageService.sendFinishAdvisoryMessage(info.getDoctorId(), info.getPatientId(), text,
                        MessageType.Finish.name(), info.getImGroupId());
            } else if (info.getAdvisoryType() == AdvisoryType.TEXT.getCode()) {
                UserSendAction action = null;
//                if (!isDoctor && prList.get(0).getPrescriptionTotalStatus() != null &&
//                        prList.get(0).getPrescriptionTotalStatus().equals(0)) {//初始状态下 如果是店员发起的取消，不需要发医生消息
//                    action = new UserSendAction();
//                    action.setUnActiveUser(Collections.singletonList(info.getDoctorId()));
//                    action.setUnPushUser(Collections.singletonList(info.getDoctorId()));
//                    action.setUnSendUser(Collections.singletonList(info.getDoctorId()));
//                }
                chatMessageService.sendFinishAdvisoryMessage(info.getDoctorId(), info.getPatientId(), text,
                        MessageType.Finish.name(), info.getImGroupId(), action);
            }
        }


        return true;
    }


    @Override
    public CoinOrderModel queryCoin(Long userId, Long doctorId) {
        log.info("咨询订单用户云币查询查询 请求开始，patientId:{},doctorId:{}", userId, doctorId);
        Long patientId = ApiGateway.getUserId();
        if (doctorId == null || patientId == null) {
            log.warn("咨询订单用户云币查询查询：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        CoinOrderModel model = new CoinOrderModel();
        try {
            //云币中心获取云币余额
            model.setCoin(coinCount(patientId));
            model.setPrice(patientAdvisoryLogic.doctorPrice(doctorId));
//            model.setOriginalCoin(patientAdvisoryLogic.doctorOrigin(doctorId));
            model.setSuccess(true);
        } catch (Exception e) {
//            throw new ShineServiceException(ExceptionCode.FAIL);
        }
        return model;
    }


    @Override
    public CoinOrderModel payString(String bizCode, int payWay) {
        log.info("咨询订单获取支付串：orderNum={}，payWay={}", bizCode, payWay);
        if (StringUtils.isEmpty(bizCode) || ApiGateway.getUserId() == null) {
            log.warn("咨询订单支付糖币：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        CoinOrderModel model = new CoinOrderModel();
        model.setSuccess(false);
        try {
            PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findByBizCode(bizCode);
            if (order == null || order.getStatus() > OrderStatus.PAYING.getCode() ||
                !ApiGateway.getUserId().equals(order.getPatientId())) {
//                throw new ShineServiceException(ExceptionCode.INVALID_ORDER);
            }
            Integer coin = coinCount(order.getPatientId());
//            Integer price = patientAdvisoryLogic.doctorPrice(order.getDoctorId());
            if (coin >= order.getAmount()) {
//                throw new ShineServiceException(ExceptionCode.COIN_EXIST);
            }
            Integer cost = order.getAmount() - coin;
            CoinRechargeResponse response;
            if (OrderStatus.PAYING.getCode() == order.getStatus() &&
                !patientAdvisoryLogic.isPaySuccess(order.getOrderNum())) {
                log.info("使用原来的订单号和支付方式，调用云币中心的接口，从第三方重新获取payString：{}", bizCode);
                response = patientAdvisoryLogic.reacquirePayString(order, payWay);
            } else {
                response = patientAdvisoryLogic.coinRecharge(order, cost, payWay);
                patientAdvisoryOrderMapper
                    .updateOrderPaying(cost, bizCode, response.getPayString());
            }
            model.setBizCode(bizCode);
            model.setPayString(response.getPayString());
            model.setSuccess(true);
//        } catch (ShineServiceException s) {
//            log.warn("咨询订单获取支付串 业务异常，bizCode:{},{}", bizCode, s.getMessage());
//            throw new ShineServiceException(Integer.valueOf(s.getCode()), s.getMessage());
//        } catch (Exception e) {
//            log.error("咨询订单获取支付串 异常，bizCode {}", bizCode, e);
//            throw new ShineServiceException(ExceptionCode.FAIL);
//        }

        } finally {

        }
        return model;
    }

    @Override
    public BaseResponse cancelOrder(Long infoId) {
        if (infoId == null || ApiGateway.getUserId() == null) {
            log.warn("取消支付：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        log.info("取消支付,请求参数：infoId={}", infoId);
        Long userId = ApiGateway.getUserId();

        PatientAdvisoryInfo oldInfo = patientAdvisoryInfoMapper.findById(infoId);
        if (oldInfo == null || !oldInfo.getStatus().equals(AdvisoryStatus.INIT.getCode())) {
//            throw new ShineServiceException(ExceptionCode.INVALID_ADVISORY);
        }
        if (!userId.equals(oldInfo.getPatientId())) {
//            throw new ShineServiceException(ExceptionCode.INVALID_USER_STATUS);
        }
        PatientAdvisoryInfo info = new PatientAdvisoryInfo();
        info.setId(infoId);
        info.setStatus(AdvisoryStatus.CANCEL.getCode());
        Long result = patientAdvisoryInfoMapper.updateOrderUserInfo(info);
        if (result <= 0) {
            log.warn("取消支付,失败：infoId={}", infoId);
//            throw new ShineServiceException(ExceptionCode.FAIL);
        }
        BaseResponse res = new BaseResponse();
        res.setSuccess(true);
        res.setMessage("取消订单成功");
        return res;
    }

    private PatientAdvisoryInfo findByOrder(String orderNum) {
        return patientAdvisoryInfoMapper.findByOrder(orderNum);
    }

    private PatientAdvisoryInfo findById(Long id) {
        return patientAdvisoryInfoMapper.findById(id);
    }


    /**
     * 云币中心获取云币
     *
     * @param userId 用户id
     *
     * @return 糖币余额
     */
    private Integer coinCount(Long userId) {
        CoinCenterPatientRequest request = new CoinCenterPatientRequest();
        request.setUserId(userId);
        request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
        Integer coin = 0;
        try {
            CoinCenterPatientResponse response = coinCenterPatientService.findPatientCoinCount(request);
            if (response != null && response.getCoinCount() != null) {
                coin = response.getCoinCount();
            }
            log.info("云币中心获取云币 结果，userId:{},{}", userId, coin);
        } catch (Exception e) {
            log.error("云币中心获取云币 异常，userId= {}", userId, e);
        }
        return coin;
    }

    @Override
    public List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> doctorIds) {
        log.info("批量查询医生咨询价格 请求开始，doctorId:{}", doctorIds);

        if (doctorIds == null || doctorIds.isEmpty()) {
            log.warn("批量查询医生咨询价格：参数为空！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }

//        return patientAdvisoryLogic.batchQueryAdvisoryPrice(doctorIds);
        return null;
    }
}
