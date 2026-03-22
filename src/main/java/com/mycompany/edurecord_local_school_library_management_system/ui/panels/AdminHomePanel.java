package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.repositories.BookRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.UserRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.TransactionRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * High-level overview panel for the Administrator.
 * 
 * @author Antigravity
 */
public class AdminHomePanel extends JPanel {

    public AdminHomePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel title = new JLabel("System Overview & Statistics");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- Stats Section ---
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        BookRepository bookRepo = new BookRepository();
        UserRepository userRepo = new UserRepository();
        TransactionRepository transRepo = new TransactionRepository();

        statsPanel.add(createStatCard("Total Books", String.valueOf(bookRepo.getTotalBooksCount()),
                ColorPalette.PRIMARY_BURGUNDY));
        statsPanel.add(createStatCard("Active Users", String.valueOf(userRepo.getTotalActiveUsers()),
                ColorPalette.TEXT_CHARCOAL));
        statsPanel.add(createStatCard("Total Transactions", String.valueOf(transRepo.getTotalTransactionsCount()),
                ColorPalette.SECONDARY_GOLD));
        statsPanel.add(createStatCard("Overdue Books", String.valueOf(transRepo.getOverdueBooksCount()), Color.RED));

        add(statsPanel, BorderLayout.NORTH);

        // --- Recent Activity Section ---
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(BorderFactory.createTitledBorder("Recent System Activity"));

        String[] cols = { "Time", "User", "Action", "Details" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);

        model.addRow(new Object[] { "10:15 AM", "admin", "User Created", "New Librarian added" });
        model.addRow(new Object[] { "09:30 AM", "librarian", "Book Added", "Java Essentials (ISBN: 445)" });
        model.addRow(new Object[] { "Yesterday", "std_2024", "Login", "Successful student access" });

        JScrollPane scroll = new JScrollPane(table);
        activityPanel.add(scroll, BorderLayout.CENTER);

        add(activityPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value, Color valueColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel titleLbl = new JLabel(title, JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel valLbl = new JLabel(value, JLabel.CENTER);
        valLbl.setFont(new Font("Serif", Font.BOLD, 32));
        valLbl.setForeground(valueColor);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valLbl, BorderLayout.CENTER);

        return card;
    }
}
