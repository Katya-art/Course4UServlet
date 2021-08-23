package com.example.finalProjectServlet.model.dao;

import com.example.finalProjectServlet.model.DBManager;
import com.example.finalProjectServlet.model.entity.Course;

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

    private static final String SQL_DELETE_COURSE = "DELETE FROM courses WHERE id = ?;";

    private static final String SQL_FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE id = ?;";

    private static final String SQL_UPDATE_COURSE_BY_ID = "UPDATE courses SET name = ?, theme = ?, duration = ?, " +
            "teacher_id = ? WHERE id = ?;";

    private static final String SQL_ADD_STUDENT_TO_COURSE = "INSERT INTO course_students_marks (course_id," +
            " student_id, mark_id) VALUES (?, ?, ?);";

    private static final String SQL_FIND_COURSE_BY_ID_AND_STUDENT = "SELECT * FROM course_students_marks WHERE" +
            " course_id = ? AND student_id = ?;";

    private static final String SQL_GET_NUMBER_OF_STUDENTS = "SELECT COUNT(*) FROM course_students_marks WHERE " +
            "course_id = ?;";

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

    public void deleteCourse(Long id) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public Course findById(Long id) {
        Course course = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course = new Course(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("theme"), resultSet.getInt("duration"),
                        resultSet.getInt("teacher_id"), resultSet.getInt("condition_id"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return course;
    }

    public void updateCourse(Long id, String name, String theme, int duration, int teacher_id) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_BY_ID);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, theme);
            preparedStatement.setInt(3, duration);
            preparedStatement.setInt(4, teacher_id);
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public void addStudentToCourse(Long course_id, Long student_id, Long mark_id) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_STUDENT_TO_COURSE);
            preparedStatement.setLong(1, course_id);
            preparedStatement.setLong(2, student_id);
            preparedStatement.setLong(3, mark_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public boolean checkCourseForStudents(Long courseId, Long studentId) {
        boolean isContain = false;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID_AND_STUDENT);
            preparedStatement.setLong(1, courseId);
            preparedStatement.setLong(2, studentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isContain = true;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return isContain;
    }

    public int getNumberOfStudents(Long courseId) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        int numberOfStudents = 0;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_NUMBER_OF_STUDENTS);
            preparedStatement.setLong(1, courseId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfStudents = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return numberOfStudents;
    }
}