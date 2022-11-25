

| 分类                      | 应用                   | net : host : container | 账号:密码        |
| ------------------------- | ---------------------- | ---------------------- | ---------------- |
| ssh                       | ssh                    | 5910:5900:             | -                |
| vnc                       | vnc                    | 5911:5900:             | -                |
| ssh                       | ssh                    | 1022:22:               | -                |
|                           |                        |                        |                  |
| Apache APISIX             | apisix                 | 9080:9080:9080         | token            |
| ^                         | ^                      | ::9091                 | -                |
| ^                         | ^                      | ::9092                 | -                |
| ^                         | ^                      | ::9443                 | -                |
| ^                         | apisix-web             | 19000:19000:9000       | admin:admin      |
| ^                         | etcd                   | ::2379                 | -                |
| ^                         | ~~grafana~~            | ~~::3000~~             | -                |
| ^                         | ~~prometheus~~         | ~~::9090~~             | -                |
| elk                       | logstash               | 5044:5044:5044         | -                |
| ^                         | Elasticsearch          | :9200:9200             | -                |
| ^                         | kibana                 | 5601:5601:5601         | -                |
| database                  | Redis                  | 6379:6379:6379         | :weichangjin     |
| ^                         | neo4j                  | 7687:7687:7687         | neo4j:secret     |
| ^                         | neo4j-web              | 7474:7474:7474         | ^                |
| ^                         | mysql5.7               | 3306:3306:3306         | root:weichangjin |
| ^                         | ~~mysql8.0~~           | ~~3308::~~             | -                |
| ^                         | mongodb                | 27017:27017:27017      | admin:123456     |
| mq                        | reabbitmq              | 5672:5672:5672         | guest:guest      |
| ^                         | reabbitmq-web          | 15672:15672:15672      | ^                |
| 阿里系                    | seata                  | 8791:8791:8791         | -                |
| ^                         | seata-web              | 7791:7791:7091         | seata:seata      |
| ^                         | nacos                  | 8848:8848:8848         | nacos:nacos      |
| polaris-server-standalone | polaris-http           | 8090:8090:8090         | polaris:polaris  |
| ^                         | polaris-gRPC           | 8091:8091:8091         | ^                |
| ^                         | nuc8i7-polaris-config  | 8093:8093:8093         | ^                |
| ^                         | nuc8i7-polaris-console | 18080:18080:8080       | ^                |
| ^                         | prometheus             | :9090:9090             | -                |
| ^                         | pushgateway            | :9091:9091             | -                |
| ^                         | xds                    | :15010:15010           | -                |
| ^                         | eureka                 | :8761:8761             | -                |
| ^                         | -                      | :9000:9000             | -                |

