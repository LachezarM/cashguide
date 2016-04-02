USE cashguidedb;

CREATE TABLE IF NOT EXISTS payment_types(
	id INTEGER NOT NULL AUTO_INCREMENT,
    type VARCHAR(45) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS categories(
	id INTEGER NOT NULL AUTO_INCREMENT,
    category VARCHAR(45),
    typeId INTEGER NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(typeId) REFERENCES payment_types(id)
);

CREATE TABLE IF NOT EXISTS users(
	id INTEGER AUTO_INCREMENT NOT NULL,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(45) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS budgets(
	id INTEGER AUTO_INCREMENT NOT NULL,
    userId INTEGER NOT NULL,
	balance DOUBLE DEFAULT 0,
	percentage DOUBLE DEFAULT 1,
    date DATE,
    PRIMARY KEY(id),
	FOREIGN KEY(userId) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS payments(
	id INTEGER AUTO_INCREMENT NOT NULL,
    categoryId INTEGER NOT NULL,
   /* typeId INTEGER NOT NULL,*/
	description NVARCHAR(200),
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    budgetId INTEGER NOT NULL,
   /* periodInDays INTEGER,*/
    PRIMARY KEY(id),
    FOREIGN KEY (categoryId) REFERENCES categories(id),
    /*FOREIGN KEY(typeId) REFERENCES payment_types(id),*/
	FOREIGN KEY(budgetId) REFERENCES budgets(id)

);


INSERT INTO payment_types(type) VALUE('INCOME');
INSERT INTO payment_types(type) VALUE('EXPENSE');

INSERT INTO categories(category, typeId) VALUE('salary', 1);
INSERT INTO categories(category, typeId) VALUE('scholarship',1);
INSERT INTO categories(category, typeId) VALUE('grants', 1);
INSERT INTO categories(category, typeId) VALUE('other', 1);


INSERT INTO categories(category, typeId) VALUE('food', 2);
INSERT INTO categories(category, typeId) VALUE('taxes', 2);
INSERT INTO categories(category, typeId) VALUE('health', 2);
INSERT INTO categories(category, typeId) VALUE('clothes', 2);
INSERT INTO categories(category, typeId) VALUE('transport', 2);
INSERT INTO categories(category, typeId) VALUE('education', 2);
INSERT INTO categories(category, typeId) VALUE('loan', 2);

INSERT INTO users(username, password, email,first_name, last_name)
VALUES ('mite', '123456', 'mite_sp@abv.bg', 'mite', 'spasov');

INSERT INTO budgets(userId, percentage, date) 
VALUES(1, 0.7, NOW());

INSERT INTO payments(categoryId, description, amount, date, budgetId)
VALUES(1,"first salary", 1000, NOW(), 1);

INSERT INTO payments(categoryId, description, amount, date, budgetId)
VALUES(4,"other", 500, NOW(), 1);


INSERT INTO payments(categoryId, description, amount, date, budgetId)
VALUES(5,"first salary", 200, NOW(), 1);

INSERT INTO payments(categoryId, description, amount, date, budgetId)
VALUES(5,"first salary", 200, str_to_date('01,5,2016','%d, %m, %Y'), 2);

INSERT INTO budgets(userId, percentage, date)
VALUE(1, 0.5, str_to_date('01,5,2016','%d, %m, %Y'));
/***************************************************************/

SELECT * FROM payment_types;
SELECT * FROM categories;
SELECT * FROM users;
SELECT * FROM payments;
SELECT * FROM budgets;

SELECT budgets.id, balance, percentage, budgets.date, description, amount, category, type
FROM users
JOIN budgets ON users.id=budgets.userId
JOIN payments ON budgets.id=payments.budgetId
JOIN categories ON payments.categoryId=categories.id
JOIN payment_types ON payment_types.id = payments.typeId
WHERE users.id=1 AND MONTH(budgets.date)=MONTH(CURDATE());

SELECT budgets.id, balance, percentage, budgets.date, description, amount, category, type
FROM users
JOIN budgets ON users.id=budgets.userId
JOIN payments ON budgets.id=payments.budgetId
JOIN categories ON payments.categoryId=categories.id
JOIN payment_types ON payment_types.id = payments.typeId;


SELECT username, password, email, first_name,
last_name, balance, percentage, budgets.date, description, amount, category, type
FROM users
JOIN budgets ON users.id=budgets.userId
JOIN payments ON budgets.id=payments.budgetId
JOIN categories ON payments.categoryId=categories.id
JOIN payment_types ON payment_types.id = payments.typeId
WHERE username='mite';
