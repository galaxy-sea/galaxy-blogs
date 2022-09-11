

| 分类          | 应用            | net : host : container | 账号:密码    |
| ------------- | --------------- | ---------------------- | ------------ |
| ssh           | ssh             | 5910:5900:             | -            |
|               |                 |                        |              |
| vnc           | vnc             | 5911:5900:             | -            |
| ssh           | ssh             | 1022:22:               | -            |
|               |                 |                        |              |
| Apache APISIX | apisix          | 9080:9080:9080         | token        |
| ^             | ^               | ::9091                 | -            |
| ^             | ^               | ::9092                 | -            |
| ^             | ^               | ::9443                 | -            |
| ^             | apisix-web      | ::9000                 | admin:admin  |
| ^             | etcd            | ::2379                 | -            |
| ^             | ~~grafana~~     | ~~::3000~~             | -            |
| ^             | ~~prometheus~~  | ~~::9090~~             | -            |
| ^             | ~~apisix_web1~~ | ~~::9081~~             | -            |
| ^             | ~~apisix_web2~~ | ~~::9082~~             | -            |
| elk           | logstash        | 5044:5044:5044         | -            |
| ^             | Elasticsearch   | 9200:9200:9200         | -            |
| ^             | kibana          | 5601:5601:5601         | -            |
| database      | Redis           | 6379:6379:6379         | :weichangjin |
| ^             | neo4j           | 7687:7687:7687         | neo4j:secret |
| ^             | neo4j-web       | 7474:7474:7474         | ^            |
| ^             | mysql5.7        | 3306:3306:3306         | root:weichangjin            |
| ^             | ~~mysql8.0~~    | ~~3308::~~             | -            |
| mq            | reabbitmq       | 5672:5672:5672         | guest:guest           |
| ^             | reabbitmq-web   | 15672:15672:15672      | guest:guest            |
| 阿里系        | seata           | 8091:8091:8091         | -            |
| ^             | seata-web       | 7091:7091:7091         | seata:seata            |
| ^             | nacos           | 8848:8848:8848         | nacos:nacos            |