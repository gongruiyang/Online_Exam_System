/*
SQLyog Enterprise - MySQL GUI v7.15 
MySQL - 5.7.31-log : Database - onlineexam
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`onlineexam` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `onlineexam`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登陆账号',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆密码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `role` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '1' COMMENT '角色: 1管理员 2教师 3学生',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `idNumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号码',
  `sex` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`username`,`password`,`name`,`role`,`email`,`idNumber`,`sex`,`tel`) values ('123','123','宫瑞阳','1','gongruiyang1999@163.com','340000000000000000','男','18100000000');

/*Table structure for table `exams` */

DROP TABLE IF EXISTS `exams`;

CREATE TABLE `exams` (
  `examId` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '考试场次id',
  `tid` int(15) DEFAULT NULL COMMENT '组织考试老师 : teacher表的外键',
  `teacherName` varchar(16) DEFAULT NULL COMMENT '组织该考试的老师姓名',
  `paperId` int(15) DEFAULT NULL COMMENT '试卷id',
  `examTime` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '考试开始时间',
  `examLastTime` int(15) DEFAULT '120' COMMENT '考试持续时间',
  `grade` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '年级',
  `term` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学期',
  `institute` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学院',
  `major` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '专业',
  `subject` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '科目',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '考试描述',
  `tips` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '考生须知',
  `examType` int(15) DEFAULT '1' COMMENT '考试类型 : 1闭卷 2开卷',
  `totalSingleChoiceScore` int(15) DEFAULT '0' COMMENT '单选题总分',
  `totalFillGapScore` int(15) DEFAULT '0' COMMENT '填空题总分',
  `totalJudgeScore` int(15) DEFAULT '0' COMMENT '判断题总分',
  `totalShortAnsScore` int(15) DEFAULT '0' COMMENT '简答题总分',
  `totalScore` int(15) DEFAULT '0' COMMENT '试卷总分',
  PRIMARY KEY (`examId`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `exams` */

insert  into `exams`(`examId`,`tid`,`teacherName`,`paperId`,`examTime`,`examLastTime`,`grade`,`term`,`institute`,`major`,`subject`,`description`,`tips`,`examType`,`totalSingleChoiceScore`,`totalFillGapScore`,`totalJudgeScore`,`totalShortAnsScore`,`totalScore`) values (8,1,'张三',0,'2022/04/10 08:28',120,'2018','第一学期','管理学院','信息管理与信息系统','计算机网络','2018级信息管理与信息系统专业计算机网络期末考试','诚信考试',1,10,10,10,10,40),(16,1,'张三',2,'2022/04/17 15:15',120,'2018','第一学期','管理学院','信息管理与信息系统','计算机网络','test','诚信考试',1,10,10,10,10,40),(17,1,'张三',3,'2022/05/06 12:05',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','不要作弊','不要作弊',1,20,20,20,40,100),(18,1,'张三',4,'2022/05/07 14:56',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','诚信第一','诚信第一',1,10,10,10,20,50),(19,1,'张三',5,'2022/05/12 16:34',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,20,20,20,20,80),(25,1,'张三',6,'2022/05/14 01:07',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(26,1,'张三',7,'2022/05/14 01:37',60,'2018级','上学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(27,1,'张三',8,'2022/05/18 14:45',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(28,1,'张三',9,'2022/05/23 16:33',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','不要作弊','不要作弊',1,40,40,40,60,180),(29,1,'张三',10,'2022/05/25 17:49',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','不作弊','不作弊',1,6,6,4,2,18),(30,1,'张三',11,'2022/05/30 16:45',120,'2018级','上学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(31,1,'张三',12,'2022/06/04 13:24',120,'2018级','上学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(32,1,'张三',13,'2022/06/22 22:35',120,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(33,5,'张二',14,'2022/06/22 22:56',90,'2018级','上学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(34,1,'张三',15,'2022/06/23 08:37',120,'2018级','上学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,6,6,4,2,18),(35,1,'张三',16,'2022/06/27 12:00',90,'2018级','第一学期','管理学院','信息管理与信息系统','计算机网络','test','test',1,10,10,10,10,40);

/*Table structure for table `papers` */

DROP TABLE IF EXISTS `papers`;

CREATE TABLE `papers` (
  `paperId` int(15) DEFAULT NULL COMMENT '试卷id',
  `examId` int(15) DEFAULT NULL COMMENT '考试场次id',
  `quesId` int(15) DEFAULT NULL COMMENT '题目id',
  `quesType` int(15) DEFAULT NULL COMMENT '题目类型：1单选题 2填空题 3判断题 4简答题',
  `score` int(15) DEFAULT NULL COMMENT '题目分数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `papers` */

insert  into `papers`(`paperId`,`examId`,`quesId`,`quesType`,`score`) values (0,8,8,1,2),(0,8,11,1,2),(0,8,16,1,2),(0,8,15,1,2),(0,8,10,1,2),(0,8,2,2,2),(0,8,11,2,2),(0,8,4,2,2),(0,8,5,2,2),(0,8,6,2,2),(0,8,6,3,2),(0,8,9,3,2),(0,8,2,3,2),(0,8,3,3,2),(0,8,8,3,2),(0,8,4,4,5),(0,8,1,4,5),(1,15,7,1,2),(1,15,14,1,2),(1,15,16,1,2),(1,15,4,1,2),(1,15,8,1,2),(1,15,5,1,2),(1,15,10,1,2),(1,15,11,1,2),(1,15,20,1,2),(1,15,6,1,2),(1,15,4,2,2),(1,15,10,2,2),(1,15,5,2,2),(1,15,6,2,2),(1,15,11,2,2),(1,15,4,3,2),(1,15,2,3,2),(1,15,10,3,2),(1,15,1,3,2),(1,15,6,3,2),(2,16,19,1,2),(2,16,11,1,2),(2,16,4,1,2),(2,16,5,1,2),(2,16,8,1,2),(2,16,1,2,2),(2,16,10,2,2),(2,16,4,2,2),(2,16,3,2,2),(2,16,9,2,2),(2,16,8,3,2),(2,16,5,3,2),(2,16,6,3,2),(2,16,10,3,2),(2,16,3,3,2),(2,16,6,4,5),(2,16,10,4,5),(3,17,3,1,2),(3,17,17,1,2),(3,17,10,1,2),(3,17,2,1,2),(3,17,8,1,2),(3,17,18,1,2),(3,17,14,1,2),(3,17,19,1,2),(3,17,5,1,2),(3,17,6,1,2),(3,17,8,2,2),(3,17,6,2,2),(3,17,7,2,2),(3,17,9,2,2),(3,17,3,2,2),(3,17,1,2,2),(3,17,10,2,2),(3,17,5,2,2),(3,17,11,2,2),(3,17,4,2,2),(3,17,1,3,2),(3,17,2,3,2),(3,17,3,3,2),(3,17,8,3,2),(3,17,7,3,2),(3,17,6,3,2),(3,17,4,3,2),(3,17,10,3,2),(3,17,9,3,2),(3,17,5,3,2),(3,17,9,4,5),(3,17,3,4,5),(3,17,4,4,5),(3,17,7,4,5),(3,17,5,4,5),(3,17,10,4,5),(3,17,6,4,5),(3,17,8,4,5),(4,18,4,1,2),(4,18,3,1,2),(4,18,6,1,2),(4,18,17,1,2),(4,18,7,1,2),(4,18,9,2,2),(4,18,8,2,2),(4,18,4,2,2),(4,18,7,2,2),(4,18,3,2,2),(4,18,1,3,2),(4,18,9,3,2),(4,18,3,3,2),(4,18,5,3,2),(4,18,2,3,2),(4,18,4,4,5),(4,18,5,4,5),(4,18,2,4,5),(4,18,1,4,5),(5,19,11,1,2),(5,19,4,1,2),(5,19,14,1,2),(5,19,15,1,2),(5,19,1,1,2),(5,19,12,1,2),(5,19,2,1,2),(5,19,13,1,2),(5,19,5,1,2),(5,19,18,1,2),(5,19,8,2,2),(5,19,3,2,2),(5,19,7,2,2),(5,19,2,2,2),(5,19,10,2,2),(5,19,5,2,2),(5,19,6,2,2),(5,19,11,2,2),(5,19,9,2,2),(5,19,1,2,2),(5,19,7,3,2),(5,19,10,3,2),(5,19,9,3,2),(5,19,3,3,2),(5,19,8,3,2),(5,19,4,3,2),(5,19,6,3,2),(5,19,5,3,2),(5,19,1,3,2),(5,19,2,3,2),(5,19,6,4,5),(5,19,8,4,5),(5,19,3,4,5),(5,19,10,4,5),(6,25,43,1,2),(6,25,44,1,2),(6,25,45,1,2),(6,25,33,2,2),(6,25,34,2,2),(6,25,35,2,2),(6,25,25,3,2),(6,25,26,3,2),(6,25,18,4,2),(7,26,46,1,2),(7,26,47,1,2),(7,26,48,1,2),(7,26,36,2,2),(7,26,37,2,2),(7,26,38,2,2),(7,26,27,3,2),(7,26,28,3,2),(7,26,19,4,2),(8,27,49,1,2),(8,27,50,1,2),(8,27,51,1,2),(8,27,39,2,2),(8,27,40,2,2),(8,27,41,2,2),(8,27,29,3,2),(8,27,30,3,2),(8,27,20,4,2),(9,28,33,1,2),(9,28,44,1,2),(9,28,4,1,2),(9,28,43,1,2),(9,28,36,1,2),(9,28,40,1,2),(9,28,17,1,2),(9,28,50,1,2),(9,28,7,1,2),(9,28,15,1,2),(9,28,16,1,2),(9,28,35,1,2),(9,28,11,1,2),(9,28,9,1,2),(9,28,26,1,2),(9,28,20,1,2),(9,28,21,1,2),(9,28,5,1,2),(9,28,48,1,2),(9,28,38,1,2),(9,28,6,2,2),(9,28,36,2,2),(9,28,9,2,2),(9,28,38,2,2),(9,28,32,2,2),(9,28,5,2,2),(9,28,7,2,2),(9,28,34,2,2),(9,28,21,2,2),(9,28,25,2,2),(9,28,20,2,2),(9,28,1,2,2),(9,28,15,2,2),(9,28,10,2,2),(9,28,11,2,2),(9,28,3,2,2),(9,28,39,2,2),(9,28,12,2,2),(9,28,26,2,2),(9,28,40,2,2),(9,28,29,3,2),(9,28,2,3,2),(9,28,13,3,2),(9,28,6,3,2),(9,28,28,3,2),(9,28,20,3,2),(9,28,30,3,2),(9,28,27,3,2),(9,28,25,3,2),(9,28,10,3,2),(9,28,17,3,2),(9,28,12,3,2),(9,28,21,3,2),(9,28,15,3,2),(9,28,11,3,2),(9,28,7,3,2),(9,28,19,3,2),(9,28,24,3,2),(9,28,8,3,2),(9,28,9,3,2),(9,28,19,4,5),(9,28,12,4,5),(9,28,18,4,5),(9,28,15,4,5),(9,28,9,4,5),(9,28,6,4,5),(9,28,11,4,5),(9,28,3,4,5),(9,28,4,4,5),(9,28,7,4,5),(9,28,20,4,5),(9,28,1,4,5),(10,29,52,1,2),(10,29,53,1,2),(10,29,54,1,2),(10,29,42,2,2),(10,29,43,2,2),(10,29,44,2,2),(10,29,31,3,2),(10,29,32,3,2),(10,29,21,4,2),(11,30,55,1,2),(11,30,56,1,2),(11,30,57,1,2),(11,30,45,2,2),(11,30,46,2,2),(11,30,47,2,2),(11,30,33,3,2),(11,30,34,3,2),(11,30,22,4,2),(12,31,58,1,2),(12,31,59,1,2),(12,31,60,1,2),(12,31,48,2,2),(12,31,49,2,2),(12,31,50,2,2),(12,31,35,3,2),(12,31,36,3,2),(12,31,23,4,2),(13,32,61,1,2),(13,32,62,1,2),(13,32,63,1,2),(13,32,51,2,2),(13,32,52,2,2),(13,32,53,2,2),(13,32,37,3,2),(13,32,38,3,2),(13,32,24,4,2),(14,33,64,1,2),(14,33,65,1,2),(14,33,66,1,2),(14,33,54,2,2),(14,33,55,2,2),(14,33,56,2,2),(14,33,39,3,2),(14,33,40,3,2),(14,33,25,4,2),(15,34,67,1,2),(15,34,68,1,2),(15,34,69,1,2),(15,34,57,2,2),(15,34,58,2,2),(15,34,59,2,2),(15,34,41,3,2),(15,34,42,3,2),(15,34,26,4,2),(16,35,67,1,2),(16,35,13,1,2),(16,35,15,1,2),(16,35,24,1,2),(16,35,17,1,2),(16,35,11,2,2),(16,35,45,2,2),(16,35,48,2,2),(16,35,22,2,2),(16,35,19,2,2),(16,35,3,3,2),(16,35,29,3,2),(16,35,5,3,2),(16,35,36,3,2),(16,35,35,3,2),(16,35,18,4,5),(16,35,8,4,5);

/*Table structure for table `question_fill_gap` */

DROP TABLE IF EXISTS `question_fill_gap`;

CREATE TABLE `question_fill_gap` (
  `quesId` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '题目编号',
  `quesDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '题目描述',
  `rightAns` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '正确答案',
  `ansAnalysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '答案解析',
  `score` int(15) DEFAULT '2' COMMENT '分值',
  `blgSub` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属科目',
  `tid` int(15) DEFAULT '1' COMMENT '题目贡献者',
  PRIMARY KEY (`quesId`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

/*Data for the table `question_fill_gap` */

insert  into `question_fill_gap`(`quesId`,`quesDesc`,`rightAns`,`ansAnalysis`,`score`,`blgSub`,`tid`) values (1,'从计算机网络系统组成的角度看，计算机网络包含____','通信子网',NULL,2,'计算机网络',1),(2,'收发电子邮件，属于ISO/OSI RM中____层的功能。','应用',NULL,2,'计算机网络',1),(3,'在TCP/IP层次模型中与OSI参考模型第四层相对应的主要协议有____','TCP|传输控制协议|UDP|用户数据报协议|tcp|udp',NULL,2,'计算机网络',1),(4,'计算机网络中常用的三种有线媒体是 (),()和 ()','同轴电缆 双绞线 光纤',NULL,2,'计算机网络',1),(5,'国内最早的四大网络包括原邮电部的ChinaNet. 原电子部的ChinaGBN. 教育部的____和中科院的CSTnet','CERnet|中国教育科研网',NULL,2,'计算机网络',1),(6,'复盖一个国家，地区或几个洲的计算机网络称为____',' 广域网|局域网|城域网',NULL,2,'计算机网络',1),(7,'Outlook等常用电子邮件软件接收邮件使用的协议是____','POP3|邮局协议版本3|邮局协议',NULL,2,'计算机网络',1),(8,'通信系统中，称调制前的电信号为____信号，调制后的信号为调制信号','基带',NULL,2,'计算机网络',1),(9,'按照IPV4标准,IP地址205.3.127.13属于____类地址','C|c',NULL,2,'计算机网络',1),(10,'计算机网络采用()技术，而传统电话网络则采用____技术','分组交换电路交换',NULL,2,'计算机网络',1),(11,'2','2','2',2,'2',123),(12,'Test','Test','Test',2,'计算机网络',1),(13,'Test','Test','Test',2,'计算机网络',1),(14,'Test','Test','Test',2,'计算机网络',1),(15,'Test','Test','Test',2,'计算机网络',1),(16,'Test','Test','Test',2,'计算机网络',1),(17,'Test','Test','Test',2,'计算机网络',1),(18,'Test','Test','Test',2,'计算机网络',1),(19,'Test','Test','Test',2,'计算机网络',1),(20,'Test','Test','Test',2,'计算机网络',1),(21,'Test','Test','Test',2,'计算机网络',1),(22,'Test','Test','Test',2,'计算机网络',1),(23,'Test','Test','Test',2,'计算机网络',1),(24,'Test','Test','Test',2,'计算机网络',1),(25,'Test','Test','Test',2,'计算机网络',1),(26,'Test','Test','Test',2,'计算机网络',1),(27,'Test','Test','Test',2,'计算机网络',1),(28,'Test','Test','Test',2,'计算机网络',1),(29,'Test','Test','Test',2,'计算机网络',1),(30,'Test','Test','Test',2,'计算机网络',1),(31,'Test','Test','Test',2,'计算机网络',1),(32,'Test','Test','Test',2,'计算机网络',1),(33,'Test','Test','Test',2,'计算机网络',1),(34,'Test','Test','Test',2,'计算机网络',1),(35,'Test','Test','Test',2,'计算机网络',1),(36,'Test','Test','Test',2,'计算机网络',1),(37,'Test','Test','Test',2,'计算机网络',1),(38,'Test','Test','Test',2,'计算机网络',1),(39,'Test','Test','Test',2,'计算机网络',1),(40,'Test','Test','Test',2,'计算机网络',1),(41,'Test','Test','Test',2,'计算机网络',1),(42,'Test','Test','Test',2,'计算机网络',1),(43,'Test','Test','Test',2,'计算机网络',1),(44,'Test','Test','Test',2,'计算机网络',1),(45,'Test','Test','Test',2,'计算机网络',1),(46,'Test','Test','Test',2,'计算机网络',1),(47,'Test','Test','Test',2,'计算机网络',1),(48,'Test','Test','Test',2,'计算机网络',1),(49,'Test','Test','Test',2,'计算机网络',1),(50,'Test','Test','Test',2,'计算机网络',1),(51,'Test','Test','Test',2,'计算机网络',1),(52,'Test','Test','Test',2,'计算机网络',1),(53,'Test','Test','Test',2,'计算机网络',1),(54,'Test','Test','Test',2,'计算机网络',5),(55,'Test','Test','Test',2,'计算机网络',5),(56,'Test','Test','Test',2,'计算机网络',5),(57,'Test','Test','Test',2,'计算机网络',1),(58,'Test','Test','Test',2,'计算机网络',1),(59,'Test','Test','Test',2,'计算机网络',1);

/*Table structure for table `question_judge` */

DROP TABLE IF EXISTS `question_judge`;

CREATE TABLE `question_judge` (
  `quesId` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '题目编号',
  `quesDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '题目描述',
  `rightAns` int(15) DEFAULT '0' COMMENT '正确答案：1表示T or 0表示F',
  `ansAnalysis` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '答案解析',
  `score` int(255) DEFAULT '2' COMMENT '分值：默认2分一个',
  `blgSub` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属科目',
  `tid` int(15) DEFAULT '1' COMMENT '题目贡献者：teacher表的外键',
  PRIMARY KEY (`quesId`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Data for the table `question_judge` */

insert  into `question_judge`(`quesId`,`quesDesc`,`rightAns`,`ansAnalysis`,`score`,`blgSub`,`tid`) values (1,'与有线网相比,无线网的数据传输率一般相对较慢',1,NULL,2,'计算机网络',1),(2,'OSI参考模型中,不同节点的同等层具有不同的功能',0,NULL,2,'计算机网络',1),(3,'普通电脑不能作为服务器',0,NULL,2,'计算机网络',1),(4,'没有网线的电脑不能连入互联网',0,NULL,2,'计算机网络',1),(5,'网卡必须安装驱动程序',1,NULL,2,'计算机网络',1),(6,'UTP为屏蔽双绞线',0,NULL,2,'计算机网络',1),(7,'网络接口卡又称为网卡,它是构成网络的基本部件',1,NULL,2,'计算机网络',1),(8,'无线AP可以成倍地扩展网络覆盖范围',1,NULL,2,'计算机网络',1),(9,'SMTP是一组用于由源地址到目的地址传送邮件的协议',1,NULL,2,'计算机网络',1),(10,'任务管理器可以关闭所有的进程',0,NULL,2,'计算机网络',1),(11,'Test',0,'Test',2,'计算机网络',1),(12,'Test',0,'Test',2,'计算机网络',1),(13,'Test',0,'Test',2,'计算机网络',1),(14,'Test',0,'Test',2,'计算机网络',1),(15,'Test',0,'Test',2,'计算机网络',1),(16,'Test',0,'Test',2,'计算机网络',1),(17,'Test',0,'Test',2,'计算机网络',1),(18,'Test',0,'Test',2,'计算机网络',1),(19,'Test',0,'Test',2,'计算机网络',1),(20,'Test',0,'Test',2,'计算机网络',1),(21,'Test',0,'Test',2,'计算机网络',1),(22,'Test',0,'Test',2,'计算机网络',1),(23,'Test',0,'Test',2,'计算机网络',1),(24,'Test',0,'Test',2,'计算机网络',1),(25,'Test',0,'Test',2,'计算机网络',1),(26,'Test',0,'Test',2,'计算机网络',1),(27,'Test',0,'Test',2,'计算机网络',1),(28,'Test',0,'Test',2,'计算机网络',1),(29,'Test',0,'Test',2,'计算机网络',1),(30,'Test',0,'Test',2,'计算机网络',1),(31,'Test',0,'Test',2,'计算机网络',1),(32,'Test',0,'Test',2,'计算机网络',1),(33,'Test',0,'Test',2,'计算机网络',1),(34,'Test',0,'Test',2,'计算机网络',1),(35,'Test',0,'Test',2,'计算机网络',1),(36,'Test',0,'Test',2,'计算机网络',1),(37,'Test',0,'Test',2,'计算机网络',1),(38,'Test',0,'Test',2,'计算机网络',1),(39,'Test',0,'Test',2,'计算机网络',5),(40,'Test',0,'Test',2,'计算机网络',5),(41,'Test',0,'Test',2,'计算机网络',1),(42,'Test',0,'Test',2,'计算机网络',1);

/*Table structure for table `question_short_ans` */

DROP TABLE IF EXISTS `question_short_ans`;

CREATE TABLE `question_short_ans` (
  `quesId` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '题目编号',
  `quesDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '题目描述',
  `refAns` text COMMENT '参考答案',
  `ansAnalysis` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '答案解析',
  `score` int(15) DEFAULT '5' COMMENT '分值： 默认5分一个',
  `blgSub` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属科目',
  `tid` int(15) DEFAULT '1' COMMENT '题目贡献者：teacher表的外键',
  PRIMARY KEY (`quesId`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `question_short_ans` */

insert  into `question_short_ans`(`quesId`,`quesDesc`,`refAns`,`ansAnalysis`,`score`,`blgSub`,`tid`) values (1,'TCP协议和UDP协议的区别有哪些？','（1）TCP属于面向连接的协议，UDP属于面向无连接的协议；\r\n（2）TCP可以保证数据可靠、有序的传输，可以进行流量控制，UDP无法实现；\r\n（3）TCP协议有效载荷小于UDP协议（基于MSS计算），UDP性能高于TCP ；\r\n（4）TCP一般用于可靠的，对延时要求不高的应用，UDP一般应用于小数据量或对延时敏感的应用；',NULL,5,'计算机网络',1),(2,'简述OSI七层模型中传输层、网络层、数据链路层的功能和它们进行数据封装时头部信息。','（1）传输层：服务点编址、分段与重组、连接控制、流量控制、差错控制，封装源端口、目的端口；\r\n（2）网络层：为网络设备提供逻辑地址；进行路由选择、分组转发；封装源IP、目的IP、协议号；\r\n（3）数据链路层：组帧、物理编址、流量控制、差错控制、接入控制；封装源MAC、目的MAC、帧类型。',NULL,5,'计算机网络',1),(3,'TCP/IP协议栈和OSI七层模型之间的区别','（1）TCP/IP协议栈是由一组协议共同组成的一个协议栈，OSI定义的是一个网络的结构体系和各层功能的划分；\r\n（2）OSI是模型、框架，TCP/IP协议栈是实现各层功能的协议族；\r\n（3）OSI为七层、TCP/IP协议栈为四层。\r\n（4）TCP/IP的应用层相对于OSI的应、表、会三层 5、TCP/IP的网络接口层相对于OSI的数链层和物理层',NULL,5,'计算机网络',1),(4,'IP地址与MAC地址的区别是什么','（1）IP地址是网络层逻辑地址，MAC地址是数据链路层物理地址；\r\n（2）IP地址用于表示网络中某设备或节点的身份（网络位、主机位）（本网段唯一）；\r\n（3）MAC地址用于表示某设备或节点在本以太网链路中的物理地址（全局唯一）；\r\n（4）IP地址由32位，MAC由48位组成；\r\n（5）IP地址可区别不同网段，MAC地址无法进行区分。',NULL,5,'计算机网络',1),(5,'ARP协议的工作原理','在TCP/IP协议中，A给B发送IP包时，在A不知道B的MAC地址的情况下，A就广播一个ARP请求包，请求包中填有B的IP(192.168.1.2)，以太网中的所有计算机都会接收这个请h求，而正常的情况下只有B会给出ARP应答包，包中就填充上了B的MAC地址，并回复给A。 A得到ARP应答后，将B的MAC地址放入本机缓存，便于下次使用。',NULL,5,'计算机网络',1),(6,'TCP三次握手的过程','基于TCP协议传输数据之前，为确认连接正常，会通过三次握手来建立虚连接，连接建立完成后才能进行数据的传输。三次握手的过程如下：首先由发起端发送连接请求；当接受方收到连接请求后，如果同意建立连接会回复应答报文；然后发送方收到此应答报文，会发送对此应答报文的确认信息。通过这种三次握手的过程来在数据发送的初期建立连接，保障数据的正常传输。',NULL,5,'计算机网络',1),(7,'请问端口号的作用是什么？请问当一台客户端主机访问互联网某服务器的WEB服务时，传输层封装的源端口、目的端口分别是什么？','（1）传输层端口号的作用是用于区分上层应用层的不同应用服务进程的。\r\n（2）客户端向服务器端发数据时，源端口为大于1024随机端口，如1150，目的端口为服务器WEB服务端口，如80。\r\n（3）当服务器端向客户端发数据时，源端口为80，目的端口为1150  ',NULL,5,'计算机网络',1),(8,'二层交换机与路由器有什么区别，为什么交换机一般用于局域网内主机的互联，不能实现不同IP网络的主机互相访问。路由器为什么可以实现不同网段主机之间的访问。为什么不使用路由器来连接局域网主机 ?','（1）从OSI的角度分析交换机和路由器的区别交换机属于数据链路层设备，识别数据帧的MAC地址信息进行转发；路由器属于网络层设备，通过识别网络层的IP地址信息进行数据转发；\r\n（2）数据处理方式的区别交换机对于数据帧进行转发，交换机不隔离广播，交换机对于未知数据帧进行扩散；路由器对IP包进行转发，路由器不转发广播包，路由器对于未知数据包进行丢弃；\r\n（3）数据转发性能方面交换机是基于硬件的二层数据转发，转发性能强；路由器是基于软件的三层数据转发，转发性能相对较差；\r\n（4）接口类型交换机一般只具备以太网接口，类型单一，接口密度大；路由器能够提供各种类型的广域网接口，能够连接不同类型的网络链路，接口数较少。\r\n（5）应用环境交换机一般应用于局域网内部，大量用户的网络接入设备。路由器一般用于网络间的互联。',NULL,5,'计算机网络',1),(9,'三层交换机和路由器有什么区别？','（1）功能三层交换机和路由器一样属于网络层设备，能够进行三层数据包的转发。\r\n（2）性能三层交换机能够基于ASIC芯片进行硬件的转发（一次路由多次交换）；路由器通过CPU＋软件进行运算转发数据，性能低。\r\n（3）接口类型三层交换机一般只具备以太网接口，类型单一，接口密度大；路由器能够提供各种类型的广域网接口，能够连接不同类型的网络链路，接口数较少。\r\n（4）应用环境三层交换机一般用于局域网内不同网段间的互通，路由器一般用于网络出口或广域网互联。',NULL,5,'计算机网络',1),(10,'请讲述交换机的启动过程','（1）交换机开机加电自检硬件；\r\n（2）交换机从ROM中读取微代码从FLASH中加载操作系统（RGNOS）；\r\n（3）将操作系统（RGNOS）加载到RAM中，操作系统启动完成；\r\n（4）系统从FLASH中检测是否有配置文件（config.text），如有，将配置文件加载到 RAM中（running-config）；\r\n（5）如无配置文件，将启动setup命令，进行交互式基本配置。',NULL,5,'计算机网络',1),(11,'Test','Test','Test',2,'计算机网络',1),(12,'Test','Test','Test',2,'计算机网络',1),(13,'Test','Test','Test',2,'计算机网络',1),(14,'Test','Test','Test',2,'计算机网络',1),(15,'Test','Test','Test',2,'计算机网络',1),(16,'Test','Test','Test',2,'计算机网络',1),(17,'Test','Test','Test',2,'计算机网络',1),(18,'Test','Test','Test',2,'计算机网络',1),(19,'Test','Test','Test',2,'计算机网络',1),(20,'Test','Test','Test',2,'计算机网络',1),(21,'Test','Test','Test',2,'计算机网络',1),(22,'Test','Test','Test',2,'计算机网络',1),(23,'Test','Test','Test',2,'计算机网络',1),(24,'Test','Test','Test',2,'计算机网络',1),(25,'Test','Test','Test',2,'计算机网络',5),(26,'Test','Test','Test',2,'计算机网络',1);

/*Table structure for table `question_single_choice` */

DROP TABLE IF EXISTS `question_single_choice`;

CREATE TABLE `question_single_choice` (
  `quesId` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '题目编号',
  `quesDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '题目描述',
  `ansA` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '答案A',
  `ansB` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '答案B',
  `ansC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '答案C',
  `ansD` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '答案D',
  `rightAns` varchar(20) DEFAULT '1' COMMENT '正确答案 : A~D',
  `ansAnalysis` varchar(255) DEFAULT NULL COMMENT '答案解析',
  `score` int(15) DEFAULT '2' COMMENT '分值 : 默认2分一个',
  `blgSub` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属科目',
  `tid` int(15) DEFAULT '1' COMMENT '题目贡献者',
  PRIMARY KEY (`quesId`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Data for the table `question_single_choice` */

insert  into `question_single_choice`(`quesId`,`quesDesc`,`ansA`,`ansB`,`ansC`,`ansD`,`rightAns`,`ansAnalysis`,`score`,`blgSub`,`tid`) values (1,'DNS 服务器和DHCP服务器的作用是（ ）','将IP地址翻译为计算机名，为客户机分配IP地址','将IP地址翻译为计算机名、解析计算机的MAC地址','将计算机名翻译为IP地址、为客户机分配IP地址','将计算机名翻译为IP地址、解析计算机的MAC地址','C',NULL,2,'计算机网络',1),(2,'HTTP协议通常使用什么协议进行传输（ ）','ARP','DHCP','UDP','TCP','D',NULL,2,'计算机网络',1),(3,'查看DNS缓存记录的命令（ ）','ipconfig/displaydns','nslookup','ipconfig/release','ipconfig/flushdns','A',NULL,2,'计算机网络',1),(4,'DHCP(  )报文的目的IP地址为255.255.255.255','DhcpDisover','DhcpOffer','DhcpAck','DhcpNack','A',NULL,2,'计算机网络',1),(5,'下列地址中，（  ）不是DHCP服务器分配的IP地址','196.254.109.100','169.254.12.42','69.254.48.45','96.254.54.15','B',NULL,2,'计算机网络',1),(6,'DHCP通常可以为客户端自动配置哪些网络参数（）','IP，掩码，网关，DNS','IP，掩码，域名，SMTP','网关，掩码，浏览器，FTP','IP，网关，DNS，服务器','A',NULL,2,'计算机网络',1),(7,'DNS服务器在名称解析过程中正确的查询顺序为（）','本地缓存记录→区域记录→转发域名服务器→根域名服务器','区域记录→本地缓存记录→转发域名服务器→根域名服务器','本地缓存记录→区域记录→根域名服务器→转发域名服务器','区域记录→本地缓存记录→根域名服务器→转发域名服务器','A',NULL,2,'计算机网络',1),(8,'在TCP/IP协议中，序号小于（  ）的端口称为熟知端口(well-known port)。','1024','64','256','128','A',NULL,2,'计算机网络',1),(9,'在Internet上用TCP/IP播放视频，想用传输层的最快协议，以减少时延，要使用（ ）','UDP协议的低开销特性','UDP协议的高开销特性','TCP协议的低开销特性','TCP协议的高开销特性','A',NULL,2,'计算机网络',1),(10,'在TCP协议中采用（ ）来区分不同的应用进程','端口号','IP地址','协议类型','MAC地址','A',NULL,2,'计算机网络',1),(11,'可靠的传输协议中的“可靠”指的是（ ）','使用面向连接的会话','使用“尽力而为”的传输','使用滑动窗口来维持可靠性','使用确认重传机制来确保传输的数据不丢失','D',NULL,2,'计算机网络',1),(12,'假设拥塞窗口为50KB，接收窗口为80KB，TCP能够发送的最大字节数为（ ）','50KB','80KB','130KB','30KB','A',NULL,2,'计算机网络',1),(13,'主机A向主机B发送一个（SYN=1，seq=2000）的TCP报文，期望与主机B建立连接，若主机B接受连接请求，则主机B发送的正确有TCP报文可能是（ ）','（SYN=0,ACK=0,seq=2001,ack=2001）','（SYN=1,ACK=1,seq=2000,ack=2000）','•	C.（SYN=1,ACK=1,seq=2001,ack=2001）','（SYN=0,ACK=1,seq=2000,ack=2000）','C',NULL,2,'计算机网络',1),(14,'主机A向主机B连续发送了两个TCP报文段，其序号分别为70和100。试问： （1）第一个报文段携带了（）个字节的数据？',' 70','30','100','170','B',NULL,2,'计算机网络',1),(15,'PCM脉码调制的过程（ ）','采样、量化、编码','量化、编码、采样','编码、量化、采样','采样、编码、量化','A',NULL,2,'计算机网络',1),(16,'若某采用4相位调制的通信链路的数据传输速率为2400bps，则该链路的波特率为（）','600Baud','1200Baud','4800Baud','9600Baud','B',NULL,2,'计算机网络',1),(17,'以下关于数据传输速率的描述中，错误的是( )','数据传输速率表示每秒钟传输构成数据代码的二进制比特数','对于二进制数据，数据传输速率为S=1/T (bps)','常用的数据传输速率单位有: 1Mbps=1.024×106bps','数据传输速率是描述数据传输系统性能的重要技术指标之一','C',NULL,2,'计算机网络',1),(18,'以下关于时分多路复用概念的描述中，错误的是.(  ).','时分多路复用将线路使用的时间分成多个时间片','时分多路复用分为同步时分多路复用与统计时分多路复用','时分多路复用使用“帧”与数据链路层“帧”的概念、作用是不同的','统计时分多路复用将时间片预先分配给各个信道','D',NULL,2,'计算机网络',1),(19,'1000BASE-T标准支持的传输介质是（）','双绞线','同轴电缆','光纤','无线电','A',NULL,2,'计算机网络',1),(20,'一个以太网交换机，读取整个数据帧，对数据帧进行差错校验后再转发出去，这种交换方式称为 （）','直通交换','无碎片交换','无差错交换','存储转发交换','D',NULL,2,'计算机网络',1),(21,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(22,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(23,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(24,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(25,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(26,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(27,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(28,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(29,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(30,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(31,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(32,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(33,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(34,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(35,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(36,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(37,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(38,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(39,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(40,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(41,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(42,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(43,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(44,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(45,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(46,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(47,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(48,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(49,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(50,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(51,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(52,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(53,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(54,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(55,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(56,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(57,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(58,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(59,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(60,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(61,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(62,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(63,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(64,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',5),(65,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',5),(66,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',5),(67,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(68,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1),(69,'Test','Test','Test','Test','Test','A','Test',2,'计算机网络',1);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `sid` int(15) unsigned NOT NULL COMMENT '学号',
  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆账号',
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆密码',
  `studentName` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `role` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色 : 1管理员 2教师 3学生',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `idNumber` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号码',
  `grade` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '年级',
  `institute` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学院',
  `major` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '专业',
  `class_` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '班级',
  `sex` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `tel` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`sid`,`username`,`password`,`studentName`,`role`,`email`,`idNumber`,`grade`,`institute`,`major`,`class_`,`sex`,`tel`) values (101,'990926','990926','大大','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统','01班','男','18100000000'),(102,'456','456','可可','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统',NULL,'男','18100000000'),(103,'789','789','莉莉','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统','01班','男','18100000000'),(104,'444','444','丽丽','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统','01班','男','18100000000'),(105,'333','333','张张','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统','01班','男','18100000000'),(106,'111','111','方方','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统','01班','男','18100000000'),(107,'123','123','宫瑞阳','3','gongruiyang1999@163.com','340402199900000000','2018','管理学院','信息管理与信息系统','01班','男','18100000000'),(1212,'1212','1212','1','3','1','1','1','1','信息管理与信息系统','1','1','1'),(123456789,'123456789','123456789','test','3','1212','1212','1','管理学院','信息管理与信息系统','1班','男','111');

/*Table structure for table `student_paper_list` */

DROP TABLE IF EXISTS `student_paper_list`;

CREATE TABLE `student_paper_list` (
  `examId` int(11) DEFAULT NULL COMMENT '考试场次id',
  `paperId` int(11) DEFAULT NULL COMMENT '试卷id',
  `sid` int(15) DEFAULT NULL COMMENT '学号',
  `totalScore` int(11) DEFAULT '0' COMMENT '试卷总分',
  `totalSingleChoiceScore` int(11) DEFAULT '0' COMMENT '单选题总分',
  `totalFillGapScore` int(11) DEFAULT '0' COMMENT '填空题总分',
  `totalJudgeScore` int(11) DEFAULT '0' COMMENT '判断题总分',
  `totalShortAnsScore` int(11) DEFAULT '0' COMMENT '简答题总分',
  `ansSingleChoice` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '单选题答案',
  `ansFillGap` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '填空题答案',
  `ansJudge` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '判断题答案',
  `ansShortAns` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '简答题答案',
  `IsSubmitted` int(11) DEFAULT '0' COMMENT '交卷状态:0表示未交卷 1表示已交卷'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student_paper_list` */

insert  into `student_paper_list`(`examId`,`paperId`,`sid`,`totalScore`,`totalSingleChoiceScore`,`totalFillGapScore`,`totalJudgeScore`,`totalShortAnsScore`,`ansSingleChoice`,`ansFillGap`,`ansJudge`,`ansShortAns`,`IsSubmitted`) values (16,2,107,12,6,0,6,0,'A|B|B|B|A|','1212|222|1|22|1|','1|1|0|1|1|','131|3131|',1),(17,3,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(17,3,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(17,3,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(17,3,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(17,3,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(17,3,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(17,3,107,12,2,0,10,0,'B|A|B|C|C|A|C|B|B|B|','1|1|1|1|1|1|1|1|1|1|','1|1|1|1|1|1|1|1|1|1|','1|1|1|1|1|1|1|1|',1),(18,4,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(18,4,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(18,4,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(18,4,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(18,4,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(18,4,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(18,4,107,12,8,0,4,0,'A|A|A|A|A|','1|1|1|1|1|','0|0|0|0|0|','1|1|1|1|',1),(19,5,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,107,16,6,0,10,0,'A|C|C|B|A|A|B|A|B|D|','1|1|1|1|1|1|1|1|1|1|','0|0|0|0|0|0|0|0|0|0|','1|1|1|1|',1),(19,5,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(19,5,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(25,6,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(26,7,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(27,8,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(28,9,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,107,10,6,0,4,0,'A|A|A|','12321|12312|12312|','0|0|','123213|',1),(29,10,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(29,10,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,107,4,2,0,2,0,'B|A|B|','1|1|1|','1|0|','1|',1),(30,11,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(30,11,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(31,12,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(32,13,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(33,14,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(34,15,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,101,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,102,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,103,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,104,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,105,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,106,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,107,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,1212,0,0,0,0,0,NULL,NULL,NULL,NULL,0),(35,16,123456789,0,0,0,0,0,NULL,NULL,NULL,NULL,0);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `tid` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '教师的唯一标识ID',
  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆账号',
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登陆密码',
  `teacherName` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `role` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '2' COMMENT '角色  : 1管理员 2教师 3学生',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `idNumber` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号码',
  `institute` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学院',
  `major` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '专业',
  `sex` varchar(3) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `title` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '教师职称',
  `tel` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `teacher` */

insert  into `teacher`(`tid`,`username`,`password`,`teacherName`,`role`,`email`,`idNumber`,`institute`,`major`,`sex`,`title`,`tel`) values (1,'123','123','张三','2','1231','213213232','管理学院','信息管理与信息系统','男','教授','1800000'),(5,'222','222','张二','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(6,'333','333','张一','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(7,'444','444','张四','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(9,'666','666','王一','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(10,'777','777','王二','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(11,'888','888','王三','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(12,'999','999','王四','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','副教授','1800000'),(15,'666666','666666','王五','2','11@163.com','666666340402199909260618','管理学院','信息管理与信息系统','男','教授','1800000'),(16,'1234567890','1234567890','李四','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','女','教授','1800000'),(17,'1252574653','199909261','宫瑞阳','2','11@163.com','340402199909260618','管理学院','信息管理与信息系统','男','教授','1800000');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
