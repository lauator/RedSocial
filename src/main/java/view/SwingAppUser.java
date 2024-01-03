package view;

import model.User;
import service.UserService;
import util.CustomDatabaseException;
import util.UserServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SwingAppUser extends JFrame {

    private UserService userService;

    private JTable userTable;

    public SwingAppUser() {

        setTitle("Red Social");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 230);


        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);


        JButton addButton = new JButton("Create new user");
        JButton loginButton = new JButton("Login");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);


        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        loginButton.setBackground(new Color(250, 160, 30));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);


        userService = new UserService();


        refreshUserTable();


        addButton.addActionListener(e -> {
            Integer userId = createUser();
            this.setVisible(false);
            SwingAppMessage app = new SwingAppMessage(userId);
            app.setVisible(true);
        });

        loginButton.addActionListener(e -> {
            String userIdTxt = JOptionPane.showInputDialog(this, "Input the id of the user: ", "Update User", JOptionPane.QUESTION_MESSAGE);
            if (userIdTxt != null){
                int userId = Integer.parseInt(userIdTxt);
                this.setVisible(false);
                SwingAppMessage app = new SwingAppMessage(userId);
                app.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "There is no user with that ID", "Error", JOptionPane.ERROR_MESSAGE);

            }


        });

        updateButton.addActionListener(e -> updateUser());

        deleteButton.addActionListener(e -> deleteUser());
    }




    private void refreshUserTable() {

        try {
            List<User> users = userService.listAll();


            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id");
            model.addColumn("username");


            for (User user : users) {
                Object[] rowData = {
                        user.getUserId(),
                        user.getUsername()
                };
                model.addRow(rowData);
            }


            userTable.setModel(model);
        } catch (CustomDatabaseException | UserServiceException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to obtain users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Integer createUser() {
        Integer user_id = 0;

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField emailField = new JTextField();


        Object[] fields = {
                "username:", usernameField,
                "password:", passwordField,
                "email:", emailField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Save user and login?", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                user_id = userService.createUser(usernameField.getText(), passwordField.getText(), emailField.getText());

                refreshUserTable();

                JOptionPane.showMessageDialog(this, "User was added correctly", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (UserServiceException | CustomDatabaseException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }



        }

        return user_id;
    }

    private void updateUser() {

        String userIdTxt = JOptionPane.showInputDialog(this, "Input the id of the user you want to change: ", "Update User", JOptionPane.QUESTION_MESSAGE);
        if (userIdTxt != null) {
            try {
                int userId = Integer.parseInt(userIdTxt);


                User user = userService.searchById(userId);

                if (user != null) {

                    JTextField usernameField = new JTextField(user.getUsername());
                    JTextField passwordField = new JTextField();
                    JTextField emailField = new JTextField(user.getEmail());


                    Object[] fields = {
                            "username:", usernameField,
                            "password:", passwordField,
                            "email:", emailField
                    };

                    int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Update user", JOptionPane.OK_CANCEL_OPTION);
                    if (confirmResult == JOptionPane.OK_OPTION) {

                        userService.updateUser(userId, usernameField.getText(), passwordField.getText(), emailField.getText());


                        refreshUserTable();

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "There is no user with that ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (UserServiceException | CustomDatabaseException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    private void deleteUser() {
        String userIdTxt= JOptionPane.showInputDialog(this, "Input the id of the user you want to delete: ", "Delete User", JOptionPane.QUESTION_MESSAGE);
        if (userIdTxt != null) {
            try {
                int userId = Integer.parseInt(userIdTxt);


                int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure that you want to delete the user?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    userService.deleteUser(userId);


                    refreshUserTable();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input a valid id", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (UserServiceException | SQLException | CustomDatabaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    }



