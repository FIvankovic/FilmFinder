package Model;

import Database.MySQLDatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

;

public class CRUD {

    public CRUD() {

    }

    // For preparing statements
    public PreparedStatement prepare(String sqlStmt, ArrayList<String> valuesList) {
        PreparedStatement stmt = null;

        try {
            stmt = MySQLDatabaseConnection.getConnection().prepareStatement(sqlStmt);

            if (!valuesList.isEmpty() || valuesList != null) {
                for (int i = 0; i < valuesList.size(); i++) {
                    stmt.setString(i + 1, valuesList.get(i));
                }
            }

            System.out.println("Prepared stament success: \"" + sqlStmt + "\".");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return stmt;
    }// prepare method end

    public ArrayList<ArrayList<String>> getData(String sqlStmt, ArrayList<String> valuesList) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();

        try {
            PreparedStatement stmt;

            // Prepare the statement
            stmt = prepare(sqlStmt, valuesList); // Calls the prepare() method

            ResultSet result = stmt.executeQuery();

            // Count the Columns
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (result.next()) {
                ArrayList<String> retrievedList = new ArrayList<String>();

                for (int i = 1; i <= columnCount; i++) {
                    String output = result.getString(i);
                    retrievedList.add(output);
                }
                // Add the retrieved values back to the 2d ArrayList
                dataList.add(retrievedList);
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return dataList;
    }// getData method end

    public boolean setData(String sqlStmt, ArrayList<String> valuesList) {
        try {
            // Prepare the statement
            PreparedStatement stmt = prepare(sqlStmt, valuesList);

            // Execute Update
            int update = stmt.executeUpdate();

            if (update == 0) {
                System.out.println("Update statement affected no rows.");
            }

            // Closing
            stmt.close();

            // Print info
            System.out.println("Update " + sqlStmt + " was succesful.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }// setData method end
}
