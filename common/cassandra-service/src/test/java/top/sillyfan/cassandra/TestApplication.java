package top.sillyfan.cassandra;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import top.sillyfan.cassandra.config.CassandraRepositoryConfiguration;

@SpringBootApplication
@Import({ CassandraRepositoryConfiguration.class })
@PropertySource(value = {
		"classpath:config/application.properties" }, ignoreResourceNotFound = true)
public class TestApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TestApplication.class)
				.web(WebApplicationType.NONE).run(args);
	}

}
