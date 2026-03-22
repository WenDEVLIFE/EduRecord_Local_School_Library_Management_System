package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for managing Book inventory.
 * 
 * @author Antigravity
 */
public class BookManagementPanel extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, authorField, isbnField, qtyField;

    public BookManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel titleLabel = new JLabel("Book Inventory Management");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        header.add(titleLabel);
        add(header, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columns = { "ID", "Title", "Author", "ISBN", "Quantity", "Category" };
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(25);
        bookTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Form Section ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Add / Edit Book"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;

        gbc.gridy = 0;
        formPanel.add(new JLabel("Book Title:"), gbc);
        gbc.gridy = 1;
        titleField = new JTextField();
        formPanel.add(titleField, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridy = 3;
        authorField = new JTextField();
        formPanel.add(authorField, gbc);

        gbc.gridy = 4;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridy = 5;
        isbnField = new JTextField();
        formPanel.add(isbnField, gbc);

        gbc.gridy = 6;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridy = 7;
        qtyField = new JTextField();
        formPanel.add(qtyField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        JButton addBtn = new JButton("Add Book");
        addBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        addBtn.setForeground(Color.WHITE);
        JButton updateBtn = new JButton("Update");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);

        gbc.gridy = 8;
        gbc.insets = new Insets(20, 10, 5, 10);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Mock data
        tableModel.addRow(new Object[] { "1", "Java Programming", "Deitel", "123-456", "10", "Technology" });
        tableModel.addRow(new Object[] { "2", "Principles of Marketing", "Kotler", "789-012", "5", "Business" });
    }
}
