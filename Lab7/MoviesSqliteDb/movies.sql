DROP TABLE IF EXISTS movies;
CREATE TABLE movies (
	title VARCHAR(255) PRIMARY KEY,
	year INTEGER,
	rated VARCHAR(255),
	released VARCHAR(255),
	runtime VARCHAR(255),
	plot TEXT,
	created DATETIME DEFAULT CURRENT_TIMESTAMP
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

INSERT INTO movies (title, year, rated, released, runtime, plot) VALUES
    ('The Dark Knight Rises', 2012, 'PG-13', '20 Jul 2012', '164 min', 'Eight years after the Joker''s reign of anarchy, the Dark Knight, with the help of the enigmatic Selia, is forced from his imposed exile to save Gotham City, now on the edge of total annihilation, from the brutal guerrilla terrorist Bane.'),
	('Gladiator', 2000, 'R', '05 May 2000', '155 min', 'When a Roman general is betrayed and his family murdered by an emperor''s corrupt son, he comes to Rome as a gladiator to seek revenge.'),
	('Rambo', 2008, 'R', '25 Jan 2008', '92 min', 'In Thailand, John Rambo joins a group of mercenaries to venture into war-torn Burma, and rescue a group of Christian aid workers who were kidnapped by the ruthless local infantry unit.'),
	('The Avengers', 2012, 'PG-13', '04 May 2012', '143 min', 'Earth''s mightiest heroes must come together and learn to fight as a team if they are to stop the mischievous Loki and his alien army from enslaving humanity.'),
	('Frozen', 2013, 'PG', '27 Nov 2013', '102 min', 'When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.'),
	('Iron Man', 2008, 'PG-13', '02 May 2008', '126 min', 'After being held captive in an Afghan cave, an industrialist creates a unique weaponized suit of armor to fight evil.');

INSERT INTO movie_genres VALUES
	('The Dark Knight Rises', 'Action'),
	('Gladiator', 'Action'),
	('Rambo', 'War'),
	('The Avengers', 'Science Fiction'),
	('Frozen', 'Comedy'),
	('Iron Man', 'Action');

INSERT INTO movie_actors VALUES
	('The Dark Knight Rises', 'Christian Bale'),
	('The Dark Knight Rises', 'Gary Oldman'),
	('The Dark Knight Rises', 'Tom Hardy'),
	('The Dark Knight Rises', 'Joseph Gordon-Levitt'),
	('Gladiator', 'Russell Crowe'),
	('Gladiator', 'Joaquin Phoenix'),
	('Gladiator', 'Connie Nielsen'),
	('Gladiator', 'Oliver Reed'),
	('Rambo', 'Sylvester Stallone'),
	('Rambo', 'Julie Benz'),
	('Rambo', 'Matthew Marsden'),
	('Rambo', 'Graham McTavish'),
	('The Avengers', 'Robert Downey Jr.'),
	('The Avengers', 'Chris Evans'),
	('The Avengers', 'Mark Ruffalo'),
	('The Avengers', 'Chris Hemsworth'),
	('Frozen', 'Kristen Bell'),
	('Frozen', 'Idina Menzel'),
	('Frozen', 'Jonathan Groff'),
	('Frozen', 'Josh Gad'),
	('Iron Man', 'Robert Downey Jr.'),
	('Iron Man', 'Terrence Howard'),
	('Iron Man', 'Jeff Bridges'),
	('Iron Man', 'Gwyneth Paltrow');