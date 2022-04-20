-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cupcake
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cupcake` ;

-- -----------------------------------------------------
-- Schema cupcake
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cupcake` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cupcake` ;

-- -----------------------------------------------------
-- Table `cupcake`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`user` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `balance` INT NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cupcake`.`topping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`topping` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`topping` (
  `topping_id` INT NOT NULL AUTO_INCREMENT,
  `topping_price` INT NOT NULL,
  `topping_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`topping_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cupcake`.`bottom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`bottom` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`bottom` (
  `bottom_id` INT NOT NULL AUTO_INCREMENT,
  `bottom_price` INT NOT NULL,
  `bottom_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`bottom_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cupcake`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cupcake`.`orders` ;

CREATE TABLE IF NOT EXISTS `cupcake`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `bottom_id` INT NOT NULL,
  `topping_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  `date` DATETIME NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_orders_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_orders_topping1_idx` (`topping_id` ASC) VISIBLE,
  INDEX `fk_orders_bottom1_idx` (`bottom_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `cupcake`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_topping1`
    FOREIGN KEY (`topping_id`)
    REFERENCES `cupcake`.`topping` (`topping_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_bottom1`
    FOREIGN KEY (`bottom_id`)
    REFERENCES `cupcake`.`bottom` (`bottom_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
