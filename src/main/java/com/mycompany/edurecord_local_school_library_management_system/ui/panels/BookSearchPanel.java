package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Book;
import com.mycompany.edurecord_local_school_library_management_system.repositories.BookRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for students to search for books in the library catalog.
 * 
 * @author Antigravity
 */
public class BookSearchPanel extends JPanel {

    private JTable catalogTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private BookRepository bookRepo;

    public BookSearchPanel() {
        bookRepo = new BookRepository();
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
        searchBtn.addActionListener(e -> performSearch());

        // Allow pressing Enter in the search field
        searchField.addActionListener(e -> performSearch());

        searchBarPanel.add(new JLabel("Keyword:"));
        searchBarPanel.add(searchField);
        searchBarPanel.add(searchBtn);

        add(searchBarPanel, BorderLayout.SOUTH);

        // --- Table Section ---
        String[] columns = { "Title", "Author", "ISBN", "Category", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        catalogTable = new JTable(tableModel);
        catalogTable.setRowHeight(30);
        catalogTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        catalogTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        catalogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(catalogTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Initial load (show all)
        performSearch();
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        tableModel.setRowCount(0); // Clear table

        List<Book> results;
        if (keyword.isEmpty()) {
            results = bookRepo.getAllBooks(); // Show all if empty search
        } else {
            results = bookRepo.searchBooks(keyword);
        }

        for (Book b : results) {
            String status = b.getQuantity() > 0 ? "AVAILABLE (" + b.getQuantity() + ")" : "BORROWED";
            tableModel.addRow(new Object[] {
                    b.getTitle(),
                    b.getAuthor(),
                    b.getIsbn(),
                    b.getCategory(),
                    status
            });
        }

        if (!keyword.isEmpty() && results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found matching: " + keyword, "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
