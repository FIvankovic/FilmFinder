package Model;

import java.util.ArrayList;

public class Movie_Collections extends CRUD {

    // Attributes
    private int movieCollectionId;
    private int user_id;
    private String collection_name;
    private String description;

    // Foreign tables
    Users users = new Users();

    public Movie_Collections() {
    }

    public Movie_Collections(int _movieCollectionId, int _user_id, String _collection_name, String _description) {
        setMovieCollectionId(_movieCollectionId);
        setUserId(_user_id);
        setCollectionName(_collection_name);
        setDescription(_description);
    }// Movie_Collections constructor

    public ArrayList<ArrayList<String>> fetchMovie_CollectionById(int _movieCollectionId) {

        String stmt = "SELECT * FROM Movie_Collections WHERE movieCollectionId = ?;";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_movieCollectionId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }// fetchMovie_CollectionById method end

    public ArrayList<ArrayList<String>> fetchAllMovie_CollectionsForUserId(int _user_id) {
        // Get the movie to check if it exists
        ArrayList<ArrayList<String>> userData = users.fetchUserById(_user_id);

        // If does not exist in DB
        if (userData.isEmpty()) {
            System.out.println("User does not exist.");
            return userData;
        }

        String stmt = "SELECT * FROM Movie_Collections WHERE user_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));

        // Run the stmt
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;

    }// fetchAllMovie_CollectionsForUserId method end

    public ArrayList<ArrayList<String>> fetchAllMovie_Collections() {
        String fetchStmt = "SELECT * FROM Movie_Collections ORDER BY movieCollectionId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllMovie_Collections method end

    public boolean insertMovie_Collection(int _user_id, String _collection_name, String _description) {
        ArrayList<ArrayList<String>> userData = users.fetchUserById(_user_id);

        if (userData.isEmpty()) {
            System.out.println(
                    "Movie Collection insert failed. User does not exist.");
            return false;
        }

        // Statement
        String createStmt = "INSERT INTO `Movie_Collections` (user_id, collection_name, description) VALUES (?, ?, ?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(_collection_name);
        valuesList.add(_description);

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertMovie_Collection method end

    public boolean updateMovie_Collection(int _movieCollectionId, int _user_id, String _collection_name,
            String _description) {
        ArrayList<ArrayList<String>> userData = users.fetchUserById(_user_id);
        ArrayList<ArrayList<String>> collectionData = this.fetchMovie_CollectionById(_movieCollectionId);

        if (userData.isEmpty() || collectionData.isEmpty()) {
            System.out.println(
                    "Movie Collection insert failed. User or movie collection does not exist.");
            return false;
        }

        String stmt = "UPDATE Movie_Collections SET user_id = ?, collection_name = ?, description = ? WHERE movieCollectionId = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(_collection_name);
        valuesList.add(_description);
        valuesList.add(Integer.toString(_movieCollectionId));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;
    }// updateMovie_Collection method end

    public boolean deleteMovie_Collection(int _movieCollectionId) {
        String stmt = "DELETE FROM Movie_Collections WHERE movieCollectionId = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movieCollectionId));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted the selected movie collection.");
        } else {
            System.out.println("Deletion of Movie_Collections went wrong. Try again.");
        }

        return status;
    }// deleteMovie_Collection method end

    public boolean deleteMovie_CollectionsForUserId(int _user_id) {
        String stmt = "DELETE FROM Movie_Collections WHERE user_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all Movie_Collections for movie with id: " + _user_id);
        } else {
            System.out.println("Deletion of Movie_Collections went wrong. Try again.");
        }
        return status;
    }// deleteReviewsForUserId method end

    // Getters/Accessors
    public int getMovieCollectionId() {
        return this.movieCollectionId;
    }

    public int getUserId() {
        return this.user_id;
    }

    public String getCollectionName() {
        return this.collection_name;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters/Mutators
    public void setMovieCollectionId(int _movieCollectionId) {
        this.movieCollectionId = _movieCollectionId;
    }

    public void setUserId(int _user_id) {
        this.user_id = _user_id;
    }

    public void setCollectionName(String _collection_name) {
        this.collection_name = _collection_name;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }
}// Movie_Collections class end
