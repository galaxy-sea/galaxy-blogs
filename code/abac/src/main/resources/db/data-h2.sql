/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



DELETE FROM user;
INSERT INTO user (id, name, age, email)
VALUES (1, '魏昌进', 26, 'mail@wcj.plus'),
       (2, 'test', 1, 'mail1@wcj.plus'),
       (3, 'admin', 1, 'mail2@wcj.plus');

DELETE FROM user_contribution;
INSERT INTO user_contribution (id, user_id, repository)
VALUES (1, 1, 'galaxy-sea/spring-cloud-apisix'),
       (2, 2, 'spring-cloud/spring-cloud-commons'),
       (3, 2, 'spring-cloud/spring-cloud-openfeign'),
       (4, 2, 'alibaba/spring-cloud-alibaba'),
       (5, 2, 'Tencent/spring-cloud-tencent'),
       (6, 2, 'apache/apisix-docker');

DELETE FROM permission;
INSERT INTO permission (id, permission)
VALUES (1, 'github:pr:merge'),
       (2, 'github:pr:close'),
       (3, 'github:pr:open'),
       (4, 'github:pr:comment');


DELETE FROM abac;
INSERT INTO abac (id, expression)
VALUES (1, 'contributions.contains(''galaxy-sea/spring-cloud-apisix'')'),
       (2, 'name == ''admin'''),
       (3, 'metadata.get(''ip'') == ''192.168.0.1''');

DELETE FROM abac_permission;
INSERT INTO abac_permission (id, abac_id, permission_id)
VALUES (1, 1, 1),

       (2, 2, 1),
       (3, 2, 2),
       (4, 2, 3),
       (5, 2, 4),

       (6, 3, 1),
       (7, 3, 2),
       (8, 3, 3),
       (9, 3, 4)
;



