package top.sillyfan.redis.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations;

public interface RedisZSetService {

	/**
	 * 保存zset信息
	 * 
	 * @param key
	 *            key
	 * @param typedTuples
	 *            要保存的值
	 */
	void saveValues(String key,
			Set<ZSetOperations.TypedTuple<String>> typedTuples);

	/**
	 * 返回降序集合中指定分数区间内的成员
	 * 
	 * @param key
	 *            key
	 * @param min
	 *            score最小值
	 * @param max
	 *            score最大值
	 * @param offset
	 *            分页偏移
	 * @param count
	 *            总条数
	 * @return
	 */
	Set<String> reverseRangeByScore(String key, Double min, Double max,
			Long offset, Long count);

	/**
	 * 返回降序集合中指定分数区间内的成员 带score
	 *
	 * @param key
	 *            key
	 * @param min
	 *            score的最小值
	 * @param max
	 *            score的最大值
	 * @param offset
	 *            分页偏移
	 * @param count
	 *            总条数
	 * @return
	 */
	Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(
			String key, Double min, Double max, Long offset, Long count);

	/**
	 * 返回升序集合中指定分数区间内的成员 带score
	 *
	 * @param key
	 *            key
	 * @param min
	 *            score的最小值
	 * @param max
	 *            score的最大值
	 * @param offset
	 *            分页偏移
	 * @param count
	 *            总条数
	 * @return
	 */
	Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key,
			Double min, Double max, Long offset, Long count);

	/**
	 * 返回升序集合中指定分数区间的成员
	 * 
	 * @param key
	 *            key
	 * @param min
	 *            score最小值
	 * @param max
	 *            score最大值
	 * @return
	 */
	Set<String> rangeByScore(String key, double min, double max);

	/**
	 * 从zset中删除值
	 * 
	 * @param key
	 *            zset的key
	 * @param values
	 *            要删除的值
	 */
	void remove(String key, List<String> values);

	/**
	 * 获取指定区间内的元素
	 *
	 * @param key
	 *            key
	 * @param start
	 *            开始下标 从 0 开始，结果包括开始下标的元素
	 * @param end
	 *            结束下标 结果包括结束下标的元素。当结束下标等于开始下标，返回当前元素
	 * @return
	 */
	Set<String> range(String key, Long start, Long end);
}
