<!-- TOC -->

- [1. Redis Collection设计灵感](#1-Redis-Collection%E8%AE%BE%E8%AE%A1%E7%81%B5%E6%84%9F)

<!-- /TOC -->
``` url
https://redis.io/commands
https://www.runoob.com/redis/redis-commands.html
https://blog.csdn.net/adsadadaddadasda/article/details/78829337

```

# 1. Redis Collection设计灵感
在使用Spring Data Redis发现其使用困难, 其学习成本较多, 既要学习Redis命令又要学习Spring Data Redis的API极其厌烦.

在其工作之中及其厌烦使用,在这种情况下逐渐的失去对学习的态度和心情, 经过不断的学习发现其所使用Spring Data Redis其可以使用 Java Collection接口实现其大部分Spring Data Redis的API, 经过一段时间的思索, 决定在最近的开发一款属于自己的Redis Collection框架.
 
>基础操作 

| 教程                       | sptring boot                                                                       | Redis Collection |
| -------------------------- | ---------------------------------------------------------------------------------- | ---------------- |
| Redis 键(key)              | <br>BoundKeyOperations                                                             |                  |
| Redis 字符串(String)       | ValueOperations<br>BoundValueOperations<br>ReactiveValueOperations                 |                  |
| Redis 哈希(Hash)           | HashOperations<br>BoundHashOperations<br>ReactiveHashOperations                    |                  |
| Redis 列表(List)           | ListOperations<br> BoundListOperations<br>ReactiveListOperations                   |                  |
| Redis 集合(Set)            | SetOperations<br>BoundSetOperations<br>ReactiveSetOperations                       |                  |
| Redis 有序集合(sorted set) | ZSetOperations<br>BoundZSetOperations<br>ReactiveZSetOperations                    |                  |
| Geo                        | GeoOperations<br>BoundGeoOperations<br>ReactiveGeoOperations                       |                  |
| Redis HyperLogLog          | HyperLogLogOperations<br>ReactiveHyperLogLogOperations                             |                  |
| Callback                   | RedisCallback<br>ReactiveRedisCallback<br>SessionCallback <br>RedisClusterCallback |                  |
| Redis                      | RedisOperations<br>ReactiveRedisOperations                                         |                  |
| Cluster                    | ClusterOperations<br>RedisClusterCallback                                          |                  |
|                            | Cursor<br>BulkMapper<br>TimeToLiveAccessor                                         |                  |

|注解|
|---|
|RedisHash|
|TimeToLive|
