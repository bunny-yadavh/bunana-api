package in.bunny.banana.peripherals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private static final long serialVersionUID = -6921462126880570161L;

    private final JButton blogin = new JButton("Login");
    private final JButton registerBtn = new JButton("Create an Account");
    private final JTextField txuser = new JTextField(20);
    private final JPasswordField pass = new JPasswordField(20);
    private final LoginData ldata = new LoginData();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }

    LoginGUI() {
        super("Login Authentication");

        // === Fullscreen window ===
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // === Background panel ===
        JPanel bgPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(50, 90, 160),
                        getWidth(), getHeight(),
                        new Color(20, 30, 60));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // === Card panel ===
        JPanel cardPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(255, 255, 255, 230),
                        getWidth(), getHeight(),
                        new Color(245, 245, 245, 240));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(420, 450));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // === Layout ===
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === Title ===
        JLabel title = new JLabel("Welcome to Banana Game", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(40, 40, 90));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(title, gbc);

        // Username label + field
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        setupTextField(txuser, "Enter Username...");
        cardPanel.add(txuser, gbc);

        // Password label + field
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        setupPasswordField(pass, "Enter Password...");
        cardPanel.add(pass, gbc);

        // === Login Button ===
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        blogin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        blogin.setBackground(new Color(70, 130, 255));
        blogin.setForeground(Color.WHITE);
        blogin.setFocusPainted(false);
        blogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cardPanel.add(blogin, gbc);

        // === Register Button (new) ===
        gbc.gridy++;
        registerBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setForeground(Color.BLUE);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cardPanel.add(registerBtn, gbc);

        bgPanel.add(cardPanel);
        add(bgPanel);

        // === Action Handlers ===
        actionlogin();
        registerBtn.addActionListener(e -> {
            // Open the RegisterGUI window
            new RegisterGUI(this); // This will open the registration window
        });

        
        setVisible(true);
    }

    private void setupTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    private void setupPasswordField(JPasswordField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setForeground(Color.GRAY);
        field.setEchoChar((char) 0);
        field.setText(placeholder);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                }
            }
        });
    }

    // 🔵 LOGIN ACTION ======================
    public void actionlogin() {
        blogin.addActionListener(ae -> {
            String puname = txuser.getText();
            String ppaswd = String.valueOf(pass.getPassword());

            if (ldata.checkPassword(puname, ppaswd)) {
                JOptionPane.showMessageDialog(this,
                        "✅ Login Successful! Welcome " + puname,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                GameGUI theGame = new GameGUI(puname);
                theGame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Incorrect Username or Password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);

                txuser.setText("Enter Username...");
                pass.setText("Enter Password...");
                txuser.setForeground(Color.GRAY);
                pass.setForeground(Color.GRAY);
                pass.setEchoChar((char) 0);
            }
        });
    }
    
    

    
}
