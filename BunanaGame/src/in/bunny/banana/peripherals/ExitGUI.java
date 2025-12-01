package in.bunny.banana.peripherals;


import javax.swing.*;
import java.awt.*;

public class ExitGUI {

    public ExitGUI(JFrame parentFrame) {

        UIManager.put("OptionPane.background", new Color(25, 25, 25));
        UIManager.put("Panel.background", new Color(25, 25, 25));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(70, 70, 70));
        UIManager.put("Button.foreground", Color.WHITE);

        int choice = JOptionPane.showConfirmDialog(
                parentFrame,
                "Are you sure you want to exit the game?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            parentFrame.dispose();
            System.exit(0);   // fully close application
        }
    }
}

