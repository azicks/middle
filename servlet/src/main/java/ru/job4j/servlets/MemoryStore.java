package ru.job4j.servlets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryStore implements Store {
    private static final MemoryStore instance = new MemoryStore();
    private final List<User> users = new CopyOnWriteArrayList<>();
    private volatile int maxId = 0;

    private MemoryStore() {

    }

    public static MemoryStore getInstance() {
        return instance;
    }

    @Override
    public synchronized boolean add(User u) {
        u.setId(maxId++);
        return users.add(u);
    }

    @Override
    public boolean update(User u) {
        User user = findById(u.getId());
        if (user == null) {
            return false;
        }
        user.setName(u.getName());
        user.setEmail(u.getEmail());
        user.setLogin(u.getLogin());
        return true;
    }

    @Override
    public boolean delete(int id) {
        User u = findById(id);
        return u != null && users.remove(u);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(int id) {
        for (User u : users) {
            if (id == u.getId()) {
                return u;
            }
        }
        return null;
    }

}
