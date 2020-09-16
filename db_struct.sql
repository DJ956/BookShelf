CREATE DATABASE bookshelf;

CREATE TABLE genre_tbl(
	id SERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(40) NOT NULL
);

INSERT INTO genre_tbl(name) VALUES('マンガ');
INSERT INTO genre_tbl(name) VALUES('小説');
INSERT INTO genre_tbl(name) VALUES('技術書');
INSERT INTO genre_tbl(name) VALUES('文学');
INSERT INTO genre_tbl(name) VALUES('SF');
INSERT INTO genre_tbl(name) VALUES('ミステリー ・ サスペンス');
INSERT INTO genre_tbl(name) VALUES('歴史');


CREATE TABLE book(
	id SERIAL NOT NULL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	title_kana VARCHAR(100),
	author VARCHAR(100) NOT NULL,
	publishdate INTEGER,
	isbn VARCHAR(20) ,
	cover_path VARCHAR(400),
	index INTEGER,
	genre_id INTEGER NOT NULL,
	FOREIGN KEY(genre_id) REFERENCES genre_tbl(id) ON DELETE CASCADE ON UPDATE CASCADE
);