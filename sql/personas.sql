CREATE DATABASE  IF NOT EXISTS `Persona` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `Persona`;
-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: Persona
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.17.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Follower`
--

DROP TABLE IF EXISTS `Follower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Follower` (
  `FollowerID` varchar(255) NOT NULL,
  `FollowingID` varchar(255) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`),
  KEY `FollowerID` (`FollowerID`)
) ENGINE=InnoDB AUTO_INCREMENT=10789 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `InfoContent`
--

DROP TABLE IF EXISTS `InfoContent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InfoContent` (
  `ContentID` int(11) NOT NULL AUTO_INCREMENT,
  `ProfileID` varchar(255) NOT NULL,
  `ContentInfo` text NOT NULL,
  PRIMARY KEY (`ContentID`),
  KEY `fk_InfoContent_1_idx` (`ProfileID`),
  CONSTRAINT `fk_InfoContent_1` FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Pictures`
--

DROP TABLE IF EXISTS `Pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pictures` (
  `PictureID` int(11) NOT NULL AUTO_INCREMENT,
  `ProfileID` varchar(255) NOT NULL,
  `Link` text NOT NULL,
  PRIMARY KEY (`PictureID`),
  KEY `fk_Pictures_1_idx` (`ProfileID`),
  CONSTRAINT `fk_Pictures_1` FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Profile`
--

DROP TABLE IF EXISTS `Profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Profile` (
  `ProfileID` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `BirthDate` varchar(255) DEFAULT NULL,
  `Gender` varchar(6) DEFAULT NULL,
  `Father` varchar(255) DEFAULT NULL,
  `Mother` varchar(255) DEFAULT NULL,
  `Children` int(11) DEFAULT NULL,
  `DeathDate` varchar(255) DEFAULT NULL,
  `NickName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ProfileID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ProfileOccupation`
--

DROP TABLE IF EXISTS `ProfileOccupation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfileOccupation` (
  `Occupation` varchar(255) NOT NULL,
  `ProfileID` varchar(255) NOT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  KEY `OccupationID` (`Occupation`),
  KEY `fk_ProfileOccupation_1_idx` (`ProfileID`),
  CONSTRAINT `fk_ProfileOccupation_1` FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `References`
--

DROP TABLE IF EXISTS `References`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `References` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ProfileId` varchar(255) NOT NULL,
  `Url` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_References_1_idx` (`ProfileId`),
  CONSTRAINT `fk_References_1` FOREIGN KEY (`ProfileId`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SocialNetworkStatus`
--

DROP TABLE IF EXISTS `SocialNetworkStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SocialNetworkStatus` (
  `StatusID` int(11) NOT NULL AUTO_INCREMENT,
  `ProfileID` varchar(255) NOT NULL,
  `PageRank` float NOT NULL,
  PRIMARY KEY (`StatusID`),
  KEY `fk_SocialNetworkStatus_1_idx` (`ProfileID`),
  CONSTRAINT `fk_SocialNetworkStatus_1` FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Spouce`
--

DROP TABLE IF EXISTS `Spouce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Spouce` (
  `SpouceId` int(11) NOT NULL AUTO_INCREMENT,
  `ProfileId` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`SpouceId`),
  KEY `fk_Spouce_1_idx` (`ProfileId`),
  CONSTRAINT `fk_Spouce_1` FOREIGN KEY (`ProfileId`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tweets`
--

DROP TABLE IF EXISTS `Tweets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tweets` (
  `TweetID` int(11) NOT NULL AUTO_INCREMENT,
  `TweetContent` text NOT NULL,
  `ProfileID` varchar(255) NOT NULL,
  `PostDate` varchar(255) NOT NULL,
  PRIMARY KEY (`TweetID`),
  KEY `fk_Tweets_1_idx` (`ProfileID`),
  CONSTRAINT `fk_Tweets_1` FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-05 19:52:30
