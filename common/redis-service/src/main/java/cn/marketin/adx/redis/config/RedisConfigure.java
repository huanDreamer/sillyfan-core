package top.sillyfan.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author huanxing
 * @date 7/7/17
 */
@Configurable
@ComponentScan({ "top.sillyfan.redis" })
public class RedisConfigure {

	static Logger logger = LoggerFactory.getLogger(RedisConfigure.class);

	@Bean("redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(
			JedisConnectionFactory jedisConnectionFactory) {

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setDefaultSerializer(stringRedisSerializer);

		redisTemplate.setEnableDefaultSerializer(true);
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);

		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);

		return redisTemplate;
	}
}
