package controller;

import database.JobDAO;
import model.Job;
import model.User;
import view.EmployerDashboardView;
import view.PostJobView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployerDashboardController {
    private EmployerDashboardView view;
    private User employer;
    private List<Job> postedJobs;

    // Constructor with job list
    public EmployerDashboardController(User employer, List<Job> postedJobs) {
        this.employer = employer;
        this.postedJobs = postedJobs;
        this.view = new EmployerDashboardView(employer, postedJobs);

        view.getEditProfileButton().addActionListener(new EditProfileListener());
        view.getPostJobButton().addActionListener(new PostJobListener());
        setViewApplicantsButtonListeners(view.getViewApplicantsButtonsMap());

        view.setVisible(true);
    }

    // Constructor with empty job list
    public EmployerDashboardController(User employer) {
        this(employer, new ArrayList<>());
    }

    private void setViewApplicantsButtonListeners(Map<JButton, Job> buttonJobMap) {
        for (Map.Entry<JButton, Job> entry : buttonJobMap.entrySet()) {
            JButton button = entry.getKey();
            Job job = entry.getValue();

            button.addActionListener(e -> {
                new ViewApplicantsController(employer, job);
                view.dispose();
            });
        }
    }

    private class EditProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new UserProfileController(employer);
            view.dispose();
        }
    }

    private class PostJobListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PostJobView postJobDialog = new PostJobView(view);
            postJobDialog.setVisible(true);

            if (postJobDialog.isJobPosted()) {
                Job newJob = new Job();
                newJob.setTitle(postJobDialog.getJobTitle());
                newJob.setDescription(postJobDialog.getJobDescription());
                newJob.setPostedBy(employer.getUsername());

                // Insert job into database
                boolean success = JobDAO.addJob(newJob, employer.getUserId());
                if (success) {
                    // Refresh postedJobs from DB to keep in sync
                    postedJobs = JobDAO.getJobsPostedByEmployer(employer.getUserId());

                    // Reload dashboard with updated jobs
                    view.dispose();
                    new EmployerDashboardController(employer, postedJobs);
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to post job. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
