-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fithub_integ_testing
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fithub_integ_testing
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fithub_integ_testing` DEFAULT CHARACTER SET utf8 ;
USE `fithub_integ_testing` ;

-- -----------------------------------------------------
-- Table `fithub_integ_testing`.`product_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub_integ_testing`.`product_category` ;

CREATE TABLE IF NOT EXISTS `fithub_integ_testing`.`product_category` (
  `product_category_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `category` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`product_category_id`)  COMMENT '',
  UNIQUE INDEX `category_UNIQUE` (`category` ASC)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'various categories of products';


-- -----------------------------------------------------
-- Table `fithub_integ_testing`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub_integ_testing`.`product` ;

CREATE TABLE IF NOT EXISTS `fithub_integ_testing`.`product` (
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
  `product_display_name` VARCHAR(100) NOT NULL COMMENT '',
  PRIMARY KEY (`product_id`)  COMMENT '',
  UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC)  COMMENT '',
  INDEX `ProductProductCategory_idx` (`category_id` ASC)  COMMENT '',
  CONSTRAINT `FK5cypb0k23bovo3rn1a5jqs6j4`
    FOREIGN KEY (`category_id`)
    REFERENCES `fithub_integ_testing`.`product_category` (`product_category_id`),
  CONSTRAINT `ProductProductCategory`
    FOREIGN KEY (`category_id`)
    REFERENCES `fithub_integ_testing`.`product_category` (`product_category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Product Entity table';


-- -----------------------------------------------------
-- Table `fithub_integ_testing`.`shipping_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub_integ_testing`.`shipping_address` ;

CREATE TABLE IF NOT EXISTS `fithub_integ_testing`.`shipping_address` (
  `shipping_address_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `address` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  `city` VARCHAR(50) NULL DEFAULT NULL COMMENT '',
  `province` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `zip` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `country` VARCHAR(30) NULL DEFAULT NULL COMMENT '',
  `phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '',
  `email` VARCHAR(100) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`shipping_address_id`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fithub_integ_testing`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub_integ_testing`.`user` ;

CREATE TABLE IF NOT EXISTS `fithub_integ_testing`.`user` (
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
  `role` ENUM('CUSTOMER','ADMIN') NULL DEFAULT 'CUSTOMER' COMMENT '',
  `profile_edit_date` DATETIME NULL DEFAULT NULL COMMENT '',
  `profile_edited_by_user` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `is_user_deleted` TINYINT(1) NULL DEFAULT '0' COMMENT '',
  `security_question` VARCHAR(45) NOT NULL COMMENT '',
  `security_question_answer` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`user_id`)  COMMENT '',
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fithub_integ_testing`.`sales_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub_integ_testing`.`sales_order` ;

CREATE TABLE IF NOT EXISTS `fithub_integ_testing`.`sales_order` (
  `sales_order_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '',
  `sales_order_total_cost` DECIMAL(10,2) NULL DEFAULT '0.00' COMMENT '',
  `tax` DECIMAL(10,2) NULL DEFAULT '0.00' COMMENT '',
  `shipping_charge` DECIMAL(10,2) NULL DEFAULT '10.00' COMMENT '',
  `sales_order_creation_date` DATETIME NULL DEFAULT NULL COMMENT '',
  `tracking_number` VARCHAR(80) NULL DEFAULT NULL COMMENT '',
  `user_id` INT(10) NOT NULL COMMENT '',
  `sales_order_edit_date` DATETIME NULL DEFAULT NULL COMMENT '',
  `sales_order_edited_by_user` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `status` ENUM('ORDERED','COMPLETED','CANCELED') NULL DEFAULT 'ORDERED' COMMENT '',
  `stripe_charge_id` VARCHAR(60) NULL DEFAULT NULL COMMENT '',
  `payment_status` VARCHAR(45) NULL DEFAULT NULL COMMENT '',
  `shipping_address_id` INT(10) NOT NULL COMMENT '',
  `sales_order_refund_amount` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '',
  `stripe_refund_id` VARCHAR(60) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`sales_order_id`)  COMMENT '',
  INDEX `SalesOrderUser_idx` (`user_id` ASC)  COMMENT '',
  INDEX `SalesOrderShippingAddress_idx` (`shipping_address_id` ASC)  COMMENT '',
  CONSTRAINT `SalesOrderShippingAddress`
    FOREIGN KEY (`shipping_address_id`)
    REFERENCES `fithub_integ_testing`.`shipping_address` (`shipping_address_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `SalesOrderUser`
    FOREIGN KEY (`user_id`)
    REFERENCES `fithub_integ_testing`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fithub_integ_testing`.`sales_order_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fithub_integ_testing`.`sales_order_item` ;

CREATE TABLE IF NOT EXISTS `fithub_integ_testing`.`sales_order_item` (
  `sales_order_item_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `product_id` INT(10) NOT NULL COMMENT '',
  `sales_order_id` INT(10) NOT NULL COMMENT '',
  `sales_order_item_quantity_sold` INT(10) NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`sales_order_item_id`)  COMMENT '',
  INDEX `SalesOrderDetailProduct_idx` (`product_id` ASC)  COMMENT '',
  INDEX `SalesOrderDetailSalesOrder_idx` (`sales_order_id` ASC)  COMMENT '',
  CONSTRAINT `SalesOrderItemProduct`
    FOREIGN KEY (`product_id`)
    REFERENCES `fithub_integ_testing`.`product` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `SalesOrderItemSalesOrder`
    FOREIGN KEY (`sales_order_id`)
    REFERENCES `fithub_integ_testing`.`sales_order` (`sales_order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
