package top.sillyfan.redis;

import java.io.Serializable;

public class Demo implements Serializable{

    private String name;

    private Integer age;

    public Demo(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Demo() {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Demo demo = (Demo) o;

        if (name != null ? !name.equals(demo.name) : demo.name != null) return false;
        return age != null ? age.equals(demo.age) : demo.age == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }
}
