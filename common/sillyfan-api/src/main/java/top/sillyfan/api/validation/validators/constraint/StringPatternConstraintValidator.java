package top.sillyfan.api.validation.validators.constraint;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 是否为整数
 * 
 * @author zhanghuan
 *
 */
public class StringPatternConstraintValidator
		implements ConstraintValidator<StringPattern, String> {

	private static final Logger logger = LoggerFactory
			.getLogger(StringPatternConstraintValidator.class);

	private boolean notBlank;

	private String regex;

	@Override
	public void initialize(StringPattern constraintAnnotation) {
		notBlank = constraintAnnotation.notBlank();
		regex = constraintAnnotation.regex();
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {

		if (logger.isDebugEnabled()) {
			logger.debug(
					"Execute StringPatternConstraintValidator with str {}.",
					str);
		}

		if (notBlank && StringUtils.isBlank(str)) {
			return false;
		}

		if (StringUtils.isNotBlank(regex) && StringUtils.isNotBlank(str)) {
			return Pattern.matches(regex, str);
		}

		return true;
	}

}
