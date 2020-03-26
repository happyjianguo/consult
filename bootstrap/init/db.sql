create database if not exists template;

CREATE TABLE `pk_sequence` (
  `id`          int(11)          NOT NULL AUTO_INCREMENT,
  `k`           varchar(16)      NOT NULL,
  `v`           bigint(20)       NOT NULL,
  `step`        int(10) unsigned NOT NULL,
  `modify_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_k_UNIQUE` (`k`)
);

insert pk_sequence(`k`, `v`, `step`) values ('demo', 0, 10);

create table demo
(
  id          bigint auto_increment primary key,
  user_id     bigint                             not null,
  name        varchar(16)                        null,
  status      tinyint                            null,
  create_time datetime default CURRENT_TIMESTAMP null,
  modify_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);