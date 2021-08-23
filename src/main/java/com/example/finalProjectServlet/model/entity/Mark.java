package com.example.finalProjectServlet.model.entity;

public enum Mark {
    A, B, C, D, E, Fx;

    public static Mark getMark(int markId) {
        return Mark.values()[markId - 1];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
