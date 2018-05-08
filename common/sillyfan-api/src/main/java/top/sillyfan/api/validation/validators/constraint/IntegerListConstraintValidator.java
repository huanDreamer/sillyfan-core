package top.sillyfan.api.validation.validators.constraint;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 是否为整数数组
 *
 * @author zhanghuan
 *
 */
public class IntegerListConstraintValidator
		implements ConstraintValidator<IntegerList, List<String>> {

	private static final Logger logger = LoggerFactory
			.getLogger(IntegerListConstraintValidator.class);

	private List<Integer> allowValues;

	@Override
	public void initialize(IntegerList constraintAnnotation) {

		allowValues = new ArrayList<>();

		for (int v : constraintAnnotation.allowValues()) {
			allowValues.add(v);
		}
	}

	@Override
	public boolean isValid(List<String> values,
			ConstraintValidatorContext context) {

		if (logger.isDebugEnabled()) {
			logger.debug("Execute IntegerListConstraintValidator with str {}.",
					values);
		}

		try {

			if (CollectionUtils.isEmpty(values)) {
				return true;
			}

			for (String str : values) {
				if (!allowValues.contains(Integer.parseInt(str))) {
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}

}
