package top.sillyfan.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 提供redis的Hash操作服务
 */
public interface RedisHashService {

	/**
	 * 保存数据到 Redis
	 * 
	 * @param key
	 * @param value
	 */
	void setValue(String key, Map<String, String> value);

	/**
	 * 从 Redis中读取值
	 * 
	 * @param key
	 * @return
	 */
	Map<String, String> getValue(String key);

	/**
	 * 从hash中获取值 hash中列的值只能是string
	 *
	 * @param key
	 *            redis key
	 * @param hashKey
	 *            hash key
	 * @return
	 */
	Optional<String> getValue(String key, String hashKey);

	/**
	 * 从 Redis 中删除
	 * 
	 * @param key
	 * @param fields
	 */
	void deleteValue(String key, List<String> fields);

	/**
	 * 计数器
	 * 
	 * @param key
	 * @param hashKey
	 * @param num
	 */
	void increment(String key, String hashKey, long num);

	/**
	 * 获取Redis Hash 操作对象
	 * 
	 * @return
	 */
	HashOperations<String, String, String> getHashOperations();

	/**
	 * 获取template
	 * 
	 * @return
	 */
	RedisTemplate getRedisTemplate();
}
