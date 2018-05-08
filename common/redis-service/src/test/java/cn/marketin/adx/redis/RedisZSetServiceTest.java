package top.sillyfan.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.sillyfan.common.util.JsonMapper;
import top.sillyfan.redis.service.RedisZSetService;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisZSetServiceTest {

	private Logger logger = LoggerFactory.getLogger(RedisZSetServiceTest.class);

	@Autowired
	RedisZSetService zSetService;

	/**
	 * 测试Redis 保存string
	 */
	@Test
	public void testSetKey() {

		Set<ZSetOperations.TypedTuple<String>> typedTuples = new HashSet<>();

		typedTuples.add(new DefaultTypedTuple("test 10", 10D));
		typedTuples.add(new DefaultTypedTuple("test 100", 100D));
		typedTuples.add(new DefaultTypedTuple("test 14", 14D));
		typedTuples.add(new DefaultTypedTuple("test 2", 2D));

		zSetService.saveValues("test:zSet", typedTuples);
	}

	/**
	 * 获取string
	 */
	@Test
	public void testGetValue() {
		Set<String> strs = zSetService.reverseRangeByScore("test:zSet", 10D,
				100D, 0L, 10L);

		Set<String> expected = new HashSet<>();
		expected.add("test 10");
		expected.add("test 100");
		expected.add("test 14");

		Assert.assertEquals(strs, expected);
	}

	/**
	 * 存储对象
	 */
	@Test
	public void testSetObject() {

		Set<ZSetOperations.TypedTuple<String>> typedTuples = new HashSet<>();

		typedTuples.add(new DefaultTypedTuple(
				JsonMapper.convertToJson(new Demo("test 10", 10)).get(), 10D));
		typedTuples.add(new DefaultTypedTuple(
				JsonMapper.convertToJson(new Demo("test 11", 11)).get(), 11D));
		typedTuples.add(new DefaultTypedTuple(
				JsonMapper.convertToJson(new Demo("test 15", 15)).get(), 15D));
		typedTuples.add(new DefaultTypedTuple(
				JsonMapper.convertToJson(new Demo("test 2", 2)).get(), 2D));

		zSetService.saveValues("test:zSet2", typedTuples);
	}

	/**
	 * 获取对象
	 */
	@Test
	public void testGetObject() {
		Set<Demo> objs = zSetService
				.reverseRangeByScore("test:zSet2", Double.MIN_VALUE, Double.MAX_VALUE, 0L, Long.MAX_VALUE).stream()
				.map(s -> {
					return JsonMapper.convert(s, Demo.class).get();
				}).collect(Collectors.toSet());

		logger.info(JsonMapper.convertToJson(objs).get());

		Set<Demo> expected = new HashSet<>();
		expected.add(new Demo("test 10", 10));
		expected.add(new Demo("test 11", 11));
		expected.add(new Demo("test 15", 15));
		expected.add(new Demo("test 2", 2));

		Assert.assertEquals(objs, expected);
	}

	/**
	 * 通过score获取ip
	 */
	@Test
	public void testGetIp() {

		zSetService.reverseRangeByScore("ip", 0D, 1744527361D, 0L, 1L);

		Long startTime = System.currentTimeMillis();
		Set<String> value = zSetService.reverseRangeByScore("ip", 0D,
				1744527361D, 0L, 1L);
		Long endTime = System.currentTimeMillis();
		logger.info("time cost: {}", endTime - startTime);
		logger.info(JsonMapper.convertToJson(value).get());
	}

	/**
	 * 通过下标获取元素
	 */
	@Test
	public void testRang() {
		Set<String> strs = zSetService.range("test:zSet", 0L, 0L);

		logger.info(JsonMapper.convertToJson(strs).get());
	}

}
