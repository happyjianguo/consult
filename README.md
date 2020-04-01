# 问诊服务

咨询单和订单是一对一的关系

方便关联二者用同一个bizcode，同样作为获取云币中心支付token来源

开药门诊，用药申请同时只能有一个，必须到处方审核结束或申请被拒绝才可以关闭

需要增加接口：

1. 当前是否存在开药问诊

2. 消费ih处方审核结果消息

## 问题：

1. 病人和医生信息从哪取的？

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