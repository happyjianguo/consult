package com.jkys.consult.infrastructure.db.mybatisplus.validate;

import com.jkys.consult.infrastructure.db.mybatisplus.validate.payload.Severity.Error;
import com.jkys.consult.infrastructure.db.mybatisplus.validate.payload.Severity.Info;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MyLengthValidator implements ConstraintValidator<MyLength, String> {

  private int expectedLength;

  @Override
  public void initialize(MyLength myLength) {
    this.expectedLength = myLength.value();
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

    HibernateConstraintValidatorContext context = constraintValidatorContext.unwrap(
        HibernateConstraintValidatorContext.class);

    if (!ObjectUtils.isEmpty(context.withDynamicPayload(Error.class))) {
      System.err.println("当前验证字段为Error级别");
    }
    if (!ObjectUtils.isEmpty(context.withDynamicPayload(Info.class))) {
      System.err.println("当前验证字段为Info级别");
    }
//    if(!ObjectUtils.isEmpty(context.getConstraintValidatorPayload(Severity.Error.class))){
//      System.err.println("当前验证字段为Error级别");
//    }
//    if(!ObjectUtils.isEmpty(context.getConstraintValidatorPayload(Severity.Info.class))){
//      System.err.println("当前验证字段为Info级别");
//    }

//    //两次密码不一样
//    if (!user.getPassword().trim().equals(user.getConfirmation().trim())) {
//      context.disableDefaultConstraintViolation();
//      context.buildConstraintViolationWithTemplate("{password.confirmation.error}")
//          .addPropertyNode("confirmation")
//          .addConstraintViolation();
//      return false;
//    }

    // TODO
    // 在addConstraintViolation之前把参数放进去，就可以创建出不同的ConstraintViolation了
    // 若不这么做，所有的 ConstraintViolation 取值都是一样的喽~~~
    if (!(s == null || s.length() >= this.expectedLength)) {
      context.disableDefaultConstraintViolation();
      context.addMessageParameter("length", s.length());
      context.addMessageParameter("expectedLength", expectedLength);
      context.buildConstraintViolationWithTemplate("长度{length}不足{expectedLength}")
          .addConstraintViolation();
      return false;
    }
    return s == null || s.length() >= this.expectedLength;
  }
}