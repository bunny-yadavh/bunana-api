package in.bunny.banana.peripherals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import in.bunny.banana.db.DBConnection;

/**
 * Handles user registration operations.
 */
public class RegisterData {

    /**
     * Registers a new user if the username does not exist.
     *
     * @param username
     * @param password
     * @return true if registration successful, false if username exists or error
     */
    public boolean registerUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                return false;
            }

            // Check if username already exists
            String checkQuery = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Username exists
                rs.close();
                checkStmt.close();
                return false;
            }

            // Insert new user
            String insertQuery = "INSERT INTO users(username, pass_word) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);

            boolean success = insertStmt.executeUpdate() > 0;

            rs.close();
            checkStmt.close();
            insertStmt.close();

            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}