package top.sillyfan.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.sillyfan.common.util.JsonMapper;
import top.sillyfan.redis.service.RedisValueService;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisObjectTest {

	private Logger logger = LoggerFactory.getLogger(RedisObjectTest.class);

	@Autowired
    RedisValueService redisService;

	/**
	 * 测试Redis 设值设值、取值
	 */
	@Test
	public void testSetKey() {
		Demo demo = new Demo();
		demo.setName("123");
		demo.setAge(11);
		redisService.setValue(demo.getName(), demo);

		Assert.assertEquals(JsonMapper.convertToJson(demo),
				JsonMapper.convertToJson(redisService
						.getValue(demo.getName(), Demo.class).get()));
	}

}
