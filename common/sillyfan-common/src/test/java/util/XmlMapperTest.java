package util;

import javax.xml.bind.annotation.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.sillyfan.common.util.XmlMapper;

public class XmlMapperTest {

	private static final Logger logger = LoggerFactory
			.getLogger(XmlMapperTest.class);

	private Person person;

	@Before
	public void init() {
		person = new Person();

		person.setVersion(1.0F);
		person.setId(1234L);
		person.setName("test persom");
		person.setAge(12);
	}

	@Test
	public void testConvertToXml() {

		// 对象转换成xml
		String xml = XmlMapper.convertToXml(person)
				.orElseThrow(() -> new RuntimeException("转化成xml失败"));

		logger.info(xml);

		// xml 转换成对象
		Person p = XmlMapper.convertToObject(xml, Person.class)
				.orElseThrow(() -> new RuntimeException("转化成xml失败"));

		Assert.assertEquals(person.getAge(), p.getAge());
		Assert.assertEquals(person.getName(), p.getName());
		Assert.assertEquals(person.getId(), p.getId());
		Assert.assertEquals(person.getVersion(), p.getVersion());
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement(name = "Person")
	static class Person {

		@XmlAttribute(name = "version")
		private Float version;

		@XmlElement(name = "id")
		private Long id;

		@XmlElement(name = "name")
		private String name;

		@XmlElement(name = "age")
		private Integer age;

		public Float getVersion() {
			return version;
		}

		public void setVersion(Float version) {
			this.version = version;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person{" + "version=" + version + ", id=" + id + ", name='"
					+ name + '\'' + ", age=" + age + '}';
		}
	}
}
