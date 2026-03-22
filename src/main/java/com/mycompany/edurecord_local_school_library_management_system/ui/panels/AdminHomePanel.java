package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.ActivityLog;
import com.mycompany.edurecord_local_school_library_management_system.repositories.ActivityLogRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.BookRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.UserRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.TransactionRepository;
import java.text.SimpleDateFormat;
import java.util.List;
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
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);

        // Fetch logs dynamically
        ActivityLogRepository logRepo = new ActivityLogRepository();
        List<ActivityLog> logs = logRepo.getRecentActivities();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, hh:mm a");

        for (ActivityLog log : logs) {
            model.addRow(new Object[] {
                    sdf.format(log.getCreatedAt()),
                    log.getUsername(),
                    log.getActionType(),
                    log.getDetails()
            });
        }

        if (logs.isEmpty()) {
            model.addRow(new Object[] { "-", "System", "Startup", "No recent activity recorded." });
        }

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
