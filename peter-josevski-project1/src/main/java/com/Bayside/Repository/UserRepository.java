package com.Bayside.Repository;

import com.Bayside.Model.*;
import com.Bayside.Repository.*;
import java.sql.*;

public class UserRepository {

    public User addUser(User user) throws SQLException {
        try (Connection con = ConnectionFactory.createConnection()) {
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);




            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, "employee");

            // Execute the INSERT statement
            int numberOfRecordsAdded = pstmt.executeUpdate();

            // Retrieve the id that got automatically generated
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return new User(id, user.getUsername(), user.getPassword(), user.getRole());
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM users as u WHERE u.username = ?";
            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery(); // ResultSet represents a temporary table that contains all data that we have
            // queried for

            if (rs.next()) { // returns a boolean indicating whether there is a record or not for the "next" row AND iterates to the next row
                int id = rs.getInt("id");
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String role = rs.getString("role");

                return new User(id, un, pw, role);
            } else {
                return null;
            }

        }
    }

}
