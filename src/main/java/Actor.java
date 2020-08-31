import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Actor {
    int id;
    String name;
    String surname;

    public Actor() {
    }

    public Actor(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if (id != actor.id) return false;
        if (name != null ? !name.equals(actor.name) : actor.name != null) return false;
        return surname != null ? surname.equals(actor.surname) : actor.surname == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
    List<Actor> allActors (Connection connect) throws SQLException {
        List<Actor> actorList = new ArrayList<>();

        ResultSet resultSet = connect.prepareStatement("select * from actor ")
                .executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getNString(2);
            String lastName = resultSet.getNString(3);
            actorList.add(new Actor(id, name, lastName));
        }
        return actorList;
    }
    void printActors (Connection connect) throws SQLException {
        ResultSet resultSet = connect.prepareStatement("select * from actor ")
                .executeQuery();
        int i = 1;
        System.out.println("#" + " " + "id" + " " + "name" + " " + "lastName");
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getNString(2);
            String lastName = resultSet.getNString(3);
            System.out.println(i + " " + id + " " + name + " " + lastName);
            i++;
        }
        System.out.println("----------------------");
    }
    void removeItemById (Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from actor where actor_id = " + id);
        preparedStatement.executeUpdate();
    }
    void removeItemsByName (Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from actor where first_name = ? "
//                + "'" + name + "'"
        );
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
    }
    void insertActorToNewRow(Connection connection, Actor actor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into actor (first_name, last_name) values (?,?)");
        preparedStatement.setString(1,actor.name);
        preparedStatement.setString(2,actor.surname);
        preparedStatement.executeUpdate();
    }
    void editRowsByCondition(Connection connection, Actor newEntry, String condition) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement(
                        "update actor " +
                        "set first_name = ?, " +
                        "last_name = ? " +
                        " where " +
                        condition
                );
        preparedStatement.setString(1, newEntry.name);
        preparedStatement.setString(2, newEntry.surname);
        preparedStatement.executeUpdate();
    }

}
