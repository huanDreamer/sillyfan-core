package top.sillyfan.dao.mapper;

public interface BaseMapper<T, ID> {

    int deleteByPrimaryKey(ID id);

    int insert(T record);


    int insertSelective(T record);

    T selectByPrimaryKey(ID id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
