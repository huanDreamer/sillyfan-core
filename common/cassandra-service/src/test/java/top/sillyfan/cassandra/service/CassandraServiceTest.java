package top.sillyfan.cassandra.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.sillyfan.cassandra.TestApplication;
import top.sillyfan.cassandra.config.CassandraRepositoryConfiguration;
import top.sillyfan.cassandra.protocal.CassandraService;
import top.sillyfan.cassandra.service.domain.model.TestModel;
import top.sillyfan.cassandra.service.domain.model.TestModelKey;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Import({ CassandraRepositoryConfiguration.class })
public class CassandraServiceTest {

	private static final Logger logger = LoggerFactory
			.getLogger(CassandraServiceTest.class);

	@Autowired
	CassandraService cassandraService;

	/**
	 * 测试保存数据
	 */
	@Test
	public void testSaveValue() {

		TestModel model = new TestModel();

		TestModelKey key = new TestModelKey();
		key.setId(3);
		model.setKey(key);
		model.setName("test3");
		model.setAge(1);

		cassandraService.insert(model);
		TestModel model2 = cassandraService.getOne(key, TestModel.class).get();

		Assert.assertEquals(model, model2);
	}

	/**
	 * 测试插入数据，忽略null
	 */
	@Test
	public void testSaveValueIgnoreNull() {

		TestModel model = new TestModel();

		TestModelKey key = new TestModelKey();
		key.setId(3);
		model.setKey(key);
		model.setAge(null);
		model.setName("null test");

		cassandraService.insert(model);
		TestModel model2 = cassandraService.getOne(key, TestModel.class).get();

		// 因为model的age设置成了null，所以数据库不会进行更新，所以两个对象不相等
		Assert.assertNotEquals(model, model2);
	}

	/**
	 * 测试获取数据
	 */
	@Test
	public void testGetValue() {

		TestModelKey key = new TestModelKey();
		key.setId(3);

		TestModel model = cassandraService.getOne(key, TestModel.class).get();

		logger.info(model.toString());
	}

	/**
	 * 测试修改数据
	 */
	@Test
	public void testUpdate() {
		TestModel model = new TestModel();

		TestModelKey key = new TestModelKey();
		key.setId(3);
		model.setKey(key);
		model.setName("test update");
		model.setAge(12);

		cassandraService.update(model);
		TestModel model2 = cassandraService.getOne(key, TestModel.class).get();

		Assert.assertEquals(model, model2);
	}

	/**
	 * 测试修改数据，忽略null值
	 */
	@Test
	public void testUpdateIgnoreNull() {
		TestModel model = new TestModel();

		TestModelKey key = new TestModelKey();
		key.setId(3);
		model.setKey(key);
		model.setName("test update ignore null");

		cassandraService.update(model);
		TestModel model2 = cassandraService.getOne(key, TestModel.class).get();

		Assert.assertNotEquals(model, model2);
	}

	/**
	 * 测试删除数据
	 */
	@Test
	public void testDelete() {
		TestModelKey key = new TestModelKey();
		key.setId(3);
		boolean succ = cassandraService.deleteByPrimaryKey(key,
				TestModel.class);

		Assert.assertEquals(succ, Boolean.TRUE);
	}

}
