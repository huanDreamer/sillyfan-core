package top.sillyfan.dao.mybatis.typehandler.util;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class TypeHandlerUtil {

	private static Logger log = LoggerFactory.getLogger(TypeHandlerUtil.class);

	private static ObjectMapper objectMapper = null;

	static {

		objectMapper = new ObjectMapper();

		// objectMapper.setDateFormat(new SimpleDateFormat("yyyyMMdd"));
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setFilters(new SimpleFilterProvider()
				.setFailOnUnknownId(false));
	}

	public static <T> String stringify(T object) {

		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public static <T> String stringify(T object, String... properties) {

		try {
			return objectMapper.writer(
					new SimpleFilterProvider().addFilter(
							AnnotationUtils
									.getValue(
											AnnotationUtils.findAnnotation(
													object.getClass(),
													JsonFilter.class))
									.toString(), SimpleBeanPropertyFilter
									.filterOutAllExcept(properties)))
					.writeValueAsString(object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public static <T> void stringify(OutputStream out, T object) {

		try {
			objectMapper.writeValue(out, object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static <T> void stringify(OutputStream out, T object,
			String... properties) {

		try {
			objectMapper.writer(
					new SimpleFilterProvider().addFilter(
							AnnotationUtils
									.getValue(
											AnnotationUtils.findAnnotation(
													object.getClass(),
													JsonFilter.class))
									.toString(), SimpleBeanPropertyFilter
									.filterOutAllExcept(properties)))
					.writeValue(out, object);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static <T> T parse(String json, Class<T> clazz) {

		if (json == null || json.length() == 0) {
			return null;
		}

		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

}
