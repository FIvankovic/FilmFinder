package Model;

import java.util.ArrayList;

public class Genres extends CRUD {

    // Attributes
    private int GenreId;
    private String GenreName;

    public Genres() {

    }

    public Genres(int _GenreId, String _GenreName) {
        this.setGenreId(_GenreId);
        this.setGenreName(_GenreName);
    }

    // This should return some type of data back to the Data Manipulator class
    public ArrayList<ArrayList<String>> fetchGenreById(int _GenreId) {

        // Statement
        String fetchStatement = "SELECT * FROM Genres WHERE GenreId = ?";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_GenreId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(fetchStatement, valuesList);

        return dataList;
    }// fetchGenreById method end

    // Fetch All Genres from table
    public ArrayList<ArrayList<String>> fetchAllGenres() {
        String fetchStmt = "SELECT * FROM Genres ORDER BY GenreId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllGenres method end

    public boolean deleteGenre(int _GenreId) {
        // Statement
        String deleteStmt = "DELETE FROM Genres WHERE GenreID = ?";

        // Values list
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString(_GenreId));

        boolean status = setData(deleteStmt, valuesList);

        if (status) {
            System.out.println("Deleting from Genres was successful.");
        } else {
            System.out.println("Deleting from Genres went wrong. Try again.");
        }
        return status;
    }// deleteGenre method end

    public boolean insertGenre(String _GenreName) {
        // Statement
        String createStmt = "INSERT INTO `Genres` (Name) VALUES (?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_GenreName);

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertGenre method end

    public boolean updateGenre(int _GenreId, String _GenreName) {
        // Statement
        String updateStmt = "UPDATE Genres set name = ? WHERE GenreId = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_GenreName);
        valuesList.add(Integer.toString(_GenreId));

        boolean status = setData(updateStmt, valuesList);

        if (status) {
            System.out.println("Update was succesful.");
        } else {
            System.out.println("Updating didn't go through. Try again.");
        }
        return status;
    }// updateGenre method end

    // Utility print method
    public void printGenre() {
        System.out.printf(
                "\n---------- Genres ----------\nGenreID: %d\nGenre Name: %s\n\n",
                this.getGenreId(), this.getGenreName());
    }// printEquipment method end

    // Getters/Accessors
    public int getGenreId() {
        return GenreId;
    }

    public String getGenreName() {
        return GenreName;
    }

    // Setters/Mutators
    public void setGenreId(int _GenreId) {
        this.GenreId = _GenreId;
    }

    public void setGenreName(String _GenreName) {
        this.GenreName = _GenreName;
    }

    public boolean checkIfGenreNameExists(String _genreName) {

        ArrayList<ArrayList<String>> dataList = this.fetchAllGenres();

        for (ArrayList<String> data : dataList) {
            String existingGenre = data.get(1);

            if (existingGenre.equals(_genreName)) {
                return true;
            }
        }

        return false;
    }// checkIfGenreNameExists method end

    public ArrayList<ArrayList<String>> fetchGenreIdByGenreName(String _name) {
        String stmt = "SELECT genreId FROM Genres WHERE name = ?;";

        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_name);
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
        if (checkIfGenreNameExists(_name)) {
            dataList = getData(stmt, valuesList);
            return dataList;
        } else {
            System.out.println("Genre does not exist");
            return dataList;

        }
    }// fetchGenreIdByGenreName method end
}
