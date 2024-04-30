package Model;

import java.util.ArrayList;

public class Movie_Directors extends CRUD {

    // Attributes
    private int movie_directors_id;
    private int movie_id;
    private int director_id;
    private Movies movie = new Movies();
    private Directors director = new Directors();

    public Movie_Directors() {

    }

    public Movie_Directors(int _movie_directors_id, int _movie_id, int _director_id) {
        this.setMovie_DirectorsId(_movie_directors_id);
        this.setMovieId(_movie_id);
        this.setDirectorId(_director_id);
    }

    public ArrayList<ArrayList<String>> fetchAllDirectorsForMovieId(int _movie_id) {

        // Get the movie to check if it exists
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);

        // If does not exist in DB
        if (movieData.isEmpty()) {
            System.out.println("Movie entry does not exist.");
            return movieData;
        }

        // Get the exact amount of genres that are connected to a single movie entry
        String stmt = "SELECT * FROM Movie_Directors WHERE movie_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        ArrayList<ArrayList<String>> directorsNumList = getData(stmt, valuesList);

        // New 2d ArrayList which will be returned
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        // Get the values for the genres that apply for the _movie_id
        for (int i = 0; i < directorsNumList.size(); i++) {
            int directorId = Integer.parseInt(directorsNumList.get(i).get(2)); // Taking out and parsing the id of
                                                                               // directorId

            // Fetch that genre's values with the Genres object
            ArrayList<ArrayList<String>> directorEntry = director.fetchDirectorById(directorId);

            // ArrayList for the values of each individual genre
            ArrayList<String> directorList = new ArrayList<String>();

            // Loop over an invidual entry from the 2d array and put it into the genre
            // ArrayList
            for (int j = 0; j < directorEntry.get(0).size(); j++) {
                directorList.add(directorEntry.get(0).get(j));
            }
            // Add the genre values to the dataList
            dataList.add(directorList);
        } // for loop end
        return dataList;
    }// fetchAllDirectorsForMovieId method end

    public ArrayList<ArrayList<String>> fetchAllMoviesForDirectorId(int _director_id) {
        // Get the passed in director to check if it exists
        ArrayList<ArrayList<String>> directorData = director.fetchDirectorById(_director_id);

        // If does not exist in DB
        if (directorData.isEmpty()) {
            System.out.println("Genre entry does not exist.");
            return directorData;
        }

        // Get the exact amount of movies that are connected to a single genre entry
        String stmt = "SELECT * FROM Movie_Directors WHERE director_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_director_id));

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
    }// fetchAllMoviesForDirectorId method end

    public boolean insertMovie_Directors(int _movie_id, int _director_id) {
        // Check if exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> directorData = director.fetchDirectorById(_director_id);

        if (movieData.isEmpty() || directorData.isEmpty()) {
            System.out.println(
                    "New connection between movie & director could not be made. Provided movie or director does not exist.");
            return false;
        }

        // Statement
        String statement = "INSERT INTO `Movie_Directors` (movie_id, director_id) VALUES (?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_director_id));

        boolean status = setData(statement, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertMovie_Directors method end

    public boolean updateMovie_Directors(int _movie_directors_id, int _movie_id, int _director_id) {
        String stmt = "UPDATE Movie_Directors SET movie_id = ?, director_id = ? WHERE movie_directors_id = ?;";

        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> directorData = director.fetchDirectorById(_director_id);

        if (movieData.isEmpty() || directorData.isEmpty()) {
            System.out.println(
                    "Update failed for Movie_Directors. Director and/or movie doesn't exist.");
            return false;
        }

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_director_id));
        valuesList.add(Integer.toString(_movie_directors_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;
    }// updateMovie_Directors method end

    public boolean deleteMovie_Directors(int _movie_directors_id) {
        String stmt = "DELETE FROM Movie_Directors WHERE movie_directors_id = ?;";
        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_directors_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_Directors method end

    public boolean deleteMovie_DirectorsForMovieId(int _movie_id) {
        String stmt = "DELETE FROM Movie_Directors WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_DirectorsByMovieId method end

    public boolean deleteMovie_DirectorsForDirectorId(int _director_id) {
        String stmt = "DELETE FROM Movie_Directors WHERE director_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_director_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_DirectorsByMovieId method end

    // Getters/Accessors
    public int getMovie_DirectorsId() {
        return movie_directors_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public int getDirectorId() {
        return director_id;
    }

    // Mutators/Setters
    public void setMovie_DirectorsId(int _movie_directors_id) {
        this.movie_directors_id = _movie_directors_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

    public void setDirectorId(int _director_id) {
        this.director_id = _director_id;
    }
}// Movie_Directors class end
