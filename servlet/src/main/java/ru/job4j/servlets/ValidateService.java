package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidateService {
    private static final ValidateService instance = new ValidateService();
    private final UserStorage userStorage = UserStorage.getInstance();
    private static final Logger log = LogManager.getLogger(ValidateService.class);

    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return instance;
    }

    public long addUser(String name, String login, String password, String email, String imageFile, String role) {
        if ((name == null || login == null || password == null || email == null) || imageFile == null || role == null) {
            log.error("Null field while creating user");
            return -1;
        }
        return userStorage.add(name, login, password, email, imageFile, Role.getRole(role));
    }

    public boolean updateUser(String id, String name, String login, String email, String  role) {
        if ((name == null || login == null || email == null || id == null || role == null)) {
            log.error("Null field while updating user");
            return false;
        }
        return userStorage.update(id, name, login, email, Role.getRole(role));
    }

    public boolean deleteUser(final String id, final String path) {
        if (id == null) {
            log.error("Null id while deleting user");
            return false;
        }
        return userStorage.delete(id, path);
    }
}
