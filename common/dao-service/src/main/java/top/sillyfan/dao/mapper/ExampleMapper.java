package top.sillyfan.dao.mapper;

import java.util.List;

public interface ExampleMapper<P, T, ID> extends BaseMapper<T, ID> {

    /**
     * 根据example进行查询多个
     *
     * @param params {@link P} 查询条件
     * @return
     */
    List<T> selectByExample(P params);

    /**
     * 根据example进行统计数量
     *
     * @param params {@link P} 查询条件
     * @return count
     */
    Integer countByExample(P params);

}
