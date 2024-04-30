package GUI;
import Controller.DataManipulator;
import Model.*;
import Model.Directors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Director {

    @FXML
    private ScrollPane directorListView = new ScrollPane();

    @FXML
    Text idTag = new Text();
    @FXML
    Button historyButton = new Button();
    @FXML
    Button loginbutton = new Button();

    public Director() {
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Actor.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void handleButtonAction3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Directors.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void handleButtonAction4(ActionEvent event) throws IOException {
        if(Login.getUserId() > 0) {
            Login.setUserId(0);
            initialize();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();
            scene.setRoot(root);
        }
    }

    @FXML
    private void handleButtonActionFavorite(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FavoriteMovies.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    public void initialize() {
        DataManipulator dm = new DataManipulator();

        if (Login.getUserId() > 0) {
            String userName = dm.getUser(Login.getUserId()).getName();
            idTag.setText(userName);
            loginbutton.setText("LOG OUT");
        } else {
            historyButton.setVisible(false);
            historyButton.setDisable(true);
            loginbutton.setText("LOGIN");
            idTag.setText("GUEST");
        }

        List<Directors> dl = dm.getAllDirectors();

        // Create a GridPane to hold the movie cards
        GridPane directorGrid = new GridPane();
        directorGrid.setHgap(20);
        directorGrid.setVgap(20);

        // Loop through the result set and create a card view for each movie
        for (int i = 0; i < dl.size(); i++){
            // Create a FlowPane to hold the elements of the card view
            FlowPane directorCard = new FlowPane();
            directorCard.setOrientation(Orientation.VERTICAL);
            directorCard.setMinWidth(640);
            directorCard.setMaxHeight(250);
            directorCard.setPadding(new Insets(20));
            directorCard.getStyleClass().add("movie-card");

            FlowPane cardLeft = new FlowPane();
            cardLeft.setPrefWidth(160);
            FlowPane cardRight = new FlowPane();

            // Create an ImageView to display the movie poster
            ImageView poster = new ImageView();
            poster.getStyleClass().add("mImage");
            poster.setFitWidth(150);
            poster.setPreserveRatio(true);

            // Load the poster image from the URL, or use a default image if the URL is null or the image fails to load
            javafx.scene.image.Image posterImage = null;
            String image = dl.get(i).getProfileImage();
            System.out.println(image);
            try {
                posterImage = new javafx.scene.image.Image("img/" + image, true);
            } catch (Exception e) {
                System.out.println("Failed to load poster image: " + e.getMessage());
                posterImage = new Image("img/movie-icon.jpg");
            }
            poster.setImage(posterImage);

            // Create a VBox to hold the movie title
            VBox directorName = new VBox();
            directorName.getStyleClass().add("dTitle");
            directorName.setMinWidth(300);
            javafx.scene.control.Label title = new javafx.scene.control.Label(dl.get(i).getName());
            directorName.getChildren().addAll(title);

            // Create genre list for movies
            List<Movies> gl = dm.getAllMoviesForDirector(i + 1);
            javafx.scene.control.Label movieList = new javafx.scene.control.Label("");
            String movieInsert = "";
            for(int j = 0; j < gl.size(); j++){
                String movieText = gl.get(j).getTitle();
                movieInsert += movieText + ", " ;
                movieList.setText(movieInsert);
            }

            // Create a VBox to hold other information
            VBox directorInfo = new VBox();
            directorInfo.getStyleClass().add("dInfo");
            Text dateOfBirth = new Text(dl.get(i).getDateOfBirth() + "\n");
            directorInfo.getChildren().addAll(dateOfBirth, movieList);

            // Add the poster and movie info to the card view
            StackPane posterPane1 = new StackPane();
            posterPane1.getChildren().add(poster);
            cardLeft.getChildren().add(posterPane1);
            cardRight.getChildren().addAll(directorName, directorInfo);
            directorCard.getChildren().addAll(cardLeft, cardRight);

            // Add the card view to the movie list
            int row = i / 2; // assuming you want 3 columns
            int col = i % 2;
            directorGrid.add(directorCard, col, row);
            directorListView.setContent(directorGrid);
        }
    }
}
