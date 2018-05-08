package top.sillyfan.common.func;

/**
 * @author kaihua
 * @date 2017/9/5
 */
@FunctionalInterface
public interface FiveFunction<T, U, S, D, E, R>  {
    /**
     * Applies this function to the given arguments.
     *
     * @param t
     *            the first function argument
     * @param u
     *            the second function argument
     * @param s
     *            the third function argument
     * @param s
     *            the fourth function argument
     * @param d
     *            the fifth function argument
     * @return the function result
     */
    R apply(T t, U u, S s, D d, E e);
}
