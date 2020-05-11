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

    public long addUser(String name,
                        String login,
                        String password,
                        String email,
                        String imageFile,
                        String role,
                        String country,
                        String city) {
        if (name == null || login == null || password == null || email == null
                || imageFile == null || role == null || country == null || city == null) {
            log.error("Null field while creating user");
            return -1;
        }

        if (name.isEmpty() || login.isEmpty() || password.isEmpty()) {
            log.error("Empty field while creating user");
            return -1;
        }

        return userStorage.add(name, login, password, email, imageFile, Role.getRole(role), country, city);
    }

    public boolean updateUser(String id, String name, String login, String email,
                              String  role, String country, String city) {
        if (name == null || login == null || email == null || id == null
                || role == null || country == null || city == null) {
            log.error("Null field while updating user");
            return false;
        }
        if (name.isEmpty() || login.isEmpty()) {
            log.error("Empty field while updating user");
            return false;
        }
        return userStorage.update(id, name, login, email, Role.getRole(role), country, city);
    }

    public boolean deleteUser(final String id, final String path) {
        if (id == null) {
            log.error("Null id while deleting user");
            return false;
        }
        return userStorage.delete(id, path);
    }
}
