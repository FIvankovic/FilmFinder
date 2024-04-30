package GUI;
import Controller.DataManipulator;
import Model.*;
import Model.Actors;
import Model.Directors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FavoriteMovies {

    @FXML
    private ScrollPane movieListView = new ScrollPane();

    @FXML
    TextField searchbar = new TextField();

    @FXML
    Text idTag = new Text();

    @FXML
    Button loginbutton = new Button();

    public FavoriteMovies() {
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

    @FXML
    public void initialize() {
        DataManipulator dm = new DataManipulator();

        if (Login.getUserId() > 0) {
            String userName = dm.getUser(Login.getUserId()).getName();
            idTag.setText(userName);
            loginbutton.setText("LOG OUT");
        } else {
            loginbutton.setText("LOGIN");
            idTag.setText("GUEST");
        }

        List<Movies> ml = dm.getWatch_HistoryForUserId(Login.getUserId());

        // Create a GridPane to hold the movie cards
        GridPane movieGrid = new GridPane();
        movieGrid.setHgap(20);
        movieGrid.setVgap(20);

        // Loop through the result set and create a card view for each movie
        for (int i = 0; i < ml.size(); i++){
            // Create a FlowPane to hold the elements of the card view
            FlowPane movieCard = new FlowPane();
            movieCard.setOrientation(Orientation.VERTICAL);
            movieCard.setMinWidth(640);
            movieCard.setMaxHeight(250);
            movieCard.setPadding(new Insets(20));
            movieCard.getStyleClass().add("movie-card");

            FlowPane cardLeft = new FlowPane();
            cardLeft.setPrefWidth(160);
            FlowPane cardRight = new FlowPane();

            // Create an ImageView to display the movie poster
            ImageView poster = new ImageView();
            poster.getStyleClass().add("mImage");
            poster.setFitWidth(150);
            poster.setPreserveRatio(true);

            // Load the poster image from the URL, or use a default image if the URL is null or the image fails to load
            Image posterImage = null;
            String image = ml.get(i).getCoverImage();
            System.out.println(image);
            try {
                posterImage = new Image("img/" + image, true);
            } catch (Exception e) {
                System.out.println("Failed to load poster image: " + e.getMessage());
                posterImage = new Image("img/movie-icon.jpg");
            }
            poster.setImage(posterImage);

            // Create a VBox to hold the movie title
            VBox movieTitle = new VBox();
            movieTitle.getStyleClass().add("mTitle");
            movieTitle.setMinWidth(300);
            Label title = new Label(ml.get(i).getTitle());
            movieTitle.getChildren().addAll(title);

            // Create genre list for movies
            List<Genres> gl = dm.getAllGenresForMovie(i + 1);
            Label genre = new Label("");
            String genreInsert = "";
            for(int j = 0; j < gl.size(); j++){
                String genreText = gl.get(j).getGenreName();
                genreInsert += genreText + ", " ;
                genre.setText(genreInsert);
            }

            // Add a button to cardRight for detail view
            Button detailButton = new Button("Show more");
            int finalI1 = i;
            detailButton.getStyleClass().add("details-button");
            detailButton.setOnAction(event -> {
                System.out.println("Opening detail view for " + ml.get(finalI1).getTitle());
                // Set the selected movie to the current movie
                MovieDetailView.setSelectedMovie(ml.get(finalI1));
                try {
                    // Load the MovieDetailView scene
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MovieDetailView.fxml")));
                    Node source = (Node) event.getSource();
                    Scene scene = source.getScene();
                    scene.setRoot(root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Create a VBox to hold other information
            VBox movieInfo = new VBox();
            movieInfo.getStyleClass().add("mInfo");
            Text releaseDate = new Text(ml.get(i).getReleaseDate() + "\n");
            movieInfo.getChildren().addAll(releaseDate, genre, detailButton);

            // Add the poster and movie info to the card view
            StackPane posterPane1 = new StackPane();
            posterPane1.getChildren().add(poster);
            cardLeft.getChildren().add(posterPane1);
            cardRight.getChildren().addAll(movieTitle, movieInfo);
            movieCard.getChildren().addAll(cardLeft, cardRight);

            // Add the card view to the movie list
            int row = i / 2; // assuming you want 3 columns
            int col = i % 2;
            movieGrid.add(movieCard, col, row);
            movieListView.setContent(movieGrid);
        }
    }
}