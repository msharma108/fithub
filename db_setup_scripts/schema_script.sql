-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fithub
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fithub
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fithub` DEFAULT CHARACTER SET utf8 ;
USE `fithub` ;

-- -----------------------------------------------------
-- Table `fithub`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub`.`user` ;

CREATE TABLE IF NOT EXISTS `fithub`.`user` (
  `user_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '',
  `user_name` VARCHAR(45) NOT NULL COMMENT '',
  `password` VARCHAR(60) NOT NULL COMMENT '',
  `given_name` VARCHAR(45) NOT NULL COMMENT '',
  `family_name` VARCHAR(45) NOT NULL COMMENT '',
  `sex` ENUM('MALE','FEMALE','UNDISCLOSED') NULL DEFAULT 'UNDISCLOSED' COMMENT '',
  `date_of_birth` DATE NOT NULL COMMENT '',
  `registration_date` DATETIME NOT NULL COMMENT '',
  `address` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `city` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `province` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `country` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `zipcode` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `email` VARCHAR(45) NOT NULL COMMENT '',
  `phone` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `payment_mode` ENUM('CREDIT','DEBIT','UNDISCLOSED') NULL DEFAULT 'CREDIT' COMMENT '',
  `role` ENUM('CUSTOMER','ADMIN') NOT NULL DEFAULT 'CUSTOMER' COMMENT '',
  `profile_edit_date` DATETIME NULL DEFAULT NULL COMMENT '',
  `profile_edited_by_user` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `is_user_deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '',
  PRIMARY KEY (`user_id`)  COMMENT '',
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC)  COMMENT '')
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fithub`.`sales_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub`.`sales_order` ;

CREATE TABLE IF NOT EXISTS `fithub`.`sales_order` (
  `sales_order_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '',
  `amount` DECIMAL(10,0) NULL DEFAULT '0' COMMENT '',
  `user_id` INT(10) NOT NULL COMMENT '',
  `tax` DECIMAL(10,0) NULL DEFAULT '0' COMMENT '',
  `address` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  `city` VARCHAR(50) NULL DEFAULT NULL COMMENT '',
  `province` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `zip` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `country` VARCHAR(30) NULL DEFAULT NULL COMMENT '',
  `phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `shipping_charge` DECIMAL(10,0) NULL DEFAULT '10' COMMENT '',
  `email` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  `order_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
  `tracking_number` VARCHAR(80) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`sales_order_id`)  COMMENT '',
  INDEX `fkule_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `FK4u1waqcu3ns8bfsrobt4eysi3`
    FOREIGN KEY (`user_id`)
    REFERENCES `fithub`.`user` (`user_id`),
  CONSTRAINT `SalesOrderUser`
    FOREIGN KEY (`user_id`)
    REFERENCES `fithub`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fithub`.`product_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub`.`product_category` ;

CREATE TABLE IF NOT EXISTS `fithub`.`product_category` (
  `product_category_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `category` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`product_category_id`)  COMMENT '',
  UNIQUE INDEX `category_UNIQUE` (`category` ASC)  COMMENT '')
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8
COMMENT = 'various categories of products';


-- -----------------------------------------------------
-- Table `fithub`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub`.`product` ;

CREATE TABLE IF NOT EXISTS `fithub`.`product` (
  `product_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '',
  `product_name` VARCHAR(100) NOT NULL COMMENT '',
  `sdesc` VARCHAR(300) NULL DEFAULT NULL COMMENT '',
  `ldesc` TEXT NULL DEFAULT NULL COMMENT '',
  `price` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '',
  `stock_quantity` INT(11) NULL DEFAULT '0' COMMENT '',
  `category_id` INT(11) NOT NULL COMMENT '',
  `manufacture_date` DATE NULL DEFAULT NULL COMMENT '',
  `expiry_date` DATE NULL DEFAULT NULL COMMENT '',
  `rating` ENUM('Bad','Average','Good','Awesome','Not Applicable') NULL DEFAULT NULL COMMENT '',
  `weight` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '',
  `thumb_image` LONGBLOB NULL DEFAULT NULL COMMENT '',
  `flavor` VARCHAR(45) NULL DEFAULT 'Not Applicable' COMMENT '',
  `product_update_date` DATETIME NULL DEFAULT NULL COMMENT '',
  `registration_date` DATETIME NOT NULL COMMENT '',
  `product_edited_by_user` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `quantity_sold` INT(11) NULL DEFAULT '0' COMMENT '',
  `is_product_deleted` TINYINT(1) NULL DEFAULT '0' COMMENT '',
  PRIMARY KEY (`product_id`)  COMMENT '',
  UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC)  COMMENT '',
  INDEX `ProductProductCategory_idx` (`category_id` ASC)  COMMENT '',
  CONSTRAINT `FK5cypb0k23bovo3rn1a5jqs6j4`
    FOREIGN KEY (`category_id`)
    REFERENCES `fithub`.`product_category` (`product_category_id`),
  CONSTRAINT `ProductProductCategory`
    FOREIGN KEY (`category_id`)
    REFERENCES `fithub`.`product_category` (`product_category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8
COMMENT = 'Product Entity table';


-- -----------------------------------------------------
-- Table `fithub`.`order_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub`.`order_detail` ;

CREATE TABLE IF NOT EXISTS `fithub`.`order_detail` (
  `order_detail_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '',
  `sales_order_id` INT(10) NOT NULL COMMENT '',
  `product_id` INT(10) NOT NULL COMMENT '',
  `product_quantity` INT(40) NULL DEFAULT '0' COMMENT '',
  PRIMARY KEY (`order_detail_id`)  COMMENT '',
  INDEX `OrderDetailProduct_idx` (`product_id` ASC)  COMMENT '',
  INDEX `OrderDetailSalesOrder_idx` (`sales_order_id` ASC)  COMMENT '',
  CONSTRAINT `FK19may4v187yhnxq14vjj5ppki`
    FOREIGN KEY (`sales_order_id`)
    REFERENCES `fithub`.`sales_order` (`sales_order_id`),
  CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc`
    FOREIGN KEY (`product_id`)
    REFERENCES `fithub`.`product` (`product_id`),
  CONSTRAINT `OrderDetailProduct`
    FOREIGN KEY (`product_id`)
    REFERENCES `fithub`.`product` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `OrderDetailSalesOrder`
    FOREIGN KEY (`sales_order_id`)
    REFERENCES `fithub`.`sales_order` (`sales_order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
