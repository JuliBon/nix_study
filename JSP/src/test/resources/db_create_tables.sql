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