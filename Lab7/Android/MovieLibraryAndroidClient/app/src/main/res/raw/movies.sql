DROP TABLE IF EXISTS movies;
CREATE TABLE movies (
	title VARCHAR(255) PRIMARY KEY,
	year INTEGER,
	rated VARCHAR(255),
	released VARCHAR(255),
	runtime VARCHAR(255),
	plot TEXT
);

DROP TABLE IF EXISTS movie_actors;
CREATE TABLE movie_actors (
	title VARCHAR(255),
	actor VARCHAR(255),
	FOREIGN KEY (title) REFERENCES movies (title)
);

DROP TABLE IF EXISTS movie_genres;
CREATE TABLE movie_genres (
	title VARCHAR(255),
	genre VARCHAR(255),
	FOREIGN KEY (title) REFERENCES movies (title)
);

INSERT INTO movies VALUES ("Test", 2016, "5 starts", "Sometime", "Some time", "Some stuff happens");
INSERT INTO movie_actors VALUES ("Test", "Person");
INSERT INTO movie_genres VALUES ("Test", "Action");