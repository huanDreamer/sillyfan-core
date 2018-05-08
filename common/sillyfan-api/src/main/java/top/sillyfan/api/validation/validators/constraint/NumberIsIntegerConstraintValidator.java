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
public class NumberIsIntegerConstraintValidator
		implements ConstraintValidator<NumberIsInteger, String> {

	private static final Logger logger = LoggerFactory
			.getLogger(NumberIsIntegerConstraintValidator.class);

	@Override
	public void initialize(NumberIsInteger constraintAnnotation) {

	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {

		if (logger.isDebugEnabled()) {
			logger.debug(
					"Execute NumberIsIntegerConstraintValidator with str {}.",
					str);
		}

		try {

			Integer.parseInt(str);

			return true;

		} catch (Exception e) {
			return false;
		}
	}

}
