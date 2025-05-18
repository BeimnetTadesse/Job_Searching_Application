package controller;

import database.JobDAO;
import database.UserDAO;
import java.util.List;
import model.Job;
import model.User;
import view.LoginView;
import view.RegisterView;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        loginView.addLoginListener(e -> {
            String email = loginView.getEmail();
            String password = loginView.getPassword();

            if (email.isEmpty() || password.isEmpty()) {
                loginView.showMessage("Please fill in all fields.");
                return;
            }

            User user = UserDAO.loginUser(email, password);

            if (user != null) {
                loginView.showMessage("Login successful! Redirecting to " + user.getRole() + " dashboard...");

                if (user.getRole().equalsIgnoreCase("Job Seeker")) {
                    List<Job> jobs = JobDAO.getAllJobs();
                    new JobSeekerDashboardController(user, jobs);
                    loginView.dispose();

                } else if (user.getRole().equalsIgnoreCase("Employer")) {
                    List<Job> employerJobs = JobDAO.getJobsPostedByEmployer(user.getUserId());
                    new EmployerDashboardController(user, employerJobs);
                    loginView.dispose();

                } else {
                    loginView.showMessage("Unknown role: " + user.getRole());
                }

            } else {
                loginView.showMessage("Invalid email or password.");
            }
        });

        loginView.addRegisterListener(e -> {
            RegisterView registerView = new RegisterView();
            new RegisterController(registerView);
            registerView.setVisible(true);
            loginView.dispose();
        });
    }
}
