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

INSERT INTO movies VALUES ('The Dark Knight Rises', 2012, 'PG-13', '20 Jul 2012', '164 min', 'Eight years after the Joker\'s reign of anarchy, the Dark Knight, with the help of the enigmatic Selia, is forced from his imposed exile to save Gotham City, now on the edge of total annihilation, from the brutal guerrilla terrorist Bane.');
INSERT INTO movies VALUES ('Gladiator', 2000, 'R', '05 May 2000', '155 min', 'When a Roman general is betrayed and his family murdered by an emperor\'s corrupt son, he comes to Rome as a gladiator to seek revenge.');
INSERT INTO movies VALUES ('Rambo', 2008, 'R', '25 Jan 2008', '92 min', 'In Thailand, John Rambo joins a group of mercenaries to venture into war-torn Burma, and rescue a group of Christian aid workers who were kidnapped by the ruthless local infantry unit.');
INSERT INTO movies VALUES ('The Avengers', 2012, 'PG-13', '04 May 2012', '143 min', 'Earth\'s mightiest heroes must come together and learn to fight as a team if they are to stop the mischievous Loki and his alien army from enslaving humanity.');
INSERT INTO movies VALUES ('Frozen', 2013, 'PG', '27 Nov 2013', '102 min', 'When the newly crowned Queen Elsa accidentally uses her power to turn things into ice to curse her home in infinite winter, her sister, Anna, teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.');
INSERT INTO movies VALUES ('Iron Man', 2008, 'PG-13', '02 May 2008', '126 min', 'After being held captive in an Afghan cave, an industrialist creates a unique weaponized suit of armor to fight evil.');
INSERT INTO movie_genres VALUES ('The Dark Knight Rises', 'Action');
INSERT INTO movie_actors VALUES ('The Dark Knight Rises', 'Christian Bale');
INSERT INTO movie_actors VALUES ('The Dark Knight Rises', 'Gary Oldman');
INSERT INTO movie_actors VALUES ('The Dark Knight Rises', 'Tom Hardy');
INSERT INTO movie_actors VALUES ('The Dark Knight Rises', 'Joseph Gordon-Levitt');
INSERT INTO movie_genres VALUES ('Gladiator', 'Action');
INSERT INTO movie_actors VALUES ('Gladiator', 'Russell Crowe');
INSERT INTO movie_actors VALUES ('Gladiator', 'Joaquin Phoenix');
INSERT INTO movie_actors VALUES ('Gladiator', 'Connie Nielsen');
INSERT INTO movie_actors VALUES ('Gladiator', 'Oliver Reed');
INSERT INTO movie_genres VALUES ('Rambo', 'War');
INSERT INTO movie_actors VALUES ('Rambo', 'Sylvester Stallone');
INSERT INTO movie_actors VALUES ('Rambo', 'Julie Benz');
INSERT INTO movie_actors VALUES ('Rambo', 'Matthew Marsden');
INSERT INTO movie_actors VALUES ('Rambo', 'Graham McTavish');
INSERT INTO movie_genres VALUES ('The Avengers', 'Science Fiction');
INSERT INTO movie_actors VALUES ('The Avengers', 'Robert Downey Jr.');
INSERT INTO movie_actors VALUES ('The Avengers', 'Chris Evans');
INSERT INTO movie_actors VALUES ('The Avengers', 'Mark Ruffalo');
INSERT INTO movie_actors VALUES ('The Avengers', 'Chris Hemsworth');
INSERT INTO movie_genres VALUES ('Frozen', 'Comedy');
INSERT INTO movie_actors VALUES ('Frozen', 'Kristen Bell');
INSERT INTO movie_actors VALUES ('Frozen', 'Idina Menzel');
INSERT INTO movie_actors VALUES ('Frozen', 'Jonathan Groff');
INSERT INTO movie_actors VALUES ('Frozen', 'Josh Gad');
INSERT INTO movie_genres VALUES ('Iron Man', 'Action');
INSERT INTO movie_actors VALUES ('Iron Man', 'Robert Downey Jr.');
INSERT INTO movie_actors VALUES ('Iron Man', 'Terrence Howard');
INSERT INTO movie_actors VALUES ('Iron Man', 'Jeff Bridges');
INSERT INTO movie_actors VALUES ('Iron Man', 'Gwyneth Paltrow');