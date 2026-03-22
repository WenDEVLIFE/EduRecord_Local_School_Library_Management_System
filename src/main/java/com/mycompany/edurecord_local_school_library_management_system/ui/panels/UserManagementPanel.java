package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Course;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for managing Users, including Students with Course selection.
 * 
 * @author Antigravity
 */
public class UserManagementPanel extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField, firstNameField, lastNameField;
    private JComboBox<String> roleCombo;
    private JComboBox<Course> courseCombo;

    public UserManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        JLabel title = new JLabel("User & Student Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columns = { "ID", "Username", "First Name", "Last Name", "Role", "Course" };
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        userTable.setRowHeight(25);
        userTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        userTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Form Section (Right Side) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Manage Record"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0;

        // Fields
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
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
        formPanel.add(new JLabel("Role:"), gbc);
        gbc.gridy = 7;
        roleCombo = new JComboBox<>(new String[] { "STUDENT", "LIBRARIAN", "ADMIN" });
        roleCombo.addActionListener(e -> {
            boolean isStudent = "STUDENT".equals(roleCombo.getSelectedItem());
            courseCombo.setEnabled(isStudent);
            if (!isStudent)
                courseCombo.setSelectedItem(Course.NONE);
        });
        formPanel.add(roleCombo, gbc);

        gbc.gridy = 8;
        formPanel.add(new JLabel("Course (For Students Only):"), gbc);
        gbc.gridy = 9;
        courseCombo = new JComboBox<>(Course.values());
        formPanel.add(courseCombo, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        btnPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);
        JButton addBtn = new JButton("Add");
        addBtn.setBackground(ColorPalette.PRIMARY_BURGUNDY);
        addBtn.setForeground(Color.WHITE);

        JButton clearBtn = new JButton("Clear");

        btnPanel.add(addBtn);
        btnPanel.add(clearBtn);

        gbc.gridy = 10;
        gbc.insets = new Insets(20, 10, 5, 10);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Mock data
        tableModel.addRow(new Object[] { "1", "admin", "System", "Admin", "ADMIN", "NONE" });
        tableModel.addRow(new Object[] { "2", "std_2024", "John", "Doe", "STUDENT", "BSIT" });
    }
}
