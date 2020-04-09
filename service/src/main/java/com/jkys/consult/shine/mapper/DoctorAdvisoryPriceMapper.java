package com.jkys.consult.shine.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author xiecw
 * @date 2018/09/04
 */
public interface DoctorAdvisoryPriceMapper {

    /** 查询医生的咨询价格 */
    @Select("select advisory_price from `doctor_advisory_price` where doctor_id = #{doctorId}")
    Integer queryDoctorAdvisoryPrice(@Param("doctorId") Long doctorId);

    /** 更新医生的咨询价格 */
    @Update("update doctor_advisory_price set advisory_price = #{price} where doctor_id = #{doctorId}")
    Integer modifyAdvisoryPrice(@Param("doctorId") Long doctorId, @Param("price") Integer price);

    /** 新增医生的咨询价格 */
    @Insert("insert into doctor_advisory_price(`doctor_id`, `advisory_price`) values(#{doctorId}, #{price})")
    Integer addAdvisoryPrice(@Param("doctorId") Long doctorId, @Param("price") Integer price);

    /** 批量查询医生咨询价格*/
//    @Select("<script>" +
//            "select doctor_id as id, advisory_price as price from `doctor_advisory_price` where doctor_id in " +
//            " <foreach collection=\"list\" item=\"item\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
//            "</script>")
//    List<CoinOrderModel> batchQueryAdvisoryPrice(@Param("list") List<Long> list);
}
