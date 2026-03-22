package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for students to search for books in the library catalog.
 * 
 * @author Antigravity
 */
public class BookSearchPanel extends JPanel {

    private JTable catalogTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public BookSearchPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        header.setPreferredSize(new Dimension(800, 60));

        JLabel title = new JLabel("  Library Catalog Search", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        header.add(title, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // --- Search Bar ---
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        searchBarPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);

        searchField = new JTextField(30);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JButton searchBtn = new JButton("Search Books");
        searchBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        searchBtn.setForeground(Color.WHITE);

        searchBarPanel.add(new JLabel("Keyword:"));
        searchBarPanel.add(searchField);
        searchBarPanel.add(searchBtn);

        add(searchBarPanel, BorderLayout.SOUTH);

        // --- Table Section ---
        String[] columns = { "Title", "Author", "ISBN", "Category", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        catalogTable = new JTable(tableModel);
        catalogTable.setRowHeight(30);
        catalogTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        catalogTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(catalogTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Mock data
        tableModel.addRow(
                new Object[] { "Java: A Beginner's Guide", "Herbert Schildt", "123-456", "Technology", "AVAILABLE" });
        tableModel.addRow(new Object[] { "Clean Code", "Robert C. Martin", "789-012", "Software", "BORROWED" });
        tableModel.addRow(new Object[] { "Effective Java", "Joshua Bloch", "345-678", "Technology", "AVAILABLE" });
    }
}
