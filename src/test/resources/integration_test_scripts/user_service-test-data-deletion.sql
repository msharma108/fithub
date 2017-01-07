LOCK TABLES `user` WRITE;
SET foreign_key_checks = 0;
TRUNCATE TABLE `user`;
SET foreign_key_checks = 1;
UNLOCK TABLES; 