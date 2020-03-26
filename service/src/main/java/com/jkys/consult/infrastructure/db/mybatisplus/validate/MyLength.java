package com.jkys.consult.infrastructure.db.mybatisplus.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MyLengthValidator.class})
public @interface MyLength {

  String message() default "String length does not match expected";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  //如果没有预设比较的值，比如验证邮箱或者手机号，身份证格式这种就可以没有value的属性
  int value();
}

