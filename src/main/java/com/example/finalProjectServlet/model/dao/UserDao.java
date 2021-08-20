package com.example.finalProjectServlet.model.dao;

import com.example.finalProjectServlet.model.DBManager;
import com.example.finalProjectServlet.model.entity.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDao {

    private static final String SQL_SAVE_USER = "INSERT INTO users" +
            "  (full_name, username, email, password, role_id, status_id) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?;";

    private static final String SQL_FIND_ALL_BY_ROLE = "SELECT * FROM users WHERE role_id = ?;";

    private static final String SQL_UPDATE_USER_STATUS = "UPDATE users SET status_id=? WHERE id=?";

    public void save(String fullName, String username, String email, String password, int roleId, int statusId)
            throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setInt(5, roleId);
            preparedStatement.setInt(6, statusId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public User findByUsername(String username) {
        User user = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            preparedStatement = con.prepareStatement(SQL_FIND_BY_USERNAME);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getString("full_name"),
                        resultSet.getString("username"), resultSet.getString("email"),
                        resultSet.getString("password"), resultSet.getInt("role_id"),
                        resultSet.getInt("status_id"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    public Set<User> findAllByRole(int roleId) {
        Set<User> users = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_ROLE);
            preparedStatement.setInt(1, roleId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("full_name"),
                        resultSet.getString("username"), resultSet.getString("email"),
                        resultSet.getString("password"), resultSet.getInt("role_id"),
                        resultSet.getInt("status_id"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return users;
    }

    public void updateUserStatus(Long id, int statusId) {
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER_STATUS);
            pstmt.setInt(1, statusId);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }
}
