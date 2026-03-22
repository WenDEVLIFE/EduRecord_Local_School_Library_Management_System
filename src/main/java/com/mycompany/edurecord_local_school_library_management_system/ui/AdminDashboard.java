package com.mycompany.edurecord_local_school_library_management_system.ui;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.AdminHomePanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.BookManagementPanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.UserManagementPanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.TransactionManagementPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Modern Admin Dashboard for EduRecord.
 * Features a sidebar navigation and a central content area.
 * 
 * @author Antigravity
 */
public class AdminDashboard extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public AdminDashboard() {
        setTitle("EduRecord Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // --- Sidebar ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        sidebar.setPreferredSize(new Dimension(250, 800));
        sidebar.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        JLabel logoLabel = new JLabel("EDURECORD", JLabel.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 40, 20);
        sidebar.add(logoLabel, gbc);

        gbc.insets = new Insets(5, 10, 5, 10);
        sidebar.add(createSidebarButton("Dashboard", "home"), gbc);
        gbc.gridy = 1;
        sidebar.add(createSidebarButton("Manage Books", "books"), gbc);
        gbc.gridy = 2;
        sidebar.add(createSidebarButton("Manage Users", "users"), gbc);
        gbc.gridy = 3;
        sidebar.add(createSidebarButton("Transactions", "transactions"), gbc);
        gbc.gridy = 4;

        gbc.gridy = 5;
        gbc.weighty = 1.0;
        sidebar.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767)), gbc);

        gbc.weighty = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 30, 10);
        JButton logoutBtn = createSidebarButton("Logout", "logout");
        logoutBtn.addActionListener(e -> {
            com.mycompany.edurecord_local_school_library_management_system.services.SessionManager.clearSession();
            dispose();
            new LoginFrame().setVisible(true);
        });
        sidebar.add(logoutBtn, gbc);

        // --- Content Area ---
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Panels
        contentPanel.add(new AdminHomePanel(), "home");
        contentPanel.add(new BookManagementPanel(), "books");
        contentPanel.add(new UserManagementPanel(), "users");
        contentPanel.add(new TransactionManagementPanel(), "transactions");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setPreferredSize(new Dimension(200, 40));

        // Hover effect or selection focus could be added here
        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
