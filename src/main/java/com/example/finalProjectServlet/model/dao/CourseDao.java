package com.example.finalProjectServlet.model.dao;

import com.example.finalProjectServlet.model.DBManager;
import com.example.finalProjectServlet.model.entity.Course;
import com.example.finalProjectServlet.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CourseDao {

    private static final String SQL_SAVE_COURSE = "INSERT INTO courses" +
            "  (name, theme, duration, teacher_id, condition_id) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String SQL_FIND_ALL_BY_CONDITION = "SELECT * FROM courses WHERE condition_id != ?;";

    public void save(String name, String theme, int duration, int teacherId, int conditionId)
            throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, theme);
            preparedStatement.setInt(3, duration);
            preparedStatement.setInt(4, teacherId);
            preparedStatement.setInt(5, conditionId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public Set<Course> findAllByCondition(int conditionId) {
        Set<Course> courses = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_CONDITION);
            preparedStatement.setInt(1, conditionId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getLong("id"),
                        resultSet.getString("name"), resultSet.getString("theme"),
                        resultSet.getInt("duration"), resultSet.getInt("teacher_id"),
                        resultSet.getInt("condition_id"));
                courses.add(course);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return courses;
    }
}
