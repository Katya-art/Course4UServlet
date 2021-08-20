package com.example.finalProjectServlet.model.entity;

public enum Status {
    UNLOCK, BLOCKED;

    public static Status getStatus(User user) {
        int statusId = user.getStatusId();
        return Status.values()[statusId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
