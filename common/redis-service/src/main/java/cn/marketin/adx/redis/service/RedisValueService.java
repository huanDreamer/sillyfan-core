package top.sillyfan.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * @author huanxing
 * @date 7/7/17
 */
public interface RedisValueService {

	/**
	 * 从Redis读取值， 如果为null则使用指定函数获取值并存入Redis当中
	 *
	 * @param cacheKey
	 *            k
	 * @param readNullToFunction
	 *            但Redis值为null时执行函数取值
	 * @return r
	 */
	<T> Optional<T> getValue(String cacheKey,
			Function<Void, T> readNullToFunction, Class<T> valueType);

	/**
	 * 从Redis读取值
	 *
	 * @param redisKey
	 *            k
	 * @return r
	 */
	<T> Optional<T> getValue(String redisKey, Class<T> valueType);

	/**
	 * 从 Redis 批量获取值
	 *
	 * @param keys
	 *            keys
	 * @return
	 */
	<T> List<T> getValue(List<String> keys, Class<T> valueType);

	/**
	 * 保存一个对象到Redis
	 *
	 * @param redisKey
	 *            k
	 * @param value
	 *            v
	 */
	void setValue(String redisKey, Object value);

	/**
	 * 保存多个对象到Redis
	 * 
	 * @param value
	 *            对象的map
	 */
	void multiSetValue(Map<String, Object> value);

	/**
	 * 保存一个对象到Redis, 并指定过期的时间，单位：秒
	 *
	 * @param redisKey
	 *            k
	 * @param value
	 *            v
	 * @param timeoutInSecond
	 *            过期时间，秒
	 */
	void setValue(String redisKey, Object value, Long timeoutInSecond);

	/**
	 * 从redis删除数据
	 *
	 * @param key
	 *            k
	 */
	void deleteValue(String key);

	/**
	 * 从redis删除多条数据
	 * 
	 * @param keys
	 */
	void deleteValues(List<String> keys);

	/**
	 * 通过表达式查找key的集合
	 * 
	 * @param pattern
	 *            匹配表达式
	 * @return
	 */
	Set<String> keys(String pattern);
}
