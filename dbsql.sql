USE cashguidedb;

CREATE TABLE IF NOT EXISTS payment_types(
	id INTEGER NOT NULL AUTO_INCREMENT,
    type VARCHAR(45) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS categories(
	id INTEGER NOT NULL AUTO_INCREMENT,
    category VARCHAR(45) UNIQUE,
    isDefault BOOLEAN DEFAULT TRUE,
    typeId INTEGER NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(typeId) REFERENCES payment_types(id)
);



CREATE TABLE IF NOT EXISTS users(
	id INTEGER AUTO_INCREMENT NOT NULL,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(45) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS customCategories(
	userId INTEGER NOT NULL,
    categoryId INTEGER NOT NULL,
    FOREIGN KEY(userId) REFERENCES users(id),
    FOREIGN KEY(categoryId) REFERENCES categories(id)
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
	description NVARCHAR(200),
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    budgetId INTEGER NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (categoryId) REFERENCES categories(id),
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




SELECT categories.id 
FROM categories 
JOIN payment_types 
ON categories.typeId=payment_types.id 
WHERE category='mitee' AND type='EXPENSE';



SELECT * FROM payment_types;
SELECT * FROM categories;
SELECT * FROM customCategories;
SELECT * FROM users;
SELECT * FROM payments;
SELECT * FROM budgets;


UPDATE budgets SET balance=100 WHERE id=2;

SELECT category, type
From ((select category, typeId
From categories
where isDefault=true)
UNION
(SELECT category, typeId
FROM categories
JOIN customCategories ON id=categoryId
WHERE userId=1)) temp
JOIN payment_types ON temp.typeId=payment_types.id;










/*
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
*/
