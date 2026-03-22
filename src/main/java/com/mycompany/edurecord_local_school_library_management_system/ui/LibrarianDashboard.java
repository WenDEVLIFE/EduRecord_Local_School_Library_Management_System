package com.mycompany.edurecord_local_school_library_management_system.ui;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.BookManagementPanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.LibrarianHomePanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.StudentManagementPanel;
import com.mycompany.edurecord_local_school_library_management_system.ui.panels.TransactionManagementPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Specialized Dashboard for the Librarian.
 * 
 * @author Antigravity
 */
public class LibrarianDashboard extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public LibrarianDashboard() {
        setTitle("EduRecord Librarian Dashboard");
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

        JLabel libLabel = new JLabel("LIBRARIAN", JLabel.CENTER);
        libLabel.setForeground(ColorPalette.SECONDARY_GOLD);
        libLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 40, 20);
        sidebar.add(libLabel, gbc);

        gbc.insets = new Insets(5, 10, 5, 10);
        sidebar.add(createSidebarButton("Dashboard", "home"), gbc);
        gbc.gridy = 1;
        sidebar.add(createSidebarButton("Books", "books"), gbc);
        gbc.gridy = 2;
        sidebar.add(createSidebarButton("Students", "students"), gbc);
        gbc.gridy = 3;
        sidebar.add(createSidebarButton("Transactions", "transactions"), gbc);

        gbc.gridy = 4;
        gbc.weighty = 1.0;
        sidebar.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767)), gbc);

        gbc.weighty = 0;
        gbc.gridy = 5;
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

        // Home Panel
        contentPanel.add(new LibrarianHomePanel(), "home");

        contentPanel.add(new BookManagementPanel(), "books");
        contentPanel.add(new StudentManagementPanel(), "students");
        contentPanel.add(new TransactionManagementPanel(), "transactions");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        return btn;
    }

    public static void main(String[] args) {
        LibrarianDashboard lib = new LibrarianDashboard();
        lib.setVisible(true);
    }
}
