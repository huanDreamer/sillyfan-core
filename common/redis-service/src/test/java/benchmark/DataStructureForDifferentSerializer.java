package benchmark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.sillyfan.redis.Demo;
import top.sillyfan.redis.TestApplication;

/**
 * 测试hash形式对不同 serializer 下的存储数据结构差异,执行完毕后使用redis desktop manager 查看存储结构
 * <pre>
 *     | type    |  key    |  value   |
 *     | json    |  name   |   "abc"  |
 *     | string  |  name   |    abc   |
 *     | jdk     |  name   |    \xAC\x00\x05\x17   |
 * </pre>
 * <p>
 * https://docs.spring.io/spring-data/redis/docs/current/reference/html/#redis:serializer
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataStructureForDifferentSerializer {

	Logger logger = LoggerFactory
			.getLogger(DataStructureForDifferentSerializer.class);

	// hash mapper
	private HashMapper mapper = new Jackson2HashMapper(false);

	// 存储的测试数据
	private Demo demo;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Test
	public void test() {

		Demo dataJson = new Demo();
		dataJson.setName("DemoCache_jsonSerializer");
		dataJson.setAge(111);

		Demo dataStr = new Demo();
		dataStr.setName("DemoCache_stringSerializer");
		// dataStr.setAge(222);

		Demo dataJdk = new Demo();
		dataJdk.setName("DemoCache_jdkSerializer");
		dataJdk.setAge(333);

		// Demo dataOxm = new Demo();
		// dataOxm.setName("DemoCache_oxmSerializer");
		// dataOxm.setAge(444);

		// 1 使用jsonSerializer 保存数据
		redisTemplate.setHashValueSerializer(
				new Jackson2JsonRedisSerializer(Object.class));
		redisTemplate.opsForHash().putAll(getHashKey(dataJson.getName()),
				mapper.toHash(dataJson));

		// 2 使用stringSerializer 保存数据
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		// 如果存储（如demo2）对象包含非string字段， 会报类型转换错误。 如java.lang.Integer cannot be
		// cast to java.lang.String
		redisTemplate.opsForHash().putAll(getHashKey(dataStr.getName()),
				mapper.toHash(dataStr));

		// 3 使用jdkSerializer 保存数据
		redisTemplate
				.setHashValueSerializer(new JdkSerializationRedisSerializer());

		redisTemplate.opsForHash().putAll(getHashKey(dataJdk.getName()),
				mapper.toHash(dataJdk));

	}

	//
	private String getHashKey(String key) {
		return "benchmark:hash_" + key;
	}
}
