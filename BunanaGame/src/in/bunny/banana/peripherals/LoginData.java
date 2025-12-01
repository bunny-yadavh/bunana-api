//package in.bunny.banana.peripherals;
///**
// * Basic class. To do: 
// * link against external database.
// * signup mechanism to create account. 
// * Encryption
// * Etc.
// * @author Marc Conrad
// *
// */
//public class LoginData {
//	/**
//	 * Returns true if passwd matches the username given.
//	 * @param username
//	 * @param passwd
//	 * @return
//	 */
//	boolean checkPassword(String username, String passwd) { 
//		if( username.equals("bunny") && passwd.equals("123123")) return true; 
//		return false; 
//		
//	}
//}




package in.bunny.banana.peripherals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import in.bunny.banana.db.DBConnection;

/**
 * Handles login and registration operations.
 */
public class LoginData {

    /**
     * Checks if the username and password are valid.
     * If username does not exist, registers the user.
     */
    boolean checkPassword(String username, String password) {
        boolean isValid = false;

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Database connection is null!");
                return false;
            }

            // Check if the user exists
            String selectQuery = "SELECT pass_word FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // User exists → check password
                String storedPass = rs.getString("pass_word");
                if (storedPass.equals(password)) {
                    isValid = true;
                    System.out.println("✅ Login successful for user: " + username);
                } else {
                    System.out.println("❌ Wrong password for user: " + username);
                }
            } 

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}



