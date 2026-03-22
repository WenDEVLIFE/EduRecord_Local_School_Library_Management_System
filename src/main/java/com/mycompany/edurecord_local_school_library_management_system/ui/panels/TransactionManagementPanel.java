package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Book;
import com.mycompany.edurecord_local_school_library_management_system.models.Transaction;
import com.mycompany.edurecord_local_school_library_management_system.models.User;
import com.mycompany.edurecord_local_school_library_management_system.repositories.BookRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.TransactionRepository;
import com.mycompany.edurecord_local_school_library_management_system.repositories.UserRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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

    private TransactionRepository transactionRepo;
    private UserRepository userRepo;
    private BookRepository bookRepo;

    private int selectedTransactionId = -1;
    private int selectedBookId = -1;
    private String selectedStatus = "";

    public TransactionManagementPanel() {
        transactionRepo = new TransactionRepository();
        userRepo = new UserRepository();
        bookRepo = new BookRepository();

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
        String[] columns = { "Trans ID", "Student Username", "Book Title", "Borrow Date", "Return Date", "Status",
                "Book ID" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(25);
        transactionTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Hide Book ID column as it's just for internal tracking
        transactionTable.getColumnModel().getColumn(6).setMinWidth(0);
        transactionTable.getColumnModel().getColumn(6).setMaxWidth(0);
        transactionTable.getColumnModel().getColumn(6).setWidth(0);

        transactionTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && transactionTable.getSelectedRow() != -1) {
                int row = transactionTable.getSelectedRow();
                selectedTransactionId = (int) tableModel.getValueAt(row, 0);
                selectedStatus = (String) tableModel.getValueAt(row, 5);
                selectedBookId = (int) tableModel.getValueAt(row, 6);

                // Toggle Return button based on status
                returnBtn.setEnabled("BORROWED".equals(selectedStatus));
            }
        });

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
        returnBtn.setEnabled(false); // Disabled by default until a borrowed row is clicked

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 10, 5, 10);
        formPanel.add(borrowBtn, gbc);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        formPanel.add(returnBtn, gbc);

        add(formPanel, BorderLayout.EAST);

        // Listeners
        borrowBtn.addActionListener(e -> handleBorrow());
        returnBtn.addActionListener(e -> handleReturn());

        loadTransactions();
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);
        selectedTransactionId = -1;
        selectedBookId = -1;
        selectedStatus = "";
        returnBtn.setEnabled(false);

        List<Transaction> transactions = transactionRepo.getAllTransactions();
        for (Transaction t : transactions) {
            String returnDateStr = t.getReturnDate() == null ? "-" : t.getReturnDate().toString();
            tableModel.addRow(new Object[] {
                    t.getId(),
                    t.getUsername(),
                    t.getBookTitle(),
                    t.getBorrowDate().toString(),
                    returnDateStr,
                    t.getStatus(),
                    t.getBookId()
            });
        }
    }

    private void handleBorrow() {
        String username = studentIdField.getText().trim();
        String isbn = bookIsbnField.getText().trim();

        if (username.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Username and Book ISBN.", "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userRepo.getUserByUsername(username);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = bookRepo.getBookByIsbn(isbn);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (book.getQuantity() <= 0) {
            JOptionPane.showMessageDialog(this, "Book is out of stock.", "Waitlist", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (transactionRepo.borrowBook(user.getId(), book.getId())) {
            JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
            studentIdField.setText("");
            bookIsbnField.setText("");
            loadTransactions();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to process borrow transaction.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleReturn() {
        if (selectedTransactionId == -1 || selectedBookId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a BORROWED transaction from the table.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Mark this book as returned?", "Confirm Return",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (transactionRepo.returnBook(selectedTransactionId, selectedBookId)) {
                JOptionPane.showMessageDialog(this, "Book registered as returned!");
                loadTransactions();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to process return.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
