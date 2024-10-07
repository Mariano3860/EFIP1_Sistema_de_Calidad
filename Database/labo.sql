CREATE DATABASE  IF NOT EXISTS `labo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `labo`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: labo
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo` (
  `idArticulo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idArticulo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
INSERT INTO `articulo` VALUES (1,'Acetato De Sodio Trihidrato','JSX010','Descripción del artículo 1'),(2,'Ácido Cítrico Sc 50%','ACX040','Descripción del artículo 2'),(3,'Ácido Láctico Tamponado','ALT010','Descripción del artículo 3'),(4,'Adama BR','ENZ010','Descripción del artículo 4'),(5,'Acetato De Sodio Trihidrato Seco','JSS010','Descripción del artículo 5'),(6,'XA-1600','XIN010','Descripción del artículo 6'),(7,'Alfa Amilasa 5000','ENZ020','Descripción del artículo 7'),(8,'Benzoato De Sodio Granulado','BNS010','Descripción del artículo 8'),(9,'Citrato De Potasio Monohidrato','CIK010','Descripción del artículo 9'),(10,'Citrato De Sodio','CIS020','Descripción del artículo 10');
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atributo`
--

DROP TABLE IF EXISTS `atributo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `atributo` (
  `idAtributo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idAtributo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atributo`
--

LOCK TABLES `atributo` WRITE;
/*!40000 ALTER TABLE `atributo` DISABLE KEYS */;
INSERT INTO `atributo` VALUES (1,'pH'),(2,'Densidad'),(3,'Solubilidad'),(4,'Título'),(5,'Pureza'),(6,'Conductividad'),(7,'Viscosidad'),(8,'Coloración'),(9,'Humedad'),(10,'Absorción UV');
/*!40000 ALTER TABLE `atributo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `califfinalatributo`
--

DROP TABLE IF EXISTS `califfinalatributo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `califfinalatributo` (
  `idIngreso` int NOT NULL,
  `idEspecificacion` int NOT NULL,
  `idAtributo` int NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idIngreso`,`idEspecificacion`,`idAtributo`),
  KEY `idEspecificacion` (`idEspecificacion`,`idAtributo`),
  CONSTRAINT `califfinalatributo_ibfk_1` FOREIGN KEY (`idEspecificacion`, `idAtributo`) REFERENCES `especificacionatributo` (`idEspecificacion`, `idAtributo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `califfinalatributo`
--

LOCK TABLES `califfinalatributo` WRITE;
/*!40000 ALTER TABLE `califfinalatributo` DISABLE KEYS */;
INSERT INTO `califfinalatributo` VALUES (1,1,1,7.50,'pH Aceptable'),(1,1,2,1.00,'Densidad Aceptable'),(2,2,1,3.50,'pH Cumple'),(2,2,5,99.00,'Pureza Aceptable'),(3,3,1,4.00,'Cumple'),(3,3,6,15.00,'Conductividad Correcta'),(4,4,5,96.00,'Pureza aceptable'),(4,4,8,25.00,'Absorbancia en rango'),(5,5,1,7.00,'pH dentro del rango'),(5,5,9,2.00,'Humedad aceptable'),(6,6,1,5.50,'pH Correcto'),(6,6,2,1.00,'Densidad Correcta'),(7,7,1,6.70,'pH Cumple'),(7,7,4,90.00,'Título correcto'),(8,8,1,7.20,'pH Aceptable'),(8,8,5,100.00,'Pureza máxima'),(9,9,1,3.50,'pH Cumple'),(9,9,5,99.00,'Pureza correcta'),(10,10,1,2.90,'pH dentro del rango'),(10,10,5,98.00,'Pureza aceptable');
/*!40000 ALTER TABLE `califfinalatributo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calificacionfinal`
--

DROP TABLE IF EXISTS `calificacionfinal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificacionfinal` (
  `idIngreso` int NOT NULL,
  `idEspecificacion` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idIngreso`,`idEspecificacion`),
  CONSTRAINT `calificacionfinal_ibfk_1` FOREIGN KEY (`idIngreso`) REFERENCES `ingreso` (`idIngreso`),
  CONSTRAINT `calificacionfinal_ibfk_2` FOREIGN KEY (`idIngreso`, `idEspecificacion`) REFERENCES `califfinalatributo` (`idIngreso`, `idEspecificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificacionfinal`
--

LOCK TABLES `calificacionfinal` WRITE;
/*!40000 ALTER TABLE `calificacionfinal` DISABLE KEYS */;
INSERT INTO `calificacionfinal` VALUES (1,1,'2022-07-27','Aprobado'),(2,2,'2022-07-26','Aprobado'),(3,3,'2022-07-25','Aprobado'),(4,4,'2022-07-24','Aprobado'),(5,5,'2022-07-23','Aprobado'),(6,6,'2022-07-22','Aprobado'),(7,7,'2022-07-21','Aprobado'),(8,8,'2022-07-20','Aprobado'),(9,9,'2022-07-19','Aprobado'),(10,10,'2022-07-18','Aprobado');
/*!40000 ALTER TABLE `calificacionfinal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calificacionlote`
--

DROP TABLE IF EXISTS `calificacionlote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificacionlote` (
  `idIngreso` int NOT NULL,
  `numMuestra` int NOT NULL,
  `idEspecificacion` int NOT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idIngreso`,`numMuestra`,`idEspecificacion`),
  CONSTRAINT `calificacionlote_ibfk_1` FOREIGN KEY (`idIngreso`) REFERENCES `ingreso` (`idIngreso`),
  CONSTRAINT `calificacionlote_ibfk_2` FOREIGN KEY (`idIngreso`, `numMuestra`, `idEspecificacion`) REFERENCES `califloteatributo` (`idIngreso`, `numMuestra`, `idEspecificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificacionlote`
--

LOCK TABLES `calificacionlote` WRITE;
/*!40000 ALTER TABLE `calificacionlote` DISABLE KEYS */;
INSERT INTO `calificacionlote` VALUES (1,1,1,'Aprobado','2022-07-27'),(1,2,1,'Aprobado','2022-07-27'),(2,1,2,'Aprobado','2022-07-26'),(2,2,2,'Aprobado','2022-07-26'),(3,1,3,'Aprobado','2022-07-25'),(3,2,3,'Aprobado','2022-07-25'),(4,1,4,'Aprobado','2022-07-24'),(4,2,4,'Aprobado','2022-07-24'),(5,1,5,'Aprobado','2022-07-23'),(5,2,5,'Aprobado','2022-07-23'),(6,1,6,'Aprobado','2022-07-22'),(6,2,6,'Aprobado','2022-07-22'),(7,1,7,'Aprobado','2022-07-21'),(7,2,7,'Aprobado','2022-07-21'),(8,1,8,'Aprobado','2022-07-20'),(8,2,8,'Aprobado','2022-07-20'),(9,1,9,'Aprobado','2022-07-19'),(9,2,9,'Aprobado','2022-07-19'),(10,1,10,'Aprobado','2022-07-18'),(10,2,10,'Aprobado','2022-07-18');
/*!40000 ALTER TABLE `calificacionlote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `califloteatributo`
--

DROP TABLE IF EXISTS `califloteatributo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `califloteatributo` (
  `idIngreso` int NOT NULL,
  `numMuestra` int NOT NULL,
  `idAtributo` int NOT NULL,
  `idEspecificacion` int NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idIngreso`,`numMuestra`,`idAtributo`,`idEspecificacion`),
  KEY `idAtributo` (`idAtributo`,`idEspecificacion`),
  KEY `idx_califloteatributo` (`idIngreso`,`numMuestra`,`idEspecificacion`),
  CONSTRAINT `califloteatributo_ibfk_1` FOREIGN KEY (`idAtributo`, `idEspecificacion`) REFERENCES `especificacionatributo` (`idAtributo`, `idEspecificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `califloteatributo`
--

LOCK TABLES `califloteatributo` WRITE;
/*!40000 ALTER TABLE `califloteatributo` DISABLE KEYS */;
INSERT INTO `califloteatributo` VALUES (1,1,1,1,7.50,'Dentro de especificación'),(1,1,2,1,1.00,'Cumple con la densidad'),(1,2,1,1,7.80,'Dentro de especificación'),(1,2,3,1,95.00,'Dentro de especificación'),(2,1,1,2,3.50,'pH dentro del rango'),(2,1,4,2,98.00,'Título cumple'),(2,2,1,2,3.90,'pH en el límite superior'),(2,2,5,2,99.00,'Pureza aceptable'),(3,1,1,3,4.00,'Cumple pH'),(3,1,6,3,15.00,'Conductividad dentro del rango'),(3,2,1,3,3.70,'Cumple pH'),(3,2,7,3,300.00,'Viscosidad aceptable'),(4,1,5,4,96.00,'Pureza aceptable'),(4,1,8,4,25.00,'Absorbancia dentro de lo esperado'),(4,2,3,4,98.00,'Solubilidad dentro de lo esperado'),(4,2,8,4,30.00,'Absorbancia ligeramente alta'),(5,1,1,5,7.00,'Dentro de especificación de pH'),(5,1,2,5,0.90,'Densidad correcta'),(5,2,1,5,7.20,'Dentro de especificación de pH'),(5,2,9,5,2.00,'Humedad aceptable'),(6,1,1,6,5.50,'pH dentro del rango'),(6,1,2,6,1.00,'Densidad correcta'),(6,2,1,6,5.80,'pH dentro de lo esperado'),(6,2,3,6,92.00,'Cumple con los parámetros'),(7,1,1,7,6.70,'pH correcto'),(7,1,2,7,1.02,'Densidad aceptable'),(7,2,1,7,6.50,'pH cumple con lo esperado'),(7,2,4,7,90.00,'Título en el rango permitido'),(8,1,1,8,7.20,'pH dentro del rango'),(8,1,3,8,98.00,'Pureza cumple'),(8,2,1,8,7.40,'pH aceptable'),(8,2,5,8,100.00,'Pureza máxima alcanzada'),(9,1,1,9,3.50,'Dentro de especificación'),(9,1,2,9,0.98,'Cumple con la densidad'),(9,2,1,9,3.80,'Dentro de especificación'),(9,2,5,9,99.00,'Pureza dentro del rango'),(10,1,1,10,2.90,'Dentro de especificación'),(10,1,2,10,0.90,'Densidad aceptable'),(10,2,1,10,3.00,'Cumple pH'),(10,2,5,10,98.00,'Pureza esperada');
/*!40000 ALTER TABLE `califloteatributo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especificacion`
--

DROP TABLE IF EXISTS `especificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especificacion` (
  `idEspecificacion` int NOT NULL AUTO_INCREMENT,
  `idArticulo` int DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idEspecificacion`),
  KEY `idArticulo` (`idArticulo`),
  CONSTRAINT `especificacion_ibfk_1` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especificacion`
--

LOCK TABLES `especificacion` WRITE;
/*!40000 ALTER TABLE `especificacion` DISABLE KEYS */;
INSERT INTO `especificacion` VALUES (1,1,'Especificación Acetato de Sodio Trihidrato'),(2,2,'Especificación Ácido Cítrico Sc 50%'),(3,3,'Especificación Ácido Láctico Tamponado'),(4,4,'Especificación Adama BR'),(5,5,'Especificación Acetato de Sodio Trihidrato Seco'),(6,6,'Especificación XA-1600'),(7,7,'Especificación Alfa Amilasa 5000'),(8,8,'Especificación Benzoato de Sodio Granulado'),(9,9,'Especificación Citrato de Potasio Monohidrato'),(10,10,'Especificación Citrato de Sodio');
/*!40000 ALTER TABLE `especificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especificacionatributo`
--

DROP TABLE IF EXISTS `especificacionatributo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especificacionatributo` (
  `idAtributo` int NOT NULL,
  `idEspecificacion` int NOT NULL,
  `valorMin` decimal(10,2) DEFAULT NULL,
  `valorMax` decimal(10,2) DEFAULT NULL,
  `unidadMedida` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idAtributo`,`idEspecificacion`),
  KEY `idEspecificacion` (`idEspecificacion`),
  CONSTRAINT `especificacionatributo_ibfk_1` FOREIGN KEY (`idAtributo`) REFERENCES `atributo` (`idAtributo`),
  CONSTRAINT `especificacionatributo_ibfk_2` FOREIGN KEY (`idEspecificacion`) REFERENCES `especificacion` (`idEspecificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especificacionatributo`
--

LOCK TABLES `especificacionatributo` WRITE;
/*!40000 ALTER TABLE `especificacionatributo` DISABLE KEYS */;
INSERT INTO `especificacionatributo` VALUES (1,1,7.00,8.00,'pH'),(1,2,2.00,4.00,'pH'),(1,3,3.50,4.50,'pH'),(1,5,6.50,7.50,'pH'),(1,6,5.00,6.00,'pH'),(1,7,6.00,7.00,'pH'),(1,8,7.00,8.00,'pH'),(1,9,3.00,4.00,'pH'),(1,10,2.50,3.50,'pH'),(2,1,0.90,1.10,'g/mL'),(2,5,0.80,1.00,'g/mL'),(2,6,0.90,1.10,'g/mL'),(2,7,0.95,1.05,'g/mL'),(2,9,0.90,1.10,'g/mL'),(2,10,0.85,1.05,'g/mL'),(3,1,90.00,100.00,'%'),(3,4,90.00,100.00,'%'),(3,6,90.00,95.00,'%'),(3,8,90.00,100.00,'%'),(4,2,90.00,100.00,'%'),(4,7,88.00,100.00,'%'),(5,2,98.00,100.00,'%'),(5,4,95.00,100.00,'%'),(5,8,98.00,100.00,'%'),(5,9,96.00,100.00,'%'),(5,10,94.00,100.00,'%'),(6,3,10.00,20.00,'mS/cm'),(7,3,100.00,500.00,'cP'),(8,4,0.00,50.00,'Absorbancia'),(9,5,0.00,5.00,'%');
/*!40000 ALTER TABLE `especificacionatributo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingreso`
--

DROP TABLE IF EXISTS `ingreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingreso` (
  `idIngreso` int NOT NULL AUTO_INCREMENT,
  `proveedor` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  `idArticulo` int DEFAULT NULL,
  PRIMARY KEY (`idIngreso`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idArticulo` (`idArticulo`),
  CONSTRAINT `ingreso_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `ingreso_ibfk_2` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingreso`
--

LOCK TABLES `ingreso` WRITE;
/*!40000 ALTER TABLE `ingreso` DISABLE KEYS */;
INSERT INTO `ingreso` VALUES (1,'QUIMTIA','MP','2022-07-26',1,1),(2,'SINO PHOS','MP','2022-07-25',2,2),(3,'LATIN CHEMICAL','MP','2022-07-25',3,3),(4,'NEOPHOS','MP','2022-07-20',1,4),(5,'INDAQUIM','MP','2022-07-20',2,5),(6,'CEAMSA','MP','2022-07-14',3,6),(7,'PLASTICOS DEL PLATA','MP','2022-07-14',1,7),(8,'DOW','MP','2022-07-14',2,8),(9,'Rame','MP','2022-07-14',3,9),(10,'LODRA','MP','2022-07-14',1,10);
/*!40000 ALTER TABLE `ingreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','admin','admin'),(2,'Caro','caro@example.com','password123'),(3,'Gonzalo','gonzalo@example.com','password123');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-02  8:28:31
