package Model;

import java.util.ArrayList;

public class Movies extends CRUD {

    // Atributes
    private int MovieId;
    private String title;
    private String release_date;
    private int duration;
    private String synopsis;
    private String cover_image;

    public Movies() {
    }

    public Movies(int _movieId, String _title, String _release_date, int _duration, String _synopsis,
            String _cover_image) {
        this.setMovieId(_movieId);
        this.setTitle(_title);
        this.setReleaseDate(_release_date);
        this.setDuration(_duration);
        this.setSynopsis(_synopsis);
        this.setCoverImage(_cover_image);
    }// Movies constructor end

    public Movies(String _title, String _release_date, int _duration, String _synopsis,
            String _cover_image) {
        this.setTitle(_title);
        this.setReleaseDate(_release_date);
        this.setDuration(_duration);
        this.setSynopsis(_synopsis);
        this.setCoverImage(_cover_image);
    }// Movies constructor end

    public ArrayList<ArrayList<String>> fetchMovieById(int _MovieId) {
        // Statement
        String fetchStatement = "SELECT * FROM Movies WHERE MovieId = ?";

        // ValuesList - values for the "?" in the prepared string
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(Integer.toString((_MovieId)));

        // Fetching data
        ArrayList<ArrayList<String>> dataList = getData(fetchStatement, valuesList);

        return dataList;
    }// fetchMovieById method end

    // Fetch All Movies from table
    public ArrayList<ArrayList<String>> fetchAllMovies() {
        String fetchStmt = "SELECT * FROM Movies ORDER BY MovieId;";
        ArrayList<String> valuesList = new ArrayList<String>();
        ArrayList<ArrayList<String>> dataList = getData(fetchStmt, valuesList);
        return dataList;
    }// fetchAllGenres method end

    public boolean deleteMovie(int _Id) {
        // Statement
        String deleteStmt = "DELETE FROM Movies WHERE MovieId = ?";

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

    public boolean insertMovie(String _title, String _release_date, int _duration, String _synopsis,
            String _cover_image) {
        // Statement
        String createStmt = "INSERT INTO `Movies` (title,release_date,duration,synopsis,cover_image) VALUES (?,?,?,?,?);";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_title);
        valuesList.add(_release_date);
        valuesList.add(Integer.toString(_duration));
        valuesList.add(_synopsis);
        valuesList.add(_cover_image);

        boolean status = setData(createStmt, valuesList);

        if (status) {
            System.out.println("Inserting was successful.");
        } else {
            System.out.println("Inserting went wrong. Try again.");
        }

        return status;
    }// insertGenre method end

    public boolean updateMovie(int _MovieId, String _title, String _release_date, int _duration, String _synopsis,
            String _cover_image) {
        // Statement
        String updateStmt = "UPDATE Movies SET title = ?, release_date = ?, duration = ?, synopsis = ?, cover_image = ? WHERE MovieId = ?;";

        // Values List
        ArrayList<String> valuesList = new ArrayList<String>();
        valuesList.add(_title);
        valuesList.add(_release_date);
        valuesList.add(Integer.toString(_duration));
        valuesList.add(_synopsis);
        valuesList.add(_cover_image);
        valuesList.add(Integer.toString(_MovieId));

        boolean status = setData(updateStmt, valuesList);

        if (status) {
            System.out.println("Update was succesful.");
        } else {
            System.out.println("Updating didn't go through. Try again.");
        }
        return status;
    }// updateGenre method end

    // --------------Accessors/Getters----------------
    public int getMovieId() {
        return this.MovieId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getReleaseDate() {
        return this.release_date;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public String getCoverImage() {
        return this.cover_image;
    }

    // Setters/Mutators
    public void setMovieId(int _MovieId) {
        this.MovieId = _MovieId;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public void setReleaseDate(String _release_date) {
        this.release_date = _release_date;
    }

    public void setDuration(int _duration) {
        this.duration = _duration;
    }

    public void setSynopsis(String _synopsis) {
        this.synopsis = _synopsis;
    }

    public void setCoverImage(String _cover_image) {
        this.cover_image = _cover_image;
    }
}
