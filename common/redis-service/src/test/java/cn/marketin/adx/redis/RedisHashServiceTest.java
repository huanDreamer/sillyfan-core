package top.sillyfan.redis;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import top.sillyfan.redis.service.RedisHashService;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisHashServiceTest {

	private static final Logger logger = LoggerFactory
			.getLogger(RedisHashService.class);

	@Autowired
	RedisHashService hashService;

	private ObjectMapper mapper;

	private HashMap<String, String> map;

	private Demo demo;

	@Before
	public void init() throws JsonProcessingException {
		mapper = new ObjectMapper();

		demo = new Demo();
		demo.setName("123");
		demo.setAge(11);

		map = new HashMap<>();
		map.put(demo.getName(), mapper.writeValueAsString(demo));
	}

	/**
	 * 测试保存值，并获取整个hash
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testSave() throws IOException {

		hashService.setValue("test:hash:123", map);

		Assert.assertEquals(demo, mapper.readValue(
				hashService.getValue("test:hash:123").get(demo.getName()),
				Demo.class));
	}

	/**
	 * 测试从hash中获取值
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void getHashValue() throws JsonProcessingException {

		hashService.setValue("test:hash:123", map);

		Assert.assertEquals(mapper.writeValueAsString(demo),
				hashService.getValue("test:hash:123", "123").get());
	}
}
