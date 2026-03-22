package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import javax.swing.*;
import java.awt.*;

/**
 * Operational overview panel for the Librarian.
 * 
 * @author Antigravity
 */
public class LibrarianHomePanel extends JPanel {

    public LibrarianHomePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel title = new JLabel("Library Operational Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- Stats & Search ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        statsPanel.add(createStatCard("Books Available", "840"));
        statsPanel.add(createStatCard("Today's Borrows", "15"));
        statsPanel.add(createStatCard("Today's Returns", "8"));

        topPanel.add(statsPanel, BorderLayout.NORTH);

        // Quick Search Section
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Quick Book Lookup"));

        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search ISBN / Title");
        searchBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        searchBtn.setForeground(Color.WHITE);

        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        // --- Footer Quick Actions ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        actionPanel.setBackground(Color.WHITE);

        JButton borrowBtn = new JButton("New Transaction");
        borrowBtn.setPreferredSize(new Dimension(200, 50));
        borrowBtn.setBackground(ColorPalette.SECONDARY_GOLD);

        actionPanel.add(borrowBtn);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        card.setBorder(BorderFactory.createLineBorder(ColorPalette.SECONDARY_GOLD, 1));

        JLabel titleLbl = new JLabel(title, JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel valLbl = new JLabel(value, JLabel.CENTER);
        valLbl.setFont(new Font("Serif", Font.BOLD, 28));
        valLbl.setForeground(ColorPalette.PRIMARY_BURGUNDY);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valLbl, BorderLayout.CENTER);

        return card;
    }
}
