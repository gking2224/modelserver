-- MySQL dump 10.13  Distrib 5.5.46, for Linux (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version   5.5.46

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
-- Table structure for table `demotable`
--

DROP TABLE IF EXISTS `model_type`;
CREATE TABLE `model_type` (
    `model_type_id` BIGINT not null auto_increment primary key,
    `name` VARCHAR(20) not null,
    `enabled` BIT not null
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `model_type`
ADD UNIQUE `unq_mod_type` (`name`);

DROP TABLE IF EXISTS `model_manifest`;
CREATE TABLE `model_manifest` (
    `model_manifest_id` BIGINT not null auto_increment primary key,
    `name` VARCHAR(20) not null,
    `model_type_id` BIGINT not null
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `model_manifest`
ADD CONSTRAINT fk_modelmfst_type FOREIGN KEY (model_type_id)
REFERENCES model_type(model_type_id)
ON DELETE RESTRICT;

ALTER TABLE `model_manifest`
ADD UNIQUE `unq_mod_manifest` (`model_type_id`, `name`);

DROP TABLE IF EXISTS `model`;
CREATE TABLE `model` (
    `model_id` BIGINT not null auto_increment primary key,
    `model_manifest_id` BIGINT not null,
    `major_version` INT(2) not null,
    `minor_version` INT(2) not null,
    `script` MEDIUMTEXT,
    `enabled` BIT not null
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `model`
ADD CONSTRAINT fk_modver_manif FOREIGN KEY (model_manifest_id)
REFERENCES model_manifest(model_manifest_id)
ON DELETE RESTRICT;

ALTER TABLE `model`
ADD UNIQUE `unq_mod_version` (`model_manifest_id`, `major_version`, `minor_version`);

DROP TABLE IF EXISTS `model_nature`;
CREATE TABLE `model_nature` (
    `model_id` BIGINT not null,
    `nature` VARCHAR(255) not null
) ENGINE=InnoDB;

ALTER TABLE `model_nature`
ADD CONSTRAINT fk_modnat_modver FOREIGN KEY (model_id)
REFERENCES model(model_id)
ON DELETE RESTRICT;

ALTER TABLE `model_nature`
ADD UNIQUE `unq_model_nature` (`model_id`, `nature`);

DROP TABLE IF EXISTS `model_variable`;
CREATE TABLE `model_variable` (
    `model_variable_id` BIGINT not null auto_increment primary key,
    `name` VARCHAR(255) not null,
    `type` VARCHAR(255) not null
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `model_variable`
ADD UNIQUE `unq_model_var` (`name`, `type`);

DROP TABLE IF EXISTS `model_assigned_variable`;
CREATE TABLE `model_assigned_variable` (
    `model_variable_id` BIGINT not null,
    `model_id` BIGINT not null,
    `direction` CHAR(1) not null,
    `mandatory` BIT not null
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `model_assigned_variable`
ADD CONSTRAINT fk_madassvar_var FOREIGN KEY (model_variable_id)
REFERENCES model_variable(model_variable_id)
ON DELETE RESTRICT;

ALTER TABLE `model_assigned_variable`
ADD CONSTRAINT fk_madassvar_mod FOREIGN KEY (model_id)
REFERENCES model(model_id)
ON DELETE RESTRICT;

ALTER TABLE `model_assigned_variable`
ADD UNIQUE `unq_model_ass_var` (`model_variable_id`, `model_id`);
