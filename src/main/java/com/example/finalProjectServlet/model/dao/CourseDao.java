package com.example.finalProjectServlet.model.dao;

import com.example.finalProjectServlet.model.DBManager;
import com.example.finalProjectServlet.model.entity.Course;
import com.example.finalProjectServlet.model.entity.Mark;
import com.example.finalProjectServlet.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CourseDao {

    private static final String SQL_SAVE_COURSE = "INSERT INTO courses" +
            "  (name, theme, duration, teacher_id, condition_id, number_of_students) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    private static final String SQL_FIND_ALL_BY_CONDITION_NOT_EQUAL = "SELECT * FROM courses WHERE condition_id != ? ";

    private static final String SQL_DELETE_COURSE = "DELETE FROM courses WHERE id = ?;";

    private static final String SQL_FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE id = ?;";

    private static final String SQL_UPDATE_COURSE_BY_ID = "UPDATE courses SET name = ?, theme = ?, duration = ?, " +
            "teacher_id = ?, number_of_students = ? WHERE id = ?;";

    private static final String SQL_ADD_STUDENT_TO_COURSE = "INSERT INTO course_students_marks (course_id," +
            " student_id, mark_id) VALUES (?, ?, ?);";

    private static final String SQL_FIND_COURSE_BY_ID_AND_STUDENT = "SELECT * FROM course_students_marks WHERE" +
            " course_id = ? AND student_id = ?;";

    private static final String SQL_GET_NUMBER_OF_STUDENTS = "SELECT COUNT(*) FROM course_students_marks WHERE " +
            "course_id = ?;";

    private static final String SQL_FIND_COURSE_ID_BY_STUDENT = "SELECT course_id FROM course_students_marks WHERE " +
            "student_id = ?;";

    private static final String SQL_FIND_COURSE_STUDENTS = "SELECT * FROM course_students_marks WHERE " +
            "course_id = ?;";

    private static final String SQL_FIND_BY_ID_AND_CONDITION = "SELECT * FROM courses WHERE id = ? AND condition_id = ?;";

    private static final String SQL_FIND_ALL_BY_TEACHER_ID = "SELECT * FROM courses WHERE teacher_id = ?;";

    private static final String SQL_UPDATE_COURSE_CONDITION = "UPDATE courses SET condition_id = ? WHERE id = ?;";

    private static final String SQL_SAVE_STUDENT_MARK = "UPDATE course_students_marks SET mark_id = ? WHERE " +
            "course_id = ? AND student_id = ?;";

    private static final String SQL_GET_STUDENT_MARK = "SELECT mark_id FROM course_students_marks WHERE course_id = ? " +
            "AND student_id = ?;";

    private static final String SQL_GET_NUMBER_OF_COURSES = "SELECT COUNT(*) FROM courses WHERE condition_id != ? ";

    private static final String SQL_UPDATE_COURSE_STUDENTS = "UPDATE courses SET number_of_students = ? WHERE id = ?;";

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
            preparedStatement.setInt(6, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public List<Course> findAllByConditionNotEqual(int conditionId, int start, int total, String sortField,
                                                   String sortDir, String teacherId, String theme) {
        List<Course> courses = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            StringBuilder ps = new StringBuilder(SQL_FIND_ALL_BY_CONDITION_NOT_EQUAL);
            if (teacherId != null && !teacherId.isEmpty()) {
                ps.append("AND teacher_id = ").append(Integer.parseInt(teacherId)).append(" ");
            }
            if (theme != null && !theme.isEmpty()) {
                ps.append("AND theme = '").append(theme).append("' ");
            }
            preparedStatement = connection.prepareStatement(ps + "ORDER BY " +
                    sortField + " " + sortDir.toUpperCase() + " LIMIT " + (start - 1) + ", " + total + ";");
            preparedStatement.setInt(1, conditionId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getLong("id"),
                        resultSet.getString("name"), resultSet.getString("theme"),
                        resultSet.getInt("duration"), resultSet.getInt("teacher_id"),
                        resultSet.getInt("condition_id"), resultSet.getInt("number_of_students"));
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
                        resultSet.getInt("teacher_id"), resultSet.getInt("condition_id"),
                        resultSet.getInt("number_of_students"));
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
            preparedStatement.setInt(5, getNumberOfStudents(id));
            preparedStatement.setLong(6, id);
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
        updateCourseStudents(course_id);
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

    public Set<Course> findAllByIdAndCondition(Long id, int conditionId) {
        Set<Course> courses = new HashSet<>();
        PreparedStatement preparedStatement, preparedStatement1 = null;
        ResultSet resultSet, resultSet1 = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_ID_BY_STUDENT);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                preparedStatement1 = connection.prepareStatement(SQL_FIND_BY_ID_AND_CONDITION);
                preparedStatement1.setLong(1, resultSet.getLong("course_id"));
                preparedStatement1.setInt(2, conditionId);
                resultSet1 = preparedStatement1.executeQuery();
                if (resultSet1.next()) {
                    Course course = new Course(resultSet1.getLong("id"),
                            resultSet1.getString("name"), resultSet1.getString("theme"),
                            resultSet1.getInt("duration"), resultSet1.getInt("teacher_id"),
                            resultSet1.getInt("condition_id"),
                            resultSet1.getInt("number_of_students"));
                    courses.add(course);
                }
            }
            resultSet1.close();
            resultSet.close();
            preparedStatement1.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return courses;
    }

    public Set<Course> findAllByTeacherId(Long teacherId) {
        Set<Course> courses = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_TEACHER_ID);
            preparedStatement.setLong(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getLong("id"),
                        resultSet.getString("name"), resultSet.getString("theme"),
                        resultSet.getInt("duration"), resultSet.getInt("teacher_id"),
                        resultSet.getInt("condition_id"), resultSet.getInt("number_of_students"));
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

    public void updateCourseCondition(Long course_id, int condition_id) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_CONDITION);
            preparedStatement.setInt(1, condition_id);
            preparedStatement.setLong(2, course_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public Map<User, Mark> findCourseStudentsAndMarks(Long id) {
        Map<User, Mark> studentsMarks = new HashMap<>();
        User student;
        Mark mark;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        UserDao userDao = new UserDao();
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_STUDENTS);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student = userDao.findById(resultSet.getLong("student_id"));
                mark = Mark.getMark(resultSet.getInt("mark_id"));
                studentsMarks.put(student, mark);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return studentsMarks;
    }

    public void saveStudentsMarks(Long course_id, String[] students, String[] marks) {
        Connection connection = null;
        UserDao userDao = new UserDao();
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_STUDENT_MARK);
            for (int i = 0; i < students.length; i++) {
                System.out.println("Mark id: " + Mark.getMarkId(marks[i]));
                preparedStatement.setInt(1, Mark.getMarkId(marks[i]));
                preparedStatement.setLong(2, course_id);
                preparedStatement.setLong(3, userDao.findByUsername(students[i]).getId());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public Mark findStudentMark(Long courseId, Long studentId) {
        Mark mark = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_STUDENT_MARK);
            preparedStatement.setLong(1, courseId);
            preparedStatement.setLong(2, studentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mark = Mark.getMark(resultSet.getInt("mark_id"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return mark;
    }

    public int getNumberOfCourses(int conditionId, String teacherId, String theme) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        int numberOfCourses = 0;
        try {
            connection = DBManager.getInstance().getConnection();
            StringBuilder ps = new StringBuilder(SQL_GET_NUMBER_OF_COURSES);
            if (teacherId != null && !teacherId.isEmpty()) {
                ps.append("AND teacher_id = ").append(Integer.parseInt(teacherId)).append(" ");
            }
            if (theme != null && !theme.isEmpty()) {
                ps.append("AND theme = '").append(theme).append("' ");
            }
            preparedStatement = connection.prepareStatement(ps.append(";").toString());
            preparedStatement.setInt(1, conditionId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfCourses = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return numberOfCourses;
    }

    public void updateCourseStudents(Long id) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE_STUDENTS);
            preparedStatement.setInt(1, getNumberOfStudents(id));
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }
}
