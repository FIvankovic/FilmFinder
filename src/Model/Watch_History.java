package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Watch_History extends CRUD {

    // Attributes
    private int watchHistoryId;
    private int user_id;
    private int movie_id;
    private String timestamp;

    // Foreign objects
    Users user = new Users();
    Movies movie = new Movies();

    // Date Formater
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public Watch_History() {

    }

    public Watch_History(int _watchHistoryId, int _user_id, int _movie_id, String _timestamp) {
        setWatchHistoryId(_watchHistoryId);
        setUserId(_user_id);
        setMovieId(_movie_id);
        setTimestamp(_timestamp);
    }

    public ArrayList<ArrayList<String>> fetchWatch_HistoryById(int _watchHistoryId) {
        String stmt = "SELECT * FROM Watch_History WHERE watchHistoryId = ?;";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_watchHistoryId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }// fetchWatch_HistoryById

    public ArrayList<ArrayList<String>> fetchWatch_HistoryForUserId(int _user_id) {
        // Get the userto check if it exists
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);
        if (userData.isEmpty()) {
            System.out.println("User entry does not exist.");
            return userData;
        }

        String stmt = "SELECT DISTINCT * FROM Movies JOIN Watch_History ON Watch_History.movie_id = movieId WHERE Watch_History.user_id = ?;";
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));

        // Run the stmt
        ArrayList<ArrayList<String>> dataList = getData(stmt, valuesList);

        return dataList;
    }// fetchWatch_HistoryForUserId

    public boolean insertWatch_History(int _user_id, int _movie_id, Timestamp _timestamp) {

        // Check if movie & user exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);

        if (movieData.isEmpty() || userData.isEmpty()) {
            System.out.println(
                    "Watch History insert failed. User or movie does not exist.");
            return false;
        }

        // Statement
        String stmt = "INSERT INTO `Watch_History` (user_id, movie_id, timestamp) VALUES (?, ?, ?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(Integer.toString(_movie_id));
        String timeString = dateFormat.format(_timestamp);
        valuesList.add(timeString);

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertWatch_History

    public boolean updateWatch_History(int _watchHistoryId, int _user_id, int _movie_id, Timestamp _timestamp) {

        String stmt = "UPDATE Watch_History SET user_id = ?, movie_id = ?, timestamp = ? WHERE watchHistoryId = ?;";

        // Check if movie & user exist
        ArrayList<ArrayList<String>> movieData = movie.fetchMovieById(_movie_id);
        ArrayList<ArrayList<String>> userData = user.fetchUserById(_user_id);

        if (movieData.isEmpty() || userData.isEmpty()) {
            System.out.println(
                    "Update failed for Watch_History. User and/or movie doesn't exist.");
            return false;
        } else if (this.fetchWatch_HistoryById(_watchHistoryId).isEmpty()) {
            System.out.println(
                    "Update failed for Watch_History. Watch History does not exist.");
        }

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));
        valuesList.add(Integer.toString(_movie_id));
        String timeString = dateFormat.format(_timestamp);
        valuesList.add(timeString);
        valuesList.add(Integer.toString(_watchHistoryId));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Updating was successful.");
        } else {
            System.out.println("Updating went wrong. Try again.");
        }

        return status;

    }// updateReview method end

    public boolean deleteWatch_HistoryItem(int _watchHistoryId) {
        String stmt = "DELETE FROM Watch_History WHERE watchHistoryId = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_watchHistoryId));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted the selected review.");
        } else {
            System.out.println("Deletion of Reviews went wrong. Try again.");
        }

        return status;
    }

    public boolean deleteWatch_HistoryForMovieId(int _movie_id) {
        String stmt = "DELETE FROM Watch_History WHERE movie_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_movie_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all Watch history for movie with id: " + _movie_id);
        } else {
            System.out.println("Deletion of Watch history went wrong. Try again.");
        }
        return status;
    }

    public boolean deleteWatch_HistoryForUserId(int _user_id) {
        String stmt = "DELETE FROM Watch_History WHERE user_id = ?;";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_user_id));

        boolean status = setData(stmt, valuesList);

        if (status) {
            System.out.println("Deleted all Watch history for user with id: " + _user_id);
        } else {
            System.out.println("Deletion of Watch history went wrong. Try again.");
        }
        return status;
    }

    // Getters/Accessory
    public int getWatchHistoryId() {
        return watchHistoryId;
    }

    public int getUserId() {
        return user_id;
    }

    public int getMovieId() {
        return movie_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters/Mutators
    public void setWatchHistoryId(int _watchHistoryId) {
        this.watchHistoryId = _watchHistoryId;
    }

    public void setUserId(int _user_id) {
        this.user_id = _user_id;
    }

    public void setMovieId(int _movie_id) {
        this.movie_id = _movie_id;
    }

    public void setTimestamp(String _timestampt) {
        this.timestamp = _timestampt;
    }
}// Watch_History class end
