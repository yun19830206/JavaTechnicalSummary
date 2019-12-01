######################创建数据库脚本#####################
##租户配置表
CREATE TABLE pub_tenant_config (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '租户主键ID',
  tenant_number varchar(64) NOT NULL unique COMMENT '租户对外唯一标识:时间戳+8位随机数; 1默认sys租户,-100底层通用库，-200行业通用库',
  tenant_name varchar(255) DEFAULT '' COMMENT '租户名称',
  PRIMARY KEY (id)
) COMMENT='租户配置表' ;

##问题表
CREATE TABLE kn_question (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  question varchar(256) DEFAULT '' COMMENT '问题描述',
  stand_question_id int(11) DEFAULT -1 COMMENT '标准问题ID',
  web_id int(11) DEFAULT NULL COMMENT '站点id',
  PRIMARY KEY (id)
  ) COMMENT='问题表';

##答案表
CREATE TABLE kn_answer (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '答案ID',
  answer varchar(512) COMMENT '答案内容',
  question_id int(11) DEFAULT NULL COMMENT '问题ID',
  web_id int(11) DEFAULT NULL COMMENT '租户id，对应pub_tenant_config的id',
  channel_id int(4) COMMENT '100网页,101微信,102H5,103小程序',
  PRIMARY KEY (id)
  ) COMMENT='答案表';

##词库表
CREATE TABLE kn_word (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '词ID',
  word_desc varchar(64) COMMENT '词的内容',
  word_type int(6) DEFAULT NULL COMMENT '词的类型:1禁止词、2停止词、4专业名词',
  word_weights int(6) DEFAULT 100 COMMENT '词权重：默认100',
  web_id int(11) DEFAULT NULL COMMENT '站点id，对应webConfig的id',
  PRIMARY KEY (id)
) COMMENT='词库表';

##词库关系表
CREATE TABLE kn_word_relation (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '词ID',
  word_desc varchar(64) COMMENT '词内容',
  relation_word varchar(64) COMMENT '关系词的内容',
  relation_value int(6) COMMENT '词关系的值',
  relation_type int(6) DEFAULT NULL COMMENT '词库关系类型:1关联词关系、2枚举词关系、3同义词关系',
  web_id int(11) DEFAULT NULL COMMENT '站点id，对应webConfig的id',
  PRIMARY KEY (id)
) COMMENT='词库表';


######################插入基础数据脚本#####################
##插入站点表
INSERT INTO `chatbotdb`.`pub_tenant_config` (`id`, `tenant_number`, `tenant_name`) VALUES ('1', '100', 'sys管理员超级租户');
INSERT INTO `chatbotdb`.`pub_tenant_config` (`id`, `tenant_number`, `tenant_name`) VALUES ('100', '29283038209821', '希尔顿大酒店');
INSERT INTO `chatbotdb`.`pub_tenant_config` (`id`, `tenant_number`, `tenant_name`) VALUES ('150', '28371179382928', '京东超级购物商场');
##插入问题和答案表
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1046', '什么是板机', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1209', '如何购买商家商品', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1241', '如何设置密钥', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1243', '实体礼品卡配送收取运费吗？', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1279', '积分支付', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1283', '大宗采购介绍/企业销售联系方式', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1287', '怎么看商品是否库存', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1304', '如何修改/取消订单信息', '-1', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1698', '扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1699', '扳机是什么', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1700', '扳机是什么意思', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1701', '什么叫做扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1702', '扳机的意思', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1703', '解释下什么是扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1704', '能告诉我什么是扳机吗', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1705', '扳机是什么东西', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1706', '扳机的定义', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1707', '什么叫扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1708', '怎么判断是不是扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1709', '怎么看是不是扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1712', '扳机怎么判断', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1715', '扳机判断方式', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1718', '怎么检查是不是扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1721', '怎么检测是不是扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1724', '怎么检测是不是扳机', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('1725', '板机是什么意思？', '1046', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4990', '商家商品订购流程', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4991', '商家商品购物购流程', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4992', '商店街购物指南', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4993', '购买商店街的商品怎么操作？', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4994', '购买商家商品，是由当当负责处理吗？', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4995', '商家商品由谁负责处理订单？', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4996', '商家商品如何购买？', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4997', '如何购买商家商品？', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4998', '商家商品怎么买', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('4999', '怎么买商家商品', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5000', '商家商品要怎么买', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5001', '我要买商家商品', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5002', '非自用商品怎么买', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5003', '如何购买商家商品呢', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5004', '怎么才能购买商家商品', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5005', '商家商品购买方式', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5006', '商家商品购买方法', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5007', '买商家商品', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5008', '我要买商家商品要怎么操作', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5009', '购买商家商品如何操作', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5010', '商家商品购物流程', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5011', '商家商品购买流程', '1209', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5501', '密钥怎么设置', '1241', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5502', '什么是密钥', '1241', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5503', '密钥忘了怎么办', '1241', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5504', '电子礼品卡密码是什么', '1241', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5505', '密钥是干嘛用的', '1241', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5515', '礼品卡收运费吗', '1243', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5516', '礼品卡不能和其他商品一起送吗', '1243', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5765', '积分怎么支付', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5766', '积分可以抵消现金吗', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5767', '怎么用积分支付', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5768', '积分等于多少钱', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5769', '为什么积分支付不能用', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5770', '为什么没办法使用积分支付', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5771', '怎么使用积分支付', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5772', '怎么使用积分', '1279', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5799', '我想买上万的书', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5800', '大宗购货', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5801', '大宗采购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5802', '我要买好几万的东西', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5803', '我要大额购货', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5804', '我要团购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5805', '企业购货怎么办理', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5806', '企业采购怎么操作', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5807', '大量采购有什么优惠', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5808', '我要买好几万的东西你们能给优惠啊', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5809', '买的书比较多能优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5810', '如何申请大宗采购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5811', '这个书批发的话便宜吗', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5812', '要买很多商品', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5813', '单位团购图书', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5814', '买*本有优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5815', '购买*元图书，有优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5816', '买了很多书，没有优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5817', '大宗采购客服电话', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5818', '我要买书，而且要买很多书？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5819', '团购很多书', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5820', '想买够上千的图书，给一个清单帮我订购吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5821', '多买书有折扣吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5822', '购买*多的图书，请问有什么优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5823', '我买的书比较多，可以下单吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5824', '我需要购买*商品*套，请问能给点折扣吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5825', '请问我想买书，买的多会便宜么', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5826', '*套能便宜吗', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5827', '大量订购可以优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5828', '我要买一批书，但是怎样能一批订购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5829', '团购买书可以更便宜吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5830', '企业用户有什么优惠？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5831', '我想问你一下团购可以打折吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5832', '我想买很多书', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5833', '我需要好多本书', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5834', '团购能不能便宜一点', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5835', '提示让我联系团购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5836', '我买的数量大，要求有个优惠', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5837', '怎样团购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5838', '团购商品是否属于自营商品', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5839', '我怎么在你们网做团购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5840', '我想买团购', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5841', '我在团购中怎么操作', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5842', '团购运费怎么收取啊', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5843', '怎么团购商品？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5844', '当当团购是什么？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5845', '我想在你们网上团购商品，该怎么操作？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5846', '团购电话多少', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5847', '如何购买多本', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5848', '这本书我想购买多件怎么操作', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5849', '订的数量多，如何让我挑选？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5850', '数量多，价格是不是可以优惠点', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5851', '我要买本书批量买，如何操作', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5852', '我需要大批量订货，是否有折扣？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5853', '我想批量购买', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5854', '我想批量购买图书，是否有优惠', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5855', '大批量买货有优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5856', '可以批发图书吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5857', '怎样才能享受团购优惠？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5858', '如何批量购买？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5859', '企业销售的联系方式', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5860', '批量/大宗/大量购买有什么好处？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5861', '达到企业销售的条件是什么？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5862', '购买多少商品可以有优惠', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5863', '订购几十件商品可以有优惠吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5864', '可以大量批发吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5865', '批量购书有什么折扣吗？', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5866', '买的量较多，有什么优惠吗', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5867', '团购优惠', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5868', '我想为单位采购一批图书', '1283', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5892', '商品有货吗', '1287', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5893', '怎么查看库存', '1287', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5894', '我想知道商品有多少库存', '1287', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('5895', '这个商品有多少货', '1287', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6174', '怎么修改订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6175', '修改订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6176', '订单修改', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6177', '如何修改订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6178', '订单要怎么修改', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6179', '要怎么修改订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6180', '订单修改方式', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6181', '订单能改吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6182', '要怎么改订单内容', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6183', '订单信息能修改吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6184', '收件人能改吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6185', '收件人联系电话能改吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6186', '送货地址还能修改吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6187', '订单信息要怎么修改', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6188', '取消订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6189', '取消订单信息', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6190', '怎么取消订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6191', '订单取消', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6192', '如何取消订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6193', '要怎么取消订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6194', '订单可以取消得了吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6195', '订单不想要了可以取消那', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6196', '东西不想买了能取消订单吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6197', '还没出货可以取消订单吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6198', '可以取消订单重新下单吗？', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6199', '我现在是否可以取消订单重新购买', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6200', '帮忙取消一单订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6201', '我的订单可以取消吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6202', '上午的订单取消不掉', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6203', '找到商家可以取消订单吗', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6204', '没法取消订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6205', '取消不了订单怎么回事', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6206', '我要修改订单', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6207', '我要修改联系方式', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6208', '电话错了怎么修改', '1304', '150');
INSERT INTO `kn_question` (`id`, `question`, `stand_question_id`, `web_id`) VALUES ('6209', '为什么修改不了电话', '1304', '150');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2066', '标准答案_什么是板机_标准答案', '1046', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2229', '标准答案_如何购买商家商品_标准答案', '1209', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2261', '标准答案_如何设置密钥_标准答案', '1241', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2263', '标准答案_实体礼品卡配送收取运费吗？_标准答案', '1243', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2299', '标准答案_积分支付_标准答案', '1279', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2303', '标准答案_大宗采购介绍/企业销售联系方式_标准答案', '1283', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2307', '标准答案_怎么看商品是否库存_标准答案', '1287', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2324', '标准答案_如何修改/取消订单信息_标准答案', '1304', '150', NULL);
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2577', '微信答案_什么是板机_微信答案', '1046', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2740', '微信答案_如何购买商家商品_微信答案', '1209', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2772', '微信答案_如何设置密钥_微信答案', '1241', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2774', '微信答案_实体礼品卡配送收取运费吗？_微信答案', '1243', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2810', '微信答案_积分支付_微信答案', '1279', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2814', '微信答案_大宗采购介绍/企业销售联系方式_微信答案', '1283', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2818', '微信答案_怎么看商品是否库存_微信答案', '1287', '150', '101');
INSERT INTO `kn_answer` (`id`, `answer`, `question_id`, `web_id`, `channel_id`) VALUES ('2835', '微信答案_如何修改/取消订单信息_微信答案', '1304', '150', '101');
##插入词库数据
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('2367', '礼券', '4', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('2368', '自营', '4', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('2369', '退单', '4', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1218', '以为', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1219', '以便', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1220', '以免', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1221', '以故', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1222', '以期', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1223', '以来', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1224', '以至', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1225', '以至于', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1226', '以致', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1227', '任', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1228', '任何', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1229', '任凭', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1230', '但凡', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1231', '何以', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1232', '何况', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1233', '何处', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1234', '何时', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1235', '余外', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1236', '作为', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1237', '使得', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1238', '依', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1239', '依据', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1240', '依照', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1241', '便于', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1242', '俺', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1243', '俺们', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1244', '倘', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1245', '倘使', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1246', '倘或', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1247', '倘然', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1248', '倘若', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1249', '借', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1250', '假使', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1251', '假如', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1252', '假若', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1253', '傥然', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1254', '儿', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1255', '先不先', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1256', '光是', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1257', '全体', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1258', '全部', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1259', '兮', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1260', '其一', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1261', '其中', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1262', '其二', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1263', '其他', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1264', '其余', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1265', '其它', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1266', '具体地说', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1267', '具体说来', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1268', '兼之', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1269', '内', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1270', '再其次', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1271', '再则', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1272', '再者', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1273', '再者说', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1274', '再说', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1275', '冒', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1276', '冲', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1277', '况且', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1278', '几', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1279', '几时', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1280', '凡', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1281', '凡是', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1282', '凭', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1283', '凭借', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1284', '出于', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1285', '出来', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1286', '分别', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1287', '则甚', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1288', '别', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1289', '别处', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1290', '别是', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1291', '别的', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1292', '别管', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1293', '别说', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1294', '前后', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1295', '前此', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1296', '前者', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1297', '加以', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1298', '即令', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1299', '即便', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1300', '即如', '2', '100', '150');
INSERT INTO `kn_word` (`id`, `word_desc`, `word_type`, `word_weights`, `web_id`) VALUES ('1301', '即或', '2', '100', '150');

INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('1', '可以', '同意', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('2', '兑换', '换到', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('3', '房金券', '房券', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('4', '可以', '能够', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('5', '如何', '怎么', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('6', '积分', '金币', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('7', '兑换', '换取', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('8', '苹果', 'Apple', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('9', '苹果', 'Apple1', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('10', 'A', 'a', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('11', 'A', 'b', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('62', '账号', '卡号', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('63', '抵扣', '冲抵', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('64', '营业执照', '代码证,税务登记证', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('65', '顺丰', '顺风', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('66', '倒闭', '黄了,关了', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('67', '中行', '建设银行', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('68', '工行', '工商银行', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('69', '农行', '农业银行', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('70', '招行', '招商银行', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('71', '邮政', '邮储,邮政银行', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('72', '销卡', '卡销,卡消', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('73', '节假日', '公休日', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('74', '银监会', '银监局', '100', '3', '150');
INSERT INTO `kn_word_relation` (`id`, `word_desc`, `relation_word`, `relation_value`, `relation_type`, `web_id`) VALUES ('394', '商品', '订单', '70', '1', '150');

