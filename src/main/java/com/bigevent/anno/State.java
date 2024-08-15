package com.bigevent.anno;

import com.bigevent.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD}) // 注解作用在字段上
@Retention(RUNTIME) // 注解保留在运行时
@Constraint(validatedBy = {StateValidation.class}) // 指定提供校验规则的类
public @interface State {
    // 校验失败后提示信息
    String message() default "state参数的值只能是已发布或草稿";

    // 指定分组
    Class<?>[] groups() default {};

    // 指定负载，获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
