package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for students to view their personal borrowing history.
 * 
 * @author Antigravity
 */
public class BorrowHistoryPanel extends JPanel {

    private JTable historyTable;
    private DefaultTableModel tableModel;

    public BorrowHistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel title = new JLabel("My Borrowing History");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        header.add(title);

        add(header, BorderLayout.NORTH);

        // --- Info Section ---
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        infoPanel.add(createCard("Currently Borrowed", "2", ColorPalette.PRIMARY_BURGUNDY));
        infoPanel.add(createCard("Books Returned", "14", ColorPalette.TEXT_CHARCOAL));
        infoPanel.add(createCard("Overdue Fines", "None", ColorPalette.SECONDARY_GOLD));

        add(infoPanel, BorderLayout.CENTER);

        // --- Table Section ---
        String[] columns = { "Book Title", "ISBN", "Borrow Date", "Return Date", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(25);
        historyTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detailed Records"));

        // Mock data
        tableModel.addRow(new Object[] { "Software Engineering", "112-233", "2024-03-10", "2024-03-17", "RETURNED" });
        tableModel.addRow(new Object[] { "Computer Networks", "445-566", "2024-03-20", "-", "BORROWED" });

        add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setPreferredSize(new Dimension(800, 300));
    }

    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel t = new JLabel(title, JLabel.CENTER);
        t.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JLabel v = new JLabel(value, JLabel.CENTER);
        v.setFont(new Font("Serif", Font.BOLD, 24));
        v.setForeground(color);

        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        return card;
    }
}
