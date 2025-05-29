-- 创建用户表
CREATE TABLE IF NOT EXISTS `la_user` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `la_article` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要',
  `content` longtext COMMENT '文章内容',
  `category` varchar(50) DEFAULT NULL COMMENT '文章分类',
  `tags` varchar(255) DEFAULT NULL COMMENT '文章标签，以逗号分隔',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图URL',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
  `status` tinyint(1) DEFAULT '0' COMMENT '发布状态: 0-草稿, 1-已发布',
  `create_user` varchar(32)          null comment '创建人',
  `update_user` varchar(32)          null comment '更新人',
  `create_time` datetime             null comment '创建时间',
  `update_time` datetime             null comment '更新时间',
  `delete_time` datetime             null comment '删除时间',
  PRIMARY KEY (`id`),
  INDEX `idx_category` (`category`),
  INDEX `idx_status` (`status`),
  INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';


CREATE TABLE IF NOT EXISTS `la_user_setting` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `bio` varchar(500) DEFAULT NULL COMMENT '个人简介',
  `blog_intro` varchar(1000) DEFAULT NULL COMMENT '博客介绍',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '邮箱联系方式',
  `github_url` varchar(255) DEFAULT NULL COMMENT 'GitHub链接',
  `extra_contacts` text DEFAULT NULL COMMENT '自定义联系方式（JSON格式存储额外的联系方式）',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户设置表';

-- 初始化管理员账号（密码：admin123，使用BCrypt加密）
INSERT INTO `la_user` (`id`, `username`, `password`, `avatar`, `nickname`, `email`, `status`, `create_time`, `update_time`)
VALUES ('1', 'admin', '$2a$10$rOXX4HqoPvCgkQ29JowMVu9IsC.2j90gXsmyzgidMR6DfEibOZ6fy', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '管理员', 'admin@example.com', 1, NOW(), NOW());

-- 插入示例文章数据
INSERT INTO `la_article` (`id`, `title`, `summary`, `content`, `category`, `tags`, `thumbnail`, `view_count`, `status`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
('1001', 'Spring Boot 入门教程', 'Spring Boot框架入门指南，从零开始学习Spring Boot', '# Spring Boot 入门教程\n\n## 简介\nSpring Boot是一个用于简化Spring应用开发的框架，它提供了自动配置、内嵌服务器等特性...\n\n## 环境搭建\n首先需要安装JDK和Maven...\n\n## 创建项目\n使用Spring Initializr可以快速创建一个Spring Boot项目...', '技术教程', 'Java,Spring,后端', 'https://example.com/images/spring-boot.jpg', 1024, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY)),

('1002', 'MySQL性能优化实战', '本文介绍MySQL数据库性能优化的实用技巧', '# MySQL性能优化实战\n\n## 索引优化\n合理的索引设计是提升MySQL性能的关键...\n\n## 查询优化\n编写高效的SQL查询语句可以大幅提升数据库性能...\n\n## 服务器配置\n调整MySQL服务器参数可以更好地适应业务需求...', '数据库', 'MySQL,性能优化,数据库', 'https://example.com/images/mysql.jpg', 768, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),

('1003', 'Docker容器化部署指南', '使用Docker实现应用的容器化部署，提高开发和运维效率', '# Docker容器化部署指南\n\n## Docker基础\nDocker是一种容器技术，可以将应用及其依赖打包成镜像...\n\n## Dockerfile编写\n通过Dockerfile可以定义镜像的构建过程...\n\n## Docker Compose\n使用Docker Compose可以管理多容器应用...', '运维', 'Docker,容器化,DevOps', 'https://example.com/images/docker.jpg', 593, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),

('1004', 'Vue3组件设计模式', '探讨Vue3中常用的组件设计模式及最佳实践', '# Vue3组件设计模式\n\n## 组合式API\nVue3的组合式API提供了更灵活的代码组织方式...\n\n## 状态管理\n在Vue3中，Pinia正逐渐取代Vuex成为首选的状态管理方案...\n\n## 组件通信\n除了props和emit，还有provide/inject等通信方式...', '前端', 'Vue,JavaScript,前端框架', 'https://example.com/images/vue3.jpg', 421, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),

('1005', '微服务架构设计原则', '微服务架构的核心设计原则和实施策略', '# 微服务架构设计原则\n\n## 服务拆分\n如何合理拆分服务是微服务架构成功的关键...\n\n## 服务通信\n服务间通信可以采用REST、gRPC或消息队列等方式...\n\n## 服务治理\n服务注册发现、负载均衡、熔断降级是微服务治理的重要内容...', '架构设计', '微服务,架构,系统设计', 'https://example.com/images/microservice.jpg', 352, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),

('1006', 'Linux服务器安全加固指南', '如何提高Linux服务器的安全性，防范常见攻击', '# Linux服务器安全加固指南\n\n## 账户安全\n禁用root远程登录，使用SSH密钥认证...\n\n## 防火墙配置\n配置iptables或ufw，只开放必要端口...\n\n## 日志监控\n设置日志监控和告警机制，及时发现安全问题...', '服务器安全', 'Linux,安全,服务器', 'https://example.com/images/linux-security.jpg', 287, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),

('1007', 'Git工作流最佳实践', 'Git版本控制工作流程及团队协作规范', '# Git工作流最佳实践\n\n## 分支管理\nGitFlow、GitHub Flow等分支管理策略的比较...\n\n## 提交规范\n规范的提交信息格式有助于生成变更日志...\n\n## 代码审查\n通过Pull Request进行代码审查的流程和要点...', '开发工具', 'Git,版本控制,团队协作', 'https://example.com/images/git-workflow.jpg', 178, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),

('1008', 'Redis缓存设计与实践', 'Redis缓存在项目中的应用和性能优化', '# Redis缓存设计与实践\n\n## 缓存策略\n缓存穿透、缓存击穿、缓存雪崩的原理和解决方案...\n\n## 数据结构选择\nRedis提供了多种数据结构，如何选择合适的数据结构...\n\n## 持久化配置\nRDB和AOF持久化机制的特点和适用场景...', '中间件', 'Redis,缓存,NoSQL', 'https://example.com/images/redis.jpg', 205, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),

('1009', '响应式编程入门', '响应式编程范式及在Java中的实现', '# 响应式编程入门\n\n## 响应式编程概念\n响应式编程是一种基于数据流和变化传播的编程范式...\n\n## Reactor框架\nReactor是Spring WebFlux使用的响应式库...\n\n## 实战案例\n使用响应式编程实现高并发API的示例...', '编程范式', 'Java,响应式编程,Reactor', 'https://example.com/images/reactive.jpg', 134, 0, '1', '1', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),

('1010', '消息队列选型对比', '主流消息队列产品的特点和适用场景分析', '# 消息队列选型对比\n\n## Kafka\n高吞吐量，适合日志收集和流处理...\n\n## RabbitMQ\n成熟稳定，支持多种消息模式...\n\n## RocketMQ\n阿里开源的消息中间件，兼具Kafka和RabbitMQ的优点...\n\n## 选型建议\n根据业务特点和技术栈选择合适的消息队列...', '中间件', 'Kafka,RabbitMQ,消息队列', 'https://example.com/images/mq.jpg', 0, 0, '1', '1', NOW(), NOW());

INSERT INTO `la_user_setting` (`id`, `user_id`, `bio`, `blog_intro`, `contact_email`, `github_url`, `extra_contacts`, `create_user`, `update_user`, `create_time`, `update_time`)
VALUES
('1001', '1', '热爱技术的全栈开发者', '这是一个分享技术文章和编程心得的个人博客', 'admin@example.com', 'https://github.com/admin', '{"wechat":"admin_wechat","twitter":"@admin_twitter"}', '1', '1', NOW(), NOW());

-- 吐槽表
CREATE TABLE IF NOT EXISTS `la_complaint` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '吐槽标题',
  `content` text COMMENT '吐槽内容',
  `mood` varchar(50) DEFAULT NULL COMMENT '心情标签',
  `images` text DEFAULT NULL COMMENT '图片URL，多个以逗号分隔',
  `status` tinyint(1) DEFAULT '1' COMMENT '发布状态: 0-草稿, 1-已发布',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
    `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_complaint_mood` (`mood`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='吐槽表';

-- 插入30条吐槽测试数据
INSERT INTO `la_complaint` (`id`, `title`, `content`, `mood`, `images`, `status`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
('2001', '今天的工作太难了', '需求变更太频繁，根本做不完，感觉要加班到天亮了...', '焦虑', 'https://example.com/images/work1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 30 DAY), DATE_SUB(NOW(), INTERVAL 30 DAY)),
('2002', '学习Spring Boot好难', '看了一天文档，感觉还是一头雾水，谁能告诉我怎么配置这个依赖啊？', '无奈', 'https://example.com/images/study1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 29 DAY), DATE_SUB(NOW(), INTERVAL 29 DAY)),
('2003', '地铁又挤爆了', '早高峰的地铁简直是噩梦，为什么大家都在同一时间上班啊！', '生气', 'https://example.com/images/life1.jpg,https://example.com/images/life2.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_SUB(NOW(), INTERVAL 28 DAY)),
('2004', '老板的新需求太离谱', '刚做完一个功能，老板又说要改，之前的设计都白做了', '生气', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 27 DAY), DATE_SUB(NOW(), INTERVAL 27 DAY)),
('2005', '今天终于解决了一个Bug', '折腾了三天的Bug终于解决了，原来是一个拼写错误，真是哭笑不得', '开心', 'https://example.com/images/tech1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 26 DAY), DATE_SUB(NOW(), INTERVAL 26 DAY)),
('2006', '分手了，心情低落', '我们在一起三年，最终还是走到了尽头，不知道以后该怎么办', '悲伤', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 25 DAY)),
('2007', '朋友聚会被放鸽子', '约好的聚会，结果所有人都临时有事，就我一个人傻等，真是太伤心了', '无奈', 'https://example.com/images/social1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 24 DAY), DATE_SUB(NOW(), INTERVAL 24 DAY)),
('2008', '新框架学习进度', '尝试学习新的前端框架，感觉好多概念不太理解，需要更多时间消化', '焦虑', NULL, 0, '1', '1', DATE_SUB(NOW(), INTERVAL 23 DAY), DATE_SUB(NOW(), INTERVAL 23 DAY)),
('2009', '周末加班没补偿', '公司让周末加班，结果没有加班费也没有调休，太黑心了', '生气', 'https://example.com/images/work2.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 22 DAY), DATE_SUB(NOW(), INTERVAL 22 DAY)),
('2010', '新电影太好看了', '今天看了期待已久的电影，剧情超出预期，演员表演也很到位', '兴奋', 'https://example.com/images/movie1.jpg,https://example.com/images/movie2.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 21 DAY), DATE_SUB(NOW(), INTERVAL 21 DAY)),
('2011', '连续熬夜三天', '项目上线在即，连续熬夜三天，感觉身体被掏空了', '疲惫', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
('2012', '又被相亲安排了', '父母安排的第10次相亲，对方条件很好但没感觉，拒绝后又被说不懂事', '无奈', 'https://example.com/images/date1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 19 DAY), DATE_SUB(NOW(), INTERVAL 19 DAY)),
('2013', '网络游戏太卡了', '花钱买的网游，结果服务器卡成狗，客服还爱答不理的', '生气', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY)),
('2014', '同事总是甩锅给我', '做错事从来不承认，问题总是推给别人，这种同事真的很难合作', '生气', 'https://example.com/images/work3.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 17 DAY), DATE_SUB(NOW(), INTERVAL 17 DAY)),
('2015', '今天面试通过了', '经过三轮面试，终于拿到了心仪公司的offer，薪资比现在高50%', '兴奋', 'https://example.com/images/interview1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY)),
('2016', '房租又涨价了', '刚租了一年，房东说要涨价20%，太离谱了，这年头租房真是太难了', '无奈', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
('2017', '新技术学习笔记', '最近学习了GraphQL，感觉比REST API在某些场景下更灵活，记录一下体会', '开心', 'https://example.com/images/tech2.jpg', 0, '1', '1', DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),
('2018', '健身计划中断了', '连续健身一个月后因为加班不得不中断，感觉前功尽弃，太可惜了', '悲伤', 'https://example.com/images/fitness1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 13 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY)),
('2019', '股票大跌心态崩了', '辛辛苦苦攒的钱投进股市，结果一周就跌了30%，真是太惨了', '悲伤', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
('2020', '同学聚会尴尬事', '同学聚会上，发现自己是唯一一个还没结婚的，被问得尴尬死了', '无奈', 'https://example.com/images/party1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),
('2021', '新领导不懂技术乱指挥', '新来的领导完全不懂技术，却喜欢指手画脚，搞得团队士气低落', '生气', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
('2022', '终于拿到驾照了', '考了三次科目二，第四次终于通过了，太开心了！', '兴奋', 'https://example.com/images/license1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
('2023', '被老板当众批评', '在会议上被老板点名批评，虽然不是我的错，但面子挂不住啊', '悲伤', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
('2024', '饭卡丢了好烦', '食堂饭卡丢了，里面还有200多块钱，补办还要收手续费，真烦人', '无奈', 'https://example.com/images/card1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
('2025', '深夜加班没打车费', '公司规定晚上10点后加班有打车报销，结果财务说预算用完了不给报，太坑了', '生气', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
('2026', '论文被拒绝了', '投了一篇论文到期刊，苦等3个月被拒绝，审稿意见简直莫名其妙', '悲伤', 'https://example.com/images/paper1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
('2027', 'Docker容器化实践', '今天尝试把项目Docker化，遇到了很多坑，记录一下解决过程', '焦虑', 'https://example.com/images/docker1.jpg', 0, '1', '1', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
('2028', '遇到神队友打游戏', '今天遇到一个超厉害的队友，带我连赢10把，爽翻了！', '兴奋', 'https://example.com/images/game1.jpg,https://example.com/images/game2.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
('2029', '连续下雨一周了', '已经连续下雨一周了，衣服都发霉了，心情也跟着变差', '无奈', 'https://example.com/images/rain1.jpg', 1, '1', '1', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
('2030', '年终奖太少了', '辛苦工作一年，年终奖还不够交房租，真是心寒啊！', '悲伤', NULL, 1, '1', '1', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));