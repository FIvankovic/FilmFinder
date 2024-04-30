package Model;

import java.util.ArrayList;

public class Movie_Actors extends CRUD {

    // Attributes
    private int movie_actors_id;
    private int movie_id;
    private int actor_id;
    Movies movie = new Movies();
    Actors actor = new Actors();

    public Movie_Actors() {

    }

    public Movie_Actors(int _movie_actors_id, int _movie_id, int _actor_id) {
        this.setMovie_ActorsId(_movie_actors_id);
        this.setMovieId(_movie_id);
        this.setActorId(_actor_id);
    }

    public ArrayList<ArrayList<String>> fetchAllActorsForMovieId(int _movie_id) {

        // Get the movie to check if it exists
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);

        // If does not exist in DB
        if (movieData.isEmpty()) {
            System.out.println("Movie entry does not exist.");
            return movieData;
        }

        // Get the exact amount of actors that are connected to a single movie entry
        String stmt = "SELECT * FROM Movie_Actors WHERE movie_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        ArrayList<ArrayList<String>> actorsNumList = getData(stmt, valuesList);

        // New 2d ArrayList which will be returned
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        // Get the values for the actors that apply for the _movie_id
        for (int i = 0; i < actorsNumList.size(); i++) {
            int actorId = Integer.parseInt(actorsNumList.get(i).get(2)); // Taking out and parsing the id of actorId

            ArrayList<ArrayList<String>> actorEntry = actor.fetchActorById(actorId);

            ArrayList<String> actorList = new ArrayList<String>();

            for (int j = 0; j < actorEntry.get(0).size(); j++) {
                actorList.add(actorEntry.get(0).get(j));
            }

            dataList.add(actorList);
        } // for loop end
        return dataList;
    }// fetchAllActorsForMovieId method end

    public ArrayList<ArrayList<String>> fetchAllMoviesForActorId(int _actor_id) {
        ArrayList<ArrayList<String>> actorData = actor.fetchActorById(_actor_id);

        // If does not exist in DB
        if (actorData.isEmpty()) {
            System.out.println("Genre entry does not exist.");
            return actorData;
        }

        String stmt = "SELECT * FROM Movie_Actors WHERE actor_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_actor_id));

        ArrayList<ArrayList<String>> movieNumList = getData(stmt, valuesList);
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < movieNumList.size(); i++) {
            int movieId = Integer.parseInt(movieNumList.get(i).get(1)); // Taking out and parsing the id of movieId

            ArrayList<ArrayList<String>> movieEntry = movie.fetchMovieById(movieId);

            ArrayList<String> entry = new ArrayList<String>();

            // Loop over an invidual entry from the 2d array and put it into the entry
            // ArrayList
            for (int j = 0; j < movieEntry.get(0).size(); j++) {
                entry.add(movieEntry.get(0).get(j));
            }
            // Add the movie values to the dataList
            dataList.add(entry);
        }
        return dataList;
    }// fetchAllMoviesForActorId method end

    public boolean deleteMovie_Actors(int _movie_actors_id) {
        String stmt = "DELETE FROM Movie_Actors WHERE movie_actors_id = ?;";
        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_actors_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_Directors method end

    public boolean deleteMovie_ActorsForMovieId(int _movie_id) {
        String stmt = "DELETE FROM Movie_Actors WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_ActorsByMovieId method end

    public boolean deleteMovie_ActorsForActorId(int _actor_id) {
        String stmt = "DELETE FROM Movie_Actors WHERE actor_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_actor_id));

        boolean status = setData(stmt, valuesList);
        return status;
    }// deleteMovie_GenresByMovieId method end

    public boolean insertMovie_Genres(int _movie_id, int _actor_id) {
        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> genreData = actor.fetchActorById(_actor_id);

        if (movieData.isEmpty() || genreData.isEmpty()) {
            System.out.println(
                    "New connection between movie & actors could not be made. Provided movie or actor does not exist.");
            return false;
        }

        // Statement
        String statement = "INSERT INTO `Movie_Actors` (movie_id, actor_id) VALUES (?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_actor_id));

        boolean status = setData(statement, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertMovie_Genres method end

    public boolean updateMovie_Actors(int _movie_actors_id, int _movie_id, int _actor_id) {
        String stmt = "UPDATE Movie_Actors SET movie_id = ?, actor_id = ? WHERE movie_actors_id = ?;";

        // Check if movie & actor exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> actorData = actor.fetchActorById(_actor_id);

        if (movieData.isEmpty() || actorData.isEmpty()) {
            System.out.println(
                    "Update failed for Movie_Actors. Actor and/or movie doesn't exist.");
            return false;
        }

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_actor_id));
        valuesList.add(Integer.toString(_movie_actors_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;
    }// updateMovie_Actors method end

    // Getters/Accessors
    public int getMovie_ActorsId() {
        return movie_actors_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public int getActorId() {
        return actor_id;
    }

    // Setters/Mutators
    public void setMovie_ActorsId(int _movie_actors_id) {
        this.movie_actors_id = _movie_actors_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

    public void setActorId(int _actor_id) {
        this.actor_id = _actor_id;
    }
}// Movie_Actors class end
