package top.sillyfan.dao.mybatis.typehandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import top.sillyfan.dao.mybatis.typehandler.util.TypeHandlerUtil;

/**
 * Json字符串转换为T。
 * 
 * @author guxiangguo
 *
 * @param <T>
 */
public abstract class JsonTypeHandler<T> extends BaseTypeHandler<T> {

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public JsonTypeHandler() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class<T>) params[0];
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Object parameter, JdbcType jdbcType) throws SQLException {

		ps.setString(i, TypeHandlerUtil.stringify(parameter));
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName)
			throws SQLException {

		String value = rs.getString(columnName);

		try {
			if (value != null && value !="") {
				value = new String(value.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage(), e);
		}
		
		return rs.wasNull() ? null
				: TypeHandlerUtil.<T> parse(value, entityClass);
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {

		String value = rs.getString(columnIndex);

		return rs.wasNull() ? null : TypeHandlerUtil.parse(value, entityClass);
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {

		String value = cs.getString(columnIndex);

		return cs.wasNull() ? null : TypeHandlerUtil.parse(value, entityClass);
	}
}
