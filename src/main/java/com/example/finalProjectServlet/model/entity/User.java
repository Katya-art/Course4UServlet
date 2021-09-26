package com.example.finalProjectServlet.model.entity;

public class User implements Comparable<User> {

    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String password;

    private int roleId;

    private int statusId;

    public User() {
    }

    public User(String fullName, String username, String email, String password, int roleId, int statusId) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public int compareTo(User o) {
        return this.getFullName().compareTo(o.fullName);
    }
}
