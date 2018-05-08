package top.sillyfan.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.sillyfan.redis.service.RedisValueService;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

	private Logger logger = LoggerFactory.getLogger(RedisTest.class);

	@Autowired
    RedisValueService redisService;

	/**
	 * 测试Redis 设值设值、取值
	 */
	@Test
	public void testSetKey() {

		String key = "test:ads:pb:timeout";
		String value = "hello world !!!";
		redisService.setValue(key, value);

		Assert.assertEquals(value,
				redisService.getValue(key, String.class).orElse(""));
	}

	/**
	 * 测试Redis 过期
	 */
	@Test
	public void testTimeout() throws InterruptedException {
		String key = "test:ads:pb:timeout";
		String value = "hello world";
		Long timeoutSecond = 3L;
		redisService.setValue(key, value, timeoutSecond);

		// 等待到达过期时间
		Thread.sleep(timeoutSecond * 1000);

		Assert.assertNull(
				redisService.getValue(key, String.class).orElse(null));
	}

}
