package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Book;
import com.mycompany.edurecord_local_school_library_management_system.repositories.BookRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing Book inventory with real-time MySQL integration.
 * 
 * @author Antigravity
 */
public class BookManagementPanel extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, authorField, isbnField, qtyField, categoryField;
    private BookRepository bookRepo;
    private int selectedBookId = -1;

    public BookManagementPanel() {
        bookRepo = new BookRepository();

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
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(25);
        bookTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Selection Listener for Edit/Delete
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bookTable.getSelectedRow() != -1) {
                int row = bookTable.getSelectedRow();
                selectedBookId = (int) tableModel.getValueAt(row, 0);
                titleField.setText((String) tableModel.getValueAt(row, 1));
                authorField.setText((String) tableModel.getValueAt(row, 2));
                isbnField.setText((String) tableModel.getValueAt(row, 3));
                qtyField.setText(tableModel.getValueAt(row, 4).toString());
                categoryField.setText((String) tableModel.getValueAt(row, 5));
            }
        });

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

        gbc.gridy = 8;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridy = 9;
        categoryField = new JTextField();
        formPanel.add(categoryField, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        btnPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        addBtn.setForeground(Color.WHITE);

        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(Color.RED);

        JButton clearBtn = new JButton("Clear");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        gbc.gridy = 10;
        gbc.insets = new Insets(20, 10, 5, 10);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Action Listeners
        addBtn.addActionListener(e -> handleAddBook());
        updateBtn.addActionListener(e -> handleUpdateBook());
        deleteBtn.addActionListener(e -> handleDeleteBook());
        clearBtn.addActionListener(e -> clearForm());

        // Load Initial Data
        loadBooks();
    }

    private void loadBooks() {
        tableModel.setRowCount(0); // Clear table
        List<Book> books = bookRepo.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getQuantity(),
                    b.getCategory() });
        }
    }

    private void handleAddBook() {
        try {
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setIsbn(isbnField.getText());
            book.setQuantity(Integer.parseInt(qtyField.getText()));
            book.setCategory(categoryField.getText());

            if (bookRepo.addBook(book)) {
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                clearForm();
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateBook() {
        if (selectedBookId == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Book book = new Book();
            book.setId(selectedBookId);
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setIsbn(isbnField.getText());
            book.setQuantity(Integer.parseInt(qtyField.getText()));
            book.setCategory(categoryField.getText());

            if (bookRepo.updateBook(book)) {
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
                clearForm();
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteBook() {
        if (selectedBookId == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookRepo.deleteBook(selectedBookId)) {
                JOptionPane.showMessageDialog(this, "Book deleted.");
                clearForm();
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        selectedBookId = -1;
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        qtyField.setText("");
        categoryField.setText("");
        bookTable.clearSelection();
    }
}
