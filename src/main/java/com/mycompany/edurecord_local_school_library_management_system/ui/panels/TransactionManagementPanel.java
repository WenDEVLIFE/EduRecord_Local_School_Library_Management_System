package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for managing Borrowing and Returning transactions.
 * 
 * @author Antigravity
 */
public class TransactionManagementPanel extends JPanel {

    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JTextField studentIdField, bookIsbnField;
    private JButton borrowBtn, returnBtn;

    public TransactionManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel title = new JLabel("Library Transactions (Borrow / Return)");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columns = { "ID", "Student Username", "Book ISBN", "Borrow Date", "Return Date", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(25);
        transactionTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Form Section ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("New Transaction"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        gbc.gridy = 0;
        formPanel.add(new JLabel("Student Username:"), gbc);
        gbc.gridy = 1;
        studentIdField = new JTextField();
        formPanel.add(studentIdField, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Book ISBN:"), gbc);
        gbc.gridy = 3;
        bookIsbnField = new JTextField();
        formPanel.add(bookIsbnField, gbc);

        // Action Buttons
        borrowBtn = new JButton("Borrow Book");
        borrowBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        borrowBtn.setForeground(Color.WHITE);
        borrowBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        returnBtn = new JButton("Return Book");
        returnBtn.setBackground(ColorPalette.SECONDARY_GOLD);
        returnBtn.setForeground(Color.BLACK);
        returnBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 10, 5, 10);
        formPanel.add(borrowBtn, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        formPanel.add(returnBtn, gbc);

        add(formPanel, BorderLayout.EAST);

        // Mock data
        tableModel.addRow(new Object[] { "1", "std_2024", "123-456", "2024-03-20", "-", "BORROWED" });
        tableModel.addRow(new Object[] { "2", "std_2025", "789-012", "2024-03-15", "2024-03-21", "RETURNED" });
    }
}
