package top.sillyfan.cassandra.protocal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.core.query.Query;

/**
 * 提供Cassandra的常用操作服务
 */
public interface CassandraService {

	/**
	 * 向cassandra插入数据
	 * 
	 * @param value
	 *            数据对象
	 */
	void insert(Object value);

	/**
	 * 删除一条数据
	 *
	 * @param pk
	 *            primary key
	 * @param javaType
	 *
	 * @return
	 */
	boolean deleteByPrimaryKey(Object pk, Class<?> javaType);

	/**
	 * 更新数据
	 *
	 * @param value
	 */
	void update(Object value);

	/**
	 * 查询一条数据
	 *
	 * @param query
	 *            查询条件
	 * @param javaType
	 * @param <T>
	 * @return
	 */
	<T> Optional<T> getOne(Query query, Class<T> javaType);

	/**
	 * 根据主键进行查询
	 * 
	 * @param pk
	 *            主键
	 * @param javaType
	 * @param <T>
	 * @return
	 */
	<T> Optional<T> getOne(Object pk, Class<T> javaType);

	/**
	 * 查询多条数据
	 * 
	 * @param query
	 *            查询条件
	 * @param javaType
	 * @param <T>
	 * @return
	 */
	<T> List<T> find(Query query, Class<T> javaType);
}
