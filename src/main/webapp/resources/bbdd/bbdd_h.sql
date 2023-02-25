-- MariaDB dump 10.19  Distrib 10.5.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: Hospital
-- ------------------------------------------------------
-- Server version	10.5.10-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `almacen`
--

DROP TABLE IF EXISTS `almacen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `almacen` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `almacen`
--

LOCK TABLES `almacen` WRITE;
/*!40000 ALTER TABLE `almacen` DISABLE KEYS */;
INSERT INTO `almacen` VALUES (1,10000,'Sin medicamento'),(2,10,'Ibuprofeno'),(3,10,'Aspirina'),(11,19,'Morfina'),(12,25,'Nolotil');
/*!40000 ALTER TABLE `almacen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadisticas`
--

DROP TABLE IF EXISTS `estadisticas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadisticas` (
  `medicinas` int(200) DEFAULT 0,
  `altas` int(11) DEFAULT NULL,
  `ingresos` int(11) DEFAULT NULL,
  `idEstadisticas` int(11) NOT NULL AUTO_INCREMENT,
  `fechaEstadistica` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  PRIMARY KEY (`idEstadisticas`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadisticas`
--

LOCK TABLES `estadisticas` WRITE;
/*!40000 ALTER TABLE `estadisticas` DISABLE KEYS */;
INSERT INTO `estadisticas` VALUES (0,0,0,1,'2021-06-25T19:06:09.991'),(5,5,5,2,'2021-06-26T19:06:09.991'),(0,1,1,6,'2021-06-28T13:16:36.578'),(3,2,0,7,'2021-06-29T11:07:51.355');
/*!40000 ALTER TABLE `estadisticas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estancias`
--

DROP TABLE IF EXISTS `estancias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estancias` (
  `idEstancias` int(50) NOT NULL AUTO_INCREMENT,
  `ingreso` varchar(100) DEFAULT NULL,
  `alta` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idEstancias`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estancias`
--

LOCK TABLES `estancias` WRITE;
/*!40000 ALTER TABLE `estancias` DISABLE KEYS */;
INSERT INTO `estancias` VALUES (22,'2021-06-28T13:27:34.926',NULL),(23,NULL,'2021-06-29T11:46:58.092'),(24,'2021-06-28T14:04:55.776',NULL),(25,'2021-06-29T12:00:36.477',NULL);
/*!40000 ALTER TABLE `estancias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `usuario` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `contraseña` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `Rol` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `idLogin` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idTrabajador` int(11) NOT NULL,
  PRIMARY KEY (`idLogin`),
  KEY `login_FK` (`fk_idTrabajador`),
  CONSTRAINT `login_FK` FOREIGN KEY (`fk_idTrabajador`) REFERENCES `personal` (`idTrabajador`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('jgoga','1234','enfermero',1,1),('jmago','1234','secretario',2,2),('jfero','1234','medico',24,40),('dgofe0','A78l25F','medico',27,43),('mgofe0','b3RChC0','enfermero',28,44);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacientes`
--

DROP TABLE IF EXISTS `pacientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pacientes` (
  `IdPaciente` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Apellido1` varchar(50) NOT NULL,
  `Apellido2` varchar(50) NOT NULL,
  `NIFNIE` varchar(50) NOT NULL,
  `Enfermedad` varchar(50) DEFAULT NULL,
  `idProducto` int(50) DEFAULT NULL,
  `idTrabajador` int(50) NOT NULL DEFAULT 0,
  `UMedicamento` int(50) DEFAULT NULL,
  `idEstancias` int(50) DEFAULT 0,
  PRIMARY KEY (`IdPaciente`),
  KEY `fk_idTrabajador` (`idTrabajador`),
  KEY `pacientes_FK` (`idEstancias`),
  KEY `pacientes_FK_1` (`idProducto`),
  CONSTRAINT `fk_idTrabajador` FOREIGN KEY (`idTrabajador`) REFERENCES `personal` (`idTrabajador`),
  CONSTRAINT `pacientes_FK` FOREIGN KEY (`idEstancias`) REFERENCES `estancias` (`idEstancias`),
  CONSTRAINT `pacientes_FK_1` FOREIGN KEY (`idProducto`) REFERENCES `almacen` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacientes`
--

LOCK TABLES `pacientes` WRITE;
/*!40000 ALTER TABLE `pacientes` DISABLE KEYS */;
INSERT INTO `pacientes` VALUES (31,'Alex','García','González','111111111',NULL,1,1,0,22),(34,'Manolo','Ferreira','González','1111111111','Enfermedad',1,40,0,25);
/*!40000 ALTER TABLE `pacientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal`
--

DROP TABLE IF EXISTS `personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal` (
  `idTrabajador` int(50) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `Apellido1` varchar(75) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `Apellido2` varchar(75) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `NIFNIE` varchar(10) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `FechaAlta` varchar(50) COLLATE utf16_spanish_ci DEFAULT NULL,
  `CuentaBancaria` varchar(30) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `Puesto` varchar(15) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `Email` varchar(40) COLLATE utf16_spanish_ci NOT NULL,
  PRIMARY KEY (`idTrabajador`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal`
--

LOCK TABLES `personal` WRITE;
/*!40000 ALTER TABLE `personal` DISABLE KEYS */;
INSERT INTO `personal` VALUES (1,'Javier','Gonzalez','Garcia','71423235B','2021-06-25T18:55:20.907','2222222222222222222','enfermero','email1@email2.com'),(2,'Jose','Martinez','Gonzalez','71256867H','2021-06-24T18:55:20.907','3333333333333333333','secretario','email3@email3.com'),(40,'Juan','Fernandez','del Rio','71563317V','2021-06-26T18:55:20.907','11111111111111111111','medico','email@email.com'),(43,'David','González','Fernández','111111111A','2021-06-28T13:23:19.494','11111111111','médico','fgarcd03@estudiantes.unileon.es'),(44,'María','González','Fernández','111111111A','2021-06-28T13:31:21.890','11111111111','enfermera','fgarcd03@estudiantes.unileon.es');
/*!40000 ALTER TABLE `personal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'Hospital'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-29 13:15:17
