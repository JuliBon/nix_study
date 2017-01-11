CREATE TABLE IF NOT EXISTS ROLE
(id LONG PRIMARY KEY,
name VARCHAR(255) UNIQUE NOT NULL,
);

CREATE TABLE IF NOT EXISTS USER
(id LONG PRIMARY KEY AUTO_INCREMENT,
login VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
birthday DATE NOT NULL,
id_role LONG NOT NULL,
FOREIGN KEY (id_role) REFERENCES Role(id)
);

INSERT into ROLE (id, name) VALUES (1, 'ADMIN');
INSERT into ROLE (id, name) VALUES (2, 'USER');

INSERT into USER (login, password, email, firstName, lastName, birthday, id_role) VALUES ('admin', 'Admin123', 'admin@mail.ru',  'admin', 'admin', '1985-05-15', 1);

INSERT into USER (login, password, email, firstName, lastName, birthday, id_role) VALUES ('yulya', 'Pass123', 'yulya@mail.ru',  'yuliya', 'bondarenko', '1993-01-10', 1);
INSERT into USER (login, password, email, firstName, lastName, birthday, id_role) VALUES ('ivan', 'Pass123', 'ivan@mail.ru',  'ivan', 'grozniy', '1530-09-03', 2);



select * from ROLE;
select *  from USER;


