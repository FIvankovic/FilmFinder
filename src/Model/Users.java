package Model;

import java.util.ArrayList;

public class Users extends CRUD {
    // Attributes
    private int userId;
    private String name;
    private String email;
    private String password;
    private String registration_date;
    private String role;

    // User constructor
    public Users() {
    }

    public Users(int _userId, String _name, String _email, String _password, String _registration_date, String _role) {
        this.setUserId(_userId);
        this.setName(_name);
        this.setEmail(_email);
        this.setPassword(_password);
        this.setRegistrationDate(_registration_date);
        this.setRole(_role);
    }

    public ArrayList<ArrayList<String>> fetchUserById(int _userId) {

        // Statement
        String fetchStatement = "SELECT * FROM Users WHERE UserId = ?";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_userId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(fetchStatement, valuesList);

        return dataList;
    }// fetchUserById method end

    public ArrayList<ArrayList<String>> fetchAllUsers() {
        String fetchStmt = "SELECT * FROM Users ORDER BY UserId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllUsers method end

    public boolean insertUser(String _name, String _email, String _password, String _registration_date, String _role) {

        // Statement
        String createStmt = "INSERT INTO `Users` (name, email, password, registration_date, role) VALUES (?,?,?,?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        valuesList.add(_email);
        valuesList.add(_password);
        valuesList.add(_registration_date);
        valuesList.add(_role);

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }
        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }
        return status;
    }// insertUser method end

    public boolean deleteUser(int _userId) {
        // Statement
        String deleteStmt = "DELETE FROM Users WHERE UserId = ?";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_userId));

        boolean status = setData(deleteStmt, valuesList);

        if (status) {
            System.out.println("Deleting from Genres was successful.");
        } else {
            System.out.println("Deleting from Genres went wrong. Try again.");
        }
        return status;

    }// deleteUser method end

    public boolean updateUser(String _name, String _email, String _password, String _registration_date, String _role) {
        // Statement
        String updateStmt = "UPDATE Users SET name = ?, email = ?, password = ?, registration_date = ?, role = ? WHERE UserId = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        valuesList.add(_email);
        valuesList.add(_password);
        valuesList.add(_registration_date);
        valuesList.add(_role);

        boolean status = setData(updateStmt, valuesList);

        if (status) {
            System.out.println("Update was succesful.");
        } else {
            System.out.println("Updating didn't go through. Try again.");
        }
        return status;

    }// updateUser method end

    // Getters/Accessors
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistrationDate() {
        return registration_date;
    }

    public String getRole() {
        return role;
    }

    // Setters/Mutators
    public void setUserId(int _userId) {
        this.userId = _userId;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setPassword(String _password) {
        this.password = _password;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public void setRegistrationDate(String _registration_date) {
        this.registration_date = _registration_date;
    }

    public void setRole(String _role) {
        this.role = _role;
    }
}// User class end
