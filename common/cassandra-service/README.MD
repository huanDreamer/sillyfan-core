#Cassandra-service

提供 Cassandra 的数据访问服务

##引用方式

1.调用方导入maven 依赖
```xml
<dependency>
    <groupId>top.sillyfan</groupId>
    <artifactId>redis-service</artifactId>
    <version>1.0</version>
</dependency>
```

2.调用方添加redis 链接信息，
```
#=======================
# Redis
#=======================
# Database index used by the connection factory.
spring.redis.database=0 

spring.redis.host=192.168.2.2
spring.redis.port=6379
# Max number of connections that can be allocated by the pool at a given time. Use a negative value for no limit.
spring.redis.pool.max-active=8
# Max number of "idle" connections in the pool. Use a negative value to indicate an unlimited number of idle connections.
spring.redis.pool.max-idle=8
# Maximum amount of time (in milliseconds) a connection allocation should block before throwing an exception when the pool is exhausted. Use a negative value to block indefinitely.
spring.redis.pool.max-wait=-1
# Target for the minimum number of idle connections to maintain in the pool. This setting only has an effect if it is positive.
spring.redis.pool.min-idle=2

```

3.调用方导入redis bean配置

```java
@Configuration
@Import({ RedisConfigure.class })
public class RedisConfiguration {

}
```

4.在需要使用redis地方注入redisService
```
@Autowired
RedisService redisService;
```
