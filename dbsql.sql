USE cashguidedb;

CREATE TABLE IF NOT EXISTS users(
	id INTEGER AUTO_INCREMENT NOT NULL,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(45) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
	balance DOUBLE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS payments(
	id INTEGER AUTO_INCREMENT NOT NULL,
    userId INTEGER NOT NULL,
    name VARCHAR(45),
	type VARCHAR(45) NOT NULL,
    description NVARCHAR(200),
    amount DOUBLE NOT NULL,
    isIncome BOOLEAN NOT NULL,
    date DATE NOT NULL,
    periodInDays INTEGER,
    PRIMARY KEY(id),
    FOREIGN KEY(userId) REFERENCES users(id)
);
