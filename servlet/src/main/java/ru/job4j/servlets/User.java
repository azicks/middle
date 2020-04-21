package ru.job4j.servlets;

import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private String email;
    private String imageFile;
    private Timestamp createDate;
    private Role role;

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id,
                String name,
                String login,
                String password,
                String email,
                String imageFile,
                Timestamp created,
                Role role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.imageFile = imageFile;
        this.createDate = created;
        this.role = role;
    }

    public User(String name, String login, String password, String email, String imageFile, Role role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.imageFile = imageFile;
        this.createDate = new Timestamp(new Date().getTime());
        this.role = role;
    }

    public boolean isRoot() {
        return login.equals("root");
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }

    @Override
    public String toString() {
        return String.format("User (id = %d) %s, login = %s, email = %s, created at %s", id, name, login, email, createDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && name.equals(user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && createDate.equals(user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, createDate);
    }
}
