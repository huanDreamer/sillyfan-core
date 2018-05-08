package top.sillyfan.redis.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import top.sillyfan.redis.service.RedisSetService;

@Service
public class RedisSetServiceImpl implements RedisSetService {

	private static final Logger logger = LoggerFactory
			.getLogger(RedisSetServiceImpl.class);

	@Autowired
	RedisTemplate redisTemplate;

	private SetOperations<String, String> setOperations;

	@PostConstruct
	private void init() {
		setOperations = redisTemplate.opsForSet();
	}

	@Override
	public Long add(String key, String... values) {
		return setOperations.add(key, values);
	}

	@Override
	public Long remove(String key, String... values) {
		return setOperations.remove(key, values);
	}

	@Override
	public Set<String> getValues(String key) {
		return setOperations.members(key);
	}

	@Override
	public Long getSize(String key) {
		return setOperations.size(key);
	}

	@Override
	public Set<String> union(List<String> keys) {
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}
		return setOperations.union(keys.get(0), keys);
	}

	@Override
	public Set<String> intersect(List<String> keys) {
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}
		return setOperations.intersect(keys.get(0), keys);
	}

}
