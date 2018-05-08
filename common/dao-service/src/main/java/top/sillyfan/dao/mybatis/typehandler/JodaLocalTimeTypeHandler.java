package top.sillyfan.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.LocalTime;

import top.sillyfan.common.date.DateUtil;

public class JodaLocalTimeTypeHandler implements TypeHandler<LocalTime> {

	@Override
	public void setParameter(PreparedStatement ps, int i, LocalTime parameter,
			JdbcType jdbcType) throws SQLException {

		java.sql.Time time = null;

		if (parameter != null) {
			LocalTime localtime = (LocalTime) parameter;
			time = DateUtil.convert(localtime);
		}

		ps.setTime(i, time);
	}

	@Override
	public LocalTime getResult(ResultSet rs, String columnName)
			throws SQLException {
		Object o = rs.getObject(columnName);

		if (rs.wasNull()) {
			return null;
		}

		if (o instanceof java.sql.Time) {
			return new LocalTime(((java.sql.Time) o).getTime());
		} else {
			throw new SQLException(
					"Failed to convert column[" + String.valueOf(columnName)
							+ "' to org.joda.time.LocalTime");
		}

	}

	@Override
	public LocalTime getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Object o = cs.getObject(columnIndex);

		if (cs.wasNull()) {
			return null;
		}

		if (o instanceof java.sql.Time) {
			return new LocalTime(((java.sql.Time) o).getTime());
		} else {
			throw new SQLException(
					"Failed to convert column[" + String.valueOf(columnIndex)
							+ "' to org.joda.time.LocalTime");
		}

	}

	@Override
	public LocalTime getResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Object o = rs.getObject(columnIndex);

		if (rs.wasNull()) {
			return null;
		}

		if (o instanceof java.sql.Time) {
			return new LocalTime(((java.sql.Time) o).getTime());
		} else {
			throw new SQLException(
					"Failed to convert column[" + String.valueOf(columnIndex)
							+ "' to org.joda.time.LocalTime");
		}
	}
}
