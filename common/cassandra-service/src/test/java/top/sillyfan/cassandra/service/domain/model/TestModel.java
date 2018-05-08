package top.sillyfan.cassandra.service.domain.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("test")
public class TestModel {

	@PrimaryKey
	TestModelKey key;

	@Column("name")
	private String name;

	@Column("age")
	private Integer age;

	public TestModelKey getKey() {
		return key;
	}

	public void setKey(TestModelKey key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TestModel testModel = (TestModel) o;

		if (key != null ? !key.equals(testModel.key) : testModel.key != null)
			return false;
		if (name != null ? !name.equals(testModel.name)
				: testModel.name != null)
			return false;
		return age != null ? age.equals(testModel.age) : testModel.age == null;
	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		return result;
	}
}
