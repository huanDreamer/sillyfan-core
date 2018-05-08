package benchmark;

import javax.annotation.PostConstruct;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;

import top.sillyfan.redis.Demo;
import top.sillyfan.redis.TestApplication;

/**
 * 对比不同 serializer 查询效率
 * <p>
 * 运行该文件，将依次执行 step 1 到 step 4,其中step2 和step4 分别使用不同的serializer进行查询
 * 
 * <pre>
 * 如需生成报表，需添加JVM参数 -Djub.consumers=CONSOLE,H2 -Djub.db.file=./target/.benchmarks
 *
 * </pre>
 */
@BenchmarkMethodChart(filePrefix = "target/report") // 报表的路径和文件名前缀
@BenchmarkHistoryChart(filePrefix = "target/report-history", labelWith = LabelType.CUSTOM_KEY, maxRuns = 20) // 历史数据报表参数
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BenchmarkForDifferentSerializer extends AbstractBenchmark {

	Logger logger = LoggerFactory
			.getLogger(BenchmarkForDifferentSerializer.class);

	// 循环多次 放大差异
	public static final Integer LOOPS_COUNT = 300;

	// hash mapper
	private HashMapper mapper = new Jackson2HashMapper(false);

	// 存储的测试数据
	private Demo demo;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostConstruct
	public void prepare() {

		// 准备cache 数据
		demo = new Demo();
		demo.setName("DemoCache");
	}


	public void setCache(){
		// 以hash 形式存储数据
		redisTemplate.opsForHash().putAll(getHashKey(demo.getName()),
				mapper.toHash(demo));
	}

	/**
	 * Step 1: 设置JsonSerializer
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
	public void m_1_setJsonSerializer() {
		redisTemplate.setHashValueSerializer(
				new Jackson2JsonRedisSerializer(Object.class));

		setCache();

	}

	/**
	 * Step 2: 使用JsonSerializer查询
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 1)
	public void m_2_searchWithJsonSerializer() {
		// logger.info("===> m2, current
		// serializer="+redisTemplate.getHashValueSerializer());
		for (int i = 0; i < LOOPS_COUNT; i++) {
			redisTemplate.opsForHash().entries(getHashKey(demo.getName()));
		}

	}

	/**
	 * Step 3: 设置StringSerializer
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
	public void m_3_setStringSerializer() {
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		setCache();
	}

	/**
	 * Step 4: 使用StringSerializer查询
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 1)
	public void m_4_searchWithStringSerializer() {
		// logger.info("=======> m4, current
		// serializer="+redisTemplate.getHashValueSerializer());
		for (int i = 0; i < LOOPS_COUNT; i++) {
			redisTemplate.opsForHash().entries(getHashKey(demo.getName()));
		}
	}


	/**
	 * Step 5: 设置 JdkSerializationRedisSerializer
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
	public void m_5_setjdkSerializer() {
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		setCache();
	}

	/**
	 * Step 6: 使用JdkSerializationRedisSerializer查询
	 */
	@Test
	@BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 1)
	public void m_6_searchWithJdkSerializer() {
		// logger.info("=======> m6, current
		// serializer="+redisTemplate.getHashValueSerializer());
		for (int i = 0; i < LOOPS_COUNT; i++) {
			redisTemplate.opsForHash().entries(getHashKey(demo.getName()));
		}
	}

	private String getHashKey(String key) {
		return "benchmark:hash_" + key;
	}

}
