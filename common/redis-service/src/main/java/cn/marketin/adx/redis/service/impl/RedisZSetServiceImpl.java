package top.sillyfan.redis.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import top.sillyfan.redis.service.RedisZSetService;

@Service
public class RedisZSetServiceImpl implements RedisZSetService {

	private final static Logger logger = LoggerFactory
			.getLogger(RedisZSetServiceImpl.class);

	@Autowired
	RedisTemplate redisTemplate;

	private ZSetOperations<String, String> ops;

	@PostConstruct
	private void init() {
		ops = redisTemplate.opsForZSet();
	}

	@Override
	public void saveValues(String key,
			Set<ZSetOperations.TypedTuple<String>> typedTuples) {

		ops.add(key, typedTuples);
	}

	@Override
	public Set<String> reverseRangeByScore(String key, Double min, Double max,
			Long offset, Long count) {
		return ops.reverseRangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(
			String key, Double min, Double max, Long offset, Long count) {
		return ops.reverseRangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(
			String key, Double min, Double max, Long offset, Long count) {
		return ops.rangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<String> rangeByScore(String key, double min, double max) {
		return ops.rangeByScore(key, min, max);
	}

	@Override
	public void remove(String key, List<String> values) {
		ops.remove(key, values.toArray());
	}

	@Override
	public Set<String> range(String key, Long start, Long end) {
		return ops.range(key, start, end);
	}
}
