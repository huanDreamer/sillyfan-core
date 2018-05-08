package top.sillyfan.cassandra.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;

import top.sillyfan.cassandra.protocal.CassandraService;

@Service
public class CassandraServiceImpl implements CassandraService {

	@Autowired(required = true)
	CassandraTemplate template;

	@Override
	public void insert(Object value) {
		template.insert(value);
	}

	@Override
	public boolean deleteByPrimaryKey(Object pk, Class<?> javaType) {
		return template.deleteById(pk, javaType);
	}

	@Override
	public void update(Object value) {
		template.update(value);
	}

	@Override
	public <T> Optional<T> getOne(Query query, Class<T> javaType) {
		return Optional.ofNullable(template.selectOne(query, javaType));
	}

	@Override
	public <T> Optional<T> getOne(Object pk, Class<T> javaType) {
		return Optional.ofNullable(template.selectOneById(pk, javaType));
	}

	@Override
	public <T> List<T> find(Query query, Class<T> javaType) {
		return template.select(query, javaType);
	}
}
