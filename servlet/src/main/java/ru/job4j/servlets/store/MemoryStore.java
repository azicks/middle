package ru.job4j.servlets.store;

import ru.job4j.servlets.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryStore implements Store {
    private static final MemoryStore instance = new MemoryStore();
    private final List<User> users = new CopyOnWriteArrayList<>();
    private AtomicInteger maxId = new AtomicInteger(1);

    private MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return instance;
    }

    @Override
    public synchronized long add(User u) {
        u.setId(maxId.getAndIncrement());
        users.add(u);
        return 0;
    }

    @Override
    public User findByLogin(String login) {
        for (User u : users) {
            if (login.equals(u.getLogin())) {
                return u;
            }
        }
        return null;
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
