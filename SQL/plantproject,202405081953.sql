-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: plantproject
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `plant_inventory`
--

DROP TABLE IF EXISTS `plant_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plant_inventory` (
  `plant_inventory_ID` int NOT NULL AUTO_INCREMENT,
  `creation_date` date DEFAULT NULL,
  PRIMARY KEY (`plant_inventory_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_inventory`
--

LOCK TABLES `plant_inventory` WRITE;
/*!40000 ALTER TABLE `plant_inventory` DISABLE KEYS */;
INSERT INTO `plant_inventory` VALUES (1,NULL),(2,NULL);
/*!40000 ALTER TABLE `plant_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_link_inventory`
--

DROP TABLE IF EXISTS `plant_link_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plant_link_inventory` (
  `plant_inventory_ID` int NOT NULL,
  `plant_ID` int NOT NULL,
  PRIMARY KEY (`plant_inventory_ID`,`plant_ID`),
  UNIQUE KEY `plant_link_plant_inventory_unique` (`plant_ID`),
  CONSTRAINT `plant_link_inventory_plants_FK` FOREIGN KEY (`plant_ID`) REFERENCES `plants` (`plant_ID`),
  CONSTRAINT `plant_link_plant_inventory_plant_inventory_FK` FOREIGN KEY (`plant_inventory_ID`) REFERENCES `plant_inventory` (`plant_inventory_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_link_inventory`
--

LOCK TABLES `plant_link_inventory` WRITE;
/*!40000 ALTER TABLE `plant_link_inventory` DISABLE KEYS */;
INSERT INTO `plant_link_inventory` VALUES (1,1);
/*!40000 ALTER TABLE `plant_link_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_moisture_bank`
--

DROP TABLE IF EXISTS `plant_moisture_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plant_moisture_bank` (
  `plant_ID` int NOT NULL,
  `moisture_level` int NOT NULL,
  `time_of_check` date NOT NULL,
  PRIMARY KEY (`plant_ID`,`moisture_level`,`time_of_check`),
  CONSTRAINT `plant_moisture_bank_plants_FK` FOREIGN KEY (`plant_ID`) REFERENCES `plants` (`plant_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_moisture_bank`
--

LOCK TABLES `plant_moisture_bank` WRITE;
/*!40000 ALTER TABLE `plant_moisture_bank` DISABLE KEYS */;
/*!40000 ALTER TABLE `plant_moisture_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plant_names`
--

DROP TABLE IF EXISTS `plant_names`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plant_names` (
  `plant_name_ID` int NOT NULL AUTO_INCREMENT,
  `plant_name` varchar(50) DEFAULT NULL,
  `plant_specise` varchar(50) DEFAULT NULL,
  `plant_description` text,
  PRIMARY KEY (`plant_name_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant_names`
--

LOCK TABLES `plant_names` WRITE;
/*!40000 ALTER TABLE `plant_names` DISABLE KEYS */;
INSERT INTO `plant_names` VALUES (1,'carrot','basic','it\'s a happy carrot'),(2,'potatoe','basic','did you know that potatoe seeds exist?'),(3,'clover','lucky','I pray that it has 4 leaves');
/*!40000 ALTER TABLE `plant_names` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plants`
--

DROP TABLE IF EXISTS `plants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plants` (
  `plant_ID` int NOT NULL AUTO_INCREMENT,
  `plant_name_ID` int NOT NULL,
  `moisture_min` int DEFAULT NULL,
  `moisture_max` int DEFAULT NULL,
  `sensor_ID` int DEFAULT NULL,
  PRIMARY KEY (`plant_ID`),
  UNIQUE KEY `plants_unique` (`sensor_ID`),
  KEY `plants_plant_names_FK` (`plant_name_ID`),
  CONSTRAINT `plants_plant_names_FK` FOREIGN KEY (`plant_name_ID`) REFERENCES `plant_names` (`plant_name_ID`),
  CONSTRAINT `plants_sensors_FK` FOREIGN KEY (`sensor_ID`) REFERENCES `sensors` (`sensor_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plants`
--

LOCK TABLES `plants` WRITE;
/*!40000 ALTER TABLE `plants` DISABLE KEYS */;
INSERT INTO `plants` VALUES (1,1,10,70,NULL),(2,1,30,70,NULL),(3,1,30,70,NULL);
/*!40000 ALTER TABLE `plants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensors`
--

DROP TABLE IF EXISTS `sensors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensors` (
  `sensor_ID` int NOT NULL AUTO_INCREMENT,
  `observation_interval` int NOT NULL,
  PRIMARY KEY (`sensor_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensors`
--

LOCK TABLES `sensors` WRITE;
/*!40000 ALTER TABLE `sensors` DISABLE KEYS */;
/*!40000 ALTER TABLE `sensors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `plant_inventory_ID` int DEFAULT NULL,
  PRIMARY KEY (`user_ID`),
  UNIQUE KEY `user_unique` (`plant_inventory_ID`),
  CONSTRAINT `user_plant_inventory_FK` FOREIGN KEY (`plant_inventory_ID`) REFERENCES `plant_inventory` (`plant_inventory_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Lance','lance',1),(3,'Steven','112',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'plantproject'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-08 19:53:29
