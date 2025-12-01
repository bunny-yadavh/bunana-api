package in.bunny.banana.peripherals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterGUI extends JDialog {

    private final JTextField txuser = new JTextField(20);
    private final JPasswordField pass = new JPasswordField(20);
    private final JButton registerBtn = new JButton("Create Account");
    private final RegisterData rdata = new RegisterData();

    public RegisterGUI(JFrame parent) {
        super(parent, "Create New Account", true); // true → modal
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // === Background Panel with Gradient ===
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

        // === Card Panel with Rounded Gradient ===
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
        cardPanel.setPreferredSize(new Dimension(400, 300));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // === Layout ===
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === Title ===
        JLabel title = new JLabel("Create a New Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 40, 90));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        cardPanel.add(title, gbc);

        // Username
        gbc.gridy++; gbc.gridwidth = 1; gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cardPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        setupTextField(txuser, "Enter Username...");
        cardPanel.add(txuser, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cardPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        setupPasswordField(pass, "Enter Password...");
        cardPanel.add(pass, gbc);

        // Register Button
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        registerBtn.setBackground(new Color(70, 130, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardPanel.add(registerBtn, gbc);

        registerBtn.addActionListener(e -> registerAction());

        bgPanel.add(cardPanel);
        add(bgPanel);
        setVisible(true);
    }

    private void setupTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void setupPasswordField(JPasswordField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setEchoChar((char) 0);
        field.setText(placeholder);
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
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char) 0);
                }
            }
        });
    }

    private void registerAction() {
        String u = txuser.getText().trim();
        String p = String.valueOf(pass.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty() || u.equals("Enter Username...") || p.equals("Enter Password...")) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
            return;
        }

        if (rdata.registerUser(u, p)) {
            JOptionPane.showMessageDialog(this, "✅ Account created successfully!");
            System.out.println("Account created successfully...");
            dispose(); // close popup
        } else {
        	System.out.println("Error to create account.");
            JOptionPane.showMessageDialog(this, "❌ Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
