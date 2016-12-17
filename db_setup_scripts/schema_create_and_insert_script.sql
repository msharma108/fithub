CREATE DATABASE  IF NOT EXISTS `fithub` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fithub`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: fithub
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_detail` (
  `order_detail_id` int(10) NOT NULL AUTO_INCREMENT,
  `sales_order_id` int(10) NOT NULL,
  `product_id` int(10) NOT NULL,
  `product_quantity` int(40) DEFAULT '0',
  PRIMARY KEY (`order_detail_id`),
  KEY `OrderDetailProduct_idx` (`product_id`),
  KEY `OrderDetailSalesOrder_idx` (`sales_order_id`),
  CONSTRAINT `FK19may4v187yhnxq14vjj5ppki` FOREIGN KEY (`sales_order_id`) REFERENCES `sales_order` (`sales_order_id`),
  CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `OrderDetailProduct` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `OrderDetailSalesOrder` FOREIGN KEY (`sales_order_id`) REFERENCES `sales_order` (`sales_order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(10) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `sdesc` varchar(300) DEFAULT NULL,
  `ldesc` text,
  `price` float DEFAULT NULL,
  `stock_quantity` int(11) DEFAULT '0',
  `category_id` int(11) NOT NULL,
  `manufacture_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `rating` enum('Bad','Average','Good','Awesome') DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `main_image` longblob,
  `thumb_image` longblob,
  `flavor` varchar(45) DEFAULT 'Not Applicable',
  `product_update_date` datetime DEFAULT NULL,
  `registration_date` datetime NOT NULL,
  `product_edited_by_user` varchar(45) DEFAULT NULL,
  `quantity_sold` int(11) DEFAULT '0',
  `is_product_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name_UNIQUE` (`product_name`),
  KEY `ProductProductCategory_idx` (`category_id`),
  CONSTRAINT `FK5cypb0k23bovo3rn1a5jqs6j4` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`product_category_id`),
  CONSTRAINT `ProductProductCategory` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`product_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Product Entity table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='various categories of products';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_order`
--

DROP TABLE IF EXISTS `sales_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales_order` (
  `sales_order_id` int(10) NOT NULL AUTO_INCREMENT,
  `amount` float DEFAULT '0',
  `user_id` int(10) NOT NULL,
  `tax` float DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `zip` varchar(20) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `shipping_charge` float DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tracking_number` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sales_order_id`),
  KEY `fkule_idx` (`user_id`),
  CONSTRAINT `FK4u1waqcu3ns8bfsrobt4eysi3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `SalesOrderUser` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_order`
--

LOCK TABLES `sales_order` WRITE;
/*!40000 ALTER TABLE `sales_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `given_name` varchar(45) NOT NULL,
  `family_name` varchar(45) NOT NULL,
  `sex` enum('MALE','FEMALE','UNDISCLOSED') DEFAULT 'UNDISCLOSED',
  `date_of_birth` date NOT NULL,
  `registration_date` datetime NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `province` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `zipcode` varchar(20) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `payment_mode` enum('CREDIT','DEBIT','UNDISCLOSED') DEFAULT 'CREDIT',
  `role` enum('CUSTOMER','ADMIN') NOT NULL DEFAULT 'CUSTOMER',
  `profile_edit_date` datetime DEFAULT NULL,
  `profile_edited_by_user` varchar(45) DEFAULT NULL,
  `is_user_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (18,'mohitshsh','$2a$10$g.MSKR9SEifOjj9vZggm3etqLJI0AuwnP./xTBu/P/jX0hvXFEIsi','MOHIT','SHARMA','MALE','2016-12-01','2016-12-17 13:24:45','921 , 2025-Othello Avenue','Ottawa','ON','CANADA','K1G3R4','mohitshsh@yahoo.co.in','6134625635','DEBIT','ADMIN',NULL,NULL,0),(19,'dummyUser_Deleted','$2a$10$kwksCNEiGiyQINyprSoQJuruAywJVrnax3ptnr2TlyUoFV/5nk0/.','User_Deleted','User_Deleted','UNDISCLOSED','1900-01-11','1900-01-11 00:10:00','User_Deleted','User_Deleted','User_Deleted','User_Deleted','User_Deleted','User_Deleted','User_Deleted','UNDISCLOSED','CUSTOMER','2016-12-17 13:26:39',NULL,1),(20,'mkabir','$2a$10$M7i4H5F6dgfxaphmj96Ci.oxUbYrjh55vnAK0C8NXLfTO8R.7MwU6','mkabir','mkabir','MALE','2016-12-01','2016-12-17 13:27:30','mkabirmkabirmkabirmkabir','mkabir','mkabir','CANADA','mkabir','mkabir@mkabir','1234567899','CREDIT','ADMIN','2016-12-17 13:27:59','mohitshsh',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'fithub'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-17 13:34:43
