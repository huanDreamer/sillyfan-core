package top.sillyfan.common.func;

@FunctionalInterface
public interface FourFunction<T, U, S, D, R> {
	/**
	 * Applies this function to the given arguments.
	 *
	 * @param t
	 *            the first function argument
	 * @param u
	 *            the second function argument
	 * @param s
	 *            the third function argument
	 * @param d
	 *            the fourth function argument
	 * @return the function result
	 */
	R apply(T t, U u, S s, D d);
}
