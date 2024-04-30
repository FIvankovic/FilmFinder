package Model;

import java.util.ArrayList;

public class Actors extends CRUD {

    // Attributes
    private int actorId;
    private String name;
    private String date_of_birth;
    private String profile_image;

    public Actors() {
    }

    public Actors(int _ActorId, String _name, String _date_of_birth, String _profile_image) {
        setActorId(_ActorId);
        setName(_name);
        setDateOfBirth(_date_of_birth);
        setProfileImage(_profile_image);
    }// Actors constructor end

    public ArrayList<ArrayList<String>> fetchActorById(int _ActorId) {

        // Statement
        String fetchStatement = "SELECT * FROM Actors WHERE ActorId = ?";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_ActorId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(fetchStatement, valuesList);

        return dataList;
    }// FetchActorById method end

    public ArrayList<ArrayList<String>> fetchAllActors() {
        String fetchStmt = "SELECT * FROM Actors ORDER BY ActorId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllGenres method end

    public boolean deleteActor(int _Id) {
        // Statement
        String deleteStmt = "DELETE FROM Actors WHERE ActorId = ?";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_Id));

        boolean status = setData(deleteStmt, valuesList);

        if (status) {
            System.out.println("Deleting from Genres was successful.");
        } else {
            System.out.println("Deleting from Genres went wrong. Try again.");
        }
        return status;
    }// deleteGenre method end

    public boolean insertActor(String _name, String _date_of_birth, String _profile_image) {
        // Statement
        String createStmt = "INSERT INTO `Actors` (name,date_of_birth,profile_image) VALUES (?,?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        valuesList.add(_date_of_birth);
        valuesList.add(_profile_image);

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertGenre method end

    public boolean updateActor(int _ActorId, String _name, String _date_of_birth, String _profile_image) {
        // Statement
        String updateStmt = "UPDATE Actors SET name = ?, date_of_birth = ?, profile_image = ? WHERE ActorId = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        valuesList.add(_date_of_birth);
        valuesList.add(_profile_image);
        valuesList.add(Integer.toString(_ActorId));

        boolean status = setData(updateStmt, valuesList);

        if (status) {
            System.out.println("Update was succesful.");
        } else {
            System.out.println("Updating didn't go through. Try again.");
        }
        return status;
    }// updateGenre method end

    // Getters/Accessors
    public int getActorId() {
        return actorId;
    }

    public String getName() {
        return this.name;
    }

    public String getDateOfBirth() {
        return this.date_of_birth;
    }

    public String getProfileImage() {
        return this.profile_image;
    }

    // Setters/Mutators
    public void setActorId(int _actorId) {
        this.actorId = _actorId;
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
}// Actors class end
