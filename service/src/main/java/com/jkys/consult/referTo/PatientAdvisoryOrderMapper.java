package com.jkys.consult.referTo;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jkys.consult.referTo.bean.OrderInfoDO;
import com.jkys.consult.referTo.bean.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author yangZh
 * @since 2018/7/13
 **/
@Component
@Mapper
@DS("medical")
public interface PatientAdvisoryOrderMapper {

    @SelectKey(statement = "SELECT LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = long.class)
    @Insert("insert into patient_advisory_order (doctor_id,patient_id,status,order_num,biz_code,type,cost_coin,charge_coin,amount,gmt_create) values (#{doctorId},#{patientId},#{status},#{orderNum},#{bizCode},#{type},#{costCoin},#{chargeCoin},#{amount},NOW())")
    Long initOrder(PatientAdvisoryOrder patientAdvisoryOrder);


    @Update("<script> update patient_advisory_order  set gmt_modify = NOW()" +
            "<if test=\"order.costCoin != null and order.costCoin >0\">, cost_coin=#{order.costCoin}</if>" +
            "<if test=\"order.chargeCoin != null and order.chargeCoin >0\">, charge_coin=#{order.chargeCoin}</if>" +
            "<if test=\"order.amount != null and order.amount >0\">, amount=#{order.amount}</if>" +

            " where id =#{order.id}</script>")
    Long updateOrderInfo(@Param("order") PatientAdvisoryOrder order);

    @Select("select * from patient_advisory_order where id =#{id}")
    PatientAdvisoryOrder findById(@Param("id") Long id);


    @Select("select * from patient_advisory_order where type = #{type} and order_num =#{orderNum} limit 1")
    PatientAdvisoryOrder findByOrderAndType(@Param("orderNum") String orderNum,
        @Param("type") String type);

    @Select("select * from patient_advisory_order where type = 'PAY' and biz_code =#{code}")
    PatientAdvisoryOrder findByBizCode(@Param("code") String code);

    /**
     * 按订单号和类型查找，一个类型和单号至多存在一个  唯一索引
     * @param orderNum
     * @param type
     * @return
     */
    @Select("select * from patient_advisory_order where type = #{type} and  order_num =#{orderNum} order by id limit 1")
    PatientAdvisoryOrder findLastPayOrder(@Param("orderNum") String orderNum,
        @Param("type") String type);


    /**
     * 查询一条在初始状态的订单
     *
     * @param doctorId  医生id
     * @param patientId 病人id
     * @return obj
     */
    @Select("select * from patient_advisory_order where doctor_id =#{doctorId} and patient_id = #{patientId} and status =0  order by id desc limit 1")
    PatientAdvisoryOrder findByDocAndPatient(@Param("doctorId") Long doctorId,
        @Param("patientId") Long patientId);

    /**
     * 更新状态
     * @param bizCode 支付订单号
     * @return result
     */
    @Update("update patient_advisory_order set status =#{status} where  biz_code = #{bizCode} ")
    Integer updateOrderStatus(@Param("status") Integer status,
        @Param("bizCode") String bizCode);

    /**
     * 发起支付充值处理
     * @param bizCode 支付订单号
     * @return result
     */
    @Update("update patient_advisory_order set status=1,charge_coin =#{charge} where  biz_code = #{bizCode} ")
    Integer updateOrderPaying(@Param("charge") Integer charge,
        @Param("bizCode") String bizCode,
        @Param("payString") String payString);

    /**
     *  支付后处理
     * @param bizCode 支付订单号
     * @return result
     */
    @Update("update patient_advisory_order  set status =#{status},amount=#{amount},cost_coin=#{coin} where  biz_code = #{bizCode} ")
    Integer updateOrderPay(@Param("status") Integer status,
        @Param("bizCode") String bizCode,
        @Param("amount") Integer amount,
        @Param("coin") Integer coin);


    @Select("<script>select info.id,info.gmt_create as startTime,info.doctor_id,info.status,info.describes,o.amount from patient_advisory_order o," +
            "patient_advisory_info info where o.order_num = info.order_num and info.patient_id = #{patientId}  " +
            " and o.type='PAY'" +
            "<if test=\"status != null  and status >0\"> and info.status=#{status}</if>" +
            "order by info.gmt_create desc limit #{start},#{limit} </script>")
    List<OrderInfoDO> queryPatientInfoOrderList(@Param("patientId") Long patientId,
        @Param("status") Integer status,
        @Param("start") Integer start,
        @Param("limit") Integer limit);

    @Select("<script>select info.id,info.gmt_create as startTime,info.doctor_id,info.patient_id,info.status,info.describes,o.amount from patient_advisory_order o," +
            "patient_advisory_info info where o.order_num = info.order_num and info.doctor_id = #{doctorId} " +
            " and o.type='PAY' and info.start_time >='2018-10-01 00:00:01'" +
            "and info.start_time between #{startDate} and #{endDate}"+
            "<if test=\"status != null  and status >0\"> and info.status=#{status}</if>" +
            "<if test=\"status != null  and status == -1\"> and info.status in(2,3,4)</if>" +
            "order by info.gmt_create desc limit #{start},#{limit} </script>")
    List<OrderInfoDO> queryDoctorInfoOrderList(@Param("doctorId") Long doctorId,
        @Param("status") Integer status,
        @Param("start") Integer start,
        @Param("limit") Integer limit,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate);


    @Select("select sum(case when info.status =2 then 1 else 0 end) as begin,sum(case when info.status =3 then 1 else 0 end) as over," +
            "sum(case when info.status =4 then 1 else 0 end) as refund from patient_advisory_info info,patient_advisory_order o where  " +
            "o.order_num = info.order_num and info.doctor_id=#{doctorId} and o.type='PAY'" +
            "and  info.status in(2,3,4) and info.start_time >='2018-10-01 00:00:01' and info.start_time between #{startDate} and #{endDate}")
    Map<String,Integer> sumDoctorInfoOrder(@Param("doctorId") Long doctorId,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate);



    /**
     * 医生收入明细 时间以支付时间界限
     * @param doctorId
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("<script>SELECT i.id, i.doctor_id, i.patient_id, o.biz_code, o.amount, o.type, i.start_time as gmt_create " +
            " FROM patient_advisory_order o left join patient_advisory_info i on o.order_num = i.order_num" +
            " where o.doctor_id = #{doctorId} and o.type in('PAY', 'BACK') and o.`status` in(2, 4) and o.amount is not null and o.amount != 0" +
            " and i.start_time between #{startDate} and #{endDate} and i.start_time &gt; '2018-10-01' order by i.start_time desc, o.type limit #{page.offSet}, #{page.limit}</script>")
    List<PatientAdvisoryOrder> queryDoctorIncome(@Param("doctorId") Long doctorId,
        @Param("startDate") String startDate, @Param("endDate") String endDate,
        @Param("page") Page page);

    @Select("<script>SELECT sum(case o.type when 'PAY' then o.cost_coin when 'BACK' then -o.cost_coin end) " +
            " FROM patient_advisory_order o left join patient_advisory_info i on o.order_num = i.order_num" +
            " where o.doctor_id = #{doctorId} and o.type in('PAY', 'BACK') and o.`status` in(2, 4) and o.amount is not null and o.amount != 0" +
            " and i.start_time between #{startDate} and #{endDate} and i.start_time &gt; '2018-10-01'</script>")
    Integer queryDoctorTotalIncome(@Param("doctorId") Long doctorId,
        @Param("startDate") String startDate, @Param("endDate") String endDate);


    @Select("<script>" +
            "select * from patient_advisory_order where  order_num in" +
            "<foreach collection=\"orderNums\" item=\"orderNum\" open=\"(\" separator=\",\" close=\")\">#{orderNum}</foreach>" +
            "  and type in ('PAY','FREE') </script>")
    List<PatientAdvisoryOrder> queryOrdersByOrderNums(@Param("orderNums") List<String> orderNums);
}
