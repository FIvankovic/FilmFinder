DROP DATABASE IF EXISTS FilmFinder;
CREATE DATABASE FilmFinder;
USE FilmFinder;

-- Create Movies table
CREATE TABLE Movies (
  movieId INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  release_date DATE,
  duration INT,
  synopsis TEXT,
  cover_image VARCHAR(255)
);

-- Create Actors table
CREATE TABLE Actors (
  actorId INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  date_of_birth DATE,
  profile_image VARCHAR(255)
);

-- Create Directors table
CREATE TABLE Directors (
  directorId INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  date_of_birth DATE,
  profile_image VARCHAR(255)
);

-- Create Users table
CREATE TABLE Users (
  userId INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  registration_date DATE,
  role char(1) NOT NULL DEFAULT ('U')
);

-- Create Genres table
CREATE TABLE Genres (
  genreId INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

-- Create Movie_Genres junction table
CREATE TABLE Movie_Genres (
  movie_genres_id INT AUTO_INCREMENT,
  movie_id INT,
  genre_id INT,
  PRIMARY KEY (movie_genres_id, movie_id, genre_id),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId),
  FOREIGN KEY (genre_id) REFERENCES Genres(genreId)
);

-- Create Movie_Actors junction table
CREATE TABLE Movie_Actors (
  movie_actors_id INT AUTO_INCREMENT,
  movie_id INT,
  actor_id INT,
  PRIMARY KEY (movie_actors_id, movie_id, actor_id),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId),
  FOREIGN KEY (actor_id) REFERENCES Actors(actorId)
);

-- Create Movie_Directors junction table
CREATE TABLE Movie_Directors (
  movie_directors_id INT AUTO_INCREMENT,
  movie_id INT,
  director_id INT,
  PRIMARY KEY (movie_directors_id, movie_id, director_id),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId),
  FOREIGN KEY (director_id) REFERENCES Directors(directorId)
);

-- Create Watch_History table
CREATE TABLE Watch_History (
  watchHistoryId INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  movie_id INT,
  timestamp TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES Users(userId),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId)
);

-- Create User_Ratings table
CREATE TABLE User_Ratings (
  userRatingId INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  movie_id INT,
  rating_score DECIMAL(3,2),
  FOREIGN KEY (user_id) REFERENCES Users(userId),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId)
);

-- Create Reviews table
CREATE TABLE Reviews (
  reviewId INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  movie_id INT,
  review_text TEXT,
  timestamp TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES Users(userId),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId)
);

-- Create Movie_Collections table
CREATE TABLE Movie_Collections (
  movieCollectionId INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  collection_name VARCHAR(255) NOT NULL,
  description TEXT,
  FOREIGN KEY (user_id) REFERENCES Users(userId)
);

-- Create Movie_Collections_Items junction table
CREATE TABLE Movie_Collections_Items (
  movie_collection_id INT AUTO_INCREMENT,
  collection_id INT,
  movie_id INT,
  PRIMARY KEY (movie_collection_id, collection_id, movie_id),
  FOREIGN KEY (collection_id) REFERENCES Movie_Collections(movieCollectionId),
  FOREIGN KEY (movie_id) REFERENCES Movies(movieId)
);


-- INSERTS
INSERT INTO Actors (name, date_of_birth, profile_image)
VALUES
('Al Pacino', '1940-04-25', 'al_pacino.jpg'),
('Robert De Niro', '1943-08-17', 'robert_de_niro.jpg'),
('John Travolta', '1954-02-18', 'john_travolta.jpg'),
('Samuel L. Jackson', '1948-12-21', 'samuel_l_jackson.jpg'),
('Henry Fonda', '1905-05-16', 'henry_fonda.jpg'),
('Liam Neeson', '1952-06-07', 'liam_neeson.jpg'),
('Ralph Fiennes', '1962-12-22', 'ralph_fiennes.jpg'),
('Elijah Wood', '1981-01-28', 'elijah_wood.jpg'),
('Viggo Mortensen', '1958-10-20', 'viggo_mortensen.jpg'),
('Brad Pitt', '1963-12-18', 'brad_pitt.jpg'),
('Edward Norton', '1969-08-18', 'edward_norton.jpg'),
('Tom Hanks', '1956-07-09', 'tom_hanks.jpg'),
('Leonardo DiCaprio', '1974-11-11', 'leonardo_dicaprio.jpg'),
('Christian Bale','1974-01-30', 'christian_bale.jpg'),
('Morgan Freeman', '1937-06-01', 'morgan_freeman.jpg'),
('Florence Pugh', '1996-01-03', 'florence_pugh.jpg'),
('Toni Collete', '1972-11-01', 'toni_collete.jpg');

INSERT INTO Directors (name, date_of_birth, profile_image)
VALUES
('Frank Darabont', '1959-01-28', 'frank_darabont.jpg'),
('Francis Ford Coppola', '1939-04-07', 'francis_ford_coppola.jpg'),
('Quentin Tarantino', '1963-03-27', 'quentin_tarantino.jpg'),
('Sidney Lumet', '1924-06-25', 'sidney_lumet.jpg'),
('Steven Spielberg', '1946-12-18', 'steven_spielberg.jpg'),
('Peter Jackson', '1961-10-31', 'peter_jackson.jpg'),
('David Fincher', '1962-08-28', 'david_fincher.jpg'),
('Robert Zemeckis', '1951-05-14', 'robert_zemeckis.jpg'),
('Christopher Nolan', '1970-07-30', 'christopher_nolan.jpg'),
('Ari Aster','1986-07-15', 'ari_aster.jpg');


-- Insert data into Genres table (if not already added)
INSERT INTO Genres (name)
VALUES ('Crime'), ('Drama'), ('Thriller'), ('Mystery'), ('War'), ('Biography'), ('History'), ('Adventure'), ('Fantasy'), ('Action'), ('Comedy'), ('Romance'), ('Sci-Fi'), ('Horror');

INSERT INTO Users (name, email, password, registration_date, role)
VALUES
('Jane Smith', 'jane.smith@example.com', 'password2', '2020-06-15','a'),
('John Doe', 'john.doe@example.com', 'pass1', '2020-06-15','u'),
('Steve Doe', 'steve.doe@example.com','pass2','2020-07-14','g'),
('Filipa Ivankovic', 'fi2615@g.rit.edu', '123', '2023-04-03','a');


INSERT INTO Movies (title, release_date, duration, synopsis, cover_image)
VALUES('The Dark Knight', '2008-07-18', 152, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'the_dark_knight_cover.jpg'),
('The Shawshank Redemption', '1994-10-14', 142, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'shawshank_redemption.jpg'),
('The Godfather', '1972-03-24', 175, 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'godfather.jpg'),
('The Godfather: Part II', '1974-12-20', 202, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', 'godfather_part2.jpg'),
('Pulp Fiction', '1994-10-14', 154, 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'pulp_fiction.jpg'),
('12 Angry Men', '1957-04-10', 96, 'A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.', '12_angry_men.jpg'),
('Schindler''s List', '1993-12-15', 195, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'schindlers_list.jpg'),
('The Lord of the Rings: The Return of the King', '2003-12-17', 201, 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'return_of_the_king.jpg'),
('Fight Club', '1999-10-15', 139, 'An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.', 'fight_club.jpg'),
('Forrest Gump', '1994-07-06', 142, 'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate and other historical events unfold through the perspective of an Alabama man with an IQ of 75.', 'forrest_gump.jpg'),
('Inception', '2010-07-16', 148, 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.', 'inception.jpg'),
('Midsommar','2019-07-03', 148,'A couple travels to Northern Europe to visit a rural hometowns fabled Swedish mid-summer festival. What begins as an idyllic retreat quickly devolves into an increasingly violent and bizarre competition at the hands of a pagan cult.','midsommar.jpg'),
('Hereditary', '2018-06-08', 127, 'A grieving family is haunted by tragic and disturbing occurrences.', 'hereditary.jpg');



INSERT INTO Movie_Genres (movie_id, genre_id)
VALUES
(1, 10),(1, 1),(1, 2),(1, 3), -- The Dark Knight - Action, Crime, Drama, Thriller
(2, 1), (2, 2), -- The Shawshank Redemption: Crime, Drama
(3, 1), (3, 2), -- The Godfather: Crime, Drama
(4, 1), (4, 2), -- The Godfather: Part II: Crime, Drama
(5, 1), (5, 2), (5, 3), -- Pulp Fiction: Crime, Drama, Thriller
(6, 2), (6, 4), -- 12 Angry Men: Drama, Mystery
(7, 2), (7, 5), (7, 6), (7, 7), -- Schindler's List: Drama, War, Biography, History
(8,10), (8, 2), (8, 8), (8, 9), -- The Lord of the Rings: The Return of the King: Action, Drama, Adventure, Fantasy
(9, 2), -- Fight Club: Drama
(10, 2), (10, 12), -- Forrest Gump: Drama, Romance
(11, 10), (11, 8), (11, 13), (11, 3), -- Inception: Action, Adventure, Sci-Fi, Thriller
(12, 2), (12, 14), (12, 4), (12, 3), -- Midsommar: Drama, Horror, Mystery, Thriller
(13, 2), (13, 14), (13, 4), (13, 3); -- Hereditary: Drama, Horror, Mystery, Thriller

INSERT INTO Movie_Actors (movie_id, actor_id)
VALUES
(1, 14), -- The Dark Knight
(2, 1), (2, 15), -- The Shawshank Redemption: Tim Robbins, Morgan Freeman
(3, 1), -- The Godfather: Al Pacino
(4, 1), (4, 2), -- The Godfather: Part II: Al Pacino, Robert De Niro
(5, 3), (5, 4), -- Pulp Fiction: John Travolta, Samuel L. Jackson
(6, 5), -- 12 Angry Men: Henry Fonda
(7, 6), (7, 7), -- Schindler's List: Liam Neeson, Ralph Fiennes
(8, 8), (8, 9), -- The Lord of the Rings: The Return of the King: Elijah Wood, Viggo Mortensen
(9, 10), (9, 11), -- Fight Club: Brad Pitt, Edward Norton
(10, 12), -- Forrest Gump: Tom Hanks
(11, 13), -- Inception: Leonardo DiCaprio
(12, 16), -- Midsommar: Florence Pugh 
(13, 17); -- Hereditary: Ton Collete


INSERT INTO Movie_Directors (movie_id, director_id)
VALUES
(1, 9), -- Dark Knight
(2, 1), -- The Shawshank Redemption: Frank Darabont
(3, 2), -- The Godfather: Francis Ford Coppola
(4, 2), -- The Godfather: Part II: Francis Ford Coppola
(5, 3), -- Pulp Fiction: Quentin Tarantino
(6, 4), -- 12 Angry
(7, 5), -- Schindler's List: Steven Spielberg
(8, 6), -- The Lord of the Rings: The Return of the King: Peter Jackson
(9, 7), -- Fight Club: David Fincher
(10, 8), -- Forrest Gump: Robert Zemeckis
(11, 9), -- Inception: Christopher Nolan
(12, 10), -- Midsomar: Ari Aster
(13, 10); -- Hereditary: Ari Aster


INSERT INTO Watch_History (user_id, movie_id, timestamp)
VALUES
(1, 1, '2022-01-15 19:30:00'),
(1, 2, '2022-01-22 21:45:00'),
(2, 3, '2022-01-10 20:15:00'),
(3, 5, '2020-09-09 11:00:00'),
(4, 1, '2015-05-12 19:16:00'),
(4, 8, '2008-07-08 22:00:00');

-- Insert data into User_Ratings table
INSERT INTO User_Ratings (user_id, movie_id, rating_score)
VALUES
(1, 1, 5),
(1, 2, 4.5),
(2, 3, 5);

INSERT INTO Reviews (user_id, movie_id, review_text, timestamp)
VALUES
(1, 1, 'The Shawshank Redemption is an amazing movie!', '2022-01-16 15:30:00'),
(1, 2, 'The Godfather is a true classic.', '2022-01-23 10:45:00'),
(2, 3, 'The Godfather: Part II is even better than the first one.', '2022-01-11 22:15:00');

INSERT INTO Movie_Collections (user_id, collection_name, description)
VALUES
(1, 'Top Rated', 'My personal top rated movies'),
(2, 'Classics', 'My favorite classic movies');
