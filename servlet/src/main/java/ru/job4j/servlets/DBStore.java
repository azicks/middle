package ru.job4j.servlets;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStore implements Store {
    private static final BasicDataSource source = new BasicDataSource();
    private static final DBStore instance = new DBStore();
    private static final Logger log = LogManager.getLogger(DBStore.class);

    private static final String ROOTURL = "jdbc:postgresql://127.0.0.1:5432/";
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/users";
    private static final String DBNAME = "users";
    private static final String USER = "postgres";
    private static final String PASS = "password";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    private DBStore() {
        initDB();
        source.setUrl(URL);
        source.setUsername(USER);
        source.setPassword(PASS);
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);
    }

    public static DBStore getInstance() {
        return instance;
    }

    @Override
    public long add(User u) {
        int userId = -1;
        try (Connection connection = source.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                     "insert into users (name, login, email, image, created) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, u.getName());
            pst.setString(2, u.getLogin());
            pst.setString(3, u.getEmail());
            pst.setString(4, u.getImagePath());
            pst.setTimestamp(5, u.getCreateDate());
            pst.executeUpdate();
            ResultSet insertedUser = pst.getGeneratedKeys();
            insertedUser.next();
            userId = insertedUser.getInt("id");
            u.setId(userId);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return -1;
        }
        log.trace(String.format("%s added", u));
        return userId;
    }

    @Override
    public boolean update(User u) {
        boolean result = false;
        try (Connection connection = source.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                     "UPDATE users SET name = ?, login = ?, email = ? WHERE id = ?")) {
            pst.setString(1, u.getName());
            pst.setString(2, u.getLogin());
            pst.setString(3, u.getEmail());
            pst.setInt(4, u.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        log.trace(String.format("%s updated", u));
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connection = source.getConnection();
             PreparedStatement pst = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            result = affectedRows > 0;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        log.trace(String.format("User with id %d removed", id));
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = source.getConnection();
             Statement st = connection.createStatement()) {
            ResultSet users = st.executeQuery("SELECT * FROM users ORDER BY id");
            while (users.next()) {
                User user = new User(
                        users.getInt("id"),
                        users.getString("name"),
                        users.getString("login"),
                        users.getString("email"),
                        users.getString("image"),
                        users.getTimestamp("created"));
                result.add(user);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection connection = source.getConnection();
             PreparedStatement pst = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            pst.setInt(1, id);
            ResultSet users = pst.executeQuery();
            if (users.next()) {
                user = new User(
                        users.getInt("id"),
                        users.getString("name"),
                        users.getString("login"),
                        users.getString("email"),
                        users.getString("image"),
                        users.getTimestamp("created"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return user;
    }

    private void initDB() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            final Connection conn = DriverManager.getConnection(ROOTURL, USER, PASS);
            if (!isDBExists(conn, DBNAME)) {
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate(String.format("CREATE DATABASE %s", DBNAME));
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDBExists(Connection connection, String dbName) throws SQLException {
        boolean result = false;
        if (connection != null) {
            try (PreparedStatement pst = connection.prepareStatement(
                    "SELECT datname FROM pg_catalog.pg_database WHERE lower(datname) = lower(?);")) {
                pst.setString(1, dbName);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        }
        return result;
    }
}
