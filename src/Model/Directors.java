package Model;

import java.util.ArrayList;

public class Directors extends CRUD {

    // Attributes
    private int directorId;
    private String name;
    private String date_of_birth;
    private String profile_image;

    public Directors() {
    }

    public Directors(int _directorId, String _name, String _date_of_birth, String _profile_image) {
        this.setDirectorId(_directorId);
        this.setName(_name);
        this.setDateOfBirth(_date_of_birth);
        this.setProfileImage(_profile_image);
    }

    public ArrayList<ArrayList<String>> fetchDirectorById(int _directorId) {

        // Statement
        String fetchStatement = "SELECT * FROM Directors WHERE directorId = ?";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_directorId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(fetchStatement, valuesList);

        return dataList;
    }// fetchDirectorById method end

    public ArrayList<ArrayList<String>> fetchAllDirectors() {
        String fetchStmt = "SELECT * FROM Directors ORDER BY directorId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }

    public boolean insertDirector(String _name, String _date_of_birth, String _profile_image) {
        String stmt = "INSERT INTO `Directors` (name, date_of_birth, profile_image) VALUES (?,?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        valuesList.add(_date_of_birth);
        valuesList.add(_profile_image);

        boolean status = setData(stmt, valuesList);
        return status;
    }// insertDirector method end

    public boolean updateDirector(int _directorId, String _name, String _date_of_birth, String _profile_image) {
        // Statement
        String updateStmt = "UPDATE Directors SET name = ?, date_of_birth = ?, profile_image = ? WHERE directorId = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        valuesList.add(_date_of_birth);
        valuesList.add(_profile_image);
        valuesList.add(Integer.toString(_directorId));

        boolean status = setData(updateStmt, valuesList);

        if (status) {
            System.out.println("Update was succesful.");
        } else {
            System.out.println("Updating didn't go through. Try again.");
        }
        return status;
    }// updateDirector method end

    public boolean deleteDirector(int _directorId) {
        // Statement
        String deleteStmt = "DELETE FROM Directors WHERE directorId = ?";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_directorId));

        boolean status = setData(deleteStmt, valuesList);

        if (status) {
            System.out.println("Deleting from Directors was successful.");
        } else {
            System.out.println("Deleting from Directors went wrong. Try again.");
        }
        return status;
    }// deleteDirector method end

    // Accessors/Getters
    public int getDirectorId() {
        return directorId;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public String getProfileImage() {
        return profile_image;
    }

    // Setters/Mutators
    public void setDirectorId(int _directorId) {
        this.directorId = _directorId;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setDateOfBirth(String _date_of_birth) {
        this.date_of_birth = _date_of_birth;
    }

    public void setProfileImage(String _profile_image) {
        this.profile_image = _profile_image;
    }
}
