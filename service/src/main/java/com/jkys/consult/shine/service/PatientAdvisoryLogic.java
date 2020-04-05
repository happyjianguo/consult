package com.jkys.consult.shine.service;

import static com.jkys.consult.common.CodeMsg.ASK_PAY_FAIL;

import com.jkys.coincenter.enums.SourceEnum;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.request.CoinCenterPatientRequest;
import com.jkys.coincenter.rpc.request.CoinRechargeRequest;
import com.jkys.coincenter.rpc.request.QueryOrderPayResultRequest;
import com.jkys.coincenter.rpc.response.CoinCenterPatientResponse;
import com.jkys.coincenter.rpc.response.CoinRechargeResponse;
import com.jkys.coincenter.rpc.response.QueryOrderPayResultModel;
import com.jkys.coincenter.rpc.response.QueryOrderPayResultResponse;
import com.jkys.common.db.SequenceGenerator;
import com.jkys.consult.common.bean.PatientAdvisoryInfo;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.shine.bean.PatientAdvisoryOrder;
import com.jkys.consult.shine.mapper.DoctorAdvisoryPriceMapper;
import com.jkys.consult.shine.mapper.PatientAdvisoryInfoMapper;
import com.jkys.pt.client.service.member.MemberAdvisoryRpcService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import com.jkys.net.sms.client.bean.SmsSendTemplateReqDTO;
//import com.jkys.net.sms.client.exception.SmsException;
//import com.jkys.net.sms.client.service.SmsService;

/**
 * @author yangZh
 * @since 2018/7/19
 **/
@Component
public class PatientAdvisoryLogic {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AppConfig appConfig;
    @Resource
    private PatientAdvisoryInfoMapper patientAdvisoryInfoMapper;
//    @Resource
//    private SmsService smsService;
    @Resource
    private UserCenterUserService userCenterUserService;
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private CoinCenterPatientService coinCenterPatientService;
    @Resource
    private DoctorAdvisoryPriceMapper doctorAdvisoryPriceMapper;
    @Resource
    private SequenceGenerator sequenceGenerator;
    @Resource
    private MqServer mqServer;
    @Resource
    private MemberAdvisoryRpcService memberAdvisoryRpcService;

    private final static Object lock = "lock";


//    @Transactional(rollbackFor = Throwable.class)
//    public void finishAdvisoryExpire(PatientAdvisoryInfo info) {
//
//        if (info.getOrderNum().contains(OrderType.FREE.name())) {
//            patientAdvisoryInfoMapper.updateStatusById(info.getId(), AdvisoryStatus.OVER.getCode());
//            return; //免费咨询直接结束
//        }
//
//        PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findByOrderAndType(info.getOrderNum(),
//                OrderType.PAY.name());
//
//        Long time = chatMessageService.queryLastMessageTimeStamp(info.getDoctorId(), info.getPatientId());
//        //存在开始后聊天过的直接发消息返回
//        if (info.getStartTime() != null && time > info.getStartTime().getTime()) {
//            log.info("咨询任务：医生已回复结束：patient:{},number:{}", info.getPatientId(), info.getOrderNum());
//            patientAdvisoryInfoMapper.updateStatusById(info.getId(), AdvisoryStatus.OVER.getCode());
//            chatMessageService.sendFinishAdvisoryMessage(info.getDoctorId(), info.getPatientId(),
//                    Constants.FINISH_TEXT, MessageType.Finish.name());
//            mqServer.sendFinishMsg(info);
//            return;
//        }
//
//        //医生无回复的需要退云币
//        if (order.getStatus() == OrderStatus.PAY_SUCCESS.getCode()) {
//            log.info("咨询任务：自动结束退云币：patient:{},number:{}", info.getPatientId(), info.getOrderNum());
//            Boolean result = isRefund(info, order);
//            if (result) {
//                chatMessageService.sendFinishAdvisoryMessage(info.getDoctorId(), info.getPatientId(),
//                        Constants.AUTO_TEXT, MessageType.FinishNoReply.name());
//            }
//        }
//    }


//    public void finishAdvisorySimple(AdvisoryQueryModel info) {
//
//        if (info.getOrderNum().contains(OrderType.FREE.name()) &&
//                !info.getSource().equals(AdvisoryResource.MEDIC_SALE.name())) {
//            patientAdvisoryInfoMapper.updateStatusById(info.getId(), AdvisoryStatus.OVER.getCode());
//            return; //免费咨询直接结束
//        }
//
//        log.info("finishAdvisorySimple：咨询结束：patient:{},number:{}", info.getPatientId(), info.getOrderNum());
//        patientAdvisoryInfoMapper.updateStatusById(info.getId(), AdvisoryStatus.OVER.getCode());
//    }


//    public void cancelAdvisory(PatientAdvisoryInfo info) {
//        //没有支付单或未付款的取消订单
//        PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findByOrderAndType(info.getOrderNum(),
//                OrderType.PAY.name());
//        if (order == null || info.getStatus().equals(AdvisoryStatus.INIT.getCode())) {
//            log.info("咨询任务：取消订单：patient:{},number:{}", info.getPatientId(), info.getOrderNum());
//            patientAdvisoryInfoMapper.updateStatus(AdvisoryStatus.CANCEL.getCode(), info.getId());
//            return;
//        }
//        log.warn("咨询任务：取消订单 订单状态错误：patient:{},number:{},status:{}", info.getPatientId(), info.getOrderNum(),
//                info.getStatus());
//    }

    /**
     * 咨询单退款操作，生成退款单，云币新增
     *
     * @param info  咨询单
     * @param order 咨询支付单
     * @return true or false
     */
//    Boolean isRefund(PatientAdvisoryInfo info, PatientAdvisoryOrder order) {
//        patientAdvisoryInfoMapper.updateStatusById(info.getId(), AdvisoryStatus.REFUND.getCode());
//
//        MemberAdvisoryMsgVO memberVo = null;
//        Boolean res = false;
//        PatientAdvisoryOrder backOrder = new PatientAdvisoryOrder();
//        if (info.getSource().equals(AdvisoryResource.MEMBER.name())) {
//            memberVo = memberAdvisoryRpcService.getByAdvisoryInfoId(info.getId());
//            if (memberVo != null && memberVo.getStatus() != null && memberVo.getStatus().equals(1)) {
//                memberAdvisoryRpcService.advisoryFailure(info.getId());
//            }
//            res = true;
//        }
//        if (memberVo == null) {
//            backOrder = backOrderCoin(order);
//            res = isIncreaseCoin(backOrder);
//        }
//        if (res) {
//            patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.REFUND.getCode(), backOrder.getBizCode());
//        } else {
//            patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.REFUND_FAIL.getCode(), backOrder.getBizCode());
//        }
//        return res;
//    }

    /**
     * 发送未咨询结束短信提醒
     */
//    public void noticeDoctorSms() {
//        long time = System.currentTimeMillis();
//        List<PatientAdvisoryInfo> infoList =
//                patientAdvisoryInfoMapper.queryDoctorNotice(AdvisoryStatus.BEGIN.getCode());
//        if (infoList == null || infoList.isEmpty()) {
//            return;
//        }
//        Map<Long, Long> smsMap = new HashMap<>();
//        for (PatientAdvisoryInfo info : infoList) {
//            if (info.getDoctorId() == null || info.getPatientId() == null || info.getStartTime() == null) {
//                continue;
//            }
//            Long doctorId = info.getDoctorId();
//            Long patientId = info.getPatientId();
//
//            if (userCenterUserService.isTransfer(doctorId)) {//托管医生
//                continue;
//            }
//
//            Long chatLastTime = chatMessageService.queryLastMessageTimeStamp(doctorId, patientId);
//            if (info.getStartTime().getTime() > chatLastTime) {
//                Long count = smsMap.get(doctorId);
//                if (count == null) {
//                    count = 0L;
//                }
//                smsMap.put(doctorId, ++count);
//            }
//        }
//        if (smsMap.isEmpty()) {
//            return;
//        }
//        for (Long docId : smsMap.keySet()) {
//
//            BaseUserInfo baseUserInfo = userCenterUserService.findUserById(docId);
//            if (baseUserInfo != null) {
//                String name = StringUtils.isNotEmpty(baseUserInfo.getName()) ? baseUserInfo.getName() :
//                        baseUserInfo.getNickname();
//                log.info("noticeDoctorSms send sms name ={},count={}", name, smsMap.get(docId));
//                if (StringUtils.isNotEmpty(baseUserInfo.getMobile()) && appConfig.isProduction()) {
//                    SmsSendTemplateReqDTO dto = new SmsSendTemplateReqDTO();
//                    Map<String, String> params = new HashMap<>();
//                    params.put("doctor_name", name);
//                    params.put("patient_count", smsMap.get(docId).toString());
//                    dto.setSignName(Constants.DOCTOR_SIGIN_SMS);
//                    dto.setMobile(baseUserInfo.getMobile());
//                    dto.setTemplateId(Constants.DOCTOR_MESSAGE_SMS);
//                    dto.setParams(params);
//                    try {
//                        smsService.sendTemplateSms(dto);
//                    } catch (SmsException s) {
//                        log.error("noticeDoctorSms error doctorId= {}", docId, s);
//                    }
//                }
//            }
//        }
//        log.info("noticeDoctorSms 耗时：{}", System.currentTimeMillis() - time);
//    }


    //云币中心加云币
    Boolean isIncreaseCoin(PatientAdvisoryOrder order) {
        if (order.getCostCoin() == 0) {
            return true;
        }
        try {
            CoinCenterPatientRequest request = new CoinCenterPatientRequest();
            request.setCoinCount(order.getCostCoin());
            request.setUserId(order.getPatientId());
            request.setSeriablize(order.getBizCode());
            request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
            CoinCenterPatientResponse res = coinCenterPatientService.increasePatientCoin(request);
            log.info("isIncreaseCoin number:{},订单：{}，金额：{}", order.getOrderNum(), order.getBizCode(),
                    order.getCostCoin());
            if (res == null || !res.getSuccess()) {
                log.warn("isIncreaseCoin false Exception orderNum:{}", order.getOrderNum());
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("isIncreaseCoin error orderNum:{}", order.getOrderNum(), e);
        }
        return false;
    }

    //云币中心扣云币
    public Boolean isDecreasePatientCoin(PatientAdvisoryOrder order, Integer coin) {
        if (coin == 0) {
            return true;
        }
        try {
            CoinCenterPatientRequest request = new CoinCenterPatientRequest();
            request.setCoinCount(coin);
            request.setUserId(order.getPatientId());
            request.setSeriablize(order.getBizCode());
            request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
            CoinCenterPatientResponse res = coinCenterPatientService.decreasePatientCoin(request);
            if (res == null || !res.getSuccess()) {
                log.warn("isDecreasePatientCoin false  orderNum:{}", order.getOrderNum());
                return false;
            }
            log.info("isDecreasePatientCoin result orderNum:{},Seriablize:{},{}", order.getOrderNum(),
                    order.getBizCode(), res.getSuccess());
            return res.getSuccess();
        } catch (Exception e) {
            log.error("isDecreasePatientCoin error orderNum:{}", order.getOrderNum(), e);
        }
        return false;
    }

    //云币中心查询处理结果
    public Boolean hasSuccessHandle(PatientAdvisoryOrder order) {
        try {
            CoinCenterPatientRequest request = new CoinCenterPatientRequest();
            request.setUserId(order.getPatientId());
            request.setSeriablize(order.getBizCode());
            request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
            CoinCenterPatientResponse res = coinCenterPatientService.hasSuccessHandle(request);
            if (res == null || !res.getSuccess()) {
                log.warn("hasSuccessHandle false Exception orderNum:{}", order.getOrderNum());
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("hasSuccessHandle error orderNum:{}", order.getOrderNum(), e);
        }
        return false;
    }

    /**
     * 根据订单ID判断是否支付成功
     *
     * @param orderNum 跟下单的id保持一致
     */
    public boolean isPaySuccess(String orderNum) {
        QueryOrderPayResultRequest request = new QueryOrderPayResultRequest();
        request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
        List<String> orderId = new ArrayList<>(1);
        orderId.add(orderNum);
        request.setOrderIdList(orderId);

        log.info("验证支付:是否成功，orderNum:{}", orderNum);
        QueryOrderPayResultResponse response = coinCenterPatientService.queryOrderPayResult(request);
        if (response != null && response.getSuccess() && response.getPayList() != null &&
                !response.getPayList().isEmpty()) {
            QueryOrderPayResultModel resultModel = response.getPayList().get(0);
            // 云币数为0，代表没有充值成功
            if (resultModel.getCoinCount() == 0) {
                log.info("验证支付充值失败，orderNum:{}", orderNum);
                return false;
            } else {
                log.info("验证支付充值成功，orderNum:{}", orderNum);
                return true;
            }
        }
        return false;
    }


    public CoinRechargeResponse coinRecharge(PatientAdvisoryOrder order, Integer coin,
        Integer payWay) {
        // 调用支付串rpc
        CoinRechargeRequest request = new CoinRechargeRequest();
        request.setOrderId(order.getOrderNum());
        request.setPayWay(payWay);
        request.setUserId(order.getPatientId());
        request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
        request.setCoinCount(coin);
        log.info("RPC 获取支付串：userId:{} orderId:{} coinCount:{}", order.getPatientId(), order.getOrderNum(), coin);

        CoinRechargeResponse response = coinCenterPatientService.coinRecharge(request);
        log.info("咨询订单获取支付串,返回结果：result={}", response == null ? null : response.getSuccess());
        if (response == null || !response.getSuccess() || response.getPayString() == null) {
            throw new ServerException(ASK_PAY_FAIL);
        }
        return response;
    }

    /**
     * 重新获取支付串
     */
    public CoinRechargeResponse reacquirePayString(PatientAdvisoryOrder order, int payWay) {
        CoinRechargeRequest request = new CoinRechargeRequest();
        request.setOrderId(order.getOrderNum());
        request.setPayWay(payWay);
        request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
        log.info("RPC 重新获取支付串：userId:{} orderId:{}", order.getPatientId(), order.getOrderNum());

        CoinRechargeResponse response = coinCenterPatientService.reacquirePayString(request);
        log.info("RPC 重新获取支付串,返回结果：result={}", response == null ? null : response.getSuccess());
        if (response == null || !response.getSuccess() || response.getPayString() == null) {
            throw new ServerException(ASK_PAY_FAIL);
        }
        return response;
    }


    /*生成退款单 默认失败*/
//    PatientAdvisoryOrder backOrderCoin(PatientAdvisoryOrder order) {
//        PatientAdvisoryOrder newOrder;
//        newOrder = patientAdvisoryOrderMapper.findByOrderAndType(order.getOrderNum(), OrderType.BACK.name());
//        if (newOrder == null) {
//            newOrder = new PatientAdvisoryOrder();
//            BeanUtils.copyProperties(order, newOrder);
//            newOrder.setBizCode(genBizCode(OrderType.BACK.name()));
//            newOrder.setType(OrderType.BACK.name());
//            patientAdvisoryOrderMapper.initOrder(newOrder);
//        }
//        return newOrder;
//    }

    /*生成咨询订单*/
//    public PatientAdvisoryOrder initOrder(Long doctorId, Long patientId, String orderNum, Integer status, String type) {
//        return initOrder(doctorId, patientId, orderNum, status, type, null);
//    }

//    public PatientAdvisoryOrder initOrder(Long doctorId, Long patientId, String orderNum, Integer status, String type
//            , Integer price) {
//        PatientAdvisoryOrder order = new PatientAdvisoryOrder();
//        order.setStatus(status);
//        order.setDoctorId(doctorId);
//        order.setPatientId(patientId);
//        if (price == null && !type.equals(OrderType.FREE.name())) {
//            price = doctorPrice(doctorId);
//        }
//        order.setCostCoin(price);
//        order.setAmount(price);
//        order.setOrderNum(orderNum);
//        order.setBizCode(genBizCode(type));
//        order.setType(type);
//        patientAdvisoryOrderMapper.initOrder(order);
//        return order;
//    }

//    /*生成咨询会话单*/
//    public PatientAdvisoryInfo initInfo(Long doctorId, Long patientId, String orderNum, Integer status) {
//        PatientAdvisoryInfo info = new PatientAdvisoryInfo();
//        info.setStatus(status);
//        info.setDoctorId(doctorId);
//        info.setPatientId(patientId);
//        info.setOrderNum(orderNum);
//        patientAdvisoryInfoMapper.initInfo(info);
//        return info;
//    }


    // TODO ---- 医生价格 ------> todoByliming
    public Integer doctorPrice(Long doctorId) {
        Integer price = doctorAdvisoryPriceMapper.queryDoctorAdvisoryPrice(doctorId);
        return price != null ? price : appConfig.getCostCoin();
    }

    public Integer doctorOrigin(Long doctorId) {
        Integer price = doctorAdvisoryPriceMapper.queryDoctorAdvisoryPrice(doctorId);
        return price != null ? price : appConfig.getOriginalCoin();
    }

    //    @Transactional(rollbackFor = Throwable.class)
//    public void refundAndCancelAll(PatientAdvisoryInfo info) {
//        PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findByOrderAndType(info.getOrderNum(), OrderType.PAY
//                .name());
//        if (order == null || info.getStatus().equals(AdvisoryStatus.INIT.getCode())) {
//            log.info("初始化老数据,取消订单：order_num={}", info.getOrderNum());
//            patientAdvisoryInfoMapper.updateStatus(AdvisoryStatus.CANCEL.getCode(), info.getId());
//        } else {
//            log.info("初始化老数据,退款订单：order_num={}", info.getOrderNum());
//            isRefund(info, order);
//        }
//    }
//    List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> list) {
//        List<CoinOrderModel> coinOrderModels = doctorAdvisoryPriceMapper.batchQueryAdvisoryPrice(list);
//
//        List<CoinOrderModel> result = new ArrayList<>();
//        for (Long doctorId : list) {
//            boolean newInstance = true;
//            if (coinOrderModels != null && !coinOrderModels.isEmpty()) {
//                for (CoinOrderModel coinOrderModel : coinOrderModels) {
//                    if (coinOrderModel.getId().equals(doctorId)) {
//                        result.add(coinOrderModel);
//                        coinOrderModel.setCoin(transformCoin(coinOrderModel.getPrice()));
//                        newInstance = false;
//                        break;
//                    }
//                }
//            }
//            if (newInstance) {
//                CoinOrderModel model = new CoinOrderModel();
//                model.setId(doctorId);
//                model.setCoin(appConfig.getCostCoin());
//                model.setPrice(appConfig.getOriginalCoin());
//                result.add(model);
//            }
//        }
//
//        return result;
//    }

    private Integer transformCoin(Integer price) {
        return price; //目前是1个云币对应一分钱
    }

//    @Transactional(rollbackFor = Throwable.class)
//    public void refundAndCancelAll(PatientAdvisoryInfo info) {
//        PatientAdvisoryOrder order = patientAdvisoryOrderMapper.findByOrderAndType(info.getOrderNum(),
//                OrderType.PAY.name());
//        if (order == null || info.getStatus().equals(AdvisoryStatus.INIT.getCode())) {
//            log.info("初始化老数据,取消订单：order_num={}", info.getOrderNum());
//            patientAdvisoryInfoMapper.updateStatus(AdvisoryStatus.CANCEL.getCode(), info.getId());
//        } else {
//            log.info("初始化老数据,退款订单：order_num={}", info.getOrderNum());
//            isRefund(info, order);
//        }
//    }

    /**
     * mysql 生成支付单号
     *
     * @return code
     */
//    private String genBizCode(String type) {
//        return type + DateUtil1.format(new Date(), DateUtil1.PATTERN_YEAR2DAY) +
//                StringUtils.leftPad(sequenceGenerator.get() + "", 11, '0');
//    }

    /**
     * 生成业务单号
     *
     * @return ordernum
     */
//    public String genOrderNum(String type) {
//        synchronized (lock) {
//            Random random = new Random();
//            final Integer rand = 10000;
//            int num = random.nextInt(rand);
//            return type + System.currentTimeMillis() + StringUtils.leftPad(Integer.toString(num), 5, '0');
//        }
//    }


    /**
     * 更新未回复的时间信息
     */
    public void updateNoRepeatWaitingTime(PatientAdvisoryInfo info) {
        Long firstTime = chatMessageService.queryFirstMessageDateTime(info.getDoctorId(), info.getPatientId(),
                info.getStartTime());
        PatientAdvisoryInfo updateInfo = new PatientAdvisoryInfo();
        final Long nd = 1000 * 24 * 60 * 60L; //一天
        final long nh = 1000 * 60 * 60L;//一小时
        final long nm = 1000 * 60L;//一分钟
        Long diff = firstTime > 0 && firstTime > info.getStartTime().getTime() ?
                firstTime - info.getStartTime().getTime() : System.currentTimeMillis() - info.getStartTime().getTime();
        //转换为10.44形式的值，将天转为小时，整数为小时，小数为分钟
        updateInfo.setWaiting((diff / nd * 24d) + diff % nd / nh + (diff % nd % nh / nm / 100d));
        if (firstTime > info.getStartTime().getTime()) {
            updateInfo.setRepeatStatus(1);
        }
        updateInfo.setId(info.getId());
        patientAdvisoryInfoMapper.updateOrderUserInfo(updateInfo);
    }


//    PatientAdvisoryInfo initFreeFinishInfo(Long doctorId, Long patientId, Long time) {
//        String orderNum = genOrderNum(OrderType.FREE.name());
//        PatientAdvisoryInfo info = new PatientAdvisoryInfo();
//        info.setStatus(AdvisoryStatus.BEGIN.getCode());
//        info.setDoctorId(doctorId);
//        info.setPatientId(patientId);
//        info.setOrderNum(orderNum);
//        info.setStartTime(new Date(time));
//        info.setExpireTime(time + Constants.EXPIRE_TIME);
//        info.setSource(AdvisoryResource.IN_APP.name());
//        info.setAdvisoryType(AdvisoryType.PATIENT.getCode());
//        patientAdvisoryInfoMapper.initInfo(info);
//
//        initOrder(doctorId, patientId, orderNum, OrderStatus.PAY_SUCCESS.getCode(), OrderType.FREE.name());
//        return info;
//    }


//    PatientAdvisoryInfo initStoreFreeInfo(StoreAdvisory storeAdvisory) {
//        String orderNum = genOrderNum(OrderType.FREE.name());
//        PatientAdvisoryInfo info = new PatientAdvisoryInfo();
//        info.setStatus(AdvisoryStatus.BEGIN.getCode());
//        info.setDoctorId(storeAdvisory.getDoctorId());
//        info.setPatientId(storeAdvisory.getStoreUserId());
//        info.setOrderNum(orderNum);
//        info.setStartTime(new Date());
//        info.setSource(AdvisoryResource.STORE.name());
//        info.setAdvisoryType(storeAdvisory.getAdvisoryType());
//        info.setImGroupId(storeAdvisory.getImGroupId());
//        patientAdvisoryInfoMapper.initInfo(info);
//        initOrder(storeAdvisory.getDoctorId(), storeAdvisory.getStoreUserId(), orderNum,
//                OrderStatus.PAY_SUCCESS.getCode(), OrderType.FREE.name());
//        return info;
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public void transferPatientAdvisoryData(final Long srcPatientId, final Long destPatientId) {
//        patientAdvisoryInfoMapper.batchUpdatePatientId(srcPatientId, destPatientId);
//        log.info("transfer patient advisory info data success, oldId={}, newId={}", srcPatientId, destPatientId);
//
//        patientAdvisoryOrderMapper.batchUpdatePatientId(srcPatientId, destPatientId);
//        log.info("transfer patient advisory order data success, oldId={}, newId={}", srcPatientId, destPatientId);
//    }
}
