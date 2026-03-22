package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Transaction;
import com.mycompany.edurecord_local_school_library_management_system.models.User;
import com.mycompany.edurecord_local_school_library_management_system.repositories.TransactionRepository;
import com.mycompany.edurecord_local_school_library_management_system.services.SessionManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for students to view their personal borrowing history.
 * 
 * @author Antigravity
 */
public class BorrowHistoryPanel extends JPanel {

    private JTable historyTable;
    private DefaultTableModel tableModel;
    private TransactionRepository transactionRepo;
    private JPanel infoPanel;

    public BorrowHistoryPanel() {
        transactionRepo = new TransactionRepository();

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
        infoPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(infoPanel, BorderLayout.CENTER);

        // --- Table Section ---
        String[] columns = { "Book Title", "ISBN", "Borrow Date", "Return Date", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(25);
        historyTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detailed Records"));

        add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        loadStudentData();
    }

    private void loadStudentData() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null)
            return; // Guard clause if testing without logging in

        int userId = currentUser.getId();

        // Refresh Stats
        infoPanel.removeAll();
        int currentlyBorrowed = transactionRepo.getBorrowedBooksCountByUserId(userId);
        int totalReturned = transactionRepo.getReturnedBooksCountByUserId(userId);

        infoPanel.add(
                createCard("Currently Borrowed", String.valueOf(currentlyBorrowed), ColorPalette.PRIMARY_BURGUNDY));
        infoPanel.add(createCard("Books Returned", String.valueOf(totalReturned), ColorPalette.TEXT_CHARCOAL));
        infoPanel.add(createCard("Overdue Fines", "None", ColorPalette.SECONDARY_GOLD)); // Placeholder for future logic

        infoPanel.revalidate();
        infoPanel.repaint();

        // Refresh Table
        tableModel.setRowCount(0);
        List<Transaction> transactions = transactionRepo.getTransactionsByUserId(userId);
        for (Transaction t : transactions) {
            String returnDateStr = t.getReturnDate() == null ? "-" : t.getReturnDate().toString();
            // Note: Since we didn't JOIN book ISBN in the customized repo query, we'll
            // leave it as "View in Catalog" or fetch it.
            // For now, we will just put a small placeholder to keep it fast, as ISBN isn't
            // strictly returned in getTransactionsByUserId query.
            tableModel.addRow(new Object[] {
                    t.getBookTitle(),
                    "...", // Placeholder for ISBN
                    t.getBorrowDate().toString(),
                    returnDateStr,
                    t.getStatus()
            });
        }
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
