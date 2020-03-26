package com.jkys.consult.referTo;

import com.jkys.coincenter.enums.SourceEnum;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.request.QueryOrderPayResultRequest;
import com.jkys.coincenter.rpc.response.QueryOrderPayResultModel;
import com.jkys.coincenter.rpc.response.QueryOrderPayResultResponse;
import com.jkys.consult.referTo.bean.AdvisoryStatus;
import com.jkys.consult.referTo.bean.OrderType;
import com.jkys.consult.referTo.bean.advisory.AdvisoryQuery;
import com.jkys.consult.referTo.bean.advisory.AdvisoryQueryModel;
import com.jkys.consult.referTo.bean.advisory.AdvisoryUserInfoModel;
import com.jkys.consult.referTo.bean.advisory.ConsultModel;
import com.jkys.consult.referTo.bean.advisory.HistoryDescModel;
import com.jkys.consult.referTo.bean.request.AdvisoryRequest;
import com.jkys.consult.referTo.bean.response.BaseResponse;
import com.jkys.consult.referTo.bean.response.PageResult;
import com.jkys.phobos.ApiGateway;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.uenum.UserTypeEnum;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author yangZh
 * @since 2018/7/23
 **/
public class PatientAdvisoryInfoServiceImpl implements PatientAdvisoryInfoService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PatientAdvisoryInfoMapper patientAdvisoryInfoMapper;
    @Resource
    private UserCenterUserService userCenterUserService;
    @Resource
    private PatientAdvisoryLogic patientAdvisoryLogic;
//    @Resource
//    private AppConfig appConfig;
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private CoinCenterPatientService coinCenterPatientService;
//    @Resource
//    private BodyMapper bodyMapper;
//    @Resource
//    private UserDiseaseMapper userDiseaseMapper;
    @Resource
    private PatientAdvisoryOrderMapper patientAdvisoryOrderMapper;

    private static final Long DELAY = 2 * 60 * 1000L;//2分钟延迟
    private static final Long FREE_TIME = 47 * 60 * 60 * 1000L - DELAY;//免费咨询聊天时间范围

    public PageResult<AdvisoryQueryModel> queryUserInfoList(AdvisoryQuery query) {
        PageResult<AdvisoryQueryModel> result = new PageResult<>();
        if (query == null) {
            return result;
        }
        Integer start = (query.getPageNo() - 1) * query.getPageSize();
        List<AdvisoryQueryModel> infoList = patientAdvisoryInfoMapper.queryUserInfoList(query, start,
                query.getPageSize());
        if (infoList == null || infoList.isEmpty()) {
            return result;
        }
        List<String> orderNumList = infoList.stream().map(AdvisoryQueryModel::getOrderNum).collect(Collectors.toList());
        List<PatientAdvisoryOrder> orders= patientAdvisoryOrderMapper.queryOrdersByOrderNums(orderNumList);
        for(AdvisoryQueryModel info :infoList){
            List<PatientAdvisoryOrder> matchOrder= orders.stream().filter(s->s.getOrderNum().equals(info.getOrderNum())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(matchOrder)){
                continue;
            }
            info.setAmount(matchOrder.get(0).getAmount());
            info.setCostCoin(matchOrder.get(0).getCostCoin());
        }

        Long count = patientAdvisoryInfoMapper.countUserInfoList(query);
        QueryOrderPayResultRequest request = new QueryOrderPayResultRequest();
        request.setSource(SourceEnum.ORDER_ADVISORY.getSource());


        request.setOrderIdList(orderNumList);
        QueryOrderPayResultResponse response = coinCenterPatientService.queryOrderPayResult(request);

        Map<String, QueryOrderPayResultModel> payMap = new HashMap<>();
        if (response != null && response.getSuccess() && response.getPayList() != null &&
                !response.getPayList().isEmpty()) {
            payMap = response.getPayList().stream().collect(Collectors.toMap(QueryOrderPayResultModel::getOrderId,
                    (p) -> p));
        }
        //分页20行了，UC是缓存的，性能无问题，循环内取
        for (AdvisoryQueryModel model : infoList) {
            BaseUserInfo doc = userCenterUserService.findUserById(model.getDoctorId());
            if (doc != null) {
                model.setDoctorMobile(doc.getMobile());
                model.setDoctorName(doc.getName());
            }

            BaseUserInfo patient = userCenterUserService.findUserById(model.getPatientId());
            if (patient != null) {
                model.setPatientMobile(patient.getMobile());
                model.setPatientName(patient.getName());
            }
            if (payMap != null && !payMap.isEmpty() && payMap.get(model.getOrderNum()) != null) {
                model.setPayAmount(payMap.get(model.getOrderNum()).getCoinCount());
            }
        }
        result.setData(infoList);
        result.setTotalCount(count.intValue());
        return result;
    }

    public Boolean updateUserInfo(AdvisoryUserInfoModel model) {
        PatientAdvisoryInfo advisory = new PatientAdvisoryInfo();
        advisory.setMemo(model.getMemo());
        advisory.setId(model.getId());
        advisory.setStatus(model.getStatus());
        return patientAdvisoryInfoMapper.updateOrderUserInfo(advisory) > 0;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponse finishInfo(AdvisoryUserInfoModel req) {
        Long userId = ApiGateway.getUserId();
        if (req.getDoctorId() == null || userId == null  ||
                (req.getPatientId() != null && !req.getPatientId().equals(userId))) {
            log.warn("完善咨询订单用户信息：参数异常,字段必填！，user:{}",userId);
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        req.setPatientId(userId);
        PatientAdvisoryInfo advisory = patientAdvisoryInfoMapper.findByDocAndPatient(req.getDoctorId(),
                req.getPatientId());

        BaseResponse res = new BaseResponse();
        if (advisory != null && advisory.getStatus().equals(AdvisoryStatus.BEGIN.getCode())) {
            log.warn("完善咨询订单用户信息：该咨询进行中,只有结束状态才能新建，user:{},status:{}",userId,advisory.getStatus());
            res.setId(advisory.getId());
            res.setSuccess(true);
            return  res;
        }
        PatientAdvisoryInfo info = new PatientAdvisoryInfo();
        if (req.getImgUrl() != null) {
            StringBuilder urls = new StringBuilder();
            for (int i = 0; i < req.getImgUrl().length; i++) {
                if (i > 0) {
                    urls.append(";");
                }
                urls.append(req.getImgUrl()[i]);
            }
            info.setImgUrl(urls.toString());
        }
        info.setDoctorId(req.getDoctorId());
        info.setPatientId(req.getPatientId());
        info.setPatientName(req.getPatientName());
        info.setDescribes(req.getDescribes()!=null&&req.getDescribes().length()>450?req.getDescribes().substring(0,400):req.getDescribes());
        info.setAge(req.getAge());
        info.setSex(req.getSex());
        info.setWeight(req.getWeight());
        info.setHeight(req.getHeight());
        // 基本信息
        info.setCountry(req.getCountry());
        info.setProvince(req.getProvince());
        info.setCity(req.getCity());
        info.setCounty(req.getCounty());
        info.setCard(req.getCard());
        info.setDistrictCode(req.getDistrictCode());
        // 现病史、过往史、过敏史
        info.setPastHistory(req.getPastHistory());
        info.setPresentIllness(req.getPresentIllness());
        info.setAllergicHistory(req.getAllergicHistory());
        info.setStatus(AdvisoryStatus.INIT.getCode());
//        if (userCenterUserService.isMember(userId)) {
//            info.setSource(AdvisoryResource.MEMBER.name());
//        }else {
//            info.setSource(AdvisoryResource.IN_APP.name());
//        }
//        info.setAdvisoryType(AdvisoryType.PATIENT.getCode());
//        UserDisease userDisease = userDiseaseMapper.findActiveUser(req.getPatientId());
//        if (userDisease != null) {
//            info.setDiseaseCategory(userDisease.getDiseaseName());
//        }
//        Body body = bodyMapper.findByUserId(req.getPatientId());
//        if (body != null) {
//            info.setDiabetesType(body.getDiabetesType());
//        }
        if (advisory == null) {
            String orderNum = patientAdvisoryLogic.genOrderNum(OrderType.PAY.name());
            info.setOrderNum(orderNum);
            if (patientAdvisoryInfoMapper.initInfo(info) <= 0) {
                log.warn("完善咨询订单用户信息：信息录入失败，user:{}",userId);
//                throw new ShineServiceException(ExceptionCode.FAIL);
            }
            patientAdvisoryLogic.initOrder(req.getDoctorId(), req.getPatientId(), orderNum,
                    OrderStatus.INIT.getCode(), OrderType.PAY.name());

        } else {
            patientAdvisoryInfoMapper.updateOrderUserInfo(info);
            PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findLastPayOrder(info.getOrderNum(),
                    OrderType.PAY.name());
            if (order != null) {
                PatientAdvisoryOrder newOrder = new PatientAdvisoryOrder();
                Integer price = patientAdvisoryLogic.doctorPrice(order.getDoctorId());
                newOrder.setCostCoin(price);
                newOrder.setAmount(price);
                newOrder.setChargeCoin(0L);
//                newOrder.setId(order.getId());
                patientAdvisoryOrderMapper.updateOrderInfo(newOrder);
            }
        }
        res.setId(info.getId());
        res.setSuccess(true);
        return res;
    }

    //TODO 老接口方法
//    public BaseResponse finishInfoOld(AdvisoryUserInfoModel req) {
//        if (req.getDoctorId() == null || req.getPatientId() == null) {
//            log.error("完善咨询订单用户信息：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
//        }
//        PatientAdvisoryInfo advisory = patientAdvisoryInfoMapper.findByDocAndPatient(req.getDoctorId(), req
//                .getPatientId());
//        if (advisory == null) {
//            log.error("完善咨询订单用户信息：无效的订单");
//            throw new ShineServiceException(ExceptionCode.INVALID_ADVISORY);
//        }
//        BaseResponse res = new BaseResponse();
//        if (req.getImgUrl() != null) {
//            StringBuilder urls = new StringBuilder();
//            for (int i = 0; i < req.getImgUrl().length; i++) {
//                if (i > 0) {
//                    urls.append(";");
//                }
//                urls.append(req.getImgUrl()[i]);
//            }
//            advisory.setImgUrl(urls.toString());
//        }
//        advisory.setPatientName(req.getPatientName());
//        advisory.setDescribes(EjiUtil.trimEji(req.getDescribes()));
//        advisory.setAge(req.getAge());
//        advisory.setSex(req.getSex());
//        advisory.setWeight(req.getWeight());
//        advisory.setHeight(req.getHeight());
//        advisory.setStartTime(new Date());
//        long current = System.currentTimeMillis() + Constants.EXPIRE_TIME;
//        advisory.setExpireTime(current);
//        if (patientAdvisoryInfoMapper.updateOrderUserInfo(advisory) <= 0) {
//            log.error("完善咨询订单用户信息：信息录入失败");
//            throw new ShineServiceException(ExceptionCode.FAIL);
//        }
//        res.setId(advisory.getId());
//        res.setSuccess(true);
//        return res;
//    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ConsultModel findLastOrderStatus(AdvisoryRequest request) {
        if (request.getDoctorId() == null || request.getPatientId() == null) {
            log.warn("咨询订单状态查询：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        Long owner = ApiGateway.getUserId();
        BaseUserInfo ownerUser = userCenterUserService.findUserById(owner);
        ConsultModel model = new ConsultModel();
        BaseUserInfo userInfo = userCenterUserService.findUserById(request.getPatientId());
        if (ownerUser !=null && !ownerUser.getUtype().equals(UserTypeEnum.DOCTOR.getValue())  && userInfo != null && !userInfo.getUtype().equals(
            UserTypeEnum.PATIENT.getValue())) {
//            throw new ShineServiceException("非法用户，请检查");
        }

//        if (request.getDoctorId().equals(appConfig.getInsideDoctor()) ||
//                request.getDoctorId().equals(appConfig.getCustomer())) {
//            return free(model, request.getDoctorId(), request.getPatientId());
//        }

        // 咨询VIP控糖顾问或vip客服时，判断用户是否为VIP
//        if ((request.getDoctorId().equals(appConfig.getVipConsultant()) ||
//                request.getDoctorId().equals(appConfig.getVipCustomerServiceId()) ) && isVipUser(owner)){
//            return free(model, request.getDoctorId(), request.getPatientId());
//        }
//
//        // 董斌需求：为抗击疫情，为用户免费问诊
//        if (request.getDoctorId().equals(appConfig.getFreeDoctor())){
//            return free(model, request.getDoctorId(), request.getPatientId());
//        }

        Integer price = patientAdvisoryLogic.doctorPrice(request.getDoctorId());
        model.setCoin(price);

//        model.setOriginalCoin(patientAdvisoryLogic.doctorOrigin(request.getDoctorId()));


        try {
            PatientAdvisoryInfo info;
            Long time = chatMessageService.queryLastMessageTimeStamp(request.getDoctorId(), request.getPatientId());
//            if (SpecialDoctorHandler.isSpecialDoctor(request.getDoctorId())) {
//                info = special(request.getDoctorId(), request.getPatientId(), time);
//            } else {
                info = patientAdvisoryInfoMapper.findByDocAndPatient(request.getDoctorId(), request.getPatientId());
                if (info == null) {//没有咨询中的，去Im看查有没医生发起的聊天
                    PatientAdvisoryInfo lastInfo =
                            patientAdvisoryInfoMapper.findLastInfoByStatus(Collections.singletonList(request.getDoctorId()), request.getPatientId(), AdvisoryStatus.OVER.getCode());
                    Long outHour = System.currentTimeMillis() - FREE_TIME; //倒退47小时58分钟，2分钟空余防止咨询中消息未达
                    //48小时内有医生发消息的，最后次结束聊天后医生发起了聊天或者完全无聊天的  开启免费咨询
                    if (time > outHour) {
                        if (lastInfo == null ||
                                time - DELAY > (lastInfo.getEndTime() == null ? 0L : lastInfo.getEndTime().getTime())) {
                            info = patientAdvisoryLogic.initFreeFinishInfo(request.getDoctorId(),
                                    request.getPatientId(), time);
                        }
                    }
                }
//            }

            if (info != null) {
                model.setId(info.getId());
                model.setDoctorId(info.getDoctorId());
                model.setPatientId(info.getPatientId());
                model.setStartTime(info.getStartTime() == null ? null : info.getStartTime().getTime());
                model.setEndTime(info.getEndTime() == null ? null : info.getEndTime().getTime());
                Integer status = info.getStatus();
                //已进入咨询，咨询进行中
                model.setTalking(status.equals(AdvisoryStatus.BEGIN.getCode()) && info.getStartTime() != null);
                //此单据是否已支付过
                if (status.equals(AdvisoryStatus.BEGIN.getCode()) || status.equals(AdvisoryStatus.OVER.getCode())) {
                    model.setPay(true);
                }
                //存在时间视为已完成资料录入
                if (StringUtils.isNotEmpty(info.getDescribes()) || info.getOrderNum().contains(OrderType.FREE.name())) {
                    model.setFinishUserInfo(true);
                }
                //为了区分信息录入的过度状态，包含客服或控糖顾问可直接聊
//                if (status.equals(AdvisoryStatus.BEGIN.getCode()) ||
//                        request.getDoctorId().equals(appConfig.getInsideDoctor()) ||
//                        request.getDoctorId().equals(appConfig.getCustomer())) {
//                    model.setConsult(true);
//                }

//                if (!SpecialDoctorHandler.isSpecialDoctor(request.getDoctorId()) && model.getStartTime() != null &&
//                        (time == 0 || time < model.getStartTime()) &&
//                        System.currentTimeMillis() - model.getStartTime() >= Constants.NOREPEAT_TIME) {
//                    model.setNoRepeat(true);
//                }

            }
            model.setSuccess(true);
        } catch (Exception e) {
            log.error("咨询订单状态查询：发送系统异常！", e);
        }
        return model;
    }

    private boolean isVipUser(Long owner) {
        Map<String, Object> user = userCenterUserService.findExtraUserById(owner);
        if (user != null) {
            Object memberLevel = user.get("memberLevel");
            // 0 代表非会员
            return Objects.nonNull(memberLevel) && !"0".equals(memberLevel);
        }
        return false;
    }


    /**
     * 特殊医生组控制
     */
//    private PatientAdvisoryInfo special(Long doctorId, Long patientId, Long time) {
//        log.info("特殊医生组，doctorId：{}，patientId:{}",doctorId,patientId);
//        PatientAdvisoryInfo info =
////                patientAdvisoryInfoMapper.findByDoctorsAndPatient(SpecialDoctorHandler.allSpecialDoctor(), patientId);
//        if (info == null) {//没有咨询中的，去Im看查有没医生发起的聊天
//            PatientAdvisoryInfo lastInfo =
//                    patientAdvisoryInfoMapper.findLastInfoByStatus(SpecialDoctorHandler.allSpecialDoctor(), patientId
//                            , AdvisoryStatus.OVER.getCode());
//            Long outHour = System.currentTimeMillis() - FREE_TIME; //倒退47小时58分钟，2分钟空余防止咨询中消息未达
//            //48小时内有医生发消息的，最后次结束聊天后医生发起了聊天或者完全无聊天的  开启免费咨询
//            if (time > outHour) {
//                if (lastInfo == null ||
//                        time - DELAY > (lastInfo.getEndTime() == null ? 0L : lastInfo.getEndTime().getTime())) {
//                    info = patientAdvisoryLogic.initFreeFinishInfo(doctorId, patientId, time);
//                }
//            }
//        }
//        return info;
//    }

    @Override
    public AdvisoryUserInfoModel findOne(Long id) {
        if (id == null) {
            log.warn("咨询订单查询：参数异常！");
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        PatientAdvisoryInfo advisory = patientAdvisoryInfoMapper.findById(id);
        if (advisory == null) {
//            throw new ShineServiceException(ExceptionCode.INVALID_ADVISORY);
        }

        AdvisoryUserInfoModel req = new AdvisoryUserInfoModel();
        BeanUtils.copyProperties(advisory, req);
        if (StringUtils.isNotEmpty(advisory.getImgUrl())) {
            String[] urls = advisory.getImgUrl().split(";");
            req.setImgUrl(urls);
        }
        if (StringUtils.isEmpty(req.getPatientName()) || req.getAge() == null) {
            BaseUserInfo patient = userCenterUserService.findUserById(req.getPatientId());
            if (patient != null) {
                req.setPatientName(StringUtils.isEmpty(req.getPatientName()) ? patient.getName() :
                        req.getPatientName());
                req.setSex(req.getSex() != null ? req.getSex() : patient.getSex());
                if (patient.getBirthday() != null && req.getAge() ==null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
                    int age = Integer.parseInt(simpleDateFormat.format(new Date())) -
                            Integer.parseInt(simpleDateFormat.format(patient.getBirthday()));
                    req.setAge(req.getAge() != null ? req.getAge() : age);
                }
            }
        }

//        DiabetesTypeEnum diabetesTypeEnum = DiabetesTypeEnum.ofType(advisory.getDiabetesType());
//        if (diabetesTypeEnum != null) {
//            CodeText codeText = new CodeText();
//            codeText.setCode(diabetesTypeEnum.getCode() + "");
//            codeText.setText(diabetesTypeEnum.getName());
//            req.setDiabetesType(codeText);
//        }

        PatientAdvisoryOrder patientAdvisoryOrder =
                patientAdvisoryOrderMapper.findByOrderAndType(advisory.getOrderNum(), OrderType.PAY.name());
        if (patientAdvisoryOrder != null) {
            req.setCostCoin(patientAdvisoryOrder.getCostCoin());
            req.setAmount(patientAdvisoryOrder.getAmount());
        }
        return req;
    }

    @Override
    public List<HistoryDescModel> describeList(Long uid) {
        Long userId = ApiGateway.getUserId();
        if (userId == null || (uid != null && !userId.equals(uid))) {
            log.warn("查询历史病史：参数异常！ user:{}",userId);
//            throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
        }
        return patientAdvisoryInfoMapper.queryDescribeList(userId);
    }

    @Override
    public ConsultModel queryLastInfo(Long doctorId, Long patientId, Integer status) {
        log.info("查询最新的咨询单{},{},{}", doctorId, patientId, status);
        if (doctorId == null || patientId == null || status == null) {
//            log.warn("查询最新的咨询单：参数异常！", ExceptionCode.PARAM_INVALID);
            return null;
        }
        ConsultModel model = new ConsultModel();
        PatientAdvisoryInfo info = patientAdvisoryInfoMapper.findLastInfoByStatus(Collections.singletonList(doctorId)
                , patientId, status);
        if (info == null) {
            log.warn("查询最新的咨询单：咨询单不存在！user:{}",patientId);
            return null;
        }
        model.setId(info.getId());
        model.setStatus(info.getStatus());
        return model;
    }

    @Override
    public List<Long> queryOnAdvisory(Long patientId) {
        if (patientId == null) {
            return null;
        }
        log.info("查询咨询中的医生：{}", patientId);
        return patientAdvisoryInfoMapper.queryOnAdvisory(patientId);
    }

    private PatientAdvisoryInfo findById(Long id) {
        return patientAdvisoryInfoMapper.findById(id);
    }

    private ConsultModel free(ConsultModel model, Long doctorId, Long patientId) {
        model.setDoctorId(doctorId);
        model.setPatientId(patientId);
        model.setPay(true);
        model.setFinishUserInfo(true);
        model.setConsult(true);
        model.setSuccess(true);
        model.setTalking(true);
        return model;
    }
}
