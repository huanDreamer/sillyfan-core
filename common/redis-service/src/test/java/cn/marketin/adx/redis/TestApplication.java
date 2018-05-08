package top.sillyfan.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import top.sillyfan.redis.config.RedisConfigure;

@SpringBootApplication
@Import({ RedisConfigure.class })
public class TestApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
