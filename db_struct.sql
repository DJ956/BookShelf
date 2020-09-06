CREATE DATABASE bookshelf;

CREATE TABLE book(
	id SERIAL NOT NULL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	title_kana VARCHAR(100),
	author VARCHAR(100) NOT NULL,
	publishdate INTEGER,
	isbn VARCHAR(20) ,
	cover_path VARCHAR(400),
	index INTEGER
);