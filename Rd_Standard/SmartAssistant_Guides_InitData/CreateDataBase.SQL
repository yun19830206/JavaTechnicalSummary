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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
alter table pub_auth_form_data comment '表单数据赋权：将我创建的表的数据，付给别人：可看、可改';





######################初始化数据#####################
## 创建租户表数据
INSERT INTO pub_tenant_config(id, tenant_number, tenant_name, create_time)  VALUES ('200', '18651886338888', 'CRM智能助手', '2019-03-09 22:13:25');
## 用户表
INSERT INTO pub_user(id,user_name,password,create_time,create_user,tenant_id) VALUES ('1', '程云', '6d8321868216602a1524fde764d2c7a6', '2019-03-09 22:11:47', '1', '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (2, '茆传羽', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (3, '盘龙泽', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (4, '李杰', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (5, '林宇卿', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (6, '张永', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (7, '李兵', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (8, '马栋淇', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (9, '李婷', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (10, '佘康凡', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (11, '徐冰', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (12, '田作富', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (13, '赵有顺', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (14, '王钰堃', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (15, '陈彦彬', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (16, '汪成', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (17, '邵言龙', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (18, '徐成', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
INSERT INTO pub_user(id,user_name,password,create_time,tenant_id) VALUES (19, '张英', '4297f44b13955235245b2497399d7a93', SYSDATE(), '200');
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
  where b.id != a.create_user and b.id in (4,5,6,7,8,9,10,11,12);
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
  chinese_name         varchar(64) not null comment '公司法定名称',
  industry_drop        varchar(43) not null comment '行业',
  employ_number        int(8) not null comment '人员规模',
  where_from           varchar(32) not null comment '客户来源',
  customer_level       varchar(8) not null comment '客户等级',
  customer_status      varchar(32) not null comment '客户阶段',
  create_user          int(11) not null comment '创建人，外键用户表',
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
  VALUES ('广东美的制冷设备有限公司', '电器', '20000', '自己开拓', 'A', 'POC', '1', '2019-03-12 17:35:31', '200');
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
  VALUES ('重庆猪八戒网络有限公司', '电商', '1000', '渠道', 'B', '售后客户关系维护', '1', '2019-03-12 17:36:55', '200');
INSERT INTO crm_customer (chinese_name, industry_drop, employ_number, where_from, customer_level, customer_status, create_user, create_time, tenant_id)
  VALUES ('上海政谱科技有限公司', '政务', '500', '老客续费', 'C', '初步方案', '1', '2019-03-12 17:37:40', '200');
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
  create_time          datetime not null comment '创建日期(类型对应Java中TimeStamp,JS中相同字符串)',
  tenant_id            int(11) not null comment '外键租户ID',
  primary key (id)
);
