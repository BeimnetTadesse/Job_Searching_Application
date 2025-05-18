package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RegisterView extends JFrame {
    private JTextField nameField = new JTextField(25); // new Name field
    private JTextField emailField = new JTextField(25);
    private JPasswordField passwordField = new JPasswordField(25);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Job Seeker", "Employer"});
    private JTextField resumeLinkField = new JTextField(20);
    private JButton browseButton = new JButton("Browse");
    private JButton registerButton = new JButton("Register");

    public RegisterView() {
        setTitle("User Registration");
        setSize(500, 350); // adjusted height for extra field
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font segoeFont = new Font("Segoe UI", Font.PLAIN, 14);
        Insets labelInsets = new Insets(5, 10, 5, 10);

        // Name Label
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(segoeFont);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = labelInsets;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(nameLabel, gbc);

        // Name Field
        nameField.setFont(segoeFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gbc);

        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(segoeFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(emailLabel, gbc);

        // Email Field
        emailField.setFont(segoeFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(emailField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(segoeFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        // Password Field
        passwordField.setFont(segoeFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        // Role Label
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(segoeFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(roleLabel, gbc);

        // Role ComboBox
        roleComboBox.setFont(segoeFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(roleComboBox, gbc);

        // Resume Label
        JLabel resumeLabel = new JLabel("Resume:");
        resumeLabel.setFont(segoeFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(resumeLabel, gbc);

        // Resume Panel with resume field + browse button
        JPanel resumePanel = new JPanel(new BorderLayout(5, 0));
        resumeLinkField.setFont(segoeFont);
        resumeLinkField.setEditable(false);
        resumePanel.add(resumeLinkField, BorderLayout.CENTER);

        browseButton.setFont(segoeFont);
        browseButton.setFocusPainted(false);
        browseButton.setBorderPainted(true);
        browseButton.setBackground(null);
        browseButton.setForeground(Color.BLACK);

        resumePanel.add(browseButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(resumePanel, gbc);

        // Register Button
        registerButton.setFont(segoeFont);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(true);
        registerButton.setBackground(null);
        registerButton.setForeground(Color.BLACK);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // Browse button action
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Resume File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Documents", "pdf", "doc", "docx", "txt"));
            int result = fileChooser.showOpenDialog(RegisterView.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
                resumeLinkField.setText(selectedPath);
            }
        });
    }

    // New getter for name
    public String getName() {
        return nameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    public String getResumeLink() {
        return resumeLinkField.getText();
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
