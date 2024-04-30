package Model;

import java.util.ArrayList;

public class Movie_Collections_Items extends CRUD {

    // Attributes
    private int movie_collection_id;
    private int collection_id;
    private int movie_id;

    // Foreigns models
    Movie_Collections movie_collections = new Movie_Collections();
    Movies movies = new Movies();

    public Movie_Collections_Items() {

    }

    public Movie_Collections_Items(int _movie_collection_id, int _collection_id, int _movie_id) {
        setMovieCollectionId(_collection_id);
        setCollectionId(_collection_id);
        setMovieId(_movie_id);
    }

    public ArrayList<ArrayList<String>> fetchMovie_Collections_ItemById(int _movie_collection_id) {
        String stmt = "SELECT * FROM Movie_Collections_Items WHERE movie_collection_id = ?;";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_movie_collection_id)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }

    public boolean insertMovie_Collections_Item(int _movie_collection_id, int _collection_id, int _movie_id) {
        // Check if exist
        ArrayList<ArrayList<String>> movieData = movies.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> collectionData = movie_collections.fetchMovie_CollectionById(_collection_id);

        if (movieData.isEmpty() || collectionData.isEmpty()) {
            System.out.println(
                    "New connection between movie & movie_collections could not be made. Provided movie or movie_collections does not exist.");
            return false;
        }

        // Statement
        String statement = "INSERT INTO `Movie_Collections_Items` (collection_id, movie_id) VALUES (?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_collection_id));
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(statement, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }

    public boolean updateMovie_Collections_Item(int _movie_collection_id, int _collection_id, int _movie_id) {
        // Check if exist
        ArrayList<ArrayList<String>> movieData = movies.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> collectionData = movie_collections.fetchMovie_CollectionById(_collection_id);

        if (movieData.isEmpty() || collectionData.isEmpty()) {
            System.out.println(
                    "New connection between movie & movie_collections could not be made. Provided movie or movie_collections does not exist.");
            return false;
        } else if (this.fetchMovie_Collections_ItemById(_movie_collection_id).isEmpty()) {
            System.out.println(
                    "Provided Movie_Collections_Item does not exist. No update.");
        }

        // Statement
        String statement = "UPDATE Movie_Collections_Items` SET collection_id = ?, movie_id = ? WHERE movie_collection_id = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_collection_id));
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Integer.toString(_movie_collection_id));

        boolean status = setData(statement, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating  went wrong. Try again.");
        }

        return status;
    }

    public boolean deleteMovie_Collections_Item(int _movie_collection_id) {

        String stmt = "DELETE FROM Movie_Collections_Items WHERE movie_collection_id = ?;";
        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_collection_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deletion was successful.");
        } else {
            System.out.println("Deletion went wrong. Try again.");
        }

        return status;
    }// deleteMovie_Collections_Item

    public boolean deleteMovie_Collections_ItemsForMovieId(int _movie_id) {
        String stmt = "DELETE FROM Movie_Collections_Items WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);
        if (status) {
            System.out.println("Deletion was successful.");
        } else {
            System.out.println("Deletion went wrong. Try again.");
        }
        return status;
    }

    public boolean deleteMovie_Collections_ItemsForCollectionId(int _collection_id) {
        String stmt = "DELETE FROM Movie_Collections_Items WHERE collection_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(collection_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deletion was successful.");
        } else {
            System.out.println("Deletion went wrong. Try again.");
        }

        return status;
    }// deleteAllMovie_Collections_ItemsForCollectionId

    // Accessors/Getters
    public int getMovieCollectionId() {
        return movie_collection_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public int getCollectionId() {
        return collection_id;
    }

    // Setters/Mutators
    public void setMovieCollectionId(int _movieCollectionId) {
        this.movie_collection_id = _movieCollectionId;
    }

    public void setCollectionId(int _collection_id) {
        this.collection_id = _collection_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

}// Movie_Collections_Items class end