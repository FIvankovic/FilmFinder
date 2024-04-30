package Controller;

import java.text.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
//import java.io.*;
import Model.*;

public class DataManipulator {

    // Attributes
    Movies movies;
    Genres genres;
    Actors actors;
    Users users;
    Directors directors;
    Movie_Genres movie_genres;
    Movie_Actors movie_actors;
    Movie_Directors movie_directors;
    Movie_Collections movie_collections;
    Movie_Collections_Items movie_collections_items;
    Watch_History watch_history;
    Reviews reviews;
    User_Ratings user_ratings;

    // Constructor
    public DataManipulator() {
        movies = new Movies();
        genres = new Genres();
        actors = new Actors();
        users = new Users();
        directors = new Directors();
        movie_genres = new Movie_Genres();
        movie_actors = new Movie_Actors();
        movie_directors = new Movie_Directors();
        movie_collections = new Movie_Collections();
        movie_collections_items = new Movie_Collections_Items();
        watch_history = new Watch_History();
        reviews = new Reviews();
        user_ratings = new User_Ratings();
    }// DataManipulator constructor end

    // -------------MOVIES TABLE------------
    public Movies getMovie(int _id) {
        Movies movieObject = new Movies();

        ArrayList<ArrayList<String>> dataList = movies.fetchMovieById(_id);
        System.out.println(dataList);

        // Should iterate only once
        for (ArrayList<String> data : dataList) {
            int movieId = Integer.parseInt(data.get(0));
            String title = data.get(1);
            String release_date = data.get(2);
            int duration = Integer.parseInt(data.get(3));
            String synopsis = data.get(4);
            String image = data.get(5);

            movieObject.setMovieId(movieId);
            movieObject.setTitle(title);
            movieObject.setReleaseDate(release_date);
            movieObject.setDuration(duration);
            movieObject.setSynopsis(synopsis);
            movieObject.setCoverImage(image);
        }

        return movieObject;
    }// getMovie method end

    public List<Movies> getAllMovies() {
        ArrayList<ArrayList<String>> dataList = movies.fetchAllMovies();
        System.out.println(dataList);

        // List
        List<Movies> moviesList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int movieId = Integer.parseInt(data.get(0));
            String title = data.get(1);
            String release_date = data.get(2);
            int duration = Integer.parseInt(data.get(3));
            String synopsis = data.get(4);
            String image = data.get(5);

            Movies movieObject = new Movies(movieId, title, release_date, duration, synopsis, image);
            moviesList.add(movieObject);

        }
        return moviesList;
    }// getAllMovies method end

    public boolean deleteMovie(int _id) {
        // Due to foreign key policies, the movie's associations need to be deleted
        this.deleteWatch_HistoryForMovieId(_id);
        this.deleteMovie_Collections_ItemsForMovieId(_id);
        this.deleteMovie_GenresForMovieId(_id);
        this.deleteMovie_ActorsForMovieId(_id);
        this.deleteMovie_DirectorsForMovieId(_id);
        this.deleteReviewsForMovieId(_id);
        this.deleteUser_RatingsForMovieId(_id);
        return movies.deleteMovie(_id);
    }

    public boolean insertMovie(String _title, String _release_date, int _duration, String _synopsis,
                               String cover_image) {
        return movies.insertMovie(_title, _release_date, _duration, _synopsis, cover_image);
    }

    public boolean updateMovie(int _MovieId, String _title, String _release_date, int _duration, String _synopsis,
                               String _cover_image) {
        return movies.updateMovie(_MovieId, _title, _release_date, _duration, _synopsis, _cover_image);
    }

    // ------------USERS TABLE-----------
    public Users getUser(int _id) {
        Users userObject = new Users();

        ArrayList<ArrayList<String>> dataList = users.fetchUserById(_id);
        System.out.println(dataList);

        for (ArrayList<String> data : dataList) {
            int userId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String email = data.get(2);
            String password = data.get(3);
            String registration_date = data.get(4);
            String role = data.get(5);

            userObject.setUserId(userId);
            userObject.setName(name);
            userObject.setEmail(email);
            userObject.setPassword(password);
            userObject.setRegistrationDate(registration_date);
            userObject.setRole(role);
        }

        return userObject;
    }// getUser method end

    // Returns all registered users from DB
    public List<Users> getAllUsers() {
        ArrayList<ArrayList<String>> dataList = users.fetchAllUsers();
        System.out.println(dataList);

        // List
        List<Users> usersList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int userId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String email = data.get(2);
            String password = data.get(3);
            String registration_date = data.get(4);
            String role = data.get(5);

            Users userObject = new Users(userId, name, email, password, registration_date, role);
            usersList.add(userObject);
        }

        return usersList;
    }// getAllUsers method end

    public boolean deleteUser(int _id) {
        // Due to foreign key policies, the user's associations need to be deleted
        this.deleteMovie_CollectionsForUserId(_id);
        this.deleteWatch_HistoryForUserId(_id);
        this.deleteReviewsForUserId(_id);
        this.deleteUser_RatingsForUserId(_id);
        return users.deleteUser(_id);
    }

    public boolean insertUser(String _name, String _email, String _password, String _registration_date, String _role) {
        return users.insertUser(_name, _email, _password, _registration_date, _role);
    }

    public boolean updateUser(String _name, String _email, String _password, String _registration_date, String _role) {
        return users.updateUser(_name, _email, _password, _registration_date, _role);
    }

    // ------------GENRES TABLE-----------
    public Genres getGenre(int _id) {
        Genres genreObject = new Genres();

        ArrayList<ArrayList<String>> dataList = genres.fetchGenreById(_id);
        System.out.println(dataList);

        for (ArrayList<String> data : dataList) {
            int genreId = Integer.parseInt(data.get(0));
            String name = data.get(1);

            genreObject = new Genres(genreId, name);
        }
        return genreObject;
    }

    public int getGenreIdByGenreName(String _name) {
        ArrayList<ArrayList<String>> dataList = genres.fetchGenreIdByGenreName(_name);
        System.out.println(dataList);

        int genreId = 0;
        for (ArrayList<String> data : dataList) {
            genreId = Integer.parseInt(data.get(0));
        }
        return genreId;
    }

    public List<Genres> getAllGenres() {
        ArrayList<ArrayList<String>> dataList = genres.fetchAllGenres();
        System.out.println(dataList);

        // List
        List<Genres> genresList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int genreId = Integer.parseInt(data.get(0));
            String name = data.get(1);

            Genres genreObject = new Genres(genreId, name);
            genresList.add(genreObject);
        }
        return genresList;
    }// getGenres method end

    public boolean deleteGenre(int _id) {
        // Due to foreign key policies, the genres associations need to be deleted
        this.deleteMovie_GenresForGenreId(_id);
        return genres.deleteGenre(_id);
    }

    public boolean insertGenre(String _name) {
        return genres.insertGenre(_name);
    }

    public boolean updateGenre(int _id, String _name) {
        return genres.updateGenre(_id, _name);
    }

    // ------------DIRECTORS TABLE-----------
    public Directors getDirector(int _id) {
        Directors fDirector = new Directors();

        ArrayList<ArrayList<String>> dataList = directors.fetchDirectorById(_id);
        System.out.println(dataList);

        // Loop prevents of error occuring if the dataList is empty or null, but it will
        // iterate only once due to the fetchDirectorById method
        for (ArrayList<String> data : dataList) {
            int directorId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String dateOfBirth = data.get(2);
            String profileImage = data.get(3);
            fDirector.setDirectorId(directorId);
            fDirector.setName(name);
            fDirector.setDateOfBirth(dateOfBirth);
            fDirector.setProfileImage(profileImage);
        }

        return fDirector;
    }

    public List<Directors> getAllDirectors() {
        ArrayList<ArrayList<String>> dataList = directors.fetchAllDirectors();
        System.out.println(dataList);

        // List
        List<Directors> directorsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int directorId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String dateOfBirth = data.get(2);
            String profileImage = data.get(3);

            Directors director = new Directors(directorId, name, dateOfBirth, profileImage);
            directorsList.add(director);
        }

        return directorsList;
    }// getAllDirectors method end

    public boolean insertDirector(String _name, String _date_of_birth, String _profile_image) {
        return directors.insertDirector(_name, _date_of_birth, _profile_image);
    }

    public boolean updateDirector(String _name, String _date_of_birth, String _profile_image) {
        return directors.updateDirector(0, _name, _date_of_birth, _profile_image);
    }

    public boolean deleteDirector(int _id) {
        this.deleteMovie_DirectorsForDirectorId(_id);
        return directors.deleteDirector(_id);
    }

    // ------------ACTORS TABLE-----------
    public Actors getActor(int _id) {
        Actors fActor = new Actors();

        ArrayList<ArrayList<String>> dataList = actors.fetchActorById(_id);
        System.out.println(dataList);

        // Should iterate only once
        for (ArrayList<String> data : dataList) {
            int actorId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String dateOfBirth = data.get(2);
            String profileImage = data.get(3);
            fActor.setActorId(actorId);
            fActor.setName(name);
            fActor.setDateOfBirth(dateOfBirth);
            fActor.setProfileImage(profileImage);
        }

        return fActor;
    }

    public List<Actors> getAllActors() {
        ArrayList<ArrayList<String>> dataList = actors.fetchAllActors();
        System.out.println(dataList);

        // List
        List<Actors> actorsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int actorId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String dateOfBirth = data.get(2);
            String profileImage = data.get(3);

            Actors actor = new Actors(actorId, name, dateOfBirth, profileImage);
            actorsList.add(actor);
        }

        return actorsList;
    }// getAllActors method end

    public boolean deleteActor(int _id) {
        this.deleteMovie_ActorsForActorId(_id);
        return actors.deleteActor(_id);
    }

    public boolean insertActor(String _name, String _date_of_birth, String _profile_image) {
        return actors.insertActor(_name, _date_of_birth, _profile_image);
    }

    public boolean updateActor(int _ActorId, String _name, String _date_of_birth, String _profile_image) {
        return actors.updateActor(_ActorId, _name, _date_of_birth, _profile_image);
    }

    // ------------MOVIE_GENRES TABLE-----------

    // Returns all genres for a specific movie (example, all applicable genres for
    // "The Godfather" movie)
    public List<Genres> getAllGenresForMovie(int _id) {
        ArrayList<ArrayList<String>> dataList = movie_genres.fetchAllGenresForMovieId(_id);
        System.out.println(dataList);

        // List
        List<Genres> genresList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int genreId = Integer.parseInt(data.get(0));
            String name = data.get(1);

            Genres genreObject = new Genres(genreId, name);
            genresList.add(genreObject);
        }
        return genresList;
    }// getAllGenresForMovie method end

    // Returns all movie entries for a specific Genre (example. all movies labeled
    // as part of the "Thriller" genre)
    public List<Movies> getAllMoviesForGenre(int _id) {
        ArrayList<ArrayList<String>> dataList = movie_genres.fetchAllMoviesForGenreId(_id);
        System.out.println(dataList);

        // List
        List<Movies> moviesList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int movieId = Integer.parseInt(data.get(0));
            String title = data.get(1);
            String release_date = data.get(2);
            int duration = Integer.parseInt(data.get(3));
            String synopsis = data.get(4);
            String image = data.get(5);

            Movies movieObject = new Movies(movieId, title, release_date, duration, synopsis, image);
            moviesList.add(movieObject);

        }
        return moviesList;
    }// getAllGenresForMovie method end

    // Add a new connection between a movies & genres entry. NOTE: Both the genre
    // and the movie's ids need to be already existing in the DB for this to work.
    public boolean insertMovies_Genres(int _movie_id, int _genre_id) {
        return movie_genres.insertMovie_Genres(_movie_id, _genre_id);
    }

    // Updates one row corresponding to the _movie_genres_id. NOTE: Both movie_id
    // and genre_id must exist in the db to work
    public boolean updateMovie_Genres(int _movie_genres_id, int _movie_id, int _genre_id) {
        return movie_genres.updateMovie_Genres(_movie_genres_id, _movie_id, _genre_id);
    }

    // Deletes a single row from the table by targetting the auto_incrementing id of
    // the table
    public boolean deleteMovie_Genres(int _movie_genres_id) {
        return movie_genres.deleteMovie_Genres(_movie_genres_id);
    }

    // Will delete all movie & genre connections for a particular movie id. NOTE:
    // Will work even if those ids dont exist, but nothing gets deleted
    public boolean deleteMovie_GenresForMovieId(int _movie_id) {
        return movie_genres.deleteMovie_GenresForMovieId(_movie_id);
    }

    // Will delete all movie & genre connections for a particular genre id. NOTE:
    // Will work even if those ids dont exist, but nothing gets deleted
    public boolean deleteMovie_GenresForGenreId(int _genre_id) {
        return movie_genres.deleteMovie_GenresForGenreId(_genre_id);
    }

    // ------------MOVIE_ACTORS TABLE-----------

    public List<Movies> getAllMoviesForActor(int _actor_id) {
        ArrayList<ArrayList<String>> dataList = movie_actors.fetchAllMoviesForActorId(_actor_id);
        System.out.println(dataList);

        // List
        List<Movies> moviesList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int movieId = Integer.parseInt(data.get(0));
            String title = data.get(1);
            String release_date = data.get(2);
            int duration = Integer.parseInt(data.get(3));
            String synopsis = data.get(4);
            String image = data.get(5);

            Movies movieObject = new Movies(movieId, title, release_date, duration, synopsis, image);
            moviesList.add(movieObject);

        }
        return moviesList;
    }// getAllMoviesForActor

    public List<Actors> getAllActorsForMovie(int _movie_id) {
        ArrayList<ArrayList<String>> dataList = movie_actors.fetchAllActorsForMovieId(_movie_id);
        System.out.println(dataList);

        // List
        List<Actors> actorsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int actorId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String dateOfBirth = data.get(2);
            String profileImage = data.get(3);

            Actors actor = new Actors(actorId, name, dateOfBirth, profileImage);
            actorsList.add(actor);
        }

        return actorsList;

    }// getAllActorsForMovie method end

    public boolean updateMovie_Actors(int _movie_actors_id, int _movie_id, int _actor_id) {
        return movie_actors.updateMovie_Actors(_movie_actors_id, _movie_id, _actor_id);
    }

    public boolean insertMovie_Actors(int _movie_id, int _actor_id) {
        return movie_actors.insertMovie_Genres(_movie_id, _actor_id);

    }

    public boolean deleteMovie_Actors(int _movie_actor_id) {
        return movie_actors.deleteMovie_Actors(_movie_actor_id);
    }

    public boolean deleteMovie_ActorsForMovieId(int _movie_id) {
        return movie_actors.deleteMovie_ActorsForMovieId(_movie_id);
    }

    public boolean deleteMovie_ActorsForActorId(int _actor_id) {
        return movie_actors.deleteMovie_ActorsForActorId(_actor_id);
    }

    // ------------MOVIE_DIRECTORS TABLE-----------

    public List<Directors> getAllDirectorsForMovie(int _movie_id) {
        ArrayList<ArrayList<String>> dataList = movie_directors.fetchAllDirectorsForMovieId(_movie_id);
        System.out.println(dataList);

        // List
        List<Directors> directorsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int directorId = Integer.parseInt(data.get(0));
            String name = data.get(1);
            String dateOfBirth = data.get(2);
            String profileImage = data.get(3);

            Directors director = new Directors(directorId, name, dateOfBirth, profileImage);
            directorsList.add(director);
        }

        return directorsList;
    }

    public List<Movies> getAllMoviesForDirector(int _director_id) {
        ArrayList<ArrayList<String>> dataList = movie_directors.fetchAllMoviesForDirectorId(_director_id);
        System.out.println(dataList);

        // List
        List<Movies> moviesList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int movieId = Integer.parseInt(data.get(0));
            String title = data.get(1);
            String release_date = data.get(2);
            int duration = Integer.parseInt(data.get(3));
            String synopsis = data.get(4);
            String image = data.get(5);

            Movies movieObject = new Movies(movieId, title, release_date, duration, synopsis, image);
            moviesList.add(movieObject);

        }
        return moviesList;
    }

    public boolean insertMovie_Directors(int _movie_id, int _director_id) {
        return movie_directors.insertMovie_Directors(_movie_id, _director_id);
    }

    public boolean updateMovie_Directors(int _movie_directors_id, int _movie_id, int _director_id) {
        return movie_directors.updateMovie_Directors(_movie_directors_id, _movie_id, _director_id);
    }

    public boolean deleteMovie_Directors(int _movie_directors_id) {
        return movie_directors.deleteMovie_Directors(_movie_directors_id);
    }

    public boolean deleteMovie_DirectorsForMovieId(int _movie_id) {
        return movie_directors.deleteMovie_DirectorsForMovieId(_movie_id);
    }

    public boolean deleteMovie_DirectorsForDirectorId(int _director_id) {
        return movie_directors.deleteMovie_DirectorsForDirectorId(_director_id);
    }

    // ------------ REVIEWS TABLE-----------

    // Fetches only 1 review according to the provded id
    public Reviews getReview(int _id) {
        Reviews reviewObject = new Reviews();

        ArrayList<ArrayList<String>> dataList = reviews.fetchReviewById(_id);
        System.out.println(dataList);

        // Should iterate only once
        for (ArrayList<String> data : dataList) {
            int reviewId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            int movie_Id = Integer.parseInt(data.get(2));
            String review_text = data.get(3);
            String timestamp = data.get(4);

            reviewObject.setReviewId(reviewId);
            reviewObject.setMovieId(movie_Id);
            reviewObject.setUserId(user_Id);
            reviewObject.setReview_Text(review_text);
            reviewObject.setTimestamp(timestamp);
        }
        return reviewObject;
    }// getReview method end

    // Fetches only revies that correspond to the movieId
    public List<Reviews> getAllReviewsForMovie(int _movieId) {
        ArrayList<ArrayList<String>> dataList = reviews.fetchAllReviewsForMovie(_movieId);
        System.out.println(dataList);

        // List
        List<Reviews> reviewsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int reviewId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            int movie_Id = Integer.parseInt(data.get(2));
            String review_text = data.get(3);
            String timestamp = data.get(4);

            Reviews review = new Reviews(reviewId, user_Id, movie_Id, review_text, timestamp);
            reviewsList.add(review);
        }
        return reviewsList;
    }// getAllReviewsForMovie method end

    // Gets EVERY review that exists in the REview tablee
    public List<Reviews> getAllReviews() {
        ArrayList<ArrayList<String>> dataList = reviews.fetchAllReviews();
        System.out.println(dataList);

        // List
        List<Reviews> reviewsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int reviewId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            int movie_Id = Integer.parseInt(data.get(2));
            String review_text = data.get(3);
            String timestamp = data.get(4);

            Reviews review = new Reviews(reviewId, user_Id, movie_Id, review_text, timestamp);
            reviewsList.add(review);
        }
        return reviewsList;
    }// getAllReviews method end

    public boolean insertReview(int _user_id, int _movie_id, String _review_text, Timestamp _timestamp) {
        return reviews.insertReview(_user_id, _movie_id, _review_text, _timestamp);
    }

    public boolean updateReview(int _review_id, int _user_id, int _movie_id, String _review_text,
                                Timestamp _timestamp) {
        return reviews.updateReview(_review_id, _user_id, _movie_id, _review_text, _timestamp);
    }// updateReview method end

    // Deletes 1 Review by targetting its primary key
    public boolean deleteReview(int _reviewId) {
        return reviews.deleteReview(_reviewId);
    }// deleteReview method end

    // Deletes all the Reviews for the associated movie
    public boolean deleteReviewsForMovieId(int _movie_id) {
        return reviews.deleteReviewsForMovieId(_movie_id);
    }// deleteReviewsForMovieId method end

    // Deletes all the reviews for the associated user
    public boolean deleteReviewsForUserId(int _user_id) {
        return reviews.deleteReviewsForUserId(_user_id);
    }// deleteReviewsForUserId method

    // ------------ USER_RATINGS TABLE-----------

    // Returns user rating specified by the passed in int
    public User_Ratings getUser_Rating(int _userRatingId) {
        User_Ratings user_RatingsObject = new User_Ratings();

        ArrayList<ArrayList<String>> dataList = user_ratings.fetchUser_RatingById(_userRatingId);
        System.out.println(dataList);

        for (ArrayList<String> data : dataList) {
            int userRatingId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            int movie_Id = Integer.parseInt(data.get(2));
            BigDecimal rating_score = new BigDecimal(data.get(3));

            user_RatingsObject.setUserRatingId(userRatingId);
            user_RatingsObject.setUserId(user_Id);
            user_RatingsObject.setMovieId(movie_Id);
            user_RatingsObject.setRatingScore(rating_score.doubleValue());
        }

        return user_RatingsObject;
    }// getUser_Rating method end

    public List<User_Ratings> getAllUser_Ratings() {
        ArrayList<ArrayList<String>> dataList = user_ratings.fetchAllUser_Ratings();
        System.out.println(dataList);

        // List
        List<User_Ratings> ratingsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int userRatingId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            int movie_Id = Integer.parseInt(data.get(2));
            BigDecimal rating_score = new BigDecimal(data.get(3));

            User_Ratings usr = new User_Ratings(userRatingId, user_Id, movie_Id, rating_score.doubleValue());
            ratingsList.add(usr);
        }
        return ratingsList;
    }// getAllUser_Ratings method end

    // Gets all user ratings for the specified movie
    public List<User_Ratings> getAllUser_RatingsForMovieId(int _movie_id) {
        ArrayList<ArrayList<String>> dataList = user_ratings.fetchAllUser_RatingsForMovieId(_movie_id);
        System.out.println(dataList);

        // List
        List<User_Ratings> ratingsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int userRatingId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            int movie_Id = Integer.parseInt(data.get(2));
            BigDecimal rating_score = new BigDecimal(data.get(3));

            User_Ratings usr = new User_Ratings(userRatingId, user_Id, movie_Id, rating_score.doubleValue());
            ratingsList.add(usr);
        }
        return ratingsList;
    }// getAllUser_RatingsForMovieId method end

    // Returns the average rating for a particular movie
    public double getAvgRatingScoreForMovieId(int _movie_id) {
        double avg = 0.00;
        List<User_Ratings> dataList = this.getAllUser_RatingsForMovieId(_movie_id);

        double sum = 0.00;
        for (int i = 0; i < dataList.size(); i++) {
            sum += dataList.get(i).getRatingScore();
        }
        avg = dataList.size() > 0 ? sum / dataList.size() : 0.00d;
        DecimalFormat df = new DecimalFormat("#.##");
        avg = Double.valueOf(df.format(avg));

        System.out.println("The avg rating of movie_id " + _movie_id + " is " + avg);
        return avg;
    }// getAvgRatingScoreForMovie method end

    public boolean insertUser_Rating(int _user_id, int _movie_id, double _rating_score) {
        return user_ratings.insertUser_Rating(_user_id, _movie_id, _rating_score);
    }

    public boolean updateUser_Rating(int _userRatingId, int _user_id, int _movie_id, double _rating_score) {
        return user_ratings.updateUser_Rating(_userRatingId, _user_id, _movie_id, _rating_score);
    }

    public boolean deleteUser_Rating(int _userRatingId) {
        return user_ratings.deleteUser_Rating(_userRatingId);
    }

    public boolean deleteUser_RatingsForMovieId(int _movie_id) {
        return user_ratings.deleteUser_RatingsForMovieId(_movie_id);
    }

    public boolean deleteUser_RatingsForUserId(int _user_id) {
        return user_ratings.deleteUser_RatingsForUserId(_user_id);
    }

    // ------------ WATCH_HISTORY TABLE-----------
    public List<Movies> getWatch_HistoryForUserId(int _user_id) {
        ArrayList<ArrayList<String>> dataList = watch_history.fetchWatch_HistoryForUserId(_user_id);
        System.out.println(dataList);

        // List
        List<Movies> moviesList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int movieId = Integer.parseInt(data.get(0));
            String title = data.get(1);
            String release_date = data.get(2);
            int duration = Integer.parseInt(data.get(3));
            String synopsis = data.get(4);
            String image = data.get(5);

            Movies movieObject = new Movies(movieId, title, release_date, duration, synopsis, image);
            moviesList.add(movieObject);

        }
        return moviesList;
    }// getWatch_HistoryForUserId

    public boolean insertWatch_History(int _user_id, int _movie_id, Timestamp _timestamp) {
        return watch_history.insertWatch_History(_user_id, _movie_id, _timestamp);
    }

    public boolean updateWatch_History(int _watchHistoryId, int _user_id, int _movie_id, Timestamp _timestamp) {
        return watch_history.updateWatch_History(_watchHistoryId, _user_id, _movie_id, _timestamp);
    }

    public boolean deleteWatch_HistoryItem(int _watchHistoryId) {
        return watch_history.deleteWatch_HistoryItem(_watchHistoryId);
    }

    public boolean deleteWatch_HistoryForUserId(int _user_id) {
        return watch_history.deleteWatch_HistoryForUserId(_user_id);
    }

    public boolean deleteWatch_HistoryForMovieId(int _movie_id) {
        return watch_history.deleteWatch_HistoryForMovieId(_movie_id);
    }

    // ------------ MOVIE_COLLECTIONS TABLE-----------

    public Movie_Collections getMovie_Collection(int _movieCollectionId) {

        Movie_Collections mc = new Movie_Collections();

        ArrayList<ArrayList<String>> dataList = movie_collections.fetchAllMovie_Collections();
        System.out.println(dataList);

        for (ArrayList<String> data : dataList) {
            int collectionId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            String colleaction_name = data.get(2);
            String description = data.get(3);

            mc.setMovieCollectionId(collectionId);
            mc.setUserId(user_Id);
            mc.setDescription(description);
            mc.setCollectionName(colleaction_name);
        }
        return mc;
    }// getMovie_Collection

    public List<Movie_Collections> getAllMovie_CollectionsForUserId(int _user_id) {
        ArrayList<ArrayList<String>> dataList = movie_collections.fetchAllMovie_CollectionsForUserId(_user_id);
        System.out.println(dataList);

        // List
        List<Movie_Collections> collectionsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int collectionId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            String colleaction_name = data.get(2);
            String description = data.get(3);

            Movie_Collections mc = new Movie_Collections(collectionId, user_Id, colleaction_name, description);
            collectionsList.add(mc);

        }
        return collectionsList;

    }// getAllMovie_CollectionsForUserId

    public List<Movie_Collections> getAllMovie_Collections() {
        ArrayList<ArrayList<String>> dataList = movie_collections.fetchAllMovie_Collections();
        System.out.println(dataList);

        // List
        List<Movie_Collections> collectionsList = new ArrayList<>();

        for (ArrayList<String> data : dataList) {
            int collectionId = Integer.parseInt(data.get(0));
            int user_Id = Integer.parseInt(data.get(1));
            String colleaction_name = data.get(2);
            String description = data.get(3);

            Movie_Collections mc = new Movie_Collections(collectionId, user_Id, colleaction_name, description);
            collectionsList.add(mc);

        }
        return collectionsList;
    }// getAllMovie_Collections

    public boolean insertMovie_Collection(int _user_id, String _collection_name, String _description) {
        return movie_collections.insertMovie_Collection(_user_id, _collection_name, _description);
    }

    public boolean updateMovie_Collection(int _movieCollectionId, int _user_id, String _collection_name,
                                          String _description) {
        return movie_collections.updateMovie_Collection(_movieCollectionId, _user_id, _collection_name,
                _description);
    }

    public boolean deleteMovie_Collection(int _movieCollectionId) {
        this.deleteMovie_Collections_ItemsForCollectionId(_movieCollectionId);
        return movie_collections.deleteMovie_Collection(_movieCollectionId);
    }

    public boolean deleteMovie_CollectionsForUserId(int _user_id) {
        return movie_collections.deleteMovie_CollectionsForUserId(_user_id);
    }

    // ------------ MOVIE_COLLECTIONS_ITEMS TABLE-----------
    public Movie_Collections_Items getMovie_Collections_Item(int _movie_collection_id) {
        Movie_Collections_Items mci = new Movie_Collections_Items();

        ArrayList<ArrayList<String>> dataList = movie_collections_items
                .fetchMovie_Collections_ItemById(_movie_collection_id);
        System.out.println(dataList);

        // Should iterate only once
        for (ArrayList<String> data : dataList) {
            int movie_collection_id = Integer.parseInt(data.get(0));
            int collection_id = Integer.parseInt(data.get(1));
            int movie_id = Integer.parseInt(data.get(2));

            mci.setMovieCollectionId(movie_collection_id);
            mci.setCollectionId(collection_id);
            mci.setMovieId(movie_id);

        }
        return mci;
    }

    public boolean insertMovie_Collections_Item(int _movie_collection_id, int _collection_id, int _movie_id) {
        return movie_collections_items.insertMovie_Collections_Item(_movie_collection_id, _collection_id,
                _movie_id);
    }

    public boolean updateMovie_Collections_Item(int _movie_collection_id, int _collection_id, int _movie_id) {
        return movie_collections_items.updateMovie_Collections_Item(_movie_collection_id, _collection_id, _movie_id);
    }

    public boolean deleteMovie_Collections_Item(int _movie_collection_id) {
        return movie_collections_items.deleteMovie_Collections_Item(_movie_collection_id);
    }

    public boolean deleteMovie_Collections_ItemsForCollectionId(int _collection_id) {
        return movie_collections_items.deleteMovie_Collections_ItemsForCollectionId(_collection_id);
    }

    public boolean deleteMovie_Collections_ItemsForMovieId(int _movie_id) {
        return movie_collections_items.deleteMovie_Collections_ItemsForMovieId(_movie_id);
    }

}// DataManipulator class end
