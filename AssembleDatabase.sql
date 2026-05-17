-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: assemble
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Create schema
CREATE SCHEMA IF NOT EXISTS `assemble` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
USE `assemble`;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `ID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `TypeID` smallint unsigned NOT NULL,
  `Status` tinyint DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `equipment_typeID_idx` (`TypeID`),
  CONSTRAINT `equipment_TypeID` FOREIGN KEY (`TypeID`) REFERENCES `equipmenttype` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,1,0),(2,1,1),(3,1,1),(4,1,1),(5,1,1),(6,1,1),(7,2,1),(8,2,0),(9,2,1),(10,2,1),(11,3,1),(12,3,1),(13,3,0),(14,3,1),(15,3,1);
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipmenttype`
--

DROP TABLE IF EXISTS `equipmenttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipmenttype` (
  `ID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipmenttype`
--

LOCK TABLES `equipmenttype` WRITE;
/*!40000 ALTER TABLE `equipmenttype` DISABLE KEYS */;
INSERT INTO `equipmenttype` VALUES (1,'mold press'),(2,'button mold'),(3,'rod mold');
/*!40000 ALTER TABLE `equipmenttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `ID` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `TypeID` smallint unsigned NOT NULL,
  `Count` mediumint unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `inventory_typeId_idx` (`TypeID`),
  CONSTRAINT `inventory_TypeID` FOREIGN KEY (`TypeID`) REFERENCES `inventorytype` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,1,10),(2,1,35),(3,1,1),(4,2,42),(5,2,56),(6,3,200),(7,3,100);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventorytype`
--

DROP TABLE IF EXISTS `inventorytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventorytype` (
  `ID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventorytype`
--

LOCK TABLES `inventorytype` WRITE;
/*!40000 ALTER TABLE `inventorytype` DISABLE KEYS */;
INSERT INTO `inventorytype` VALUES (1,'Steel'),(2,'Brass'),(3,'Copper');
/*!40000 ALTER TABLE `inventorytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `ID` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `ProductID` smallint unsigned NOT NULL,
  `StartTime` datetime NOT NULL,
  `ProjectedEndTime` datetime NOT NULL,
  `ActualEndTime` datetime DEFAULT NULL,
  `PersonnelCount` tinyint NOT NULL,
  `LineNumber` tinyint NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `job_productId_idx` (`ProductID`),
  CONSTRAINT `job_ProductID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,1,'2026-05-09 05:00:00','2026-05-09 05:30:00','2026-05-09 05:30:00',1,1),(2,3,'2026-05-09 05:15:00','2026-05-09 07:00:00','2026-05-09 07:00:00',2,2),(3,1,'2026-05-09 05:35:00','2026-05-09 06:05:00','2026-05-09 06:10:00',1,1),(4,2,'2026-05-10 05:00:00','2026-05-10 06:00:00','2026-05-10 06:03:00',1,1),(5,2,'2026-05-10 06:05:00','2026-05-10 07:05:00','2026-05-10 07:05:00',1,1),(6,3,'2026-05-10 05:30:00','2026-05-10 07:05:00','2026-05-10 07:10:00',2,2),(7,4,'2026-05-11 05:00:00','2026-05-11 06:30:00','2026-05-11 06:33:00',2,1),(8,4,'2026-05-11 06:35:00','2026-05-11 08:05:00','2026-05-11 08:08:00',2,1),(9,4,'2026-05-11 08:10:00','2026-05-11 09:40:00',NULL,2,1),(10,2,'2026-05-12 06:00:00','2026-05-12 07:00:00',NULL,1,1),(11,4,'2026-05-12 11:00:00','2026-05-12 12:30:00',NULL,2,2),(12,1,'2026-05-12 16:00:00','2026-05-12 16:30:00',NULL,1,1),(13,1,'2026-05-13 06:00:00','2026-05-13 06:30:00',NULL,1,1),(14,1,'2026-05-13 11:00:00','2026-05-13 11:30:00',NULL,1,1),(15,1,'2026-05-13 16:00:00','2026-05-13 16:30:00',NULL,1,1);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobequipment`
--

DROP TABLE IF EXISTS `jobequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobequipment` (
  `JobID` mediumint unsigned NOT NULL,
  `EquipmentID` smallint unsigned NOT NULL,
  PRIMARY KEY (`JobID`,`EquipmentID`),
  KEY `jobequipment_EquipmentID_idx` (`EquipmentID`),
  CONSTRAINT `jobequipment_EquipmentID` FOREIGN KEY (`EquipmentID`) REFERENCES `equipment` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `jobequipment_JobID` FOREIGN KEY (`JobID`) REFERENCES `job` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobequipment`
--

LOCK TABLES `jobequipment` WRITE;
/*!40000 ALTER TABLE `jobequipment` DISABLE KEYS */;
INSERT INTO `jobequipment` VALUES (1,1),(3,1),(4,1),(5,1),(13,2),(14,2),(15,2),(2,3),(6,3),(7,4),(8,4),(9,4),(11,5),(12,5),(10,6),(10,7),(4,8),(5,8),(1,9),(3,9),(12,9),(13,10),(14,10),(15,10),(7,11),(8,11),(9,11),(11,11),(2,13),(6,13);
/*!40000 ALTER TABLE `jobequipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobinventory`
--

DROP TABLE IF EXISTS `jobinventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobinventory` (
  `JobID` mediumint unsigned NOT NULL,
  `InventoryID` mediumint unsigned NOT NULL,
  `InventoryUsedCount` tinyint unsigned NOT NULL,
  PRIMARY KEY (`JobID`,`InventoryID`),
  KEY `jobinventory_InventoryID_idx` (`InventoryID`),
  CONSTRAINT `jobinventory_InventoryID` FOREIGN KEY (`InventoryID`) REFERENCES `inventory` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `jobinventory_JobID` FOREIGN KEY (`JobID`) REFERENCES `job` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobinventory`
--

LOCK TABLES `jobinventory` WRITE;
/*!40000 ALTER TABLE `jobinventory` DISABLE KEYS */;
INSERT INTO `jobinventory` VALUES (1,3,1),(2,3,2),(3,3,1),(4,5,1),(5,5,1),(6,3,2),(7,6,2),(8,6,2),(9,6,2),(10,5,1),(11,6,2),(12,1,1),(13,2,1),(14,2,1),(15,2,1);
/*!40000 ALTER TABLE `jobinventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personnel` (
  `ID` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `Count` smallint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personnel`
--

LOCK TABLES `personnel` WRITE;
/*!40000 ALTER TABLE `personnel` DISABLE KEYS */;
INSERT INTO `personnel` VALUES (1,'2026-05-09',5),(2,'2026-05-10',6),(3,'2026-05-11',4),(4,'2026-05-12',7),(5,'2026-05-13',6);
/*!40000 ALTER TABLE `personnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  `MinutesDuration` smallint unsigned NOT NULL,
  `TargetPersonnelCount` tinyint unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'steel button',30,1),(2,'brass button',60,1),(3,'steel rod',105,2),(4,'copper rod',90,2);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productequipment`
--

DROP TABLE IF EXISTS `productequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productequipment` (
  `ProductID` smallint unsigned NOT NULL,
  `EquipmentTypeID` smallint unsigned NOT NULL,
  `RequiredEquipmentTypeCount` tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`ProductID`,`EquipmentTypeID`),
  KEY `productequipment_EquipmentTypeID_idx` (`EquipmentTypeID`),
  CONSTRAINT `productequipment_EquipmentTypeID` FOREIGN KEY (`EquipmentTypeID`) REFERENCES `equipmenttype` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `productequipment_ProductID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productequipment`
--

LOCK TABLES `productequipment` WRITE;
/*!40000 ALTER TABLE `productequipment` DISABLE KEYS */;
INSERT INTO `productequipment` VALUES (1,1,1),(1,2,1),(2,1,1),(2,2,1),(3,1,1),(3,3,1),(4,1,1),(4,3,1);
/*!40000 ALTER TABLE `productequipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productinventory`
--

DROP TABLE IF EXISTS `productinventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productinventory` (
  `ProductID` smallint unsigned NOT NULL,
  `InventoryTypeID` smallint unsigned NOT NULL,
  `RequiredInventoryCount` tinyint unsigned NOT NULL,
  PRIMARY KEY (`ProductID`,`InventoryTypeID`),
  KEY `productinventory_InventoryID_idx` (`InventoryTypeID`),
  CONSTRAINT `productinventory_InventoryID` FOREIGN KEY (`InventoryTypeID`) REFERENCES `inventorytype` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `productinventory_ProductID` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productinventory`
--

LOCK TABLES `productinventory` WRITE;
/*!40000 ALTER TABLE `productinventory` DISABLE KEYS */;
INSERT INTO `productinventory` VALUES (1,1,1),(2,2,1),(3,1,2),(4,3,2);
/*!40000 ALTER TABLE `productinventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `SessionID` char(64) NOT NULL,
  `UserID` smallint unsigned NOT NULL,
  `LastUsed` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `session_UserID_idx` (`UserID`),
  CONSTRAINT `session_UserID` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (1,'h{D8-ebcc)QfjPY}He66e3W6QntA}HuVcjksB]rJHo(GWSQS_7Nvf}36eZCpojx-',1,'2026-05-11 12:41:18');
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `PermissionID` tinyint unsigned NOT NULL,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `PasswordHash` char(64) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `user_PermissionID_idx` (`PermissionID`),
  CONSTRAINT `user_PermissionID` FOREIGN KEY (`PermissionID`) REFERENCES `userpermission` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jSmith05',1,'John','Smith','be6fded9eba153d774245490f8f4120cebe2a6d3a5467603eca3343de90d6275'),
                          (2,'sLantern9',1,'Sarah','Lantern','7ab048ba3931ced4c81ddc169a632642b38dbc3701ab5b9e616ad83a0eedbcb1'),
                          (3,'fRedd8',2,'Felix','Redd','4396a959dd6b5e0bb46a1b8328afa4d1ba9420a1a5ce2ce94a92468cf977aae6'),
                          (4,'mLord4',2,'Maxwell','Lord','8c37dca73a2d50b337534a6693a96c3c77015aec2a2c8facad45e225f91aed38'),
                          (5,'lWater1',2,'Llyod','Water','b4526c6e38770dc68b7fceac04f3f1cf52415eff508482d058e26c6f8669c981'),
                          (6,'viewUser',3,'','','c9bfbb12cada9a1a4e1ede3ae6b3003dfca37f6d2b1fd9065fabdd79e5929bc8');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userpermission`
--

DROP TABLE IF EXISTS `userpermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userpermission` (
  `ID` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userpermission`
--

LOCK TABLES `userpermission` WRITE;
/*!40000 ALTER TABLE `userpermission` DISABLE KEYS */;
INSERT INTO `userpermission` VALUES (1,'admin'),(2,'editor'),(3,'viewer');
/*!40000 ALTER TABLE `userpermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'assemble'
--
/*!50003 DROP PROCEDURE IF EXISTS `ClearTables` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_bin */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ClearTables`()
BEGIN
	 -- Disable foreign key enforcement
	SET FOREIGN_KEY_CHECKS = 0;
	-- truncate tables
	truncate productequipment;
	truncate productinventory;
    truncate jobequipment;
    truncate jobinventory;
    truncate job;
    truncate product;
    truncate equipmenttype;
    truncate inventorytype;
	truncate equipment;
    truncate inventory;
    truncate personnel;
    truncate assemble.`session`;
	truncate `user`;
    -- Re-enable foreign key enforcement
	SET FOREIGN_KEY_CHECKS = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GenData_BasicData` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_bin */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GenData_BasicData`()
BEGIN
	
	DECLARE currentDate DATE;
	
    -- Disable foreign key enforcement
	SET FOREIGN_KEY_CHECKS = 0;
    
	-- truncate tables
    
    
	truncate productequipment;
	truncate productinventory;
    
    
    truncate jobequipment;
    truncate jobinventory;
    truncate job;
    truncate product;
    truncate equipmenttype;
    truncate inventorytype;
	truncate equipment;
    truncate inventory;
    
    truncate personnel;
    truncate assemble.`session`;
	truncate `user`;
    -- Re-enable foreign key enforcement
	SET FOREIGN_KEY_CHECKS = 1;
   
	INSERT INTO `equipmenttype` (`Description`)
	VALUES 
		("mold press"),
		("button mold"),
        ("rod mold");
	INSERT INTO `equipment` (`TypeID`, `Status`) 
    VALUES
		(1, 0),
        (1, 1),
        (1, 1),
        (1, 1),
        (1, 1),
        (1, 1),
        (2, 1), 
        (2, 0),
        (2, 1),
        (2, 1),
        (3, 1),
        (3, 1),
        (3, 0),
        (3, 1),
        (3, 1);
	INSERT INTO `inventorytype` (`Description`)
	VALUES 
		("Steel"),
        ("Brass"),
        ("Copper");
	INSERT INTO `inventory` (`TypeID`, `Count`)
    VALUES
		(1, 10),
        (1, 35),
        (1, 1),
        (2, 42),
        (2, 56),
        (3, 200),
        (3, 100);
        
    -- create 3 products
	INSERT INTO `product` (`Description`, `MinutesDuration`, `TargetPersonnelCount`)
    VALUES
		("steel button", 30, 1),
		("brass button", 60, 1),
		("steel rod", 105, 2),
		("copper rod", 90, 2);
        
	-- Create productequipment
	INSERT INTO `productequipment` (`ProductID`, `EquipmentTypeID`, `RequiredEquipmentTypeCount`)
	VALUES
		(1, 1, 1),
		(1, 2, 1),
		(2, 1, 1),
		(2, 2, 1),
		(3, 1, 1),
	    (3, 3, 1),
	    (4, 1, 1),
	    (4, 3, 1);
        
        -- create productInventory
	INSERT INTO `productInventory` (`ProductID`, `InventoryTypeID`, `RequiredInventoryCount`)
	VALUES
		(1, 1, 1),
		(2, 2, 1),
		(3, 1, 2),
		(4, 3, 2);
	
    INSERT INTO `user` (`Username`, `PermissionID`, `FirstName`, `LastName`, `PasswordHash`)
    VALUES
       ("jSmith05", 1, "John", "Smith", "0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e"),
       ("sLantern9", 1, "Sarah", "Lantern", "6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4"),
       ("fRedd8", 2, "Felix", "Redd", "5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764"),
       ("mLord4", 2, "Maxwell", "Lord", "b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b"),
       ("lWater1", 2, "Llyod", "Water", "8b2c86ea9cf2ea4eb517fd1e06b74f399e7fec0fef92e3b482a6cf2e2b092023"),
       ("viewUser", 3, "", "", "598a1a400c1dfdf36974e69d7e1bc98593f2e15015eed8e9b7e47a83b31693d5");
	
    SET currentDate = CURRENT_DATE();
    INSERT INTO `personnel` (`Date`, `Count`)
	VALUES
    (DATE_ADD(currentDate, INTERVAL -2 DAY), 5),
    (DATE_ADD(currentDate, INTERVAL -1 DAY), 6),
    (currentDate, 4),
    (DATE_ADD(currentDate, INTERVAL 1 DAY), 7),
    (DATE_ADD(currentDate, INTERVAL 2 DAY), 6);
    
    INSERT INTO `job` (`ProductID`, `StartTime`, `ProjectedEndTime`, `ActualEndTime`, `PersonnelCount`, `LineNumber`)
    VALUES
    -- Day -2 [DONE]
    (1, TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "5:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "5:30:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "5:30:00"),  1, 1),
    (3, TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "5:15:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "7:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "7:00:00"),  2, 2),
    (1, TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "5:35:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "6:05:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -2 DAY), "6:10:00"),  1, 1),
    
    -- Day -1 [DONE]
    (2, TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "5:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "6:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "6:03:00"),  1, 1),
    (2, TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "6:05:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "7:05:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "7:05:00"),  1, 1),
    (3, TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "5:30:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "7:05:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL -1 DAY), "7:10:00"),  2, 2),
    
    -- Day 0 [DONE]
    (4, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "5:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "6:30:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "6:33:00"),  2, 1),
    (4, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "6:35:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "8:05:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "8:08:00"),  2, 1),
    (4, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "8:10:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 0 DAY), "9:40:00"), null,  2, 1),
    -- Day 1
    (2, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 1 DAY), "6:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 1 DAY), "7:00:00"), null,  1, 1),
    (4, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 1 DAY), "11:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 1 DAY), "12:30:00"), null,  2, 2),
    (1, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 1 DAY), "16:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 1 DAY), "16:30:00"), null,  1, 1),
    -- Day 2
    (1, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 2 DAY), "6:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 2 DAY), "6:30:00"), null,  1, 1),
    (1, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 2 DAY), "11:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 2 DAY), "11:30:00"), null,  1, 1),
    (1, TIMESTAMP(DATE_ADD(currentDate, INTERVAL 2 DAY), "16:00:00"), TIMESTAMP(DATE_ADD(currentDate, INTERVAL 2 DAY), "16:30:00"), null,  1, 1)
    ;
    
    INSERT INTO `jobequipment` (`JobID`, `EquipmentID`)
    VALUES
    -- DAY -2 [DONE]
    (1, 1),
    (1, 9),
    (2, 3),
    (2, 13),
    (3, 1),
    (3, 9),
    -- DAY -1 [DONE]
    (4, 1),
    (4, 8),
    (5, 1),
    (5, 8),
    (6, 3),
    (6, 13),
    -- DAY 0
    (7, 4),
    (7, 11),
    (8, 4),
    (8, 11),
    (9, 4),
    (9, 11),
    -- DAY 1
    (10, 6),
    (10, 7),
    (11, 5),
    (11, 11),
    (12, 5),
    (12, 9),
    -- DAY 2
	(13, 2),
    (13, 10),
    (14, 2),
    (14, 10),
    (15, 2),
    (15, 10)
    ;
    INSERT INTO `jobinventory` (`JobID`, `InventoryID`, `InventoryUsedCount`) 
    VALUES
    -- DAY -2 [DONE]
    (1, 3, 1),
    (2, 3, 2),
    (3, 3, 1),
    -- DAY -1 [DONE]
    (4, 5, 1),
    (5, 5, 1),
    (6, 3, 2),
    -- DAY 0
    (7, 6, 2),
    (8, 6, 2),
    (9, 6, 2),
    -- DAY 1
    (10, 5, 1),
    (11, 6, 2),
    (12, 1, 1),
    -- DAY 2
    (13, 2, 1),
    (14, 2, 1),
    (15, 2, 1)
    ;
		

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-11 14:29:56
