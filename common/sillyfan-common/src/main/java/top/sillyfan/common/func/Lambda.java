package top.sillyfan.common.func;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda {

	/**
	 * 根据对象的属性去重
	 * <p>
	 * 用法: list.stream().filter(Lambda.distinctByKey(p -> p.getXX()))
	 *
	 */
	public static <T> Predicate<T> distinctByKey(
			Function<? super T, Object> target) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();

		return t -> seen.putIfAbsent(target.apply(t), Boolean.TRUE) == null;
	}

}
