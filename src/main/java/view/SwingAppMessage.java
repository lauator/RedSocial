package view;


import model.Message;
import model.User;
import service.MessageService;

import service.UserService;
import util.CustomDatabaseException;
import util.MessageServiceException;
import util.UserServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;


public class SwingAppMessage extends JFrame {

    private Integer userId;

    private MessageService messageService;

    private UserService userService;

    private JTable messageTable;

    public SwingAppMessage(Integer userId) {


        setTitle("Messages");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 230);


        messageTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(messageTable);
        add(scrollPane, BorderLayout.CENTER);


        JButton addButton = new JButton("Create new message");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);


        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);


        messageService = new MessageService();
        userService = new UserService();


        refreshMessageTable(userId);


        addButton.addActionListener(e -> {
            createMessage(userId);
        });

        updateButton.addActionListener(e -> updateMessage(userId));

        deleteButton.addActionListener(e -> deleteMessage(userId));
    }

    private void deleteMessage(Integer userId) {
        String messageIdTxt = JOptionPane.showInputDialog(this, "Input the id of the message you want to delete: ", "Delete Message", JOptionPane.QUESTION_MESSAGE);
        if (messageIdTxt != null) {
            try {
                int messageId = Integer.parseInt(messageIdTxt);


                int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure that you want to delete the message?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    messageService.deleteMessage(messageId);


                    refreshMessageTable(userId);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input a valid id", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (MessageServiceException | SQLException | CustomDatabaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateMessage(Integer userId) {
        String messageIdTxt = JOptionPane.showInputDialog(this, "Input the id of the message you want to change: ", "Update Message", JOptionPane.QUESTION_MESSAGE);
        if (messageIdTxt != null) {
            try {
                int messageId = Integer.parseInt(messageIdTxt);


                Message message = messageService.searchById(messageId);

                if (message != null) {

                    JTextField messageField = new JTextField(message.getMessage());


                    Object[] fields = {
                            "message:", messageField,
                    };

                    int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Update message", JOptionPane.OK_CANCEL_OPTION);
                    if (confirmResult == JOptionPane.OK_OPTION) {

                        messageService.changeMessage(messageField.getText(), userId);


                        refreshMessageTable(userId);

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "There is no message with that ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (MessageServiceException | CustomDatabaseException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }


    private void refreshMessageTable(Integer userId) {

        try {

            User user = userService.searchById(userId);

            List<Message> messages = messageService.getAllByUser(user);


            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id");
            model.addColumn("message");
            model.addColumn("date");


            for (Message message : messages) {
                Object[] rowData = {
                        message.getMessageId(),
                        message.getMessage(),
                        message.getDate()
                };
                model.addRow(rowData);
            }

            messageTable.setModel(model);

        } catch (UserServiceException  |CustomDatabaseException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to obtain Messages", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createMessage(Integer userId) {


        JTextField messageField = new JTextField();


        Object[] fields = {
                "message:", messageField,
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Save message?", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                messageService.createMessage(messageField.getText(), userId);

                refreshMessageTable(userId);


            } catch (MessageServiceException | CustomDatabaseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}




