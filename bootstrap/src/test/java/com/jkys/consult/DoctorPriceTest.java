package com.jkys.consult;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.reponse.DoctorIncomeResponse;
import com.jkys.consult.request.DoctorIncomeRequest;
import com.jkys.consult.request.DoctorPriceRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.doctor.DoctorIncomeRpcService;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class DoctorPriceTest extends BaseTest {

  @Autowired
  private DoctorIncomeRpcService doctorIncomeRpcService;

  private static Stopwatch stopwatch;

  @BeforeClass
  public static void setUp() {
    stopwatch = Stopwatch.createStarted();
  }

  @AfterClass
  public static void afterAll() {
    log.error(stopwatch.toString());
  }

  /**
   * 查询医生咨询定价
   */
  @Test
  public void testGetDoctorPrice(){
    Integer price = doctorIncomeRpcService.getDoctorPrice(doctorId);
    Gson gson = new Gson();
    log.info(gson.toJson(price));
  }

  /**
   * 修改医生咨询定价
   */
  @Test
  public void testUpdateDoctorPrice(){
    DoctorPriceRequest request = DoctorPriceRequest.builder()
        .doctorId(doctorId)
        .price(200)
        .build();
    Boolean response = doctorIncomeRpcService.updateDoctorPrice(request);
    Gson gson = new Gson();
    log.info(gson.toJson(response));
  }

  /**
   * 查询医生咨询收入
   */
  @Test
  public void testQueryDoctorIncome(){
    DoctorIncomeRequest request = DoctorIncomeRequest.builder()
        .doctorId(doctorId)
        .date("202004")
        .build();
    PageRequest page = PageRequest.builder()
        .pageNo(1)
        .pageSize(5)
        .request(request)
        .build();

    DoctorIncomeResponse response = doctorIncomeRpcService.searchDoctorIncome(page);
    Gson gson = new Gson();
    log.info(gson.toJson(response));
  }

}