DROP TABLE IF EXISTS dealer_customer;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS dealership;

CREATE TABLE dealership {
	dealership_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR (256) NOT NULL,
	address VARCHAR (128) NOT NULL,
	city VARCHAR (128) NOT NULL,
	state VARCHAR (64),
	zip VARCHAR (20),
	phone VARCHAR (20),
	PRIMARY KEY (dealership_id)
};

CREATE TABLE employee {
	employee_id INT NOT NULL AUTO_INCREMENT,
	dealership_id INT NOT NULL,
	first_name VARCHAR (128) NOT NULL,
	last_name VARCHAR (128) NOT NULL,
	phone VARCHAR (20) NOT NULL,
	job_title VARCHAR (64)
	PRIMARY KEY (employee_id),
	FOREIGN KEY (dealership_id) REFERENCES dealership (dealership_id)
};

CREATE TABLE customer {
	customer_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR (128) NOT NULL,
	last_name VARCHAR (128) NOT NULL,
	email VARCHAR (128) NOT NULL,
	phone VARCHAR (128),
	address VARCHAR (128),
	city VARCHAR (128) ,
	state VARCHAR (128),
	zip VARCHAR (128),
	PRIMARY KEY (customer_id),
};

CREATE TABLE vehicle {
	vehicle_id INT NOT NULL AUTO_INCREMENT,
	year INT NOT NULL,
	dealership_id INT NOT NULL,
	customer_id INT,
	make VARCHAR (128) NOT NULL,
	model VARCHAR (128) NOT NULL,
	color VARCHAR (128) NOT NULL,
	trim VARCHAR (128),
	PRIMARY KEY (vehicle_id),
	FOREIGN KEY (dealership_id) REFERENCES dealership (dealership_id),
	FOREIGN KEY (customer_id) REFRENCES customer (customer_id)
};

CREATE TABLE dealer_customer {
	dealership_id INT NOT NULL,
	customer_id INT NOT NULL,
	FOREIGN KEY (dealership_id) REFERENCES dealership (dealership_id) ON DELETE CASCADE,
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
};
