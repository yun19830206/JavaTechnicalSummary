######################创建工具工程相关的数据库脚本#####################
drop table if exists  ;



##标签 与 语料 关系表
CREATE TABLE up_label_corpus (
    id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID自增',
    label_name varchar(64) NOT NULL COMMENT '标签名称',
    corpus varchar(255) DEFAULT NULL COMMENT '标签对应的语料',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) COMMENT='标签与语料关系表' ;