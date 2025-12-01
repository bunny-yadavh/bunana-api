package in.bunny.banana.peripherals;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import in.bunny.banana.db.DBConnection;

import java.awt.*;
import java.sql.*;

/**
 * Displays a leaderBoard (Top 5 players) in a stylish popup dialog.
 * Requires GameDataManager and DBConnection.
 */
public class LeaderBoard {

    /**
     * Shows the leaderBoard dialog with top 5 players.
     */
    public static void showLeaderboard(JFrame parent) {
        ResultSet rs = fetchTopScores();
        if (rs == null) {
            JOptionPane.showMessageDialog(parent,
                    "⚠️ Failed to load leaderboard.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build table model
        String[] columns = {"🏅 Rank", "👤 Player Name", "🔥 High Score"};
        Object[][] data = new Object[5][3];

        try {
            int rank = 1;
            while (rs.next()) {
                data[rank - 1][0] = rank;
                data[rank - 1][1] = rs.getString("player_name");
                data[rank - 1][2] = rs.getInt("top_score");
                rank++;
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parent,
                    "⚠️ Error reading leaderboard data.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTable table = new JTable(data, columns);
        table.setFont(new Font("Poppins", Font.PLAIN, 16));
        table.setRowHeight(32);
        table.setEnabled(false);
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 17));

        // Styling rows (optional: color for top 3 players)
     // Styling rows (optional: color for top 3 players)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);

                // Base color
                lbl.setBackground(Color.WHITE);
                lbl.setOpaque(true);

                // Highlight top 3
                if (row == 0) lbl.setBackground(new Color(255, 223, 88));      // 🥇 Gold
                else if (row == 1) lbl.setBackground(new Color(192, 192, 192)); // 🥈 Silver
                else if (row == 2) lbl.setBackground(new Color(205, 127, 50));  // 🥉 Bronze

                return lbl;
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title Panel
        JLabel titleLabel = new JLabel("🏆 Leaderboard - Top 5 Players", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        titleLabel.setForeground(new Color(30, 30, 30));
        titleLabel.setBorder(new EmptyBorder(15, 10, 15, 10));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                parent,
                mainPanel,
                "Leaderboard",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Fetch top 5 players from database.
     */
    private static ResultSet fetchTopScores() {
        String sql = "SELECT player_name, MAX(score) AS top_score " +
                     "FROM game_scores " +
                     "GROUP BY player_name " +
                     "ORDER BY top_score DESC " +
                     "LIMIT 5";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch leaderboard: " + e.getMessage());
            return null;
        }
    }
}
