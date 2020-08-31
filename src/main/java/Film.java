import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Film {
    int id;
    String title;
    int year;

    public Film() {
    }

    public Film(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (id != film.id) return false;
        if (year != film.year) return false;
        return title != null ? title.equals(film.title) : film.title == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
    Connection getConnect() throws SQLException {
        Connect connect = new Connect("jdbc:mysql://localhost:3306/sakila?serverTimezone=UTC", "root", "root");
        return connect.connect();
    }

    List<Film> getAllFilms() throws SQLException {
        Connection connection = this.getConnect();
        ResultSet resultSet = connection.prepareStatement("select * from film").executeQuery();

        List<Film> films = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getNString(2);
            int year = resultSet.getInt("release_year");
            films.add(new Film(id, title, year));
        }
        return films;
    }
    void printAllFilmsList(int limit) throws SQLException {
        ResultSet resultSet = this.getConnect().prepareStatement("select * from film").executeQuery();
        int i = 1;
        System.out.println("#" + " " +  "film_id" + " " +  "title" + " " +  "release_year");
        while (resultSet.next() && i <= limit) {
            int id = resultSet.getInt(1);
            String title = resultSet.getNString(2);
            int year = resultSet.getInt("release_year");
            System.out.println(i + " " + id + " " + title + " " + year);
            i++;
        }
        System.out.println("----------------------");
    }
    void printListOfFilms(List<Film> list) throws SQLException {
        int i = 1;
        System.out.println("#" + " " +  "film_id" + " " +  "title" + " " +  "release_year");
        for (Film film : list) {
            System.out.println(i + " " + film.getId() + " " + film.getTitle() + " " + film.getYear());
            i++;
        }
        System.out.println("----------------------");
    }
    Film getFilmById (int id) throws SQLException {
        PreparedStatement preparedStatement = this.getConnect().prepareStatement("select * from film where film_id = ?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Film(id, resultSet.getString(2),resultSet.getInt("release_year"));
    }
    List<Film> getFilmsByYear (int year) throws SQLException {
        PreparedStatement preparedStatement = this.getConnect().prepareStatement("select * from film where release_year = ?");
        preparedStatement.setInt(1,year);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Film> films = new ArrayList<>();
        while (resultSet.next()) {
            films.add(new Film(resultSet.getInt(1), resultSet.getString(2),year));
        }
        return films;
    }
}
