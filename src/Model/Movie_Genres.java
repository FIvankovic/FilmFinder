package Model;

import java.util.ArrayList;

public class Movie_Genres extends CRUD {

    // Attributes
    private int movie_genres_id;
    private int movie_id;
    private int genre_id;
    Movies movie = new Movies();
    Genres genre = new Genres();

    public Movie_Genres() {
    }

    public Movie_Genres(int _movie_genres_id, int _movie_id, int _genre_id) {
        this.setMovieGenresId(_movie_genres_id);
        this.setMovieId(_movie_id);
        this.setGenreId(_genre_id);
    }

    public ArrayList<ArrayList<String>> fetchAllGenresForMovieId(int _movie_id) {

        // Get the movie to check if it exists
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);

        // If does not exist in DB
        if (movieData.isEmpty()) {
            System.out.println("Movie entry does not exist.");
            return movieData;
        }

        // Get the exact amount of genres that are connected to a single movie entry
        String stmt = "SELECT * FROM Movie_Genres WHERE movie_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        ArrayList<ArrayList<String>> genresNumList = getData(stmt, valuesList);

        // New 2d ArrayList which will be returned
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        // Get the values for the genres that apply for the _movie_id
        for (int i = 0; i < genresNumList.size(); i++) {
            int genreId = Integer.parseInt(genresNumList.get(i).get(2)); // Taking out and parsing the id of genreId

            // Fetch that genre's values with the Genres object
            ArrayList<ArrayList<String>> genreEntry = genre.fetchGenreById(genreId);

            // ArrayList for the values of each individual genre
            ArrayList<String> genre = new ArrayList<String>();

            // Loop over an invidual entry from the 2d array and put it into the genre
            // ArrayList
            for (int j = 0; j < genreEntry.get(0).size(); j++) {
                genre.add(genreEntry.get(0).get(j));
            }
            // Add the genre values to the dataList
            dataList.add(genre);
        } // for loop end
        return dataList;
    }// getAllGenresForMovie method end

    public ArrayList<ArrayList<String>> fetchAllMoviesForGenreId(int _genre_id) {
        // Get the passed in genre to check if it exists
        ArrayList<ArrayList<String>> genreData = genre.fetchGenreById(_genre_id);

        // If does not exist in DB
        if (genreData.isEmpty()) {
            System.out.println("Genre entry does not exist.");
            return genreData;
        }

        // Get the exact amount of movies that are connected to a single genre entry
        String stmt = "SELECT * FROM Movie_Genres WHERE genre_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_genre_id));

        ArrayList<ArrayList<String>> movieNumList = getData(stmt, valuesList);
        // New 2d ArrayList which will be returned
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        // Get the values for the genres that apply for the _movie_id
        for (int i = 0; i < movieNumList.size(); i++) {
            int movieId = Integer.parseInt(movieNumList.get(i).get(1)); // Taking out and parsing the id of movieId

            ArrayList<ArrayList<String>> movieEntry = movie.fetchMovieById(movieId);
            // ArrayList for the values of each individual genre
            ArrayList<String> entry = new ArrayList<String>();

            // Loop over an invidual entry from the 2d array and put it into the genre
            // ArrayList
            for (int j = 0; j < movieEntry.get(0).size(); j++) {
                entry.add(movieEntry.get(0).get(j));
            }
            // Add the genre values to the dataList
            dataList.add(entry);
        } // for loop end

        return dataList;
    }// getAllMoviesForGenre method end

    public boolean insertMovie_Genres(int _movie_id, int _genre_id) {
        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> genreData = genre.fetchGenreById(_genre_id);

        if (movieData.isEmpty() || genreData.isEmpty()) {
            System.out.println(
                    "New connection between movie & genre could not be made. Provided movie or genre does not exist.");
            return false;
        }

        // Statement
        String statement = "INSERT INTO `Movie_Genres` (movie_id, genre_id) VALUES (?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_genre_id));

        boolean status = setData(statement, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertMovie_Genres method end

    public boolean updateMovie_Genres(int _movie_genres_id, int _movie_id, int _genre_id) {
        String stmt = "UPDATE Movie_Genres SET movie_id = ?, genre_id = ? WHERE movie_genres_id = ?;";

        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> genreData = genre.fetchGenreById(_genre_id);

        if (movieData.isEmpty() || genreData.isEmpty()) {
            System.out.println(
                    "Update failed for Movie_Genres. Genre and/or movie doesn't exist.");
            return false;
        }

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_genre_id));
        valuesList.add(Integer.toString(_movie_genres_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;
    }// updateMovieGenres method end

    public boolean deleteMovie_Genres(int _movie_genres_id) {
        String stmt = "DELETE FROM Movie_Genres WHERE movie_genres_id = ?;";
        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_genres_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_Directors method end

    public boolean deleteMovie_GenresForMovieId(int _movie_id) {
        String stmt = "DELETE FROM Movie_Genres WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_GenresByMovieId method end

    public boolean deleteMovie_GenresForGenreId(int _genre_id) {
        String stmt = "DELETE FROM Movie_Genres WHERE genre_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_genre_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_GenresByMovieId method end

    // Getters/Accessors
    public int getMovieGenresId() {
        return movie_genres_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public int getGenreId() {
        return genre_id;
    }

    // Setters/Mutators
    public void setMovieGenresId(int _movie_genres_id) {
        this.movie_genres_id = _movie_genres_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

    public void setGenreId(int _genre_id) {
        this.genre_id = _genre_id;
    }

}// Movie_Genres class end