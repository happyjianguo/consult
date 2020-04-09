//package com.jkys.consult.shine.service;
//
//import static com.jkys.consult.common.component.CodeMsg.COIN_EXIST;
//import static com.jkys.consult.common.component.CodeMsg.INVALID_ORDER;
//import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
//import static com.jkys.consult.common.component.ExceptionMessage.COIN_FAIL;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jkys.coincenter.enums.SourceEnum;
//import com.jkys.coincenter.rpc.CoinCenterPatientService;
//import com.jkys.coincenter.rpc.request.CoinCenterPatientRequest;
//import com.jkys.coincenter.rpc.response.CoinCenterPatientResponse;
//import com.jkys.coincenter.rpc.response.CoinRechargeResponse;
//import com.jkys.consult.common.bean.PatientAdvisoryInfo;
//import com.jkys.consult.exception.ServerException;
//import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
//import com.jkys.consult.service.shine.PatientAdvisoryService;
//import com.jkys.consult.shine.bean.CoinOrderModel;
//import com.jkys.consult.shine.bean.PatientAdvisoryOrder;
//import com.jkys.consult.shine.constants.Constants;
//import com.jkys.consult.shine.mapper.PatientAdvisoryInfoMapper;
//import com.jkys.consult.shine.mapper.PatientAdvisoryOrderMapper;
//import com.jkys.consult.shine.request.AdvisoryRequest;
//import com.jkys.consult.statemachine.enums.OrderStatus;
//import com.jkys.phobos.ApiGateway;
//import com.jkys.pt.client.service.member.MemberAdvisoryRpcService;
//import javax.annotation.Resource;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author yangZh
// * @since 2018/7/13
// **/
//public class PatientAdvisoryServiceImpl implements PatientAdvisoryService {
//    private Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Resource
//    private PatientAdvisoryInfoMapper patientAdvisoryInfoMapper;
//    @Resource
//    private PatientAdvisoryOrderMapper patientAdvisoryOrderMapper;
//    @Resource
//    private ChatMessageService chatMessageService;
//    @Resource
//    private CoinCenterPatientService coinCenterPatientService;
//    @Resource
//    private PatientAdvisoryLogic patientAdvisoryLogic;
//    @Resource
//    private RedisService redisService;
//    @Resource
//    private MqServer mqServer;
//    @Resource
//    private UserCenterUserService userCenterUserService;
////    @Resource
////    private PrescriptionDataRpcService prescriptionDataRpcService;
////    @Resource
////    private SpecialAdvisoryService specialAdvisoryService;
//    @Resource
//    private MemberAdvisoryRpcService memberAdvisoryRpcService;
//
//    private static final Integer REDIS_TIMEOUT = 10;//重复发起支付间隔 10秒
//    private static final Long PAY_TIMEOUT = 1000L; //自循环间隔时间 毫秒
//
//    /**
//     * 可重入方法,无事务，多次支付优先查找是否已支付糖币成功
//     *
//     * @param request 使用 医生id  病人id
//     */
//    @Override
//    public CoinOrderModel payGo(AdvisoryRequest request) {
///*
//        Long patientId = ApiGateway.getUserId();
//        log.info("确认支付 请求开始，patientId:{},doctorId:{}", patientId, request.getDoctorId());
//        if (request.getDoctorId() == null || patientId == null) {
//            log.warn("咨询订单确认支付：参数异常！user:{}", patientId);
//            throw new ServerException(SERVER_ERROR, String.format("咨询订单确认支付：参数异常！user:%s", patientId));
//        }
//
//        if (request.getPatientId() != null && !patientId.equals(request.getPatientId())) {
//            throw new ServerException(INVALID_USER_STATUS);
//        }
//        //支付频率
//        if (redisService.get(patientId.toString()) != null && !request.getReTry()) {
//            throw new ServerException(SERVER_ERROR, ORDER_PAY_BUSY);
//        }
//        redisService.set(patientId.toString(), patientId.toString(), REDIS_TIMEOUT);
//        // TODO ---- 查找咨询单 ------> todoByliming
//        PatientAdvisoryInfo info = patientAdvisoryInfoMapper.findByDocAndPatient(request.getDoctorId(), patientId);
//        if (info == null) {
//            throw new ServerException(SERVER_ERROR, INVALID_ADVISORY);
//        }
////        Integer price = patientAdvisoryLogic.doctorPrice(request.getDoctorId());
//        CoinOrderModel model = new CoinOrderModel();
//        boolean success = false;
//        PatientAdvisoryOrder order = null;
////        synchronized (request.getDoctorId()) {
//        try {
//            String orderNum;
//            orderNum = info.getOrderNum();
//            // TODO ---- 判断咨询单状态 ------> todoByliming
//            if (!info.getStatus().equals(AdvisoryStatus.INIT.getCode())) {
//                throw new ServerException(SERVER_ERROR, USING_ADVISORY);
//            }
//            // TODO ---- 修改 ------> todoByliming
//            order = patientAdvisoryOrderMapper.findLastPayOrder(orderNum, OrderType.PAY.name());
//            if (order != null) { //3种情况 已支付，支付失败，未发起支付（同订单再发起）
//                model.setAmount(order.getAmount());
//                // TODO ---- 判断咨询单状态 ------> todoByliming
//                if (order.getStatus() > OrderStatus.PAY_FAIL.getCode()) {
//                    throw new ServerException(SERVER_ERROR, ORDER_BACK);
//                }
//                // TODO ---- 支付成功更新计时时间 ------> todoByliming
//                if (order.getStatus() == OrderStatus.PAYED.getCode()) {//已支付成功过
//                    long expire = System.currentTimeMillis() + Constants.EXPIRE_TIME;
//                    patientAdvisoryInfoMapper.updateOrderStatusStart(info.getId(), expire);
//                    model.setBizCode(order.getBizCode());
//                    model.setId(order.getId());
//                    model.setCoinPay(1);
//                    model.setSuccess(true);
//                    return model;
//                }
//            } else {//无支付订单，新生成
//                log.info("确认支付 订单生成，orderNum：{},patientId:{},doctorId:{}", orderNum, patientId, request.getDoctorId());
//
//                order = patientAdvisoryLogic.initOrder(request.getDoctorId(), patientId, orderNum,
//                        OrderStatus.INIT.getCode(), OrderType.PAY.name());
//
//            }
////            if (order == null) {
////                throw new ShineServiceException(ExceptionCode.TRY_AGAIN);
////            }
//*/
//        // TODO ---- 查找咨询单 ------> todoByliming
//        Long patientId = ApiGateway.getUserId();
//        PatientAdvisoryOrder order = null;
//
//            Integer price = order.getAmount();
//            Integer memberFree = 0;
//            if (userCenterUserService.isMember(patientId)) {
//                memberFree = memberAdvisoryRpcService.getFreeAdvisoryByDocId(patientId, request.getDoctorId());
//                if (memberFree != null && memberFree > 0) {
//                    price = 0;
//                }
//            }
//        CoinOrderModel model = new CoinOrderModel();
//        boolean success = false;
//            model.setAmount(price);
//            int times = 0;
//            while (times < 3) {
//                times++;
//                //处理云币余额扣款
//                Integer coin = coinCount(patientId);
//                model.setCoin(coin);
//                if (coin < price) {
//                    model.setCoinPay(2);//余额不足
//                }
//                if (coin >= price) {
//                    if (!patientAdvisoryLogic.isDecreasePatientCoin(order, price)) {
//                        throw new ServerException(SERVER_ERROR, COIN_FAIL);
//                    }
//                    model.setCoinPay(1);
//                    success = true;
//                    model.setCoin(coin - price);
//                    break;
//                }
//                if (!request.getReTry()) {
//                    break;
//                }
//                try {
//                    Thread.sleep(PAY_TIMEOUT);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        PatientAdvisoryInfo info = null;
//        //        PatientAdvisoryInfo info = patientAdvisoryInfoMapper.findByDocAndPatient(request.getDoctorId(), patientId);
//        if (success) {
//                if (memberFree != null && memberFree > 0) {
//                    boolean member = memberAdvisoryRpcService.addFreeAdvisory(info.getId(), patientId,
//                            info.getDoctorId(), request.getClient());
//                    if (!member) {
//                        throw new ServerException(SERVER_ERROR, "请检查您的会员咨询权限或退出重试!");
//                    }
//                }
//                patientAdvisoryInfoMapper.updateOrderStatusStart(info.getId(),
//                        System.currentTimeMillis() + Constants.EXPIRE_TIME);
////                patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.PAY_SUCCESS.getCode(), order.getBizCode());
//
//
//                chatMessageService.sendOrderMessage(info.getPatientId(), info.getDoctorId(), info.getId());
//                model.setHasSent(Boolean.TRUE);
//
//            }
////        } catch (ShineServiceException s) {
////            log.warn("确认支付 发生错误，doctorId:{},patientId:{},{}", request.getDoctorId(), patientId, s.getMessage());
////            throw s;
////        } catch (Exception e) {
////            log.error("确认支付 订单异常，patient= {}", patientId, e);
////            throw new ShineServiceException(ExceptionCode.FAIL);
////        } finally {
////            if (model.getCoinPay() == 0 && order != null) {
////                log.info("确认支付 支付失败，doctorId:{},patientId:{},orderNum:{}", request.getDoctorId(), patientId,
////                        order.getOrderNum());
////                patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.PAY_FAIL.getCode(), order.getBizCode());
////
////            }
////        }
////        }
//        model.setId(order.getId());
//        model.setOrderId(order.getId());
//        model.setInfoId(info.getId());
//        model.setBizCode(order.getBizCode());
//        model.setSuccess(true);
//        log.info("确认支付 返回结果，result:{}", JSONObject.toJSONString(model));
//        return model;
//    }
//
////    @Override
////    public BaseResponse finishAdvisoryByGroup(AdvisoryRequest request) {
////        log.info("更新咨询会话结束 请求开始，imGroupId:{},patientId:{}", request.getImGroupId(), request.getPatientId());
////        Long userId = ApiGateway.getUserId();
////        if (userId == null) {
////            log.warn("更新咨询会话结束：参数异常！");
////            throw new ServerException(SERVER_ERROR, "更新咨询会话结束：参数异常！");
////        }
////        BaseUserInfo user = userCenterUserService.findUserById(userId);
////        if (user == null) {
////            throw new ServerException(USER_NOT_EXIST);
////        }
////        // TODO ---- 怎么判断的 ------> todoByliming
////        boolean isDoctor = false;//是否为医生结束
////        if (user.getUtype().equals(UserTypeEnum.DOCTOR.getValue())) {
////            isDoctor = true;
////        }
////
////        Long patientId = request.getPatientId();
////        if (patientId != null && patientId == 0 && !isDoctor) {//微信是病人发起的结束，只有token
////            patientId = userId;
////        }
////
////        boolean patientAdvisory = false;//如果是掌糖的付费咨询（客户端要求兼容下）
////        if (patientId != null) {
////            BaseUserInfo patient = userCenterUserService.findUserById(patientId);
////            if (patient != null && patient.getUtype().equals(UserTypeEnum.PATIENT.getValue())) {
////                patientAdvisory = true;
////            }
////        }
////
////        AdvisoryQuery query = new AdvisoryQuery();
////        if (patientAdvisory && isDoctor) {//如果是掌糖的付费咨询且是医生发起
////            query.setDoctorId(userId);
////            query.setPatientId(patientId);
////        }/* else {//药店的咨询
////            if (request.getImGroupId() == null || (isDoctor && request.getImGroupId() < 0)) {
////                throw new ShineServiceException(ExceptionCode.PARAM_INVALID);
////            }
////            query.setImGroupId(request.getImGroupId());
////            if (isDoctor) {
////                query.setDoctorId(userId);
////            } else {
////                query.setPatientId(userId);
////            }
////
////        }*/
////        PatientAdvisoryInfo advisory = patientAdvisoryInfoMapper.findByQuery(query);
////
//////        stepOver(userId, advisory, isDoctor, patientAdvisory);
////        return new BaseResponse();
////    }
//
//
////    private void stepOver(Long userId, PatientAdvisoryInfo advisory, boolean isDoctor, boolean patientAdvisory) {
////
////        if (!patientAdvisory && isStoreAdvisoryOver(advisory, isDoctor)) {
////            log.info("药店咨询结束请求：userId:{}", userId);
////            return;
////        }
////
////        if (advisory == null || advisory.getStartTime() == null) {
////            log.warn("更新咨询会话结束：没有可结束的咨询，请勿重复点击 user:{}", userId);
////            throw new ShineServiceException(ExceptionCode.OVER_ADVISORY);
////        }
////
////        if (!userId.equals(advisory.getDoctorId()) && !userId.equals(advisory.getPatientId())) {
////            log.warn("用户无权结束咨询：userId:{}", userId);
////            throw new ShineServiceException(ExceptionCode.OVER_ADVISORY);
////        }
////
////        isPatientAdvisoryOver(advisory);
////        // TODO ---- 这是什么，给谁发消息 ------> todoByliming
////        mqServer.sendFinishMsg(advisory, isDoctor);
////    }
//
//
////    private void isPatientAdvisoryOver(PatientAdvisoryInfo advisory) {
////        if (advisory.getAdvisoryType() != AdvisoryType.PATIENT.getCode()) {//判定为非付费咨询的，出去
////            throw new ShineServiceException("非付费咨询会话，请检查");
////        }
////        if (patientAdvisoryInfoMapper.updateOrderStatusOver(AdvisoryStatus.OVER.getCode(), advisory.getDoctorId(),
////                advisory.getPatientId()) <=
////                0) {
////            throw new ShineServiceException(ExceptionCode.FAIL);
////            throw new ServerException(SERVER_ERROR, "咨询订单用户云币查询：参数异常！");
////        }
////        //最后做im消息通知
////        // TODO ---- 可用 ------> todoByliming
////        chatMessageService.sendFinishAdvisoryMessage(advisory.getDoctorId(), advisory.getPatientId(),
////                advisory.getSource() != null && advisory.getSource().equals(AdvisoryResource.MEDIC_SALE.name())
////                        ? Constants.FINISH_TEXT_24 : Constants.FINISH_TEXT, MessageType.Finish.name());
////    }
//
//    @Override
//    public CoinOrderModel queryCoin(Long userId, Long doctorId) {
//        log.info("咨询订单用户云币查询查询 请求开始，patientId:{},doctorId:{}", userId, doctorId);
//        Long patientId = ApiGateway.getUserId();
//        if (doctorId == null || patientId == null) {
//            log.warn("咨询订单用户云币查询查询：参数异常！");
//            throw new ServerException(SERVER_ERROR, "咨询订单用户云币查询：参数异常！");
//        }
//        CoinOrderModel model = new CoinOrderModel();
//        try {
//            //云币中心获取云币余额
//            model.setCoin(coinCount(patientId));
//            model.setPrice(patientAdvisoryLogic.doctorPrice(doctorId));
//            model.setOriginalCoin(patientAdvisoryLogic.doctorOrigin(doctorId));
//            model.setSuccess(true);
//        } catch (Exception e) {
////            throw new ShineServiceException(ExceptionCode.FAIL);
//            throw new ServerException(SERVER_ERROR, String.format("咨询订单用户云币查询：%s", e.getMessage()));
//        }
//        return model;
//    }
//
//
//    @Override
//    public CoinOrderModel payString(String bizCode, int payWay) {
//        log.info("咨询订单获取支付串：orderNum={}，payWay={}", bizCode, payWay);
//        if (StringUtils.isEmpty(bizCode) || ApiGateway.getUserId() == null) {
//            log.warn("咨询订单支付糖币：参数异常！");
//            throw new ServerException(SERVER_ERROR, "咨询订单支付糖币：参数异常！");
//        }
//        CoinOrderModel model = new CoinOrderModel();
//        model.setSuccess(false);
////        try {
//            // TODO ---- PatientAdvisoryOrder换成自己的对象 ------> todoByliming
//            PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findByBizCode(bizCode);
//            // TODO ---- 支付中 ------> todoByliming
//            if (order == null || order.getStatus() > OrderStatus.PAYING.getCode() ||
//                    !ApiGateway.getUserId().equals(order.getPatientId())) {
//                throw new ServerException(INVALID_ORDER);
//            }
//            // 云币中心获取余额
//            Integer coin = coinCount(order.getPatientId());
////            Integer price = patientAdvisoryLogic.doctorPrice(order.getDoctorId());
//            if (coin >= order.getAmount()) {
//                // 再看看是否这么处理
//                throw new ServerException(COIN_EXIST);
//            }
//            Integer cost = order.getAmount() - coin;
//            CoinRechargeResponse response;
//            if (OrderStatus.PAYING.getCode() == order.getStatus() &&
//                    !patientAdvisoryLogic.isPaySuccess(order.getOrderNum())) {
//                log.info("使用原来的订单号和支付方式，调用云币中心的接口，从第三方重新获取payString：{}", bizCode);
//                response = patientAdvisoryLogic.reacquirePayString(order, payWay);
//            } else {
//                response = patientAdvisoryLogic.coinRecharge(order, cost, payWay);
//                // TODO ---- 更新状态 ------> todoByliming
//                patientAdvisoryOrderMapper.updateOrderPaying(cost, bizCode, response.getPayString());
//            }
//            model.setBizCode(bizCode);
//            model.setPayString(response.getPayString());
//            model.setSuccess(true);
//        /*} catch (ShineServiceException s) {
//            log.warn("咨询订单获取支付串 业务异常，bizCode:{},{}", bizCode, s.getMessage());
//            throw new ShineServiceException(Integer.valueOf(s.getCode()), s.getMessage());
//        } catch (Exception e) {
//            log.error("咨询订单获取支付串 异常，bizCode {}", bizCode, e);
//            throw new ShineServiceException(ExceptionCode.FAIL);
//        }*/
//
//        return model;
//    }
//
//    /**
//     * 云币中心获取云币
//     *
//     * @param userId 用户id
//     * @return 糖币余额
//     */
//    private Integer coinCount(Long userId) {
//        CoinCenterPatientRequest request = new CoinCenterPatientRequest();
//        request.setUserId(userId);
//        request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
//        Integer coin = 0;
//        try {
//            CoinCenterPatientResponse response = coinCenterPatientService.findPatientCoinCount(request);
//            if (response != null && response.getCoinCount() != null) {
//                coin = response.getCoinCount();
//            }
//            log.info("云币中心获取云币 结果，userId:{},{}", userId, coin);
//        } catch (Exception e) {
//            log.error("云币中心获取云币 异常，userId= {}", userId, e);
//        }
//        return coin;
//    }
//
////    @Override
////    public List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> doctorIds) {
////        log.info("批量查询医生咨询价格 请求开始，doctorId:{}", doctorIds);
////
////        if (doctorIds == null || doctorIds.isEmpty()) {
////            log.warn("批量查询医生咨询价格：参数为空！");
////            throw new ServerException(SERVER_ERROR, "批量查询医生咨询价格：参数为空！");
////        }
////
////        return patientAdvisoryLogic.batchQueryAdvisoryPrice(doctorIds);
////    }
//}
