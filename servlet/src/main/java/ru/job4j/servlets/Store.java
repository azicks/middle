package ru.job4j.servlets;

import java.util.List;

public interface Store {
    long add(User u);
    boolean update(User u);
    boolean delete(int id);
    List<User> findAll();
    User findById(int id);
}
