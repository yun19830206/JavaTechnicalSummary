######################创建数据库脚本#####################
## 统一删除表，然后在创建表
# drop table if exists pub_table_column;
# drop table if exists pub_table_conf;
# drop table if exists pub_table_display;
# drop table if exists pub_table_query;
# drop table if exists pub_tenant;
# drop table if exists pub_user;


## 租户表
create table pub_tenant_config
(
  id                   int(11) not null auto_increment comment '主键ID',
  tenant_name          varchar(64) not null comment '租户全名公司名、部门名',
  tenant_no            varchar(64) not null comment '租户NO',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  create_user          int(11) comment '创建人ID，可控',
  primary key (id)
);
alter table pub_tenant_config comment '租户表';

## 用户表
create table pub_user
(
  id                   int(11) not null auto_increment comment '主键ID',
  user_name            varchar(64) not null comment '用户名',
  password             varchar(64) not null comment '密码',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  create_user          int(11) comment '创建人ID，可控',
  tenant_id            int(11) not null comment '外键租户ID',
  wx_openid            varchar(32) comment '关联公众号里面的微信openid',
  primary key (id)
);
alter table pub_user comment '用户表';


## 表单设计表
create table pub_table_conf
(
  id                   int(11) not null auto_increment comment '主键ID',
  chinese_name         varchar(64) not null comment '中文名',
  english_name         varchar(64) not null comment '英文名',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_table_conf comment '表单设计表';

## 表单字段设计表
create table pub_table_column
(
  id                   int(11) not null auto_increment comment '主键ID',
  table_id             int(11) not null comment '设计表ID，外键表单设计表',
  col_seq              int(4) not null comment '本字段的序号，默认从1开始',
  chinese_name         varchar(64) not null comment '字段中文名',
  english_name         varchar(64) not null comment '字段英文名',
  col_type             varchar(32) not null comment '字段类型(对应枚举EnumTableConfig:单行文本框、下拉框、富文本、多行文本框、日期、数字、手机号、邮箱号、附件)',
  col_length           int(4) not null comment '字段长度',
  drop_value           varchar(32) comment '下拉框值域(拜访方法:电话拜访、上门拜访)',
  fk_value             int(11) comment '引用表值域(如:项目系统表 引用 客户表信息)',
  default_value        varchar(64) comment '默认值',
  display_column       tinyint(1) comment '是否引用展示字段(被表外键引用，展示字段)：1是展示，0不是',
  uniqued               tinyint(1) comment '是否唯一：1必须唯一，0不做判断',
  empty                tinyint(1) comment '能否为空：1能为空，0不能为空',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_table_column comment '表单字段设计表';

## 表单查询展示字段配置表
create table pub_table_display
(
  id                   int(11) not null auto_increment comment '主键ID',
  table_id             int(11) not null comment '设计表ID，外键表单设计表',
  display_seq          int(4) not null comment '展示序号',
  table_column         int(11) not null comment '外键到表单字段设计表主键',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_table_display comment '表单查询展示字段配置表';

## 表单查询条件列
create table pub_table_query
(
  id                   int(11) not null auto_increment comment '主键ID',
  table_id             int(11) not null comment '设计表ID，外键表单设计表',
  query_seq            int(4) not null comment '查询序号',
  table_column         int(11) not null comment '外键到表单字段设计表主键',
  query_type           varchar(32) not null comment '查询条件类型(like,=,>,<,<>)',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_table_query comment '表单查询条件列';

## 表单赋权：将我创建的表单配置，付给其他人能管理数据
create table pub_auth_form
(
  id                   int(11) not null auto_increment comment '主键ID',
  table_id             int(11) not null comment '设计表ID，外键表单设计表',
  user_id              int(11) not null comment '权限付给对方ID',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_auth_form comment '表单赋权：将我创建的表单配置，付给其他人能管理数据';

## 表单数据赋权：将我创建的表的数据，付给别人：可看、可改
create table pub_auth_form_data
(
  id                   int(11) not null auto_increment comment '主键ID',
  from_user            int(11) not null comment '将谁的表付给别人',
  from_table           int(11) not null comment '设计表ID，外键表单设计表',
  to_user              int(11) not null comment '权限付给对方ID',
  data_auth_type       varchar(32) not null comment '赋权类型枚举：DATA_AUTH_TYPE_VIEW=查看权限,DATA_AUTH_TYPE_EDIT=修改权限,DATA_AUTH_TYPE_DEL=删除权限',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_auth_form_data comment '表单数据赋权：将我创建的表的数据，付给别人：可看、可改';

####附件存储:pub_file:附件表
CREATE TABLE pub_file (
  id              int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  file_name_original varchar(127) NOT NULL COMMENT '原始文件名',
  file_name_new   varchar(127) NOT NULL COMMENT '系统中存储的唯一文件名',
  relative_path   varchar(127) NOT NULL COMMENT '相对upload文件夹相对路径',
  absolute_path   varchar(255) NOT NULL COMMENT '操作系统绝对路径',
  url             varchar(127)   COMMENT '对应系统URL',
  content_type    varchar(128)   COMMENT '文件类型',
  create_user     int(11) not null comment '创建人，外键用户表',
  create_time     datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id       int(11) NOT NULL COMMENT '所属租户ID',
  del             int(1) NOT NULL DEFAULT 0 COMMENT '统一加入数据del标志：del=1为删除，del=0为正常(默认为0)',
  PRIMARY KEY (`id`)
) COMMENT='附件表';







######################初始化数据#####################
## 创建租户表数据
INSERT INTO pub_tenant_config(id, tenant_number, tenant_name, create_time)  VALUES ('200', '18651886338888', 'CRM智能助手', '2019-03-09 22:13:25');
## 用户表
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('1', '程云', '4297f44b13955235245b2497399d7a93', '2019-03-09 22:11:47', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('2', '茆传羽', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('3', '盘龙泽', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('4', '李杰', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('5', '林宇卿', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('6', '张永', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('7', '李兵', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('8', '马栋淇', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('9', '李婷', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('10', '佘康凡', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('11', '徐冰', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:16', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('12', '田作富', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('13', '赵有顺', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('14', '王钰堃', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('15', '陈彦彬', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('16', '汪成', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('17', '邵言龙', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('18', '徐成', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('19', '张英', '4297f44b13955235245b2497399d7a93', '2019-03-12 21:19:17', '1', '200', NULL);
INSERT INTO pub_user (id, user_name, password, create_time, create_user, tenant_id, wx_openid) VALUES ('20', '王海荣', '4297f44b13955235245b2497399d7a93', '2019-03-12 22:50:37', '1', '200', NULL);
##下面人员不需要CRM权限
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('陈德金', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王思', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('竺文聪', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('孙鹏', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('周岗', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('徐健', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张荣松', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('戴天彤', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('徐浩', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('余游', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张雨轩', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('徐成东', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('谷和金', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张媛媛', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张赛', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张永康', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('李冬洋', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('余辰鹏', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('郭皓', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('司徒棋巽', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王彬', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('顾荣', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('赵宇星', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('黄世鹏', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('刘丹', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('钱家豪', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('李辰刚', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('杨萌', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('姚奥', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('李平', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('沈盛宇', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张国威', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王晓波', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('葛军', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('卢春', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('杨柳', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('许杰', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('沙如拉', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('徐冠男', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张雅冰', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('孟凡华', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王修兰', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('李昂', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王雪', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('纪伟', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('朱文健', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('周光杰', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('徐境源', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('陈潜', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('宋婷婷', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('梅傲寒', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王少华', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('冯欣', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('金伟娟', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('赵婷', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('罗陵', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('付志宝', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('戴玉龙', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('雎垚', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('刘晓豹', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('杨雪江', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('季春', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('吴国群', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('杜金鑫', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('戎宽', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('倪亚州', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('向威', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('高海成', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('钱家佳', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('申华', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('陈鑫', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('张浩', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('唐彧昊', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王栋梁', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('靖亮亮', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('薛江浩', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('李树亮', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('周春成', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('王建亮', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
insert into pub_user(user_name,password,create_time,create_user,tenant_id) values('何德勇', '4297f44b13955235245b2497399d7a93', SYSDATE(), '100', '200');
## 表单赋权：将所有创建的表单配置，付给所有人员(销售人员)
INSERT INTO pub_auth_form(table_id,user_id,create_user,create_time,tenant_id)
  select
    a.id,b.id,a.create_user,SYSDATE(),200
  from pub_table_conf a, pub_user b
  where b.id != a.create_user ;
## 表单数据赋权1：所有人的给茆总
INSERT INTO pub_auth_form_data(from_user,from_table,to_user,data_auth_type,create_user,create_time,tenant_id)
  select
    b.id,a.id,2,'DATA_AUTH_TYPE_VIEW',a.create_user,SYSDATE(),200
  from pub_table_conf a, pub_user b
  where b.id != a.create_user and b.id != 2;
## 表单数据赋权2：本部给老潘
INSERT INTO pub_auth_form_data(from_user,from_table,to_user,data_auth_type,create_user,create_time,tenant_id)
  select
    b.id,a.id,3,'DATA_AUTH_TYPE_VIEW',a.create_user,SYSDATE(),200
  from pub_table_conf a, pub_user b
  where b.id != a.create_user and b.id in (1,4,5,6,7,8,9,10,11,12);
## 表单数据赋权3：北区给有顺
INSERT INTO pub_auth_form_data(from_user,from_table,to_user,data_auth_type,create_user,create_time,tenant_id)
  select
    b.id,a.id,13,'DATA_AUTH_TYPE_VIEW',a.create_user,SYSDATE(),200
  from pub_table_conf a, pub_user b
  where b.id != a.create_user and b.id in (14,15,16);










######################未了减少开发时间，直接创建客户表(应该由接口调用)#####################
## 创建客户表
create table crm_customer
(
  id                   int(11) not null auto_increment comment '主键ID',
  chinese_name         varchar(64) not null unique comment '公司法定名称',
  industry_drop        varchar(43) not null comment '行业',
  employ_number        int(8) not null comment '人员规模',
  where_from           varchar(32) not null comment '客户来源',
  customer_level       varchar(8) not null comment '客户等级',
  customer_status      varchar(32) not null comment '客户阶段',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
  VALUES ('广东美的制冷设备有限公司', '电器', '20000', '自己开拓', 'A', 'POC', '1', '2019-03-12 17:35:31', '200');
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
  VALUES ('重庆猪八戒网络有限公司', '电商', '1000', '渠道', 'B', '售后客户关系维护', '1', '2019-03-12 17:36:55', '200');
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
  VALUES ('上海政谱科技有限公司', '政务', '500', '老客续费', 'C', '初步方案', '1', '2019-03-12 17:37:40', '200');
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
VALUES ('北京当当网科技有限公司', '电商', '553', '自己开拓', 'A', '售后客户关系维护', '1', '2019-03-12 17:37:40', '200');
## 创建联系人表
create table crm_project_user
(
  id                   int(11) not null auto_increment comment '主键ID',
  compange_name        int(11) not null comment '公司法定名称',
  user_name            varchar(32) not null comment '姓名',
  user_sex             varchar(8) not null comment '性别',
  department           varchar(64) not null comment '部门',
  position             varchar(32) not null comment '职位',
  phone_number         varchar(16) not null comment '手机号',
  qq_wx                varchar(32) not null comment 'QQ微信',
  address              varchar(64) not null comment '地址',
  age                  int(4) not null comment '年龄',
  birthday             datetime not null comment '生日',
  hobby                varchar(32) not null comment '爱好',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null DEFAULT CURRENT_TIMESTAMP comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
INSERT INTO crm_project_user (id, compange_name, user_name, user_sex, department, position, phone_number, qq_wx, address, age, birthday, hobby, create_user, create_time, tenant_id)
  VALUES ('1', '3', '张经理', '女', 'IT部门', '经理', '15005156145', '441719065', '南京市江宁区胜利路89号', '20', '2019-03-14 21:15:01', '足球', '1', '2019-03-14 21:15:09', '200');
INSERT INTO crm_project_user (id, compange_name, user_name, user_sex, department, position, phone_number, qq_wx, address, age, birthday, hobby, create_user, create_time, tenant_id)
  VALUES ('2', '1', '王总', '男', 'HR部门', '经理', '15345156145', '34353434', '南京市江宁区胜利路89号', '20', '2019-03-14 21:15:01', '足球', '1', '2019-03-14 21:15:09', '200');
## 创建CRM项目表
CREATE TABLE crm_project (
  id                INT (11) NOT NULL auto_increment COMMENT '主键ID',
  compange_name     INT (11) NOT NULL COMMENT '公司法定名称',
  project_name      INT (11) NOT NULL COMMENT '项目名称',
  use_range         VARCHAR (32) NOT NULL COMMENT '使用范围',
  use_department    VARCHAR (32) NOT NULL COMMENT '使用部门',
  use_channel       VARCHAR (32) NOT NULL COMMENT '对接渠道',
  compet_vendor     VARCHAR (64) NOT NULL COMMENT '竞对厂商',
  buy_system        VARCHAR (64) COMMENT '业务系统构成',
  pre_company       VARCHAR (64) COMMENT '前任合作厂商',
  success_probability VARCHAR (64) NOT NULL COMMENT '成功概率',
  money             INT (8) NOT NULL COMMENT '项目预算（万元）',
  start_date        datetime COMMENT '项目开始时间',
  receive_date      datetime COMMENT '项目验收时间',
  contract_amount   INT (8) COMMENT '合同金额（万元）',
  deploy_method     VARCHAR (16) COMMENT '部署方式',
  project_statu     VARCHAR (16) NOT NULL COMMENT '项目状态',
  create_user       INT (11) NOT NULL COMMENT '创建人，外键用户表',
  create_time       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id         INT (11) NOT NULL COMMENT '外键租户ID',
  PRIMARY KEY (id)
);
INSERT INTO crm_project (id, compange_name, project_name, use_range, use_department, use_channel, compet_vendor, buy_system, pre_company, success_probability, money, start_date, receive_date, contract_amount, deploy_method, project_statu, create_user, create_time, tenant_id)
  VALUES ('1', '1', '美的IT智能客服项目', '对内', 'IT部', 'APP', '追一、小i', '呼叫中心', '小I', '40%-50%', '500', '2019-03-19 15:06:56', '2019-03-30 15:06:58', '300', '单点', '正常', '1', '2019-03-19 15:06:44', '200');
INSERT INTO crm_project (id, compange_name, project_name, use_range, use_department, use_channel, compet_vendor, buy_system, pre_company, success_probability, money, start_date, receive_date, contract_amount, deploy_method, project_statu, create_user, create_time, tenant_id)
  VALUES ('2', '1', '美的客服中心项目', '对外', '客服部', '微信', '追一', '机器人', '小I', '40%-50%', '456', '2019-03-19 15:06:56', '2019-03-30 15:06:58', '365', '单点', '正常', '1', '2019-03-19 15:06:44', '200');
INSERT INTO crm_project (id, compange_name, project_name, use_range, use_department, use_channel, compet_vendor, buy_system, pre_company, success_probability, money, start_date, receive_date, contract_amount, deploy_method, project_statu, create_user, create_time, tenant_id)
  VALUES ('3', '2', '猪八戒智能客服项目', '对外', '客服部', '微信', '无', '机器人', '小I', '40%-50%', '600', '2019-03-19 15:06:56', '2019-03-30 15:06:58', '253', '私有云', '未签订', '1', '2019-03-19 15:06:44', '200');
## CRM拜访记录表 crm_project_visit
CREATE TABLE crm_project_visit (
  id                INT (11) NOT NULL auto_increment COMMENT '主键ID',
  project_name      INT (11) NOT NULL COMMENT '项目名称',
  connect_persion   INT (11) NOT NULL COMMENT '联系人姓名',
  visit_type         VARCHAR (32) NOT NULL COMMENT '拜访方式',
  visit_text         VARCHAR (1023) NOT NULL COMMENT '拜访记录',
  file_id   				 INT (11) COMMENT '附件',
  create_user       INT (11) NOT NULL COMMENT '创建人，外键用户表',
  create_time       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id         INT (11) NOT NULL COMMENT '外键租户ID',
  PRIMARY KEY (id)
);
INSERT INTO crm_project_visit (id, project_name, connect_persion, visit_type, visit_text, file_id, create_user, create_time, tenant_id) VALUES ('1', '1', '1', '电话', '聊需求', '1', '1', '2019-03-19 16:01:00', '200');
INSERT INTO crm_project_visit (id, project_name, connect_persion, visit_type, visit_text, file_id, create_user, create_time, tenant_id) VALUES ('2', '1', '2', '上门', '讲方案', '1', '1', '2019-03-19 16:01:00', '200');
INSERT INTO crm_project_visit (id, project_name, connect_persion, visit_type, visit_text, file_id, create_user, create_time, tenant_id) VALUES ('3', '1', '1', '电话', '聊合同', '1', '1', '2019-03-19 16:01:00', '200');
