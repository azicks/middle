package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlets.store.DBStore;
import ru.job4j.servlets.store.Store;

import java.io.File;
import java.util.List;
import java.util.Set;

public class UserStorage {
    private static final UserStorage instance = new UserStorage();
    private final Store store = DBStore.getInstance();
    private static final Logger log = LogManager.getLogger(UserStorage.class);

    private UserStorage() {
    }

    public static UserStorage getInstance() {
        return instance;
    }

    public boolean hasCredentials(String login, String password) {
        boolean result = false;
        User u = store.findByLogin(login);
        if (u != null && password.equals(u.getPassword())) {
            result = true;
        }
        return result;
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public User findByLogin(String login) {
        return store.findByLogin(login);
    }

    public long add(String name, String login, String password, String email, String imageFile, Role role,
                    String country, String city) {
        User u = new User(name, login, password, email, imageFile, role, country, city);
        long userId;
        User existsUser = store.findByLogin(u.getLogin());
        if (existsUser != null) {
            log.trace("User already exists");
            userId = -2;
        } else {
            userId = store.add(u);
        }
        return userId;
    }

    public boolean update(String id, String name, String login, String email, Role role, String country, String city) {
        User user = store.findById(Integer.parseInt(id));
        if (user.getLogin().equals("root") || "root".equals(login)) {
            return false;
        }
        user.setName(name);
        user.setLogin(login);
        user.setEmail(email);
        user.setRole(role);
        user.setCountry(country);
        user.setCity(city);
        return store.update(user);
    }

    public boolean delete(String id, String path) {
        User user = findById(Integer.parseInt(id));
        if (user.getLogin().equals("root")) {
            return false;
        }
        File f = new File(path);
        String pattern = id + "_(.*)";
        final File[] files = f.listFiles((dir, name) -> name.matches(pattern));
        if (files != null) {
            for (final File file : files) {
                if (!file.delete()) {
                    log.error("Can't remove " + file.getAbsolutePath());
                }
            }
        }
        return store.delete(Integer.parseInt(id));
    }

    public Set<String> getUserRoles() {
        return Role.getAll();
    }
}
