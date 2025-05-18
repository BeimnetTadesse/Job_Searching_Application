package controller;

import database.JobDAO;
import database.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Job;
import model.User;
import view.RegisterView;

public class RegisterController {
    private RegisterView view;

    private static final String EMAIL_PATTERN =
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public RegisterController(RegisterView view) {
        this.view = view;
        this.view.addRegisterListener(new RegisterListener());
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getName();            // get name from form
            String email = view.getEmail();
            String password = view.getPassword();
            String role = view.getRole();
            String resumeLink = view.getResumeLink();

            if (name == null || name.trim().isEmpty()) {
                view.showMessage("Name cannot be empty");
                return;
            }

            if (!validateEmail(email)) {
                view.showMessage("Invalid email format");
                return;
            }

            if (!validatePassword(password)) {
                view.showMessage("Password must be at least 6 characters");
                return;
            }

            // Hash the password before saving
            String hashedPassword = sha256(password);

            User user = new User(name, email, hashedPassword, role, resumeLink);

            boolean success = UserDAO.registerUser(user);
            if (success) {
                view.showMessage("Registration successful!");

                // Fetch the full user from DB after registration
                User registeredUser = UserDAO.getUserByEmail(email);

                if (registeredUser != null) {
                    if (role.equalsIgnoreCase("Job Seeker")) {
                        List<Job> jobs = JobDAO.getAllJobs();
                        new JobSeekerDashboardController(registeredUser, jobs);
                        view.dispose();
                    } else if (role.equalsIgnoreCase("Employer")) {
                        new EmployerDashboardController(registeredUser);
                        view.dispose();
                    } else {
                        view.showMessage("Unknown role. Please log in manually.");
                    }
                } else {
                    view.showMessage("Error loading user after registration. Please log in.");
                }
            } else {
                view.showMessage("Registration failed. Email may already exist.");
            }
        }

        private boolean validateEmail(String email) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        private boolean validatePassword(String password) {
            return password != null && password.length() >= 6;
        }

        private String sha256(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                return hexString.toString();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
