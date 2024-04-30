package GUI;
import Controller.LoginController;
import Database.MySQLDatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Controller.LoginController.*;

public class Login {

    private Button moviebutton;

    @FXML
    TextField username = new TextField();
    @FXML
    TextField password = new TextField();
    @FXML
    Text userError = new Text();

    private static int userId;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    public static String validateLogin(String name, String password) {
        try {
            Connection conn = MySQLDatabaseConnection.getConnection();

            String sql = "SELECT userId, password, role FROM Users WHERE email=?"; // Updated SQL query
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name); // Assuming 'username' is the email address

            // Execute the query and check if the result set is not empty
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId"); // Retrieve the user ID from the ResultSet
                String storedPassword = rs.getString("password"); // Retrieve the password from the ResultSet
                String role = rs.getString("role"); // Retrieve the role from the ResultSet

                // Compare the provided password with the stored password
                if (password.equals(storedPassword)) {
                    Login.userId = userId; // Set the userId field to the retrieved user ID
                    return role;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @FXML
    public boolean handleButtonAction2(ActionEvent event) throws IOException {
        String un = username.getText();
        String ps = password.getText();
        String role = validateLogin(un, ps);
        if (role != null) {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();
            scene.setRoot(root);

            if (role.equals("A")) {

            } else if (role.equals("U")) {

            } else if (role.equals("G")) {

            }

            return true;
        } else {
            System.out.println("Invalid username or password");
            userError.setText("Invalid username or password");
            return false;
        }
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Login.userId = userId;
    }
}

