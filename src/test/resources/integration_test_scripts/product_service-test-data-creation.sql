--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
SET FOREIGN_KEY_CHECKS=0;
INSERT INTO `product` VALUES (1,'ProteinProduct1','muscle recovery','Product for muscle recovery and muscle growth',10,7,1,'2017-01-01','2019-01-02','Average',5,'','Chocolate',NULL,'2017-01-07 13:42:24',NULL,0,0),(2,'VitaminProduct1','multivitamins','product containing all necessary vitamins for a healthy life',2,9,2,'2016-10-07','2019-01-01','Awesome',1,'','orange',NULL,'2017-01-07 13:44:34',NULL,0,0);
SET FOREIGN_KEY_CHECKS=1;
UNLOCK TABLES;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
SET FOREIGN_KEY_CHECKS=0;
INSERT INTO `product_category` VALUES (1,'Protein'),(2,'Vitamins');
SET FOREIGN_KEY_CHECKS=1;
UNLOCK TABLES;