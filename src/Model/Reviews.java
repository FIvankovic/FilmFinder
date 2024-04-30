package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Reviews extends CRUD {
    // Attributes
    private int reviewId;
    private int user_id;
    private int movie_id;
    private String review_text;
    private String timestamp;

    // Foreign objects
    Users user = new Users();
    Movies movie = new Movies();

    // Date Formater
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public Reviews() {

    }

    public Reviews(int _reviewId, int _user_id, int _movie_id, String _review_text, String _timestamp) {
        setReviewId(_reviewId);
        setUserId(_user_id);
        setMovieId(_movie_id);
        setReview_Text(_review_text);
        setTimestamp(_timestamp);
    }// Reviews constructor

    public ArrayList<ArrayList<String>> fetchReviewById(int _reviewId) {

        String stmt = "SELECT * FROM Reviews WHERE reviewId = ?;";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_reviewId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }// fetchReviewById method end

    public ArrayList<ArrayList<String>> fetchAllReviewsForMovie(int _movieId) {
        // Get the movie to check if it exists
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movieId);

        // If does not exist in DB
        if (movieData.isEmpty()) {
            System.out.println("Movie entry does not exist.");
            return movieData;
        }

        String stmt = "SELECT * FROM Reviews WHERE reviews.movie_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movieId));

        // Run the stmt
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;

    }// fetchAllReviewsForMovie method end

    public ArrayList<ArrayList<String>> fetchAllReviews() {
        String fetchStmt = "SELECT * FROM Reviews ORDER BY reviewId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllReviews method end

    public boolean insertReview(int _user_id, int _movie_id, String _review_text, Timestamp _timestamp) {

        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);

        if (movieData.isEmpty() || userData.isEmpty()) {
            System.out.println(
                    "Review failed. User or movie does not exist.");
            return false;
        }

        // Statement
        String createStmt = "INSERT INTO `Reviews` (user_id, movie_id, review_text, timestamp) VALUES (?, ?, ?, ?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(_review_text);
        valuesList.add(this.timestampToString(_timestamp));

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertReview method end

    public boolean updateReview(int _review_id, int _user_id, int _movie_id, String _review_text,
            Timestamp _timestamp) {

        String stmt = "UPDATE Reviews SET user_id = ?, movie_id = ?, review_text = ?, timestamp = ? WHERE reviewId = ?;";

        // Check if movie & genre exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);

        if (movieData.isEmpty() || userData.isEmpty()) {
            System.out.println(
                    "Update failed for Reviews. User and/or movie doesn't exist.");
            return false;
        }
        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(Integer.toString(_movie_id));
        valuesList.add(_review_text);
        valuesList.add(this.timestampToString(_timestamp));
        valuesList.add(Integer.toString(_review_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;

    }// updateReview method end

    public boolean deleteReview(int _reviewId) {
        String stmt = "DELETE FROM Reviews WHERE reviewId = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_reviewId));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted the selected review.");
        } else {
            System.out.println("Deletion of Reviews went wrong. Try again.");
        }

        return status;
    }// deleteReview method end

    public boolean deleteReviewsForMovieId(int _movie_id) {
        String stmt = "DELETE FROM Reviews WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all Reviews for movie with id: " + _movie_id);
        } else {
            System.out.println("Deletion of Reviews went wrong. Try again.");
        }

        return status;
    }// deleteReviewsForMovieId method end

    public boolean deleteReviewsForUserId(int _user_id) {
        String stmt = "DELETE FROM Reviews WHERE user_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all Reviews for movie with id: " + _user_id);
        } else {
            System.out.println("Deletion of Reviews went wrong. Try again.");
        }
        return status;
    }// deleteReviewsForUserId method end

    // Getters/Accessors
    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return user_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public String getReview_Text() {
        return review_text;
    }

    public String getTimestampt() {
        return timestamp;
    }

    // Setters/Mutators
    public void setReviewId(int _reviewId) {
        this.reviewId = _reviewId;
    }

    public void setUserId(int _user_id) {
        this.user_id = _user_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

    public void setReview_Text(String _review_text) {
        this.review_text = _review_text;
    }

    public void setTimestamp(String _timestamp) {
        this.timestamp = _timestamp;
    }

    // Util method
    public String timestampToString(Timestamp _timestamp) {
        String timeString = dateFormat.format(_timestamp);
        return timeString;
    }

}// Reviews class end
