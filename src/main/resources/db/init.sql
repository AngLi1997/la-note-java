-- MySQL dump 10.13  Distrib 8.0.42, for macos15.2 (arm64)
--
-- Host: 127.0.0.1    Database: la_note
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `la_note`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `la_note` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `la_note`;

--
-- Table structure for table `la_article`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `la_article` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要',
  `content` longtext COMMENT '文章内容',
  `category` varchar(50) DEFAULT NULL COMMENT '文章分类',
  `tags` varchar(255) DEFAULT NULL COMMENT '文章标签，以逗号分隔',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图URL',
  `view_count` int DEFAULT '0' COMMENT '浏览量',
  `status` tinyint(1) DEFAULT '0' COMMENT '发布状态: 0-草稿, 1-已发布',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `la_article`
--

INSERT INTO `la_article` VALUES ('1001','Spring Boot 入门教程','Spring Boot框架入门指南，从零开始学习Spring Boot','# Spring Boot 入门教程\n\n## 简介\nSpring Boot是一个用于简化Spring应用开发的框架，它提供了自动配置、内嵌服务器等特性...\n\n## 环境搭建\n首先需要安装JDK和Maven...\n\n## 创建项目\n使用Spring Initializr可以快速创建一个Spring Boot项目...','技术教程','Java,Spring,后端','http://172.30.1.160:9000/bmos-agent/1920771687566016512_IMG_6258.jpeg',6,1,'1','1','2025-04-28 16:28:23','2025-05-03 16:28:23',NULL);

--
-- Table structure for table `la_complaint`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `la_complaint` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '吐槽标题',
  `content` text COMMENT '吐槽内容',
  `mood` varchar(50) DEFAULT NULL COMMENT '心情标签',
  `images` text COMMENT '图片URL，多个以逗号分隔',
  `status` tinyint(1) DEFAULT '1' COMMENT '发布状态: 0-草稿, 1-已发布',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_complaint_mood` (`mood`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='吐槽表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `la_complaint`
--

INSERT INTO `la_complaint` VALUES ('2025','深夜加班没打车费','公司规定晚上10点后加班有打车报销，结果财务说预算用完了不给报，太坑了','生气','http://172.30.1.160:9000/bmos-agent/1920771687566016512_IMG_6258.jpeg',1,'1','1','2025-05-23 09:56:46','2025-05-23 09:56:46',NULL);

--
-- Table structure for table `la_site_setting`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `la_site_setting` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '网站标题',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '网站副标题',
  `description` varchar(500) DEFAULT NULL COMMENT '网站描述',
  `slogan` varchar(200) DEFAULT NULL COMMENT '网站标语/口号',
  `social_links` text COMMENT '社交链接，JSON格式存储',
  `keywords` varchar(255) DEFAULT NULL COMMENT '网站关键词',
  `icp` varchar(100) DEFAULT NULL COMMENT '备案信息',
  `is_default` tinyint(1) DEFAULT '1' COMMENT '是否为默认设置',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网站设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `la_site_setting`
--

INSERT INTO `la_site_setting` VALUES ('1','Liang\'s Note','个人技术博客','我会在这里分享我的心得，干货笔记，以及生活中的感悟、吐槽、看法，与思考。','交换余生','[\"https://github.com/AngLi1997\"]','技术,博客,笔记,分享','',1,'1','1','2025-05-29 14:38:15','2025-05-29 14:38:15',NULL);

--
-- Table structure for table `la_timeline_event`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `la_timeline_event` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '事件标题',
  `content` text COMMENT '事件内容',
  `event_date` date NOT NULL COMMENT '事件日期',
  `category` varchar(50) DEFAULT NULL COMMENT '事件分类',
  `icon` varchar(50) DEFAULT NULL COMMENT '事件图标',
  `display_order` int DEFAULT '0' COMMENT '显示顺序',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_timeline_category` (`category`),
  KEY `idx_timeline_event_date` (`event_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='时间轴事件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `la_timeline_event`
--

INSERT INTO `la_timeline_event` VALUES ('3001','个人博客上线','经过三天的努力，个人技术博客终于正式上线辣！','2025-05-29','技术','🚀',1,'1','1','2025-05-29 10:46:22','2025-05-29 10:46:22',NULL);

--
-- Table structure for table `la_user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `la_user` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0-禁用，1-正常',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `la_user`
--

INSERT INTO `la_user` VALUES ('1','admin','$2a$10$rOXX4HqoPvCgkQ29JowMVu9IsC.2j90gXsmyzgidMR6DfEibOZ6fy','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','李昂','liangliangaichirou@gmail.com',NULL,1,NULL,NULL,'2025-05-27 16:57:34','2025-05-27 16:57:34',NULL);

--
-- Table structure for table `la_user_setting`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `la_user_setting` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `bio` varchar(500) DEFAULT NULL COMMENT '个人简介',
  `blog_intro` varchar(1000) DEFAULT NULL COMMENT '博客介绍',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '邮箱联系方式',
  `github_url` varchar(255) DEFAULT NULL COMMENT 'GitHub链接',
  `extra_contacts` text COMMENT '自定义联系方式（JSON格式存储额外的联系方式）',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `la_user_setting`
--

INSERT INTO `la_user_setting` VALUES ('1001','1','热爱技术的全栈开发者','这是一个分享技术文章和编程心得的个人博客','liangliangaichirou@gmail.com','https://github.com/AngLi1997','{\"wechat\":\"Dec_LiangLiang\"}','1','1','2025-05-28 17:04:32','2025-05-28 17:04:32',NULL);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29 17:51:31
