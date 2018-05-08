package top.sillyfan.api.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

public class ValidatorUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ValidatorUtils.class);

	/**
	 * 将错误放入errors中
	 * 
	 * @param errors
	 * @param obj
	 * @param validField
	 * @param errorCode
	 */
	public static void rejectError(Errors errors, Object obj, String validField,
			String errorCode) {

		Boolean validFieldResult = false;

		// 验证validField是否存在在form中
		final List<Field> allFields = new ArrayList<Field>();
		Class<?> currentClass = obj.getClass();
		while (currentClass != null) {
			final Field[] declaredFields = currentClass.getDeclaredFields();
			for (Field field : declaredFields) {
				allFields.add(field);
			}
			currentClass = currentClass.getSuperclass();
		}
		Field[] fields = allFields.toArray(new Field[allFields.size()]);

		for (Field field : fields) {
			if (field.getName().equals(validField)) {
				validFieldResult = true;
			}
		}

		if (!validFieldResult) {
			logger.error("验证字段{}在表单中不存在,验证错误{}.", validField, errorCode);
		}

		// 将错误放入validate的errors
		errors.rejectValue(validField, errorCode);

	}

}
