package top.sillyfan.redis.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import top.sillyfan.redis.service.RedisHashService;

@Service
public class RedisHashServiceImpl implements RedisHashService {

	private static final Logger logger = LoggerFactory
			.getLogger(RedisHashServiceImpl.class);

	@Autowired
	RedisTemplate redisTemplate;

	public HashOperations<String, String, String> getHashOperations() {
		return hashOperations;
	}

	private HashOperations<String, String, String> hashOperations;

	private HashMapper mapper = new Jackson2HashMapper(false);

	@Override
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void setValue(String key, Map<String, String> value) {
		hashOperations.putAll(key, value);
	}

	@Override
	public Map<String, String> getValue(String key) {

		return hashOperations.entries(key);
	}

	@Override
	public Optional<String> getValue(String key, String hashKey) {

		return Optional.ofNullable((String) hashOperations.get(key, hashKey));
	}

	@Override
	public void deleteValue(String key, List<String> fields) {
		hashOperations.delete(key, fields.toArray());
	}

	/**
	 * 计数器
	 *
	 * @param key
	 * @param hashKey
	 * @param num
	 */
	@Override
	public void increment(String key, String hashKey, long num) {
		hashOperations.increment(key, hashKey, num);
	}
}
