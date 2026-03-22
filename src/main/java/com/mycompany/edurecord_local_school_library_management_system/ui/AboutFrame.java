package com.mycompany.edurecord_local_school_library_management_system.ui;

import javax.swing.*;
import java.awt.*;

/**
 * About window for the EduRecord system, detailing scope and limitations.
 * 
 * @author Antigravity
 */
public class AboutFrame extends JDialog {

    public AboutFrame(Frame parent) {
        super(parent, "About EduRecord", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel titleLabel = new JLabel("EduRecord Information");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        headerPanel.add(titleLabel);

        // Content
        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        contentArea.setForeground(ColorPalette.TEXT_CHARCOAL);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setMargin(new Insets(10, 10, 10, 10));

        String aboutText = "EduRecord: Local School Library Management System\n"
                + "Specifically designed for Peñaranda Off-Campus Library.\n\n"
                + "CORE SCOPE:\n"
                + "- Student record management\n"
                + "- Book inventory management\n"
                + "- Offline borrowing and returning transactions\n"
                + "- Supported Courses: BSED, BSBA, and BSIT\n\n"
                + "SYSTEM LIMITATIONS:\n"
                + "- 100% Offline operation (Desktop-based)\n"
                + "- Local access within the school library only\n"
                + "- No remote or online functionality\n"
                + "- Limited to current library inventory\n"
                + "- Restricted to BSED, BSBA, and BSIT students\n\n"
                + "Developed by Antigravity AI @ 2026";

        contentArea.setText(aboutText);
        JScrollPane scrollPane = new JScrollPane(contentArea);

        // Footer
        JPanel footerPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        footerPanel.add(closeButton);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
}
