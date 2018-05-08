package top.sillyfan.redis.service;

import java.util.List;
import java.util.Set;

/**
 * 提供 Redis 的 Set 操作服务
 */
public interface RedisSetService {

	/**
	 * 添加元素到set中
	 * 
	 * @param key
	 *            set的key
	 * @param values
	 *            要添加的元素
	 * @return
	 */
	Long add(String key, String... values);

	/**
	 * 从set中删除元素
	 *
	 * @param key
	 *            set的key
	 * @param values
	 *            要删除的元素
	 * @return
	 */
	Long remove(String key, String... values);

	/**
	 * 获取set中的所有元素
	 * 
	 * @param key
	 *            set的key
	 * @return
	 */
	Set<String> getValues(String key);

	/**
	 * 获取set的元素个数
	 * 
	 * @param key
	 *            key
	 * @return
	 */
	Long getSize(String key);

	/**
	 * 根据多个key查找多个set的并集
	 *
	 * @param keys
	 *            key
	 * @return
	 */
	Set<String> union(List<String> keys);

	/**
	 * 根据多个key查找多个set的交集
	 *
	 * @param keys
	 *            key
	 * @return
	 */
	Set<String> intersect(List<String> keys);

}
