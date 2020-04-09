# 问诊服务

咨询单和订单是一对一的关系

方便关联二者用同一个bizcode，同样作为获取云币中心支付token来源

开药门诊，用药申请同时只能有一个，必须到处方审核结束或申请被拒绝才可以关闭



需要增加接口：

1. 判断当前用户咨询单状态，分5个类型

1. 开药问诊订单创建完成后自动支付，顾问和客户的价格怎么设置








1. 患者ID尽量用Long userId = ApiGateway.getUserId();
自测时可以mock

1. 当前是否存在开药问诊

2. 消费ih处方审核结果消息

4. 修改rpc*.xml 注册bean 如kafkasender

5. 支付payGo时需判断当前咨询单状态

支付和退款动作

PatientAdvisoryServiceImpl

```
payGo, payString
```

PatientAdvisoryLogic

```
云币中心扣款付款操作
```

判断是否会员，权益，获取免费计数，

计数余额（扣除计数）memberAdvisoryRpcService.addFreeAdvisory

云币余额

扣计数或者云币

根据结果更新状态（成功或者失败）

将结果返回给前端

5. 发送IM消息

TODO 提示消息不同
    chatMessageService.sendOrderMessage(info.getPatientId(), info.getDoctorId(), info.getId());

6. 计时从支付时开始

7. 修改状态机，添加新的状态

8. 结束咨询单时发送

    mqServer.sendFinishMsg(advisory, isDoctor);

2. 特殊医生组具体逻辑

只是在查询涉及医生咨询单状态时，同组医生一同作为条件的逻辑


1. 商城咨询单，同时只能有一个咨询单

//没有咨询中的，去Im看查有没医生发起的聊天
//48小时内有医生发消息的，最后次结束聊天后医生发起了聊天或者完全无聊天的  开启免费咨询

1. 24小时结束咨询单

按是否回复，可以是完成也可以是终止

## 问题：

1. **状态机和eventBus会把异常吞掉**

1. 病人和医生信息从哪取的？

3. 发IM消息

        // TODO ---- 可用 ------> todoByliming
        chatMessageService.sendFinishAdvisoryMessage(advisory.getDoctorId(), advisory.getPatientId(),
                advisory.getSource() != null && advisory.getSource().equals(AdvisoryResource.MEDIC_SALE.name())
                        ? Constants.FINISH_TEXT_24 : Constants.FINISH_TEXT, MessageType.Finish.name());

5. 医生价格维护和收入统计

UserCenterUserService 用户信息

UserExtraReadService 额外信息

payGo

Long patientId = ApiGateway.getUserId();

PatientAdvisoryLogic 云币中心付款退款

特殊医生组
谢春为

            if (SpecialDoctorHandler.isSpecialDoctor(request.getDoctorId())) {
                info = special(request.getDoctorId(), request.getPatientId(), time);
            }
            
DOCTOR_ADVISORY_PRICE	医生咨询价格有效区间	{"min": 0, "max": 30000, "defaultPrice": 0, "range": "0-30000"}	2	1536076800000


获取医生额外信息
```
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

```

        // 咨询VIP控糖顾问或vip客服时，判断用户是否为VIP
        if ((request.getDoctorId().equals(appConfig.getVipConsultant()) ||
            request.getDoctorId().equals(appConfig.getVipCustomerServiceId())) && isVipUser(owner)) {
            return free(model, request.getDoctorId(), request.getPatientId());
        }