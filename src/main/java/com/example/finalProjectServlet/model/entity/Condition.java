package com.example.finalProjectServlet.model.entity;

public enum Condition {
    NOT_STARTED, IN_PROGRESS, COMPLETED;

    public static Condition getCondition(Course course) {
        int conditionId = course.getConditionId();
        return Condition.values()[conditionId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
