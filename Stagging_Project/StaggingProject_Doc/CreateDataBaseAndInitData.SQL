######################创建脚手架工程数据库脚本#####################
drop table sp_class ;
drop table sp_label ;
drop table sp_question ;
drop table sp_answer ;
drop table sp_knowledge_label ;
##分类表
CREATE TABLE sp_class (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID自增',
  class_name varchar(64) NOT NULL unique COMMENT '分类名称',
  PRIMARY KEY (id)
) COMMENT='分类表' ;
##标签表
CREATE TABLE sp_label (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID自增',
  label_name varchar(64) NOT NULL unique COMMENT '标签名称',
  PRIMARY KEY (id)
) COMMENT='标签表' ;
##问题表
CREATE TABLE sp_question (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  question varchar(256) DEFAULT '' COMMENT '问题描述',
  class_id int(11) NOT NULL COMMENT '分类ID',
  stand_question_id int(11) DEFAULT -1 COMMENT '标准问题ID',
  question_type int(4) DEFAULT 100 COMMENT '问题类型:100=标准问题、200相似问法、300词条知识、400集合知识、500文档知识',
  PRIMARY KEY (id)
) COMMENT='问题表';
##答案表
CREATE TABLE sp_answer (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '答案ID',
  answer varchar(512) COMMENT '答案内容',
  question_id int(11) DEFAULT NULL COMMENT '问题ID',
  PRIMARY KEY (id)
) COMMENT='答案表';
##知识与标签关系
CREATE TABLE sp_knowledge_label (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID自增',
  label_id int(11) NOT NULL COMMENT '标签ID',
  question_id  int(11) NOT NULL COMMENT '问题ID',
  PRIMARY KEY (id)
) COMMENT='标签表' ;



######################插入基础数据脚本#####################
##插入分类表数据
INSERT INTO sp_class(`id`, `class_name`) VALUES ('1', '商品咨询');
INSERT INTO sp_class(`id`, `class_name`) VALUES ('2', '退货');
INSERT INTO sp_class(`id`, `class_name`) VALUES ('3', '换货');
##插入标签表数据
INSERT INTO sp_label(`id`, `label_name`) VALUES ('4', '取消订单');
INSERT INTO sp_label(`id`, `label_name`) VALUES ('10', '潮流商品');
INSERT INTO sp_label(`id`, `label_name`) VALUES ('20', '热卖商品');
INSERT INTO sp_label(`id`, `label_name`) VALUES ('30', '支持退货');
INSERT INTO sp_label(`id`, `label_name`) VALUES ('40', '描述商品');
##插入问题和答案表
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1046', '什么是板机', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1209', '如何购买商家商品', '-1', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1241', '如何设置密钥', '-1', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1243', '实体礼品卡配送收取运费吗？', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1279', '积分支付', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1283', '大宗采购介绍/企业销售联系方式', '-1', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1287', '怎么看商品是否库存', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1304', '如何修改/取消订单信息', '-1', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('500', '我想注销直销资格证', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('501', '项目概述的项目实施前提', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('502', '转人工', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('503', '康宝莱公司简介', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('504', '货物破损处理方式', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('505', '麻烦介绍一下你们公司的产品', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('506', '更改提货人信息', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('507', '如何瘦身', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('508', '如何健身', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('509', '{产品名}的成份和储存方式', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('510', '{产品名}的原产地', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('511', '{产品名}的产品性质', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('512', '{产品名}的使用方法', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('513', '原产地是{原产地}的产品有哪些', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('514', '{产品名}的SKU', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('515', '{产品名}的成份', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('516', '{产品名}的价格', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('517', '{产品名}的存储方式', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('518', '手机信息查询', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('519', '玉米奶昔制作方式', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('520', '玉米菠菜奶昔制作方法', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('521', '蛋白混合饮料产品介绍', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('522', '海外购流程指引', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('523', '蛋白营养粉价格介绍', '-1', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1698', '扳机', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1699', '扳机是什么', '1046', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1700', '扳机是什么意思', '1046', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1701', '什么叫做扳机', '1046', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1702', '扳机的意思', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1703', '解释下什么是扳机', '1046', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1704', '能告诉我什么是扳机吗', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1705', '扳机是什么东西', '1046', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1706', '扳机的定义', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1707', '什么叫扳机', '1046', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1708', '怎么判断是不是扳机', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1709', '怎么看是不是扳机', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1712', '扳机怎么判断', '1046', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1715', '扳机判断方式', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1718', '怎么检查是不是扳机', '1046', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1721', '怎么检测是不是扳机', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1724', '怎么检测是不是扳机', '1046', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('1725', '板机是什么意思？', '1046', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4990', '商家商品订购流程', '1209', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4991', '商家商品购物购流程', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4992', '商店街购物指南', '1209', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4993', '购买商店街的商品怎么操作？', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4994', '购买商家商品，是由当当负责处理吗？', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4995', '商家商品由谁负责处理订单？', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4996', '商家商品如何购买？', '1209', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4997', '如何购买商家商品？', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4998', '商家商品怎么买', '1209', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('4999', '怎么买商家商品', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5000', '商家商品要怎么买', '1209', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5001', '我要买商家商品', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5002', '非自用商品怎么买', '1209', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5003', '如何购买商家商品呢', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5004', '怎么才能购买商家商品', '1209', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5005', '商家商品购买方式', '1209', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5006', '商家商品购买方法', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5007', '买商家商品', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5008', '我要买商家商品要怎么操作', '1209', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5009', '购买商家商品如何操作', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5010', '商家商品购物流程', '1209', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5011', '商家商品购买流程', '1209', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5501', '密钥怎么设置', '1241', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5502', '什么是密钥', '1241', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5503', '密钥忘了怎么办', '1241', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5504', '电子礼品卡密码是什么', '1241', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5505', '密钥是干嘛用的', '1241', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5515', '礼品卡收运费吗', '1243', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5516', '礼品卡不能和其他商品一起送吗', '1243', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5765', '积分怎么支付', '1279', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5766', '积分可以抵消现金吗', '1279', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5767', '怎么用积分支付', '1279', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5768', '积分等于多少钱', '1279', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5769', '为什么积分支付不能用', '1279', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5770', '为什么没办法使用积分支付', '1279', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5771', '怎么使用积分支付', '1279', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5772', '怎么使用积分', '1279', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5799', '我想买上万的书', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5800', '大宗购货', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5801', '大宗采购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5802', '我要买好几万的东西', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5803', '我要大额购货', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5804', '我要团购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5805', '企业购货怎么办理', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5806', '企业采购怎么操作', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5807', '大量采购有什么优惠', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5808', '我要买好几万的东西你们能给优惠啊', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5809', '买的书比较多能优惠吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5810', '如何申请大宗采购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5811', '这个书批发的话便宜吗', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5812', '要买很多商品', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5813', '单位团购图书', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5814', '买*本有优惠吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5815', '购买*元图书，有优惠吗？', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5816', '买了很多书，没有优惠吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5817', '大宗采购客服电话', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5818', '我要买书，而且要买很多书？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5819', '团购很多书', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5820', '想买够上千的图书，给一个清单帮我订购吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5821', '多买书有折扣吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5822', '购买*多的图书，请问有什么优惠吗？', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5823', '我买的书比较多，可以下单吗？', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5824', '我需要购买*商品*套，请问能给点折扣吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5825', '请问我想买书，买的多会便宜么', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5826', '*套能便宜吗', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5827', '大量订购可以优惠吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5828', '我要买一批书，但是怎样能一批订购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5829', '团购买书可以更便宜吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5830', '企业用户有什么优惠？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5831', '我想问你一下团购可以打折吗？', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5832', '我想买很多书', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5833', '我需要好多本书', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5834', '团购能不能便宜一点', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5835', '提示让我联系团购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5836', '我买的数量大，要求有个优惠', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5837', '怎样团购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5838', '团购商品是否属于自营商品', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5839', '我怎么在你们网做团购', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5840', '我想买团购', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5841', '我在团购中怎么操作', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5842', '团购运费怎么收取啊', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5843', '怎么团购商品？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5844', '当当团购是什么？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5845', '我想在你们网上团购商品，该怎么操作？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5846', '团购电话多少', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5847', '如何购买多本', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5848', '这本书我想购买多件怎么操作', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5849', '订的数量多，如何让我挑选？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5850', '数量多，价格是不是可以优惠点', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5851', '我要买本书批量买，如何操作', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5852', '我需要大批量订货，是否有折扣？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5853', '我想批量购买', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5854', '我想批量购买图书，是否有优惠', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5855', '大批量买货有优惠吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5856', '可以批发图书吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5857', '怎样才能享受团购优惠？', '1283', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5858', '如何批量购买？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5859', '企业销售的联系方式', '1283', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5860', '批量/大宗/大量购买有什么好处？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5861', '达到企业销售的条件是什么？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5862', '购买多少商品可以有优惠', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5863', '订购几十件商品可以有优惠吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5864', '可以大量批发吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5865', '批量购书有什么折扣吗？', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5866', '买的量较多，有什么优惠吗', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5867', '团购优惠', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5868', '我想为单位采购一批图书', '1283', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5892', '商品有货吗', '1287', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5893', '怎么查看库存', '1287', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5894', '我想知道商品有多少库存', '1287', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('5895', '这个商品有多少货', '1287', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6174', '怎么修改订单', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6175', '修改订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6176', '订单修改', '1304', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6177', '如何修改订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6178', '订单要怎么修改', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6179', '要怎么修改订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6180', '订单修改方式', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6181', '订单能改吗', '1304', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6182', '要怎么改订单内容', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6183', '订单信息能修改吗', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6184', '收件人能改吗', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6185', '收件人联系电话能改吗', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6186', '送货地址还能修改吗', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6187', '订单信息要怎么修改', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6188', '取消订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6189', '取消订单信息', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6190', '怎么取消订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6191', '订单取消', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6192', '如何取消订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6193', '要怎么取消订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6194', '订单可以取消得了吗', '1304', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6195', '订单不想要了可以取消那', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6196', '东西不想买了能取消订单吗', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6197', '还没出货可以取消订单吗', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6198', '可以取消订单重新下单吗？', '1304', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6199', '我现在是否可以取消订单重新购买', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6200', '帮忙取消一单订单', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6201', '我的订单可以取消吗', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6202', '上午的订单取消不掉', '1304', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6203', '找到商家可以取消订单吗', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6204', '没法取消订单', '1304', 2);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6205', '取消不了订单怎么回事', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6206', '我要修改订单', '1304', 3);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6207', '我要修改联系方式', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6208', '电话错了怎么修改', '1304', 1);
INSERT INTO sp_question (`id`, `question`, `stand_question_id`, `class_id`) VALUES ('6209', '为什么修改不了电话', '1304', 1);

INSERT INTO sp_answer (`answer`, `question_id`)
  select CONCAT('标准答案:',a.question),a.id from sp_question a where a.stand_question_id=-1 ;
INSERT INTO sp_knowledge_label (label_id, question_id)
  select b.id 'label_id',a.id 'question_id' from sp_question a, sp_label b where a.stand_question_id=-1 ;
update sp_question set sp_question.question_type=100 where sp_question.stand_question_id=-1;
update sp_question set sp_question.question_type=200 where sp_question.stand_question_id!=-1;
update sp_question set sp_question.question_type=300 where sp_question.id=6208;
update sp_question set sp_question.question_type=500 where sp_question.id=6209;