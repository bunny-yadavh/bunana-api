package in.bunny.banana.peripherals;

import java.awt.*;
import in.bunny.banana.db.GameDataManager;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import in.bunny.banana.engine.GameEngine;

import in.bunny.banana.peripherals.LeaderBoard;


public class GameGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel questArea;
    private JLabel welcomeLabel;

    private GameEngine myGame;
    private BufferedImage currentGame;
    private JTextArea infoArea;

    private JLabel scoreLabel, correctLabel, wrongLabel, timerLabel;
    private JButton restartBtn;
    private int correctCount = 0, wrongCount = 0, timeLeft = 60;
    private Timer gameTimer;
    private JPanel buttonPanel;    

    public GameGUI(String player) {
        super(" What is the Missing Value?");
        initGame(player);
        setVisible(true);
    }

    public GameGUI() {
        this(null);
    }

    private void initGame(String player) {
        // ---------- Full Screen ----------
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(new Color(15, 15, 15));

        // ---------- Game Engine Setup ----------
        myGame = new GameEngine(player);
        currentGame = myGame.nextGame();

        // ---------- TOP PANEL ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(20, 30, 10, 30));
        topPanel.setBackground(new Color(25, 25, 25));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 10));
        statsPanel.setBackground(new Color(25, 25, 25));

        scoreLabel = createStatLabel("Score: 0", new Color(0, 200, 255));
        correctLabel = createStatLabel("✅ Correct: 0", new Color(0, 255, 140));
        wrongLabel = createStatLabel("❌ Wrong: 0", new Color(255, 80, 80));
        timerLabel = createStatLabel("⏱ Time: 60s", new Color(255, 220, 0));

        statsPanel.add(scoreLabel);
        statsPanel.add(correctLabel);
        statsPanel.add(wrongLabel);
        statsPanel.add(timerLabel);

        restartBtn = createRestartButton();
        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        rightTopPanel.setBackground(new Color(25, 25, 25));
        rightTopPanel.add(restartBtn);

        topPanel.add(statsPanel, BorderLayout.CENTER);
        topPanel.add(rightTopPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
     // ----------- Welcome Label From Database ----------
        String playerName = GameDataManager.getPlayerName(player);

        welcomeLabel = new JLabel("Welcome, " + playerName + "!");
        welcomeLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0, 200, 255));
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        welcomeLabel.setOpaque(false);

        // Add welcome label to TOP-LEFT
        topPanel.add(welcomeLabel, BorderLayout.WEST);


        // ---------- CENTER (Game Image) ----------
        questArea = new JLabel(new ImageIcon(currentGame));
        questArea.setHorizontalAlignment(SwingConstants.CENTER);
        questArea.setBorder(new LineBorder(new Color(50, 50, 50), 3, true));
        questArea.setBackground(new Color(30, 30, 30));
        questArea.setOpaque(true);
        add(questArea, BorderLayout.CENTER);

        // ---------- BOTTOM (Buttons + Info) ----------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(15, 15, 15));

        // Number buttons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(15, 15, 15));
        for (int i = 0; i <= 9; i++) {
            JButton btn = createRoundButton(String.valueOf(i));
            buttonPanel.add(btn);
        }

        // Info text below buttons
        infoArea = new JTextArea("🧩 What is the value of the banana?");
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Segoe UI", Font.BOLD, 22));
        infoArea.setForeground(Color.WHITE);
        infoArea.setBackground(new Color(20, 20, 20));
        infoArea.setBorder(new CompoundBorder(
                new LineBorder(new Color(70, 70, 70), 2, true),
                new EmptyBorder(15, 20, 15, 20)
        ));
        infoArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setMaximumSize(new Dimension(800, 100));

//        bottomPanel.add(buttonPanel);
//        bottomPanel.add(Box.createVerticalStrut(10));
//        bottomPanel.add(infoArea);
        
     // =================== BOTTOM PANEL ===================
        JPanel bottomPanel1 = new JPanel(new BorderLayout());
        bottomPanel1.setBackground(new Color(15, 15, 15));
        bottomPanel1.setBorder(new EmptyBorder(10, 20, 10, 20));

        // ------ LEFT SIDE (Number Buttons + Info) ------
        JPanel leftArea = new JPanel();
        leftArea.setLayout(new BoxLayout(leftArea, BoxLayout.Y_AXIS));
        leftArea.setBackground(new Color(15, 15, 15));

        leftArea.add(buttonPanel);           
        leftArea.add(Box.createVerticalStrut(10));
        leftArea.add(infoArea);              

        bottomPanel1.add(leftArea, BorderLayout.WEST);

        // ------ RIGHT SIDE (Leaderboard + Exit Buttons) ------
        JPanel rightButtons = new JPanel();
        rightButtons.setLayout(new BoxLayout(rightButtons, BoxLayout.Y_AXIS));
        rightButtons.setBackground(new Color(15, 15, 15));

        // 🔹 Make Leaderboard button identical to Restart style
        JButton leaderboardBtn = new JButton("📊 Leaderboard");
        copyRestartStyle(leaderboardBtn);
        leaderboardBtn.addActionListener(e -> LeaderBoard.showLeaderboard(this));

        // 🔹 Make Exit button identical to Restart style
        JButton exitBtn = new JButton("🚪 Exit");
        copyRestartStyle(exitBtn);
        exitBtn.addActionListener(e -> new ExitGUI(this));

        rightButtons.add(Box.createVerticalStrut(10));
        rightButtons.add(leaderboardBtn);
        rightButtons.add(Box.createVerticalStrut(10));
        rightButtons.add(exitBtn);

        bottomPanel1.add(rightButtons, BorderLayout.EAST);

        // 🔥 Add final bottom panel to FRAME (NOT mainPanel)
        add(bottomPanel1, BorderLayout.SOUTH);








        add(bottomPanel1, BorderLayout.SOUTH);

        startTimer();
    }
    
    private void copyRestartStyle(JButton btn) {
        btn.setFont(new Font("Poppins", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(60, 60, 60));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(90, 90, 90), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setMaximumSize(new Dimension(160, 40));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 200, 255));
                btn.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 60, 60));
                btn.setForeground(Color.WHITE);
            }
        });
    }



    // ---------- Component Styling ----------

    private JLabel createStatLabel(String text, Color accent) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Poppins", Font.BOLD, 18));
        lbl.setForeground(accent);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(35, 35, 35));
        lbl.setBorder(new LineBorder(accent.darker(), 2, true));
        return lbl;
    }

    private JButton createRestartButton() {
        JButton btn = new JButton("🔁 Restart");
        btn.setFont(new Font("Poppins", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(60, 60, 60));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(90, 90, 90), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 40));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 200, 255));
                btn.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 60, 60));
                btn.setForeground(Color.WHITE);
            }
        });
        btn.addActionListener(e -> restartGame());
        return btn;
    }

    private JButton createRoundButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Consolas", Font.BOLD, 22));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(65, 65));

        JButton circle = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int diameter = Math.min(getWidth(), getHeight());
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 200, 255),
                        diameter, diameter, new Color(0, 255, 150));
                g2.setPaint(gp);
                g2.fillOval(0, 0, diameter, diameter);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        circle.setFont(btn.getFont());
        circle.setForeground(btn.getForeground());
        circle.setFocusPainted(false);
        circle.setContentAreaFilled(false);
        circle.setBorder(new EmptyBorder(10, 10, 10, 10));
        circle.setOpaque(false);
        circle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        circle.setPreferredSize(btn.getPreferredSize());
        circle.addActionListener(this);

        circle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                circle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                circle.setForeground(Color.BLACK);
            }
        });

        return circle;
    }

    // ---------- Game Logic ----------

    @Override
    public void actionPerformed(ActionEvent e) {
        if (timeLeft <= 0) return;

        String cmd = e.getActionCommand();
        if (!cmd.matches("\\d")) return;

        int solution = Integer.parseInt(cmd);
        boolean correct = myGame.checkSolution(solution);
        int score = myGame.getScore();

        if (correct) {
            correctCount++;
            infoArea.setForeground(new Color(0, 255, 150));
            infoArea.setText("✅ Correct! Awesome job! Score: " + score);
            currentGame = myGame.nextGame();
            questArea.setIcon(new ImageIcon(currentGame));
        } else {
            wrongCount++;
            infoArea.setForeground(new Color(255, 80, 80));
            infoArea.setText("❌ Incorrect. Try again! Score: " + score);
        }

        scoreLabel.setText("Score: " + score);
        correctLabel.setText("✅ Correct: " + correctCount);
        wrongLabel.setText("❌ Wrong: " + wrongCount);
    }

    private void startTimer() {
        timeLeft = 60;
        if (gameTimer != null) gameTimer.stop();

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("⏱ Time: " + timeLeft + "s");

            if (timeLeft <= 0) {
                ((Timer) e.getSource()).stop();
                infoArea.setForeground(new Color(255, 80, 80));
                infoArea.setText("⏳ Time’s up! Final Score: " + myGame.getScore());
                disableButtons();

               

            }
        });

        gameTimer.start();
    }


    private void disableButtons() {
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton) comp.setEnabled(false);
        }
    }

    private void enableButtons() {
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton) comp.setEnabled(true);
        }
    }

    private void restartGame() {
        // ✅ Save last game before restart
        boolean saved = GameDataManager.saveGameResult(
            myGame.getPlayerName(),
            myGame.getScore(),
            correctCount,
            wrongCount,
            timeLeft
        );

        if (saved) {
            // ✅ Fetch highest score correctly for this player
//            int highScore = GameDataManager.getHighestScore(myGame.getPlayerName());

            // 🎨 Beautiful message panel (custom styled)
            JPanel panel = new JPanel();
            panel.setBackground(new Color(30, 30, 30));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

            JLabel title = new JLabel("🎮 Game Saved Successfully!");
            title.setFont(new Font("Poppins", Font.BOLD, 22));
            title.setForeground(new Color(0, 255, 150));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel playerLbl = new JLabel("👤 Player: " + (myGame.getPlayerName() != null ? myGame.getPlayerName() : "Guest"));
            JLabel scoreLbl = new JLabel("🏆 Your Score: " + myGame.getScore());
//            JLabel correctLbl = new JLabel("✅ Correct Answers: " + correctCount);
//            JLabel wrongLbl = new JLabel("❌ Wrong Answers: " + wrongCount);
//            JLabel timeLbl = new JLabel("⏱ Time Left: " + timeLeft + "s");
//            JLabel highLbl = new JLabel("🔥 Highest Score: " + highScore);

            Font infoFont = new Font("Segoe UI", Font.PLAIN, 18);
            Color textColor = Color.WHITE;

            for (JLabel lbl : new JLabel[]{playerLbl, scoreLbl}) {  // correctLbl, wrongLbl, timeLbl, highLbl
                lbl.setFont(infoFont);
                lbl.setForeground(textColor);
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(Box.createVerticalStrut(5));
                panel.add(lbl);
            }

            panel.add(Box.createVerticalStrut(15));
            panel.add(title, 0);

            JOptionPane.showMessageDialog(
                this,
                panel,
                "Game Data Saved",
                JOptionPane.PLAIN_MESSAGE
            );
        }

        // 🧹 Reset game state
        correctCount = 0;
        wrongCount = 0;
        timeLeft = 60;
        myGame.resetGame();
        currentGame = myGame.nextGame();
        questArea.setIcon(new ImageIcon(currentGame));

        scoreLabel.setText("Score: 0");
        correctLabel.setText("✅ Correct: 0");
        wrongLabel.setText("❌ Wrong: 0");
        timerLabel.setText("⏱ Time: 60s");

        infoArea.setText("🧩 New game started! What is the value of the banana?");
        infoArea.setForeground(Color.WHITE);

        enableButtons();
        startTimer();
        
     // after saving data successfully
        LeaderBoard.showLeaderboard(this);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }
}
