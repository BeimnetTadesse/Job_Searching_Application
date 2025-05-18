package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginView extends JFrame {
    private JTextField emailField = new JTextField(25);
    private JPasswordField passwordField = new JPasswordField(25);
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");

    public LoginView() {
        setTitle("User Login");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font segoeFont = new Font("Segoe UI", Font.PLAIN, 14);
        Insets labelInsets = new Insets(5, 10, 5, 10);

        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(segoeFont);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = labelInsets;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(emailLabel, gbc);

        // Email Field
        emailField.setFont(segoeFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(emailField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(segoeFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        // Password Field
        passwordField.setFont(segoeFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Login Button
        loginButton.setFont(segoeFont);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(true);
        loginButton.setBackground(null);
        loginButton.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Register Button
        registerButton.setFont(segoeFont);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(true);
        registerButton.setBackground(null);
        registerButton.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}