package Model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class User_Ratings extends CRUD {

    // Attributes
    private int userRatingId;
    private int user_id;
    private int movie_id;
    private double rating_score;

    // Foreign objects
    Users user = new Users();
    Movies movie = new Movies();

    // Empty constructor
    public User_Ratings() {
    }

    public User_Ratings(int _userRatingId, int _user_id, int _movie_id, double _rating_score) {
        this.setUserRatingId(_userRatingId);
        this.setUserId(_user_id);
        this.setMovieId(_movie_id);
        this.setRatingScore(_rating_score);
    }// User_Ratings constructor end

    public ArrayList<ArrayList<String>> fetchUser_RatingById(int _userRatingId) {
        String stmt = "SELECT * FROM User_Ratings WHERE userRatingId = ?;";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_userRatingId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }// fetchUser_RatingById

    public ArrayList<ArrayList<String>> fetchAllUser_Ratings() {
        String fetchStmt = "SELECT * FROM User_Ratings ORDER BY userRatingId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllUser_Ratings method end

    public ArrayList<ArrayList<String>> fetchAllUser_RatingsForMovieId(int _movie_id) {

        // Get the movie to check if it exists
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);

        // If does not exist in DB
        if (movieData.isEmpty()) {
            System.out.println("Movie entry does not exist.");
            return movieData;
        }

        String stmt = "SELECT * FROM User_Ratings WHERE movie_id = ? ORDER BY userRatingId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        // Run the stmt
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }// fetchAllUser_RatingsForMovieId method end

    public boolean insertUser_Rating(int _user_id, int _movie_id, double _rating_score) {
        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);

        if (movieData.isEmpty() || userData.isEmpty()) {
            System.out.println(
                    "Insert of user ratings failed. User or movie does not exist.");
            return false;
        }

        if (!this.validateRatingScore(_rating_score)) {
            System.out.println(
                    "Insert failed for User_Ratings. Rating Score was invalid.");
            return false;
        }

        // Statement
        String createStmt = "INSERT INTO `User_Ratings` (user_id, movie_id, rating_score) VALUES (?, ?, ?);";

        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Double.toString(_rating_score));

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertUser_Rating method end

    public boolean updateUser_Rating(int _userRatingId, int _user_id, int _movie_id, double _rating_score) {
        String stmt = "UPDATE User_Ratings SET user_id = ?, movie_id = ?, rating_score = ? WHERE userRatingId = ?;";

        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);

        if (movieData.isEmpty() || userData.isEmpty()) {
            System.out.println(
                    "Update failed for User_Ratings. User and/or movie doesn't exist.");
            return false;
        }

        if (!this.validateRatingScore(_rating_score)) {
            System.out.println(
                    "Update failed for User_Ratings. Rating Score was invalid.");
            return false;
        }

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(Double.toString(_rating_score));
        valuesList.add(Integer.toString(_userRatingId));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;
    }// updateUser_Rating method end

    public boolean deleteUser_Rating(int _userRatingId) {
        // Statement
        String deleteStmt = "DELETE FROM User_Ratings WHERE userRatingId = ?";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_userRatingId));

        boolean status = setData(deleteStmt, valuesList);

        if (status) {
            System.out.println("Deleting from User_Ratings was successful.");
        } else {
            System.out.println("Deleting from User_Ratings went wrong. Try again.");
        }
        return status;
    }// deleteUser_Rating method end

    public boolean deleteUser_RatingsForMovieId(int _movie_id) {
        String stmt = "DELETE FROM User_Ratings WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all User_Ratings for movie with id: " + _movie_id);
        } else {
            System.out.println("Deletion of User_Ratings went wrong. Try again.");
        }

        return status;
    }// deleteUser_RatingsForMovieId method end

    public boolean deleteUser_RatingsForUserId(int _user_id) {
        String stmt = "DELETE FROM User_Ratings WHERE user_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all User_Ratings for user with id: " + _user_id);
        } else {
            System.out.println("Deletion of User_Ratings went wrong. Try again.");
        }

        return status;
    }// deleteUser_RatingsForUserId method end

    // Checks whether the Rating is a valid one or not
    public boolean validateRatingScore(double _rating_score) {
        if (_rating_score > 5.00 || _rating_score < 1.00) {
            System.out.println("Invalid rating score. Score must be within the range of 1-5.");
            return false;
        } else if (BigDecimal.valueOf(_rating_score).scale() > 2) {
            System.out.println("Invalid rating score. Score must contain a maximum of 2 decimals (ex.: x.??).");
            return false;
        } else {
            return true;
        }

    }// validateRatingScore method end

    // Accessors/Getters
    public int getUserRatingId() {
        return this.userRatingId;
    }

    public int getUserId() {
        return user_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public double getRatingScore() {
        return rating_score;
    }

    // Setters/Mutators
    public void setUserRatingId(int _userRatingId) {
        this.userRatingId = _userRatingId;
    }

    public void setUserId(int _user_id) {
        this.user_id = _user_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

    public void setRatingScore(double _rating_score) {
        this.rating_score = _rating_score;
    }

}// User_Ratings class end