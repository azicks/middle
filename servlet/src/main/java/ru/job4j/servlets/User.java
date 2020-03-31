package ru.job4j.servlets;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String login;
    private String email;
    private Date createDate;

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String name, String login, String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = new Date();
    }

    public User(String name, String login, String email) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = new Date();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return String.format("User (id = %d) %s, login = %s, email = %s, created at %s", id, name, login, email, createDate);
    }

    public void setId(int id) {
        this.id = id;
    }
}