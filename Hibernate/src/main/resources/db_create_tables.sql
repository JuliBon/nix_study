CREATE TABLE IF NOT EXISTS Role
(id LONG PRIMARY KEY,
name VARCHAR(255) UNIQUE NOT NULL,
);

CREATE TABLE IF NOT EXISTS User
(id LONG PRIMARY KEY AUTO_INCREMENT,
login VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
birthday DATE NOT NULL,
id_role INT NOT NULL,
FOREIGN KEY (id_role) REFERENCES Role(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT into Role (id, name) VALUES (1, 'Admin');
INSERT into Role (id, name) VALUES (2, 'User');

INSERT into User (login, password, email, firstName, lastName, birthday, id_role) VALUES ('yulya', '12345', 'yulya@mail.ru',  'yuliya', 'bondarenko', '1993-01-10', 1);
INSERT into User (login, password, email, firstName, lastName, birthday, id_role) VALUES ('ivan', '98765', 'ivan@mail.ru',  'ivan', 'grozniy', '1530-09-03', 2);
