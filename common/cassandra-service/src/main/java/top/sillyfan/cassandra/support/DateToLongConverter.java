package top.sillyfan.cassandra.support;

import java.util.Date;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class DateToLongConverter implements Converter<Date, Long> {

	@Override
	public Long convert(@Nullable Date source) {
		if (Objects.isNull(source)) {
			return null;
		}
		return source.getTime();
	}

}
