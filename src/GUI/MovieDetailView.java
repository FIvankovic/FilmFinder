package GUI;
import Controller.DataManipulator;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class MovieDetailView {

    private static Movies selectedMovie;

    @FXML
    ImageView movieDetailImage = new ImageView();
    @FXML
    Text titleDetail = new Text();
    @FXML
    Text yearDetail = new Text();
    @FXML
    Text synopsisDetail = new Text();
    @FXML
    Text directorDetail = new Text();
    @FXML
    Text actorDetail = new Text();
    @FXML
    Text genreDetail = new Text();
    @FXML
    TextArea reviewDetail = new TextArea();
    @FXML
    TextArea writeReview = new TextArea();
    @FXML
    Text idTag = new Text();
    @FXML
    Button favoriteButton = new Button();
    @FXML
    Button historyButton = new Button();
    @FXML
    Button loginbutton = new Button();
    @FXML
    Button delateButton = new Button();
    @FXML
    Button submitReviewButton = new Button();

    public MovieDetailView() {
    }

    public static void setSelectedMovie(Movies movies) {
        selectedMovie = movies;
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
    private void handleButtonAction5(ActionEvent event) throws IOException {
        DataManipulator dm = new DataManipulator();

        String review = writeReview.getText();
        Timestamp time = new Timestamp(System.currentTimeMillis());

        if (!Objects.equals(review, "")) {
            dm.insertReview(Login.getUserId(), selectedMovie.getMovieId(), review, time);
            writeReview.setText("");

            List<Reviews> rl = dm.getAllReviewsForMovie(selectedMovie.getMovieId());
            String reviewInsert = "";
            for(int j = 0; j < rl.size(); j++){
                int idReview = rl.get(j).getUserId();
                String reviewUser = dm.getUser(idReview).getName();
                String reviewText = rl.get(j).getReview_Text();
                String reviewDate = rl.get(j).getTimestampt();
                reviewInsert += reviewUser + "\n" + reviewText + "\n" + reviewDate + "\n\n";
                reviewDetail.setText(reviewInsert);
            }
        }
    }

    @FXML
    private void handleButtonAction6(ActionEvent event) throws IOException {
        DataManipulator dm = new DataManipulator();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        dm.insertWatch_History(Login.getUserId(), selectedMovie.getMovieId(), time);
    }

    @FXML
    private void handleButtonActionFavorite(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FavoriteMovies.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void handleButtonActionDelate(ActionEvent event) throws IOException {
        DataManipulator dm = new DataManipulator();
        dm.deleteReviewsForMovieId(selectedMovie.getMovieId());
        dm.deleteMovie(selectedMovie.getMovieId());
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        scene.setRoot(root);
    }

    public void initialize() {

        DataManipulator dm = new DataManipulator();
        reviewDetail.setDisable(true);
        reviewDetail.setOpacity(1);

        if (Login.getUserId() > 0) {
            String userName = dm.getUser(Login.getUserId()).getName();
            idTag.setText(userName);
            loginbutton.setText("LOG OUT");
            if (!dm.getUser(Login.getUserId()).getRole().equals("a")) {
                delateButton.setVisible(false);
                delateButton.setDisable(true);
            }
        } else {
            favoriteButton.setVisible(false);
            favoriteButton.setDisable(true);
            historyButton.setVisible(false);
            historyButton.setDisable(true);
            loginbutton.setText("LOGIN");
            idTag.setText("GUEST");
            delateButton.setVisible(false);
            delateButton.setDisable(true);
            submitReviewButton.setVisible(false);
            submitReviewButton.setDisable(true);
            writeReview.setVisible(false);
            writeReview.setDisable(true);
        }

        Movies movie = selectedMovie;

        try {
            javafx.scene.image.Image mimg = new javafx.scene.image.Image("img/" + movie.getCoverImage(), true);
            movieDetailImage.setImage(mimg);
        } catch (Exception e) {
            System.out.println("Failed to load detail image: " + e.getMessage());
            javafx.scene.image.Image mimg = new javafx.scene.image.Image("img/movie-icon.jpg", true);
            movieDetailImage.setImage(mimg);
        }

        titleDetail.setText(movie.getTitle());

        yearDetail.setText(movie.getReleaseDate());

        synopsisDetail.setText(movie.getSynopsis());

        List<Genres> gl = dm.getAllGenresForMovie(movie.getMovieId());
        String genreInsert = "";
        for(int j = 0; j < gl.size(); j++){
            String genreText = gl.get(j).getGenreName();
            genreInsert += genreText + ", " ;
            genreDetail.setText(genreInsert);
        }

        List<Directors> dl = dm.getAllDirectorsForMovie(movie.getMovieId());
        String directorInsert = "Directors: ";
        for(int j = 0; j < dl.size(); j++){
            String directorText = dl.get(j).getName();
            directorInsert += directorText + ", " ;
            directorDetail.setText(directorInsert);
        }

        List<Actors> al = dm.getAllActorsForMovie(movie.getMovieId());
        String actorInsert = "Actors: ";
        for(int j = 0; j < al.size(); j++){
            String actorText = al.get(j).getName();
            actorInsert += actorText + ", " ;
            actorDetail.setText(actorInsert);
        }

        List<Reviews> rl = dm.getAllReviewsForMovie(movie.getMovieId());
        String reviewInsert = "";
        for(int j = 0; j < rl.size(); j++){
            int idReview = rl.get(j).getUserId();
            String reviewUser = dm.getUser(idReview).getName();
            String reviewText = rl.get(j).getReview_Text();
            String reviewDate = rl.get(j).getTimestampt();
            reviewInsert += reviewUser + "\n" + reviewText + "\n" + reviewDate + "\n\n";
            reviewDetail.setText(reviewInsert);
        }
    }
}
