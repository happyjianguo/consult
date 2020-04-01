package com.jkys.consult.web;

import com.jkys.consult.logic.ConsultLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consult")
public class ConsultController {

  @Autowired
  ConsultLogic consultLogic;

  @PostMapping("/createConsult")
  public String createConsult() throws Exception {
    long doctorId = 1L;
    long patientId = 1L;
    int consultType = 1;
    String result = consultLogic.createConsult(doctorId, patientId, consultType);
    return result;
  }

}
