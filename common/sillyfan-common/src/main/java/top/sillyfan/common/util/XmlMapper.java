package top.sillyfan.common.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

import javax.xml.bind.JAXBContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapper {

	private final static Logger logger = LoggerFactory
			.getLogger(XmlMapper.class);

	/**
	 * 将对象转换成xml
	 *
	 * @param src
	 * @return
	 */
	public static Optional<String> convertToXml(Object src) {

		try {
			// 创建输出流
			StringWriter sw = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(src.getClass());

			context.createMarshaller().marshal(src, sw);

			return Optional.of(sw.toString());

		} catch (Exception e) {
			logger.error("convert To Xml error.", e);
			return Optional.empty();
		}
	}

	/**
	 * 将xml转换成对象
	 *
	 * @param xml
	 * @return
	 */
	public static <T> Optional<T> convertToObject(String xml,
			Class<T> javaType) {

		try {
			// 创建输出流
			StringReader sr = new StringReader(xml);
			JAXBContext context = JAXBContext.newInstance(javaType);

			T t = (T) context.createUnmarshaller().unmarshal(sr);

			return Optional.of(t);

		} catch (Exception e) {
			logger.error("convert To Xml error.", e);
			return Optional.empty();
		}
	}

}
