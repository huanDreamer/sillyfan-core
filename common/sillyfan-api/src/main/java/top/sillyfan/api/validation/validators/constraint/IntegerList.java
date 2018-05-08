package top.sillyfan.api.validation.validators.constraint;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 是否为整数
 * 
 * @author zhanghuan
 *
 */
@Target({ FIELD, PARAMETER, LOCAL_VARIABLE, METHOD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { IntegerListConstraintValidator.class })
public @interface IntegerList {

	String message() default "";

	int[] allowValues();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
