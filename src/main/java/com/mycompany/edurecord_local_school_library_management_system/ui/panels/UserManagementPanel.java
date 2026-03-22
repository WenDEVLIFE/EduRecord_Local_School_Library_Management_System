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
 * Panel for managing Users with MySQL real-time integration.
 * 
 * @author Antigravity
 */
public class UserManagementPanel extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField, passwordField, firstNameField, lastNameField;
    private JComboBox<String> roleCombo;
    private JComboBox<Course> courseCombo;
    private UserRepository userRepo;
    private int selectedUserId = -1;

    public UserManagementPanel() {
        userRepo = new UserRepository();

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
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setRowHeight(25);
        userTable.getTableHeader().setBackground(ColorPalette.SECONDARY_GOLD);
        userTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                int row = userTable.getSelectedRow();
                selectedUserId = (int) tableModel.getValueAt(row, 0);
                usernameField.setText((String) tableModel.getValueAt(row, 1));
                // We don't display password in the table, so leave field empty or fetch it
                // directly. For security, leave blank or require re-entry to update.
                passwordField.setText("");
                firstNameField.setText((String) tableModel.getValueAt(row, 2));
                lastNameField.setText((String) tableModel.getValueAt(row, 3));
                roleCombo.setSelectedItem((String) tableModel.getValueAt(row, 4));

                String courseObj = tableModel.getValueAt(row, 5).toString();
                try {
                    courseCombo.setSelectedItem(Course.valueOf(courseObj));
                } catch (Exception ex) {
                    courseCombo.setSelectedItem(Course.NONE);
                }
            }
        });

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
        formPanel.add(new JLabel("Role:"), gbc);
        gbc.gridy = 9;
        roleCombo = new JComboBox<>(new String[] { "STUDENT", "LIBRARIAN", "ADMIN" });
        roleCombo.addActionListener(e -> {
            boolean isStudent = "STUDENT".equals(roleCombo.getSelectedItem());
            courseCombo.setEnabled(isStudent);
            if (!isStudent)
                courseCombo.setSelectedItem(Course.NONE);
        });
        formPanel.add(roleCombo, gbc);

        gbc.gridy = 10;
        formPanel.add(new JLabel("Course (For Students):"), gbc);
        gbc.gridy = 11;
        courseCombo = new JComboBox<>(Course.values());
        formPanel.add(courseCombo, gbc);

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

        gbc.gridy = 12;
        gbc.insets = new Insets(20, 10, 5, 10);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Listeners
        addBtn.addActionListener(e -> handleAddUser());
        updateBtn.addActionListener(e -> handleUpdateUser());
        deleteBtn.addActionListener(e -> handleDeleteUser());
        clearBtn.addActionListener(e -> clearForm());

        loadUsers();
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userRepo.getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[] { u.getId(), u.getUsername(), u.getFirstName(), u.getLastName(), u.getRole(),
                    u.getCourse().name() });
        }
    }

    private void handleAddUser() {
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
        user.setRole((String) roleCombo.getSelectedItem());
        user.setCourse((Course) courseCombo.getSelectedItem());

        if (userRepo.addUser(user)) {
            JOptionPane.showMessageDialog(this, "User added successfully!");
            clearForm();
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user. Username may already exist.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateUser() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = new User();
        user.setId(selectedUserId);
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText()); // In a real app, only update if not empty
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setRole((String) roleCombo.getSelectedItem());
        user.setCourse((Course) courseCombo.getSelectedItem());

        if (userRepo.updateUser(user)) {
            JOptionPane.showMessageDialog(this, "User updated successfully!");
            clearForm();
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteUser() {
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userRepo.deleteUser(selectedUserId)) {
                JOptionPane.showMessageDialog(this, "User deleted.");
                clearForm();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        selectedUserId = -1;
        usernameField.setText("");
        passwordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        roleCombo.setSelectedIndex(0);
        courseCombo.setSelectedItem(Course.NONE);
        userTable.clearSelection();
    }
}
