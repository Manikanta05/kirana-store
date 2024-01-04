# kirana-store
Springboot Application

### Required Configuration before application startup <br>
MySQL Database name : kiranastore <br>
MySQL Table : kirana_transaction <br>
MYSQL DDL Statement : <br>
<code>
CREATE SCHEMA `kiranastore` ;

CREATE TABLE `kiranastore`.`kirana_transaction` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`currency` VARCHAR(5) NOT NULL,
`type` VARCHAR(5) NOT NULL,
`amount` DOUBLE NOT NULL,
`date` DATE NOT NULL,
PRIMARY KEY (`id`));
</code>


### To access UI-documentation :
- Run the application
- Navigate to http://localhost:8080/store-documentation

### To access Text-documentation :
- Run the application
- Navigate to http://localhost:8080/store-api-docs
