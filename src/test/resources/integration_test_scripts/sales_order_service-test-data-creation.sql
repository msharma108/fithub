
--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
SET foreign_key_checks=0;
INSERT INTO `product` VALUES (1,'ProteinProduct1','muscle recovery','Product for muscle recovery and muscle growth',10,7,1,'2017-01-01','2019-01-02','Average',5,'','Chocolate',NULL,'2017-01-07 13:42:24',NULL,0,0,'ProteinProduct'),(2,'VitaminProduct1','multivitamins','product containing all necessary vitamins for a healthy life',2,8,2,'2016-10-07','2019-01-01','Awesome',1,'','orange',NULL,'2017-01-07 13:44:34',NULL,1,0,'VitaminProduct'),(3,'ProteinProduct2','muscle recovery','Product for muscle recovery and muscle growth',10,6,1,'2017-01-01','2019-01-02','Average',5,'','Chocolate',NULL,'2017-01-07 13:42:24',NULL,1,0,'ProteinProduct'),(4,'VitaminProduct2','multivitamins','product containing all necessary vitamins for a healthy life',2,8,2,'2016-10-07','2019-01-01','Awesome',1,'','orange',NULL,'2017-01-07 13:44:34',NULL,1,0,'VitaminProduct');
SET foreign_key_checks=1;
UNLOCK TABLES;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
SET foreign_key_checks=0;
INSERT INTO `product_category` VALUES (1,'Protein'),(2,'Vitamins');
SET foreign_key_checks=1;
UNLOCK TABLES;

--
-- Dumping data for table `sales_order`
--

LOCK TABLES `sales_order` WRITE;
SET foreign_key_checks=0;
INSERT INTO `sales_order` VALUES (1,12.26,0.26,10.00,'2017-01-08 00:06:32','2017n01Q0800f06b31936',1,NULL,NULL,'ORDERED','ch_19ZYtAB9D9kUqS3T343LxsY5','succeeded',1,NULL,NULL),(2,23.56,1.56,10.00,'2017-01-08 00:09:15','2017d01J0800r09T15012',1,NULL,NULL,'ORDERED','ch_19ZYvoB9D9kUqS3T15I1NmNU','succeeded',1,NULL,NULL);
SET foreign_key_checks=1;
UNLOCK TABLES;

--
-- Dumping data for table `sales_order_item`
--

LOCK TABLES `sales_order_item` WRITE;
SET foreign_key_checks=0;
INSERT INTO `sales_order_item` VALUES (1,2,1,1),(2,4,2,1),(3,3,2,1);
SET foreign_key_checks=1;
UNLOCK TABLES;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
SET foreign_key_checks=0;
INSERT INTO `shipping_address` VALUES (1,'921 , 2025-Othello Avenue','Ottawa','ON','K1G3R4','CANADA','6134625635','mohitshsh@yahoo.co.in');
SET foreign_key_checks=1;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
SET foreign_key_checks=0;
INSERT INTO `user` VALUES (1,'mohitshsh','$2a$10$.wUKpkc.Xsm2nJzt3A3TDe5CxQMs1OhUdNKeIupgy4opWuXyt4b2u','Mohit','Sharma','MALE','1989-10-11','2017-01-04 00:16:41','1110, 2900 Carling Avenue','Ottawa','ON','CANADA','K1G3R4','mohitshsh@yahoo.co.in','1234567899','CREDIT','ADMIN',NULL,NULL,0,'First City','Delhi');
INSERT INTO `user` VALUES (2,'actualUser','$2a$10$.wUKpkc.Xsm2nJzt3A3TDe5CxQMs1OhUdNKeIupgy4opWuXyt4b2u','ActualUserGivenName','ActualUserFamilyName','MALE','1989-10-11','2017-01-04 00:16:41','1110, 2900 Carling Avenue','Ottawa','ON','CANADA','K1G3R4','befithub@gmail.com','1234567899','CREDIT','CUSTOMER',NULL,NULL,0,'First City','Delhi');
SET foreign_key_checks=1;
UNLOCK TABLES;

