;
CREATE SCHEMA FITHUB AUTHORIZATION SA;
CREATE USER IF NOT EXISTS SA SALT '1f180958500f84f2' HASH '69176de866635e8f2109b37555b0fc1218b17ffe756dde5a21774ee8204e8ac2' ADMIN;           
DROP TABLE IF EXISTS PUBLIC.PRODUCT CASCADE;  
DROP TABLE IF EXISTS PUBLIC.PRODUCT_CATEGORY CASCADE;         
DROP TABLE IF EXISTS PUBLIC.SALES_ORDER CASCADE;              
DROP TABLE IF EXISTS PUBLIC.SALES_ORDER_ITEM CASCADE;         
DROP TABLE IF EXISTS PUBLIC.SHIPPING_ADDRESS CASCADE;         
DROP TABLE IF EXISTS PUBLIC.USER CASCADE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8533696B_570A_4F4F_B6F8_925C6B35A74C START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_1CDB139A_F1D2_4AF0_BC56_B3A5D724CCBF START WITH 2 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_5E203EA8_16B3_401D_BC1E_C5C98BB45C4E START WITH 4 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_16370F80_1034_4C1C_A047_7DE30453DEB7 START WITH 2 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_F9DD4AB6_F3E1_44CE_AD47_9D75DFFB4D77 START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_0AAE576C_88FD_4314_84DC_ECDF12293830 START WITH 4 BELONGS_TO_TABLE;    
CREATE CACHED TABLE PUBLIC.PRODUCT(
    PRODUCT_ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_5E203EA8_16B3_401D_BC1E_C5C98BB45C4E) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_5E203EA8_16B3_401D_BC1E_C5C98BB45C4E,
    EXPIRY_DATE DATE,
    FLAVOR VARCHAR(45),
    IS_PRODUCT_DELETED BOOLEAN,
    LDESC CLOB,
    MANUFACTURE_DATE DATE,
    PRICE DECIMAL(10, 0),
    PRODUCT_DISPLAY_NAME VARCHAR(100),
    PRODUCT_EDITED_BY_USER VARCHAR(45),
    PRODUCT_NAME VARCHAR(100) NOT NULL,
    PRODUCT_UPDATE_DATE TIMESTAMP,
    QUANTITY_SOLD INTEGER,
    RATING VARCHAR(20),
    REGISTRATION_DATE TIMESTAMP NOT NULL,
    SDESC VARCHAR(300),
    STOCK_QUANTITY INTEGER,
    THUMB_IMAGE BLOB,
    WEIGHT DECIMAL(10, 0),
    CATEGORY_ID INTEGER NOT NULL
);         
ALTER TABLE PUBLIC.PRODUCT ADD CONSTRAINT PUBLIC.CONSTRAINT_1 PRIMARY KEY(PRODUCT_ID);        
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT; 
INSERT INTO PUBLIC.PRODUCT(PRODUCT_ID, EXPIRY_DATE, FLAVOR, IS_PRODUCT_DELETED, LDESC, MANUFACTURE_DATE, PRICE, PRODUCT_DISPLAY_NAME, PRODUCT_EDITED_BY_USER, PRODUCT_NAME, PRODUCT_UPDATE_DATE, QUANTITY_SOLD, RATING, REGISTRATION_DATE, SDESC, STOCK_QUANTITY, THUMB_IMAGE, WEIGHT, CATEGORY_ID) VALUES
(1, DATE '2019-01-02', 'Chocolate', FALSE, 'Product for muscle recovery and muscle growth', DATE '2017-01-01', 3, 'ProteinProduct', NULL, 'ProteinProduct1', NULL, 0, 'Average', TIMESTAMP '2017-01-13 23:22:40.75', 'muscle recovery', 8, X'', 1, 1),
(2, DATE '2021-01-21', 'orange', FALSE, 'product containing all necessary vitamins for a healthy life', DATE '2017-01-02', 4, 'VitaminProduct', NULL, 'VitaminProduct1', NULL, 2, 'Good', TIMESTAMP '2017-01-13 23:23:40.616', 'multivitamins', 6, X'', 2, 2),
(3, DATE '2022-01-22', 'vanilla', FALSE, 'Product for muscle recovery and muscle growth', DATE '2016-11-17', 7, 'ProteinProduct', NULL, 'ProteinProduct2', NULL, 1, 'Awesome', TIMESTAMP '2017-01-13 23:25:07.742', 'muscle recovery', 3, X'', 3, 1);     
CREATE CACHED TABLE PUBLIC.PRODUCT_CATEGORY(
    PRODUCT_CATEGORY_ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_8533696B_570A_4F4F_B6F8_925C6B35A74C) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_8533696B_570A_4F4F_B6F8_925C6B35A74C,
    CATEGORY VARCHAR(45)
);               
ALTER TABLE PUBLIC.PRODUCT_CATEGORY ADD CONSTRAINT PUBLIC.CONSTRAINT_3 PRIMARY KEY(PRODUCT_CATEGORY_ID);      
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT_CATEGORY;        
INSERT INTO PUBLIC.PRODUCT_CATEGORY(PRODUCT_CATEGORY_ID, CATEGORY) VALUES
(1, 'Protein'),
(2, 'Vitamins');  
CREATE CACHED TABLE PUBLIC.SALES_ORDER(
    SALES_ORDER_ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_F9DD4AB6_F3E1_44CE_AD47_9D75DFFB4D77) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_F9DD4AB6_F3E1_44CE_AD47_9D75DFFB4D77,
    PAYMENT_STATUS VARCHAR(45),
    SALES_ORDER_CREATION_DATE TIMESTAMP,
    SALES_ORDER_EDIT_DATE TIMESTAMP,
    SALES_ORDER_EDITED_BY_USER VARCHAR(45),
    SALES_ORDER_REFUND_AMOUNT DECIMAL(10, 2),
    SALES_ORDER_TOTAL_COST DECIMAL(10, 2),
    SHIPPING_CHARGE DECIMAL(10, 0),
    STATUS VARCHAR(20),
    STRIPE_CHARGE_ID VARCHAR(60),
    STRIPE_REFUND_ID VARCHAR(60),
    TAX DECIMAL(10, 2),
    TRACKING_NUMBER VARCHAR(80),
    SHIPPING_ADDRESS_ID INTEGER NOT NULL,
    USER_ID INTEGER
);           
ALTER TABLE PUBLIC.SALES_ORDER ADD CONSTRAINT PUBLIC.CONSTRAINT_1E PRIMARY KEY(SALES_ORDER_ID);               
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.SALES_ORDER;             
INSERT INTO PUBLIC.SALES_ORDER(SALES_ORDER_ID, PAYMENT_STATUS, SALES_ORDER_CREATION_DATE, SALES_ORDER_EDIT_DATE, SALES_ORDER_EDITED_BY_USER, SALES_ORDER_REFUND_AMOUNT, SALES_ORDER_TOTAL_COST, SHIPPING_CHARGE, STATUS, STRIPE_CHARGE_ID, STRIPE_REFUND_ID, TAX, TRACKING_NUMBER, SHIPPING_ADDRESS_ID, USER_ID) VALUES
(1, 'succeeded', TIMESTAMP '2017-01-13 23:28:37.057', NULL, NULL, NULL, 14.52, 10, 'ORDERED', 'ch_19bj9mB9D9kUqS3TBBpQG1mn', NULL, 0.52, '2017d0101323p28w37036', 1, 1),
(2, 'succeeded', TIMESTAMP '2017-01-13 23:29:11.589', NULL, NULL, NULL, 22.43, 10, 'ORDERED', 'ch_19bjALB9D9kUqS3TnKTaTgQ5', NULL, 1.43, '2017R0141323r29811584', 1, 1);   
CREATE CACHED TABLE PUBLIC.SALES_ORDER_ITEM(
    SALES_ORDER_ITEM_ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_0AAE576C_88FD_4314_84DC_ECDF12293830) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_0AAE576C_88FD_4314_84DC_ECDF12293830,
    SALES_ORDER_ITEM_QUANTITY_SOLD INTEGER,
    PRODUCT_ID INTEGER NOT NULL,
    SALES_ORDER_ID INTEGER NOT NULL
);     
ALTER TABLE PUBLIC.SALES_ORDER_ITEM ADD CONSTRAINT PUBLIC.CONSTRAINT_5 PRIMARY KEY(SALES_ORDER_ITEM_ID);      
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.SALES_ORDER_ITEM;        
INSERT INTO PUBLIC.SALES_ORDER_ITEM(SALES_ORDER_ITEM_ID, SALES_ORDER_ITEM_QUANTITY_SOLD, PRODUCT_ID, SALES_ORDER_ID) VALUES
(1, 1, 2, 1),
(2, 1, 2, 2),
(3, 1, 3, 2);      
CREATE CACHED TABLE PUBLIC.SHIPPING_ADDRESS(
    SHIPPING_ADDRESS_ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_16370F80_1034_4C1C_A047_7DE30453DEB7) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_16370F80_1034_4C1C_A047_7DE30453DEB7,
    ADDRESS VARCHAR(100),
    CITY VARCHAR(50),
    COUNTRY VARCHAR(30),
    EMAIL VARCHAR(100),
    PHONE VARCHAR(20),
    PROVINCE VARCHAR(20),
    ZIP VARCHAR(20)
);            
ALTER TABLE PUBLIC.SHIPPING_ADDRESS ADD CONSTRAINT PUBLIC.CONSTRAINT_36 PRIMARY KEY(SHIPPING_ADDRESS_ID);     
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.SHIPPING_ADDRESS;        
INSERT INTO PUBLIC.SHIPPING_ADDRESS(SHIPPING_ADDRESS_ID, ADDRESS, CITY, COUNTRY, EMAIL, PHONE, PROVINCE, ZIP) VALUES
(1, '921 , 2025-Othello Avenue', 'Ottawa', 'CANADA', 'mohitshsh@yahoo.co.in', '6134625635', 'ON', 'K1G3R4');            
CREATE CACHED TABLE PUBLIC.USER(
    USER_ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_1CDB139A_F1D2_4AF0_BC56_B3A5D724CCBF) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_1CDB139A_F1D2_4AF0_BC56_B3A5D724CCBF,
    ADDRESS VARCHAR(45),
    CITY VARCHAR(45),
    COUNTRY VARCHAR(45),
    DATE_OF_BIRTH DATE NOT NULL,
    EMAIL VARCHAR(45) NOT NULL,
    FAMILY_NAME VARCHAR(45) NOT NULL,
    GIVEN_NAME VARCHAR(45) NOT NULL,
    IS_USER_DELETED BOOLEAN,
    PASSWORD VARCHAR(60) NOT NULL,
    PAYMENT_MODE VARCHAR(20),
    PHONE VARCHAR(45),
    PROFILE_EDIT_DATE TIMESTAMP,
    PROFILE_EDITED_BY_USER VARCHAR(45),
    PROVINCE VARCHAR(45),
    REGISTRATION_DATE TIMESTAMP NOT NULL,
    ROLE VARCHAR(20),
    SECURITY_QUESTION VARCHAR(45),
    SECURITY_QUESTION_ANSWER VARCHAR(45),
    SEX VARCHAR(20),
    USER_NAME VARCHAR(45) NOT NULL,
    ZIPCODE VARCHAR(20)
);  
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(USER_ID);              
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.USER;    
INSERT INTO PUBLIC.USER(USER_ID, ADDRESS, CITY, COUNTRY, DATE_OF_BIRTH, EMAIL, FAMILY_NAME, GIVEN_NAME, IS_USER_DELETED, PASSWORD, PAYMENT_MODE, PHONE, PROFILE_EDIT_DATE, PROFILE_EDITED_BY_USER, PROVINCE, REGISTRATION_DATE, ROLE, SECURITY_QUESTION, SECURITY_QUESTION_ANSWER, SEX, USER_NAME, ZIPCODE) VALUES
(1, '921 , 2025-Othello Avenue', 'Ottawa', 'CANADA', DATE '1989-10-11', 'mohitshsh@yahoo.co.in', 'SHARMA', 'MOHIT', FALSE, '$2a$10$Zx55SE1YkqfMWe1.hQ1W5uYqq0X1dL.Z5KOZk/l8rdsJcfpFqP5xi', 'CREDIT', '6134625635', NULL, NULL, 'ON', TIMESTAMP '2017-01-13 22:36:33.521', 'ADMIN', 'First Car', 'Maruti', 'MALE', 'registeredUser', 'K1G3R4');            
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.UK_LQJRCOBRH9JC8WPCAR64Q1BFH UNIQUE(USER_NAME); 
ALTER TABLE PUBLIC.SALES_ORDER_ITEM ADD CONSTRAINT PUBLIC.FKAUHJNF2U1TCYXJ79SU49LPQAA FOREIGN KEY(PRODUCT_ID) REFERENCES PUBLIC.PRODUCT(PRODUCT_ID) NOCHECK;  
ALTER TABLE PUBLIC.SALES_ORDER ADD CONSTRAINT PUBLIC.FK3KV2SQYBOFCV8Q0S1169FH491 FOREIGN KEY(SHIPPING_ADDRESS_ID) REFERENCES PUBLIC.SHIPPING_ADDRESS(SHIPPING_ADDRESS_ID) NOCHECK;            
ALTER TABLE PUBLIC.SALES_ORDER ADD CONSTRAINT PUBLIC.FK4U1WAQCU3NS8BFSROBT4EYSI3 FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(USER_ID) NOCHECK;
ALTER TABLE PUBLIC.PRODUCT ADD CONSTRAINT PUBLIC.FK5CYPB0K23BOVO3RN1A5JQS6J4 FOREIGN KEY(CATEGORY_ID) REFERENCES PUBLIC.PRODUCT_CATEGORY(PRODUCT_CATEGORY_ID) NOCHECK;        
ALTER TABLE PUBLIC.SALES_ORDER_ITEM ADD CONSTRAINT PUBLIC.FKFKFKJLVT4M5XFFT7BYS91TWFN FOREIGN KEY(SALES_ORDER_ID) REFERENCES PUBLIC.SALES_ORDER(SALES_ORDER_ID) NOCHECK;      