import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connect connectSakila = new Connect("jdbc:mysql://localhost:3306/sakila?serverTimezone=UTC", "root", "root");
        Connection sakila = connectSakila.connect();
        PreparedStatement preparedStatement = sakila.prepareStatement("select * from category");
        ResultSet categorySet = preparedStatement.executeQuery();

        while (categorySet.next()) {
            int category_id = categorySet.getInt("category_id");
            String name = categorySet.getString(2);
            System.out.println(category_id + " " + name);
            }
        System.out.println("--------------------------");

//            PreparedStatement preparedStatementActors = sakila.prepareStatement("select * from actor");
//            ResultSet setActors = preparedStatementActors.executeQuery();
//            ArrayList<Actor> actors = new ArrayList<>();
//            while (setActors.next()) {
//                int id = setActors.getInt(1);
//                String name = setActors.getNString(2);
//                String surname = setActors.getNString(3);
//                actors.add(new Actor(id, name, surname));
//            }
        Actor actorX = new Actor();
        List<Actor> actorsList = actorX.allActors(sakila);

        System.out.println(actorsList);
        System.out.println("--------------------------");

//        PreparedStatement delete205 = sakila.prepareStatement("DELETE FROM actor WHERE actor_id = 205");
//        delete205.executeUpdate();

        actorX.removeItemById(sakila,206);
        actorX.insertActorToNewRow(sakila, new Actor(500, "aaa", "bbb"));
        actorX.insertActorToNewRow(sakila, new Actor(300, "Danny", "Davito"));
        actorX.editRowsByCondition(sakila, new Actor(0, "Steve", "Jobs"),"first_name = 'aaa'");
        actorX.removeItemsByName(sakila,"Danny");
        actorX.printActors(sakila);

//        List<Actor> listPE = actors.stream().filter(actor -> actor.name.toLowerCase().contains("pe")).collect(Collectors.toList());
//        System.out.println(listPE);
//        System.out.println("--------------------------");

//        PreparedStatement insertActor = connection.prepareStatement("insert into actor(first_name,last_name) values ('hhh', 'jjj')");
//        insertActor.executeUpdate();
//        PreparedStatement insertActorX = connection.prepareStatement("insert into actor(first_name,last_name) values (?, ?)");
//        insertActorX.setString(1, "aaa");
//        insertActorX.setString(2, "kkk");
//        insertActorX.executeUpdate();
//
//        sakila.close();

        Film filmX = new Film();
        filmX.printAllFilmsList(10);
        System.out.println(filmX.getFilmById(5));
        System.out.println("--------------------------");

        List<Film> films2006 = filmX.getFilmsByYear(2000);
        filmX.printListOfFilms(films2006);

        filmX.getConnect().close();

        System.out.println("--------------------------");
        System.out.println("--------------------------");

        ResultSet filmsOfActor = sakila.prepareStatement(
                "select A.first_name Name, A.last_name surName, " +
                        "F.film_id, F.title " +
                        "from ((actor A " +
                        "inner join film_actor FA " +
                        "on A.actor_id = FA.actor_id) " +
                        "inner join film F " +
                        "on F.film_id = FA.film_id " +
                        "and F.release_year = 2020) " +
                        "order by Name, surName"
        ).executeQuery();

        ResultSetMetaData md = filmsOfActor.getMetaData();
        int count = md.getColumnCount();
        System.out.println("cLabel" + " " + "cName");

        for (int i =1; i <= count; i++) {
            String cLabel = md.getColumnLabel(i);
            String cName = md.getColumnName(i);
            System.out.println(cLabel + " " + cName);

        }
        System.out.println("--------------------------");

        while (filmsOfActor.next()) {
            String name = filmsOfActor.getString("Name");
            String surname = filmsOfActor.getString("surName");
            int id = filmsOfActor.getInt(3);
            String title = filmsOfActor.getString("title");
            System.out.println(name  + " " + surname + " " + id + " " + title);
        }
        System.out.println("--------------------------");

    }
}
