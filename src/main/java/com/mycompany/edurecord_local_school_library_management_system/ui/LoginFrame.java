package com.mycompany.edurecord_local_school_library_management_system.ui;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.User;
import com.mycompany.edurecord_local_school_library_management_system.services.AuthenticationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Modern Login Screen for EduRecord.
 * 
 * @author Antigravity
 */
public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private AuthenticationService authService;

    public LoginFrame() {
        this.authService = new AuthenticationService();

        setTitle("EduRecord Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new GridBagLayout()); // Using GridBagLayout for better control

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // --- Left Side: Branding ---
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("EduRecord");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Peñaranda Off-Campus");
        subLabel.setForeground(ColorPalette.SECONDARY_GOLD);
        subLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(subLabel);
        leftPanel.add(Box.createVerticalGlue());

        // --- Right Side: Form ---
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginHeader = new JLabel("Welcome Back");
        loginHeader.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginHeader.setForeground(ColorPalette.TEXT_CHARCOAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(loginHeader, gbc);

        // Username
        gbc.gridy = 1;
        rightPanel.add(new JLabel("Username:"), gbc);
        userField = new JTextField(15);
        gbc.gridy = 2;
        rightPanel.add(userField, gbc);

        // Password
        gbc.gridy = 3;
        rightPanel.add(new JLabel("Password:"), gbc);
        passField = new JPasswordField(15);
        gbc.gridy = 4;
        rightPanel.add(passField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this::handleLogin);
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 30, 10, 30);
        rightPanel.add(loginButton, gbc);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        setContentPane(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        User user = authService.authenticate(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + user.getFirstName());
            dispose();
            if ("ADMIN".equals(user.getRole())) {
                new AdminDashboard().setVisible(true);
            } else if ("LIBRARIAN".equals(user.getRole())) {
                new LibrarianDashboard().setVisible(true);
            } else {
                new MainFrame().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
