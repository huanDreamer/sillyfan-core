package top.sillyfan.redis.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import top.sillyfan.common.util.JsonMapper;
import top.sillyfan.redis.service.RedisValueService;

/**
 * Redis 基础操作
 *
 * @author huanxing
 * @date 7/7/17
 */
@Service
public class RedisValueServiceImpl implements RedisValueService {

	private final static Logger logger = LoggerFactory
			.getLogger(RedisValueServiceImpl.class);

	@Autowired
	RedisTemplate redisTemplate;

	ValueOperations<String, String> ops;

	@PostConstruct
	public void init() {
		ops = redisTemplate.opsForValue();
	}

	@Override
	public <T> Optional<T> getValue(String cacheKey,
			Function<Void, T> readNullToFunction, Class<T> valueType) {
		return Optional.ofNullable(JsonMapper
				.convert(ops.get(cacheKey), valueType).orElseGet(() -> {
					T value = readNullToFunction.apply(null);
					setValue(cacheKey, value);
					return value;
				}));
	}

	@Override
	public <T> Optional<T> getValue(String redisKey, Class<T> valueType) {
		return Optional.ofNullable(ops.get(redisKey)).map(json -> {
			return JsonMapper.convert(json, valueType);
		}).orElse(Optional.empty());
	}

	@Override
	public <T> List<T> getValue(List<String> keys, Class<T> valueType) {
		List<String> caches = ops.multiGet(keys);

		return caches.stream().map(c -> {
			Optional<T> t = JsonMapper.convert(c, valueType);
			if (t.isPresent()) {
				return t.get();
			} else {
				return null;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public void setValue(String redisKey, Object value) {
		JsonMapper.convertToJson(value).map(json -> {
			ops.set(redisKey, json);
			return json;
		});
	}

	@Override
	public void multiSetValue(Map<String, Object> value) {

		Map<String, String> map = new HashMap<>();
		value.forEach((k, v) -> {
			JsonMapper.convertToJson(v).ifPresent(q -> {
				map.put(k, q);
			});
		});
		ops.multiSet(map);
	}

	@Override
	public void setValue(String redisKey, Object value, Long timeoutInSecond) {
		JsonMapper.convertToJson(value).map(json -> {

			ops.set(redisKey, json, timeoutInSecond, TimeUnit.SECONDS);
			return json;
		});
	}

	@Override
	public void deleteValue(String redisKey) {
		ops.getOperations().delete(redisKey);
	}

	@Override
	public void deleteValues(List<String> keys) {
		ops.getOperations().delete(keys);
	}

	@Override
	public Set<String> keys(String pattern) {
		return ops.getOperations().keys(pattern);
	}

}
