package view;

import javax.swing.*;
import controller.JobSeekerDashboardController;
import controller.EmployerDashboardController;
import model.Job;
import model.User;
import database.JobDAO;
import database.UserDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class UserProfileView extends JFrame {
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel roleLabel;

    private JButton backToDashboardButton;
    private JButton uploadResumeButton;
    private JButton changePasswordButton;
    private JButton logoutButton;

    private User user;

    public UserProfileView(User user) {
        this.user = user;

        setTitle("User Profile");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        nameLabel = new JLabel("Name: " + user.getName());
        emailLabel = new JLabel("Email: " + user.getEmail());
        roleLabel = new JLabel("Role: " + user.getRole());

        uploadResumeButton = new JButton("Upload New Resume");
        changePasswordButton = new JButton("Change Password");
        backToDashboardButton = new JButton("Back to Dashboard");
        logoutButton = new JButton("Logout");

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(nameLabel);
        panel.add(emailLabel);
        panel.add(roleLabel);
        panel.add(uploadResumeButton);
        panel.add(changePasswordButton);
        panel.add(logoutButton);
        panel.add(new JLabel());  // Spacer
        panel.add(backToDashboardButton);

        add(panel);

        // Upload resume logic
        uploadResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(UserProfileView.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    boolean updated = UserDAO.updateResumeLink(user.getEmail(), filePath);
                    if (updated) {
                        JOptionPane.showMessageDialog(UserProfileView.this,
                                "Resume updated successfully!");
                        user.setResumeLink(filePath);
                    } else {
                        JOptionPane.showMessageDialog(UserProfileView.this,
                                "Failed to update resume. Please try again.");
                    }
                }
            }
        });

        // Change password logic
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField oldPasswordField = new JPasswordField();
                JPasswordField newPasswordField = new JPasswordField();
                JPasswordField confirmPasswordField = new JPasswordField();

                Object[] message = {
                    "Old Password:", oldPasswordField,
                    "New Password:", newPasswordField,
                    "Confirm New Password:", confirmPasswordField
                };

                int option = JOptionPane.showConfirmDialog(
                        UserProfileView.this, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    String oldPass = new String(oldPasswordField.getPassword());
                    String newPass = new String(newPasswordField.getPassword());
                    String confirmPass = new String(confirmPasswordField.getPassword());

                    if (!newPass.equals(confirmPass)) {
                        JOptionPane.showMessageDialog(UserProfileView.this,
                                "New passwords do not match.");
                        return;
                    }

                    User loggedInUser = UserDAO.loginUser(user.getEmail(), oldPass);
                    if (loggedInUser != null) {
                        boolean success = UserDAO.updatePassword(user.getEmail(), newPass);
                        if (success) {
                            JOptionPane.showMessageDialog(UserProfileView.this,
                                    "Password updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(UserProfileView.this,
                                    "Failed to update password.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(UserProfileView.this,
                                "Old password is incorrect.");
                    }
                }
            }
        });

        // Logout button logic
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    view.LoginView loginView = new view.LoginView();
                    new controller.LoginController(loginView);
                    loginView.setVisible(true);
                });
            }
        });

        // Back to dashboard logic - fixed to correctly navigate by role
        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String role = user.getRole().toLowerCase();

                if (role.equals("job seeker")) {
                    List<Job> jobs = JobDAO.getAllJobs();
                    new JobSeekerDashboardController(user, jobs);
                } else if (role.equals("employer")) {
                    List<Job> postedJobs = JobDAO.getJobsPostedByEmployer(user.getUserId());
                    new EmployerDashboardController(user, postedJobs);
                } else {
                    JOptionPane.showMessageDialog(UserProfileView.this,
                            "Unknown user role: " + user.getRole());
                }
            }
        });

        setVisible(true);
    }
}
