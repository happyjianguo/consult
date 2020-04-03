package com.jkys.consult.shine.mapper;

import com.jkys.consult.common.bean.PatientAdvisoryInfo;
import com.jkys.consult.shine.bean.AdvisoryUserInfoModel;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
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
public interface PatientAdvisoryInfoMapper {


    /**
     * 按天剑查询最新的一条未结束的咨询记录
     */
//    @Select("<script> select * from patient_advisory_info where status in(1,2)" +
//            "<if test= \"query.imGroupId != null  and query.imGroupId >0\"> and im_group_id=#{query.imGroupId}</if>" +
//            "<if test= \"query.doctorId != null  and query.doctorId >0\"> and doctor_id=#{query.doctorId}</if>" +
//            "<if test= \"query.patientId != null  and query.patientId >0\"> and patient_id=#{query.patientId}</if>" +
//            " order by id desc limit 1 </script>")
//    PatientAdvisoryInfo findByQuery(@Param("query") AdvisoryQuery query);


    @Update("<script> update patient_advisory_info  set gmt_modify = NOW()" +
            "<if test=\"info.patientName != null and info.patientName != ''\">, patient_name =#{info.patientName}</if>" +
            "<if test=\"info.sex != null and info.sex >0\">, sex=#{info.sex}</if>" +
            "<if test=\"info.weight != null and info.weight >0\">, weight=#{info.weight}</if>" +
            "<if test=\"info.height != null and info.height >0\">, height=#{info.height}</if>" +
            "<if test=\"info.age != null and info.age >0\">, age=#{info.age}</if>" +
            "<if test=\"info.describes != null and info.describes  != ''\">, describes=#{info.describes}</if>" +
            "<if test=\"info.imgUrl != null and info.imgUrl  != ''\">, img_url=#{info.imgUrl}</if>" +
            "<if test=\"info.startTime != null \">, start_time=#{info.startTime}</if>" +
            "<if test=\"info.endTime != null \">, end_time=#{info.endTime}</if>" +
            "<if test=\"info.expireTime != null and info.expireTime >0\">, expire_time=#{info.expireTime}</if>" +
            "<if test=\"info.status != null and info.status >0\">, status=#{info.status}</if>" +
            "<if test=\"info.memo != null and info.memo  != ''\">, memo=#{info.memo}</if>" +
            "<if test=\"info.presentIllness != null and info.presentIllness  != ''\">, present_illness=#{info.presentIllness}</if>" +
            "<if test=\"info.pastHistory != null and info.pastHistory  != ''\">, past_history=#{info.pastHistory}</if>" +
            "<if test=\"info.allergicHistory != null and info.allergicHistory  != ''\">, allergic_history=#{info.allergicHistory}</if>" +
            "<if test=\"info.waiting != null and info.waiting >0\">, waiting=#{info.waiting}</if>" +
            "<if test=\"info.repeatStatus != null and info.repeatStatus >0\">, repeat_status=#{info.repeatStatus}</if>" +
            "<if test=\"info.card != null and info.card  != ''\">, card=#{info.card}</if>" +
            "<if test=\"info.districtCode != null and info.districtCode  != ''\">, district_code=#{info.districtCode}</if>" +
            "<if test=\"info.country != null and info.country  != ''\">, country=#{info.country}</if>" +
            "<if test=\"info.province != null and info.province  != ''\">, province=#{info.province}</if>" +
            "<if test=\"info.city != null and info.city  != ''\">, city=#{info.city}</if>" +
            "<if test=\"info.county != null and info.county  != ''\">, county=#{info.county}</if>" +
            "<if test=\"info.diabetesType != null and info.diabetesType  != ''\">, diabetes_type=#{info.diabetesType}</if>" +
            "<if test=\"info.source != null and info.source  != ''\">, source=#{info.source}</if>" +
            "<if test=\"info.diseaseCategory != null and info.diseaseCategory  != ''\">, disease_category=#{info.diseaseCategory}</if>" +

            " where id =#{info.id}</script>")
    Long updateOrderUserInfo(@Param("info") PatientAdvisoryInfo info);

    @SelectKey(statement = "SELECT LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = long.class)
    @Insert("<script>  insert into patient_advisory_info (doctor_id,patient_id,patient_name, age, weight, height, " +
            "card, sex, img_url, district_code, country, province, city, county, diabetes_type, disease_category ," +
            "describes,status,order_num,past_history,present_illness,allergic_history,expire_time,start_time," +
            "before_info_id,source,advisory_type,im_group_id,gmt_create,gmt_modify) values  (#{doctorId}," +
            "#{patientId}," +
            "#{patientName}, " + "#{age}, #{weight}, #{height}, #{card}, #{sex}, #{imgUrl}, " +
            "#{districtCode}, #{country}, #{province}, #{city}, #{county}, #{diabetesType}, #{diseaseCategory} ," +
            "#{describes},#{status},#{orderNum},#{pastHistory},#{presentIllness},#{allergicHistory},#{expireTime}," +
            "#{startTime},#{beforeInfoId},#{source},#{advisoryType},#{imGroupId},NOW(),NOW())</script>")
    Long initInfo(PatientAdvisoryInfo patientAdvisoryInfo);

    /**
     * 永远至多存在一个医生-病人进行中的咨询，因此更新为结束时全记录全部更新掉
     * 这里暂时放开任何状态可直接结束
     *  refund 是否退款结束
     */
    @Update("update patient_advisory_info set status =#{status},end_time=NOW() where doctor_id =#{doctorId} and patient_id = #{patientId} and status =2")
    Integer updateOrderStatusOver(@Param("status") Integer status,
        @Param("doctorId") Long doctorId,
        @Param("patientId") Long patientId);

    @Update("update patient_advisory_info set status =#{status} where id =#{id} ")
    Integer updateStatus(@Param("status") Integer status,
        @Param("id") Long id);

    @Update("update patient_advisory_info set status =#{status},end_time=NOW() where id =#{id} ")
    Integer updateStatusById(@Param("id") Long id,
        @Param("status") Integer status);

    @Update("update patient_advisory_info set status =2, pay_time=NOW(),start_time=NOW(),expire_time=#{expireTime}  where id= #{id} ")
    Integer updateOrderStatusStart(@Param("id") Long id,
        @Param("expireTime") Long expireTime);


    @Select("select * from patient_advisory_info where id =#{id}")
    PatientAdvisoryInfo findById(@Param("id") Long id);


    @Select("select * from patient_advisory_info where order_num =#{orderNum}")
    PatientAdvisoryInfo findByOrder(@Param("orderNum") String orderNum);

    /**
     * 查询最新的一条未结束的咨询记录
     *
     * @param doctorId  医生id
     * @param patientId 病人id
     * @return obj
     */
    @Select("select * from patient_advisory_info where doctor_id =#{doctorId} and patient_id = #{patientId} and status in(1,2)  order by id desc limit 1")
    PatientAdvisoryInfo findByDocAndPatient(@Param("doctorId") Long doctorId,
        @Param("patientId") Long patientId);

    /**
     * 查询最新的一条未结束的咨询记录
     *
     * @param doctorIds  医生id
     * @param patientId 病人id
     * @return obj
     */
    @Select("<script>" +
            "select * from patient_advisory_info where doctor_id in" +
            "<foreach collection=\"doctorIds\" item=\"doctorId\" open=\"(\" separator=\",\" close=\")\">#{doctorId}</foreach>" +
            " and patient_id = #{patientId} and status &lt;3  order by id desc limit 1" +
            "</script>")
    PatientAdvisoryInfo findByDoctorsAndPatient(@Param("doctorIds") List<Long> doctorIds,
        @Param("patientId") Long patientId);

    /**
     * 查询已过超时时间的咨询单
     *
     * @param currentTime 当前时间
     * @return result
     */
    @Select("select id,doctor_id,patient_id,order_num,start_time,status,source from patient_advisory_info where status =2 and source in('IN_APP','MEMBER') and expire_time < #{currentTime}")
    List<PatientAdvisoryInfo> findExpireAll(@Param("currentTime") Long currentTime);

    /**
     * 查询已过超时时间的咨询单
     *
     * @param days 超时时间
     * @return result
     */
    @Select("select id,doctor_id,patient_id,order_num,start_time,status from patient_advisory_info where  status =1 and source in('IN_APP','MEMBER','MEDIC_SALE') and gmt_create <#{days} ")
    List<PatientAdvisoryInfo> findCancelList(@Param("days") LocalDateTime days);

    /**
     * 查询医生对应状态的的咨询
     *
     * @param status 状态
     * @return result
     */
    @Select("select doctor_id as doctorId, patient_id as patientId,start_time as startTime from patient_advisory_info where status =2 and describes >'' and start_time >'' " +
            " and  order_num like '%PAY%'  group by patient_id,doctor_id order by doctor_id")
    List<PatientAdvisoryInfo> queryDoctorNotice(@Param("status") Integer status);


//    @Select("<script>select f.* from patient_advisory_info f " +
//            "where f.status >0" +
//            "<if test=\"query.doctorIds !=null \">and f.doctor_id  " +
//            "in <foreach close=\")\" collection=\"query.doctorIds\" item=\"id\" open=\"(\" separator=\",\"> #{id} </foreach> </if> " +
//            "<if test=\"query.patientIds !=null \">and f.patient_id  " +
//            "in <foreach close=\")\" collection=\"query.patientIds\" item=\"id\" open=\"(\" separator=\",\"> #{id} </foreach> </if> " +
//            "<if test=\"query.orderNum != null and query.orderNum != ''\">and f.order_num=#{query.orderNum} </if>" +
//            "<if test=\"query.status != null  and query.status >0\"> and f.status=#{query.status}</if>" +
//            "<if test=\"query.refund != null  \"> and f.refund=#{query.refund}</if>" +
//            "<if test=\"query.startDate != null  and query.endDate != null \"> " +
//            " and f.start_time>=#{query.startDate} and f.start_time &lt;=#{query.endDate} </if>" +
//            "<if test=\"query.payStart != null  and query.payEnd != null \"> " +
//            " and f.pay_time>=#{query.payStart} and f.pay_time &lt;=#{query.payEnd} </if>" +
//            "<if test=\"query.aType != null and query.aType ==1\"> and f.order_num like '%FREE%'</if>" +
//            "<if test=\"query.aType != null  and query.aType ==2\"> and f.order_num like '%PAY%'</if>" +
//            "<if test=\"query.hour24 != null  and query.hour24 >0\"> and f.waiting >=24</if>" +
//            "<if test=\"query.hour8 != null  and query.hour8 >0\"> and f.waiting >=8</if>" +
//            "order by f.gmt_create desc limit #{pageStart},#{size} </script>")
//    List<AdvisoryQueryModel> queryUserInfoList(@Param("query") AdvisoryQuery query,
//        @Param("pageStart") Integer pageStart,
//        @Param("size") Integer size);


//    @Select("<script>select count(1) from patient_advisory_info" +
//            " where 1=1 and status >0" +
//            "<if test=\"query.doctorIds !=null \">and doctor_id  " +
//            "in <foreach close=\")\" collection=\"query.doctorIds\" item=\"id\" open=\"(\" separator=\",\"> #{id} </foreach> </if> " +
//            "<if test=\"query.patientIds !=null \">and patient_id  " +
//            "in <foreach close=\")\" collection=\"query.patientIds\" item=\"id\" open=\"(\" separator=\",\"> #{id} </foreach> </if> " +
//            "<if test=\"query.orderNum != null and query.orderNum != ''\">and order_num=#{query.orderNum} </if>" +
//            "<if test=\"query.status != null  and query.status >0\"> and status=#{query.status}</if>" +
//            "<if test=\"query.refund != null  \"> and refund=#{query.refund}</if>" +
//            "<if test=\"query.startDate != null  and query.endDate != null \"> " +
//            " and start_time>=#{query.startDate} and start_time &lt;=#{query.endDate} </if>" +
//            "<if test=\"query.aType != null and query.aType ==1\"> and order_num like '%FREE%'</if>" +
//            "<if test=\"query.aType != null  and query.aType ==2\"> and order_num like '%PAY%'</if>" +
//            "<if test=\"query.payStart != null  and query.payEnd != null \"> " +
//            " and pay_time>=#{query.payStart} and pay_time &lt;=#{query.payEnd} </if>" +
//            "<if test=\"query.hour24 != null  and query.hour24 >0\"> and waiting >=24</if>" +
//            "<if test=\"query.hour8 != null  and query.hour8 >0\"> and waiting >=8</if>" +
//
//            " </script>")
//    Long countUserInfoList(@Param("query") AdvisoryQuery query);

//    @Select("select id,describes,past_history,present_illness,allergic_history,UNIX_TIMESTAMP(gmt_create)*1000 as startTime from patient_advisory_info where  describes >' ' and patient_id =#{uid} order by gmt_create desc")
//    List<HistoryDescModel> queryDescribeList(@Param("uid") Long uid);


    /**
     *
     * @param doctorIds 医生id
     * @param patientId 病人id
     * @param status 状态， 如果参数小于0，查询全部状态信息
     * @return info
     */
    @Select("<script>select id,end_time,doctor_id,patient_id ,status,past_history,present_illness,allergic_history from patient_advisory_info" +
            " where doctor_id in <foreach collection=\"doctorIds\" item=\"doctorId\" open=\"(\" separator=\",\" close=\")" +
            "\">#{doctorId}</foreach> " +
            "and patient_id = #{patientId} " +
            "<if test=\"status != null  and status >0\"> and status=#{status}</if>" +
            "<if test=\"status == -2\"> and status in (2,3,4)</if>" +
            " order by id desc limit 1</script>")
    PatientAdvisoryInfo findLastInfoByStatus(@Param("doctorIds") List<Long> doctorIds,
        @Param("patientId") Long patientId,
        @Param("status") Integer status);

    @Select("<script>" +
            "select doctor_id from patient_advisory_info where `status` = 2 and patient_id = #{patientId}" +
            "</script>")
    List<Long> queryOnAdvisory(@Param("patientId") Long patientId);

    /**
     * 查询在咨询中且医生未回复的
     *
     * @return result
     */
    @Select("select id,doctor_id,patient_id,start_time,status from patient_advisory_info where status=2 and repeat_status =0 and source in('IN_APP','MEMBER') and order_num like '%PAY%'")
    List<PatientAdvisoryInfo> findInfoListNoRepeat();


    /**
     * 查询在咨询中且医生未回复的
     *
     * @return result
     */
    @Select("select id,doctor_id,patient_id,start_time,status,advisory_type from patient_advisory_info where status=2  and source='STORE'")
    List<PatientAdvisoryInfo> findInfoListInStore();

    @Select("select id,doctor_id,patient_id,patient_name,status,im_group_id from patient_advisory_info where " +
            "patient_id=#{ownerId}  and source='STORE' order by id desc limit 1")
    PatientAdvisoryInfo findStoreLastInfoOwner(@Param("ownerId") Long ownerId);

    /*
      视频咨询中的咨询单
     */
    @Select("<script>select doctor_id from patient_advisory_info where source='STORE' and advisory_type = " +
            "#{advisoryType} and " +
            "doctor_id in <foreach collection=\"doctorIds\" " +
            "item=\"doctorId\" open=\"(\" separator=\",\" close=\")" + "\">#{doctorId}</foreach> " + "and  status" +
            " in (1,2)</script>")
    List<Long> findBusyDoctors(@Param("doctorIds") List<Long> doctorIds,
        @Param("advisoryType") Integer advisoryType);

    /*
     视频咨询中的咨询单
    */
    @Select("<script>select id from patient_advisory_info where source='STORE' and advisory_type = " +
            "#{advisoryType} and " +
            "doctor_id in <foreach collection=\"doctorIds\" " +
            "item=\"doctorId\" open=\"(\" separator=\",\" close=\")" + "\">#{doctorId}</foreach> " + "and  status" +
            " in (1,2)</script>")
    List<Long> findIdsByDoctors(@Param("doctorIds") List<Long> doctorIds,
        @Param("advisoryType") Integer advisoryType);

    @Select("<script>select id,doctor_id,patient_id,start_time,end_time,status,advisory_type,im_group_id from " +
            "patient_advisory_info where id in <foreach collection=\"ids\" " +
            "item=\"id\" open=\"(\" separator=\",\" close=\")" + "\">#{id}</foreach> </script>")
    List<AdvisoryUserInfoModel> findByIds(@Param("ids") List<Long> ids);


    @Select("select id,doctor_id,patient_id,order_num,start_time,status,source from patient_advisory_info where status =2 and source =#{source} and start_time < #{time}")
    List<PatientAdvisoryInfo> findExpireBySourceAndTime(@Param("source") String source,
        @Param("time") Date time);

    @Update("update patient_advisory_info set patient_id = #{destPatientId} where patient_id = #{srcPatientId}")
    int batchUpdatePatientId(@Param("srcPatientId") Long srcPatientId,
        @Param("destPatientId") Long destPatientId);
}
