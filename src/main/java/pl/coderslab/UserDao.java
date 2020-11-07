package pl.coderslab;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    // zapytanie dodawania użytkownika
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(email, username, password) VALUES (?, ?, ?)";

    // zapytanie zmiany danych użytkownika
    private static final String UPDATE_USER_QUERY =
            "UPDATE users set email=?, username=?, password=? WHERE id=?";

    // zapytanie pobierania danych użytkownika po id
    private static final String READ_USER_QUERY =
            "SELECT email, username, password from users WHERE id=?";

    // zapytanie usuwania użytkownika po id
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id=?";

    // zapytanie pobierania wszystkich użytkowników
    private static final String READ_ALL_USERS_QUERY =
            "SELECT id, email, username, password FROM users";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // zapis nowego użytkownika do bazy,
    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLIntegrityConstraintViolationException sqli) {
            System.out.println(sqli.getMessage());
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // wczytanie użytkownika po jego id,
    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            // jeżeli znalazło użytkownika, to odczytaj jego dane
            if (resultSet.next()) {
                // tworzymy nowy obiekt który zostanie zwrócony
                User user = new User();
                user.setId(userId); // nie pobieramy id bo to jest to samo id po którym wyszukujemy
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;    // nie znalazło takiego użytkownika
    }

    // edycja danych użytkownika
    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // usunięcie użytkownika
    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // wczytanie wszystkich użytkowników
    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(READ_ALL_USERS_QUERY);
            User[] allUsers = new User[0];    // tablica która będzie zawierała wszystkich użytkowników

            while (resultSet.next()) {
                // tworzymy nowy obiekt który zostanie zwrócony
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                allUsers = addToArray(user, allUsers);
            }
            return allUsers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u;     // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers;                // Zwracamy nową tablicę.
    }

}
