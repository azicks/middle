package ru.job4j.servlets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ValidateService {
    private static final ValidateService instance = new ValidateService();
    private final Map<String, Function<Map<String, String[]>, Boolean>> dispatch = new HashMap<>();
    private final Store store = MemoryStore.getInstance();

    private ValidateService() {
        init();
    }

    public static ValidateService getInstance() {
        return instance;
    }

    public List<User> getUsers() {
        return store.findAll();
    }

    public boolean dispatch(Map<String, String[]> parameters) {
        String[] action = parameters.get("action");
        if (action == null || dispatch.get(action[0]) == null) {
            return false;
        }
        return dispatch.get(action[0]).apply(parameters);
    }

    private Function<Map<String, String[]>, Boolean> addUser() {
        return parameters -> {
            String[] name = parameters.get("name");
            String[] login = parameters.get("login");
            String[] email = parameters.get("email");
            if ((name == null || login == null || email == null)) {
                return false;
            }
            return store.add(new User(name[0], login[0], email[0]));
        };
    }

    private Function<Map<String, String[]>, Boolean> updateUser() {
        return parameters -> {
            String[] id = parameters.get("id");
            String[] name = parameters.get("name");
            String[] login = parameters.get("login");
            String[] email = parameters.get("email");
            if ((name == null || login == null || email == null || id == null)) {
                return false;
            }
            User user = new User(Integer.parseInt(id[0]), name[0], login[0], email[0]);
            return store.update(user);
        };
    }

    private Function<Map<String, String[]>, Boolean> deleteUser() {
        return parameters -> {
            String[] id = parameters.get("id");
            if (id == null) {
                return false;
            }
            return store.delete(Integer.parseInt(id[0]));
        };
    }

    private void init() {
        dispatch.put("add", addUser());
        dispatch.put("update", updateUser());
        dispatch.put("delete", deleteUser());
    }
}
