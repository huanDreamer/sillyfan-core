package top.sillyfan.common.util;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

	private final static Logger logger = LoggerFactory
			.getLogger(JsonMapper.class);

	private static ObjectMapper jsonMapper = new ObjectMapper();

	/**
	 * 将对象装成Json
	 * 
	 * @param src
	 * @return
	 */
	public static Optional<String> convertToJson(Object src) {

		try {
			return Optional.ofNullable(jsonMapper.writeValueAsString(src));
		} catch (Exception e) {
			logger.error("convert To Json error.", e);
			return Optional.empty();
		}

	}

	/**
	 * 将 Json 转成指定对象
	 * 
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> Optional<T> convert(String json, Class<T> valueType) {

		try {
			if (StringUtils.isBlank(json)) {
				return Optional.empty();
			}

			return Optional.ofNullable(jsonMapper.readValue(json, valueType));
		} catch (Exception e) {

			logger.error(
					String.format("convert json [%s] To value type [%s] Error.",
							json, valueType),
					e);
			return Optional.empty();
		}
	}

}
