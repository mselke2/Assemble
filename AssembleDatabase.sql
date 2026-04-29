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

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `ID` smallint unsigned NOT NULL AUTO_INCREMENT,
  `TypeID` smallint unsigned NOT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,1,'0'),(2,1,'1'),(3,1,'1'),(4,1,'1'),(5,1,'1'),(6,1,'1'),(7,2,'1'),(8,2,'0'),(9,2,'1'),(10,2,'1'),(11,3,'1'),(12,3,'1'),(13,3,'0'),(14,3,'1'),(15,3,'1');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
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
  PRIMARY KEY (`JobID`,`EquipmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobequipment`
--

LOCK TABLES `jobequipment` WRITE;
/*!40000 ALTER TABLE `jobequipment` DISABLE KEYS */;
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
  PRIMARY KEY (`JobID`,`InventoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobinventory`
--

LOCK TABLES `jobinventory` WRITE;
/*!40000 ALTER TABLE `jobinventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobinventory` ENABLE KEYS */;
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
  `Duration` time NOT NULL,
  `TargetPersonnelCount` tinyint unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'steel button','00:30:00',1),(2,'brass button','01:00:00',1),(3,'steel rod','01:45:00',2),(4,'copper rod','01:30:00',2);
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
  PRIMARY KEY (`ProductID`,`EquipmentTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  PRIMARY KEY (`ProductID`,`InventoryTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `LoginToken` char(64) NOT NULL,
  `UserID` smallint unsigned NOT NULL,
  `LastUsed` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ClearTables`()
BEGIN
	truncate equipment;
    truncate equipmenttype;
    truncate inventory;
    truncate inventorytype;
    truncate job;
	truncate jobequipment;
    truncate jobinventory;
    truncate product;
    truncate productequipment;
	truncate productinventory;
    truncate assemble.`session`;
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
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GenData_BasicData`()
BEGIN
	-- truncate tables
	truncate equipment;
    truncate equipmenttype;
    truncate inventory;
    truncate inventorytype;
    truncate job;
	truncate jobequipment;
    truncate jobinventory;
    truncate product;
    truncate productequipment;
	truncate productinventory;
    truncate `session`;
	-- truncate `user`;
    
   
	INSERT INTO `equipmenttype` (`Description`)
	VALUES 
		("mold press"),
		("button mold"),
        ("rod mold");
	INSERT INTO `equipment` (`TypeID`, `Status`) 
    VALUES
		(1, "0"),
        (1, "1"),
        (1, "1"),
        (1, "1"),
        (1, "1"),
        (1, "1"),
        (2, "1"), (2, "0"),
        (2, "1"),
        (2, "1"),
        (3, "1"),
        (3, "1"),
        (3, "0"),
        (3, "1"),
        (3, "1");
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
	INSERT INTO `product` (`Description`, `Duration`, `TargetPersonnelCount`)
    VALUES
		("steel button", "0:30:00", 1),
		("brass button", "1:00:00", 1),
		("steel rod", "1:45:00", 2),
		("copper rod", "1:30:00", 2);
        
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

-- Dump completed on 2026-04-27 20:01:01
