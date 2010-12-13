-- MySQL dump 10.11
--
-- Host: localhost    Database: extensiblecatalog
-- ------------------------------------------------------
-- Server version	5.0.51a-community-nt

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
-- Create database `extensiblecatalog`
--

DROP DATABASE IF EXISTS `extensiblecatalog`;

CREATE DATABASE IF NOT EXISTS `extensiblecatalog` DEFAULT CHARACTER SET utf8;

USE `extensiblecatalog`;


--
-- Table structure for table `records`
--

DROP TABLE IF EXISTS `records`;
CREATE TABLE `records` (
  `record_id` int(11) NOT NULL auto_increment,
  `external_id` varchar(20) NOT NULL default '',
  `record_type` int(11) NOT NULL,
  `repository_code` varchar(20) NOT NULL default '',
  `xc_oai_id` varchar(90) NOT NULL,
  `creation_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `modification_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `is_deleted` boolean NOT NULL default false,
--  `is_bigxml` boolean NOT NULL default false,
  `root_name` varchar(30) NOT NULL default '',
  `root_namespace` varchar(90) NOT NULL default '',
  PRIMARY KEY  (`record_id`),
  KEY `externalid` (`external_id`),
  KEY `modified` (`modification_date`),
  KEY `root_name` (`root_name`),
  KEY `root_namespace` (`root_namespace`),
  KEY `record_type` (`record_type`),
  KEY `modification_date` (`modification_date`,`record_type`),
  FOREIGN KEY (`record_type`) REFERENCES `sets` (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `records`
--

LOCK TABLES `records` WRITE;
/*!40000 ALTER TABLE `records` DISABLE KEYS */;
/*!40000 ALTER TABLE `records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sets_to_records`
--

DROP TABLE IF EXISTS `sets_to_records`;
CREATE TABLE `sets_to_records` (
  `record_id` int(11) NOT NULL,
  `set_id` int(11) NOT NULL,
  KEY `record_id` (`record_id`),
  KEY `set_id` (`set_id`),
  FOREIGN KEY (`record_id`) REFERENCES `records` (`record_id`),
  FOREIGN KEY (`set_id`) REFERENCES `sets` (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sets_to_records`
--

LOCK TABLES `sets_to_records` WRITE;
/*!40000 ALTER TABLE `sets_to_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `sets_to_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sets`
--

DROP TABLE IF EXISTS `sets`;
CREATE TABLE `sets` (
  `set_id` int(11) NOT NULL auto_increment,
  `set_name` varchar(40) NOT NULL default '',
  `set_tag` varchar(30) NOT NULL default '',
  `set_spec` varchar(30) NOT NULL default '',
  `set_description` varchar(50) default NULL,
  KEY `SETS_SET_SPEC_IDX` (`set_spec`),
  PRIMARY KEY  (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sets`
--

LOCK TABLES `sets` WRITE;
/*!40000 ALTER TABLE `sets` DISABLE KEYS */;
INSERT INTO `sets` (set_name, set_tag, set_spec, set_description) VALUES ('Bibliographic records','bib','bib','Bibliographic records'),('Authority records','auth','auth','Authority records'),('Holdings record','hold','hold','Holdings records'),('Classification records','class','class','Classification records'),('Community Information records','comm','comm','Community Information records');
/*!40000 ALTER TABLE `sets` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `resumption_tokens`;
CREATE TABLE `resumption_tokens` (
  `id` int(11) NOT NULL auto_increment,
  `creation_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `query` blob NOT NULL,
  `query_for_count` blob NOT NULL,
  `metadata_prefix` varchar(50),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table for normal xml files (with max length of 64K)
DROP TABLE IF EXISTS `xmls`;
CREATE TABLE `xmls` (
  `record_id` int(11) NOT NULL,
  `xml` LONGBLOB NOT NULL,
  PRIMARY KEY  (`record_id`),
  FOREIGN KEY (`record_id`) REFERENCES `records` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Table for big xml files
DROP TABLE IF EXISTS `bigxmls`;
CREATE TABLE `bigxmls` (
  `record_id` int(11) NOT NULL,
  `xml` LONGBLOB NOT NULL,
  PRIMARY KEY  (`record_id`),
  FOREIGN KEY (`record_id`) REFERENCES `records` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Table for tracking OAI ID number
DROP TABLE IF EXISTS `tracking_oaiidnumber`;
CREATE TABLE `tracking_oaiidnumber` (
  `tracking_id` int(11) NOT NULL auto_increment,
  `tracked_oaiidnumber` int(11) NOT NULL,
  PRIMARY KEY  (`tracking_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into tracking_oaiidnumber values(1,0);

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
