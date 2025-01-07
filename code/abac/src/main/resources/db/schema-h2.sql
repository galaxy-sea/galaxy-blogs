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

DROP TABLE if EXISTS `user`;
CREATE TABLE `user` (
    id bigint NOT NULL COMMENT '主键ID',
    name varchar(30) NULL DEFAULT NULL COMMENT '姓名',
    age int NULL DEFAULT NULL COMMENT '年龄',
    email varchar(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

DROP TABLE if EXISTS user_contribution;
CREATE TABLE user_contribution (
    id bigint NOT NULL COMMENT '主键ID',
    user_id bigint  NOT NULL COMMENT '用户表ID',
    repository varchar(100) NOT NULL COMMENT '仓库',
    PRIMARY KEY (id)
);


DROP TABLE if EXISTS permission;
CREATE TABLE permission (
    id bigint NOT NULL COMMENT '主键ID',
    permission varchar(100) NOT NULL COMMENT '权限名称',
    PRIMARY KEY (id)
);

DROP TABLE if EXISTS abac;
CREATE TABLE abac (
    id bigint NOT NULL COMMENT '主键ID',
    expression varchar(100) NOT NULL COMMENT 'abac表达式',
    PRIMARY KEY (id)
);


DROP TABLE if EXISTS abac_permission;
CREATE TABLE abac_permission (
    id bigint NOT NULL COMMENT '主键ID',
    abac_id bigint NOT NULL COMMENT 'abac表ID',
    permission_id bigint NOT NULL COMMENT 'permission表ID',
    PRIMARY KEY (id)
);
