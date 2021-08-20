package com.example.finalProjectServlet.model.entity;

public enum Role {
    STUDENT, ADMIN, TEACHER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
