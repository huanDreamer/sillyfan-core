package benchmark;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;

import top.sillyfan.redis.Demo;
import top.sillyfan.redis.TestApplication;

/**
 * 在相同的Jackson2JsonRedisSerializer前提下 比较不同redis数据存储结构（ key/value、 hash）的查询效率
 *
 * <pre>
 *    如需生成报表，需添加JVM参数 -Djub.consumers=CONSOLE,H2 -Djub.db.file=./target/.benchmarks
 * </pre>
 */
@BenchmarkMethodChart(filePrefix = "target/report") // 报表的路径和文件名前缀
@BenchmarkHistoryChart(filePrefix = "target/report-history", labelWith = LabelType.CUSTOM_KEY, maxRuns = 50) // 历史数据报表参数
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BenchmarkForDifferentDataStructure extends AbstractBenchmark {

	Logger logger = LoggerFactory
			.getLogger(BenchmarkForDifferentDataStructure.class);

	// 循环多次 放大差异
	public static final Integer LOOPS_COUNT = 100;

	// hash mapper
	private HashMapper mapper = new Jackson2HashMapper(false);

	// 存储的测试数据
	private Demo demo;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostConstruct
	public void prepare() {

		Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(
				Object.class);
		// 设置相同的 serializer
		redisTemplate.setValueSerializer(jsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jsonRedisSerializer);

		logger.info(String.format(
				"\n==> valueSerializer=%s hashValueSerializer=%s\n",
				redisTemplate.getValueSerializer(),
				redisTemplate.getHashValueSerializer()));

		// 准备cache 数据
		demo = new Demo();
		demo.setName("DemoCache");
		demo.setAge(111111);

		// 以key/value 形式存储数据
		redisTemplate.opsForValue().set(getStringKey(demo.getName()), demo);
		// 以hash 形式存储数据
		redisTemplate.opsForHash().putAll(getHashKey(demo.getName()),
				mapper.toHash(demo));

	}

	/**
	 * 查询 key/value形式的数据
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 1)
	public void m_1_key_value() {
		for (int i = 0; i < LOOPS_COUNT; i++) {
			redisTemplate.opsForValue().get(getStringKey(demo.getName()));
		}
	}

	/**
	 * 查询 hash形式的数据
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 1)
	public void m_2_hash() {
		for (int i = 0; i < LOOPS_COUNT; i++) {
			mapper.fromHash(redisTemplate.opsForHash()
					.entries(getHashKey(demo.getName())));
		}
	}

	private String getStringKey(String key) {
		return "benchmark:string_" + key;
	}

	private String getHashKey(String key) {
		return "benchmark:hash_" + key;
	}
}
