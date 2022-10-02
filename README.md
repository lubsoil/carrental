# CarRental
created by Emil Andrzejak

## How to launch app
To launch app you need to create MySQL database called "carrental" and user "test" with password "test" that have full acess do this database (or change database name, login and password in application.properies file and recompile). Then you need to create table using this command "CREATE TABLE `carrental`.`cars` (`id` INT NOT NULL AUTO_INCREMENT , `car_code` TEXT NOT NULL , `model` TEXT NOT NULL , `seats` INT NOT NULL , `borrowed` BOOLEAN NOT NULL , PRIMARY KEY (`id`));". Then you can start the application.