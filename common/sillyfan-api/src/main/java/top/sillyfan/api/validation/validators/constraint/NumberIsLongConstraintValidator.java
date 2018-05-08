package top.sillyfan.api.validation.validators.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 是否为整数
 * 
 * @author zhanghuan
 *
 */
public class NumberIsLongConstraintValidator
		implements ConstraintValidator<NumberIsLong, String> {

	private static final Logger logger = LoggerFactory
			.getLogger(NumberIsLongConstraintValidator.class);

	@Override
	public void initialize(NumberIsLong constraintAnnotation) {

	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {

		if (logger.isDebugEnabled()) {
			logger.debug("Execute NumberIsLongConstraintValidator with str {}.",
					str);
		}

		try {

			Long.parseLong(str);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

}
