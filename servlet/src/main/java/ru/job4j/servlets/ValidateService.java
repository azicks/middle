package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.List;

public class ValidateService {
    private static final ValidateService instance = new ValidateService();
    private final Store store = DBStore.getInstance();
    private static final Logger log = LogManager.getLogger(ValidateService.class);

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return instance;
    }

    public List<User> getUsers() {
        return store.findAll();
    }

    public User getUser(int id) {
        return store.findById(id);
    }

    public long addUser(String name, String login, String email, String imageFile) {
        if ((name == null || login == null || email == null) || imageFile == null) {
            return -1;
        }
        return store.add(new User(name, login, email, imageFile));
    }


    public boolean updateUser(String id, String name, String login, String email) {
        if ((name == null || login == null || email == null || id == null)) {
            return false;
        }
        User user = store.findById(Integer.parseInt(id));
        user.setName(name);
        user.setLogin(login);
        user.setEmail(email);
        return store.update(user);
    }

    public boolean deleteUser(final String id, final String path) {
        if (id == null) {
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
}
