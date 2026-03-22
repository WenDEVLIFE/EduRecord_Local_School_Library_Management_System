package com.mycompany.edurecord_local_school_library_management_system.ui;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.BookSearchPanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.BorrowHistoryPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Learner-centric Dashboard for Students.
 * Shows personal borrowing stats and searchable library catalog.
 * 
 * @author Antigravity
 */
public class StudentDashboard extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public StudentDashboard() {
        setTitle("EduRecord Student Dashboard - Digital Integration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
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
        gbc.weightx = 1.0;

        JLabel logoLabel = new JLabel("STUDENT PORTAL", JLabel.CENTER);
        logoLabel.setForeground(ColorPalette.SECONDARY_GOLD);
        logoLabel.setFont(new Font("Serif", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 10, 40, 10);
        sidebar.add(logoLabel, gbc);

        gbc.insets = new Insets(5, 10, 5, 10);
        sidebar.add(createSidebarButton("Library Catalog", "search"), gbc);
        gbc.gridy = 1;
        sidebar.add(createSidebarButton("My Borrowing History", "history"), gbc);

        gbc.weighty = 1.0;
        gbc.gridy = 2;
        sidebar.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767)), gbc);

        gbc.weighty = 0;
        gbc.gridy = 3;
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

        contentPanel.add(new BookSearchPanel(), "search");
        contentPanel.add(new BorrowHistoryPanel(), "history");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}
