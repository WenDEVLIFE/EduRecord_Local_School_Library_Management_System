package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Course;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for managing Students specifically. Used by Librarians.
 * 
 * @author Antigravity
 */
public class StudentManagementPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField, firstNameField, lastNameField;
    private JComboBox<Course> courseCombo;

    public StudentManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel title = new JLabel("Student Directory Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columns = { "ID", "Student ID / Username", "First Name", "Last Name", "Course" };
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Form Section ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;

        gbc.gridy = 0;
        formPanel.add(new JLabel("Username / ID:"), gbc);
        gbc.gridy = 1;
        usernameField = new JTextField();
        formPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridy = 3;
        firstNameField = new JTextField();
        formPanel.add(firstNameField, gbc);

        gbc.gridy = 4;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridy = 5;
        lastNameField = new JTextField();
        formPanel.add(lastNameField, gbc);

        gbc.gridy = 6;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridy = 7;
        courseCombo = new JComboBox<>(new Course[] { Course.BSED, Course.BSBA, Course.BSIT });
        formPanel.add(courseCombo, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        JButton addBtn = new JButton("Register");
        addBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        addBtn.setForeground(Color.WHITE);
        JButton clearBtn = new JButton("Clear");

        btnPanel.add(addBtn);
        btnPanel.add(clearBtn);

        gbc.gridy = 8;
        gbc.insets = new Insets(20, 10, 5, 10);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Mock data
        tableModel.addRow(new Object[] { "1", "std_2024_01", "Maria", "Clara", "BSED" });
        tableModel.addRow(new Object[] { "2", "std_2024_02", "Jose", "Rizal", "BSIT" });
    }
}
