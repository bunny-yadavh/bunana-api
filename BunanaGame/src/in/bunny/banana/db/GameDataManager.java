package in.bunny.banana.db;

import java.sql.*;

/**
 * Handles saving and retrieving game data (score, correct answers, etc.)
 * Uses DBConnection for database connectivity.
 */
public class GameDataManager {

    /**
     * Saves the player's game result into the database.
     */
    public static boolean saveGameResult(String playerName, int score, int correct, int wrong, int timeLeft) {
        String sql = "INSERT INTO game_scores (player_name, score, correct_count, wrong_count, time_left) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, (playerName != null && !playerName.trim().isEmpty()) ? playerName : "Guest");
            stmt.setInt(2, score);
            stmt.setInt(3, correct);
            stmt.setInt(4, wrong);
            stmt.setInt(5, timeLeft);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Failed to save game result: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the highest score for a player.
     */
    public static int getHighestScore(String playerName) {
        String sql = "SELECT MAX(score) AS high_score FROM game_scores WHERE player_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("high_score");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch highest score: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Prints all previous game results for debugging or leaderboard.
     */
    public static void printAllScores() {
        String sql = "SELECT player_name, score, correct_count, wrong_count, time_left, created_at FROM game_scores ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("📊 Player Game Results:");
            while (rs.next()) {
                System.out.println("👤 " + rs.getString("player_name") +
                        " | Score: " + rs.getInt("score") +
                        " | ✅ " + rs.getInt("correct_count") +
                        " | ❌ " + rs.getInt("wrong_count") +
                        " | ⏱ " + rs.getInt("time_left") +
                        " | 📅 " + rs.getTimestamp("created_at"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching game scores: " + e.getMessage());
        }
    }

    public static String getPlayerName(String username) {
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(
                "SELECT username FROM users WHERE username = ?"
            );
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username != null ? username : "Guest";
    }


}
