package com.mycompany.edurecord_local_school_library_management_system.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Main application frame for EduRecord.
 * 
 * @author Antigravity
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("EduRecord: Peñaranda Off-Campus Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        headerPanel.setPreferredSize(new Dimension(1024, 100));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(" EDURECORD LIBRARY SYSTEM", JLabel.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JLabel subtitleLabel = new JLabel("Peñaranda Off-Campus  ", JLabel.RIGHT);
        subtitleLabel.setForeground(ColorPalette.SECONDARY_GOLD);
        subtitleLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        headerPanel.add(subtitleLabel, BorderLayout.EAST);

        // Main Content Area
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        contentPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome to EduRecord");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 36));
        welcomeLabel.setForeground(ColorPalette.TEXT_CHARCOAL);
        contentPanel.add(welcomeLabel);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(ColorPalette.TEXT_CHARCOAL);
        footerPanel.setPreferredSize(new Dimension(1024, 30));
        JLabel footerLabel = new JLabel("© 2026 EduRecord - Academic Excellence & Digital Integration", JLabel.CENTER);
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerPanel.add(footerLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
