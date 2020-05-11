package ru.job4j.servlets.store;

import ru.job4j.servlets.User;

import java.util.List;
import java.util.Set;

public interface Store {
    long add(User u);
    boolean update(User u);
    boolean delete(int id);
    List<User> findAll();
    Set<String> findAllCities();
    User findById(int id);
    User findByLogin(String login);
}
