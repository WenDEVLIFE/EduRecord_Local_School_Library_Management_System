package com.mycompany.edurecord_local_school_library_management_system.ui.panels;

import com.mycompany.edurecord_local_school_system_library_management_system.utils.ColorPalette;
import com.mycompany.edurecord_local_school_library_management_system.models.Course;
import com.mycompany.edurecord_local_school_library_management_system.models.User;
import com.mycompany.edurecord_local_school_library_management_system.repositories.UserRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing Students specifically. Used by Librarians.
 * 
 * @author Antigravity
 */
public class StudentManagementPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField, passwordField, firstNameField, lastNameField;
    private JComboBox<Course> courseCombo;
    private UserRepository userRepo;
    private int selectedUserId = -1;

    public StudentManagementPanel() {
        userRepo = new UserRepository();
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
        String[] columns = { "ID", "Username", "First Name", "Last Name", "Course" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int row = studentTable.getSelectedRow();
                selectedUserId = (int) tableModel.getValueAt(row, 0);
                usernameField.setText((String) tableModel.getValueAt(row, 1));
                passwordField.setText(""); // Security
                firstNameField.setText((String) tableModel.getValueAt(row, 2));
                lastNameField.setText((String) tableModel.getValueAt(row, 3));

                String courseObj = tableModel.getValueAt(row, 4).toString();
                try {
                    courseCombo.setSelectedItem(Course.valueOf(courseObj));
                } catch (Exception ex) {
                    courseCombo.setSelectedItem(Course.NONE);
                }
            }
        });

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
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 1;
        usernameField = new JTextField();
        formPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 3;
        passwordField = new JPasswordField();
        formPanel.add(passwordField, gbc);

        gbc.gridy = 4;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridy = 5;
        firstNameField = new JTextField();
        formPanel.add(firstNameField, gbc);

        gbc.gridy = 6;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridy = 7;
        lastNameField = new JTextField();
        formPanel.add(lastNameField, gbc);

        gbc.gridy = 8;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridy = 9;
        courseCombo = new JComboBox<>(new Course[] { Course.BSED, Course.BSBA, Course.BSIT });
        formPanel.add(courseCombo, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        btnPanel.setBackground(ColorPalette.BACKGROUND_OFF_WHITE);

        JButton addBtn = new JButton("Register");
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

        // Listeners
        addBtn.addActionListener(e -> handleAddStudent());
        updateBtn.addActionListener(e -> handleUpdateStudent());
        deleteBtn.addActionListener(e -> handleDeleteStudent());
        clearBtn.addActionListener(e -> clearForm());

        // Load initially
        loadStudents();
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<User> users = userRepo.getAllUsers();
        for (User u : users) {
            if ("STUDENT".equals(u.getRole())) {
                tableModel.addRow(new Object[] { u.getId(), u.getUsername(), u.getFirstName(), u.getLastName(),
                        u.getCourse().name() });
            }
        }
    }

    private void handleAddStudent() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setRole("STUDENT"); // Force role
        user.setCourse((Course) courseCombo.getSelectedItem());

        if (userRepo.addUser(user)) {
            JOptionPane.showMessageDialog(this, "Student registered successfully!");
            clearForm();
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register student. Username may already exist.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateStudent() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = new User();
        user.setId(selectedUserId);
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setRole("STUDENT");
        user.setCourse((Course) courseCombo.getSelectedItem());

        if (userRepo.updateUser(user)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            clearForm();
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteStudent() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userRepo.deleteUser(selectedUserId)) {
                JOptionPane.showMessageDialog(this, "Student deleted.");
                clearForm();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        selectedUserId = -1;
        usernameField.setText("");
        passwordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        courseCombo.setSelectedIndex(0);
        studentTable.clearSelection();
    }
}
