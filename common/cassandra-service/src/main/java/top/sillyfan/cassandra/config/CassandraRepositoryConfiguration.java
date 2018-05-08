package top.sillyfan.cassandra.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

import top.sillyfan.cassandra.support.DateToLongConverter;
import top.sillyfan.cassandra.support.NullIgnoredMappingCassandraConverter;

@Configurable
@ComponentScan(basePackages = { "top.sillyfan.cassandra" })
public class CassandraRepositoryConfiguration {

	/**
	 * {@link org.springframework.data.cassandra.core.convert.AbstractCassandraConverter}
	 *
	 *
	 * {@link org.springframework.data.cassandra.core.convert.MappingCassandraConverter#write}
	 *
	 * @param mapping
	 * @return
	 */
	@Bean
	public CassandraConverter cassandraConverter(
			CassandraMappingContext mapping) {

		MappingCassandraConverter c = new NullIgnoredMappingCassandraConverter(
				mapping);

		ConversionService conversionService = c.getConversionService();
		if (conversionService instanceof ConverterRegistry) {
			((ConverterRegistry) conversionService)
					.addConverter(new DateToLongConverter());
		}

		return c;
	}

}
