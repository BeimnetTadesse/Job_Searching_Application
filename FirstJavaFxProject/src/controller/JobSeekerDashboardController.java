package controller;

import database.ApplicationsDAO;
import model.Job;
import model.User;
import view.JobSeekerDashboardView;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class JobSeekerDashboardController {
    private JobSeekerDashboardView view;
    private User user;

    public JobSeekerDashboardController(User user, List<Job> jobs) {
        this.user = user;
        this.view = new JobSeekerDashboardView(user.getName(), jobs);

        this.view.setJobList(jobs);
        this.view.getViewProfileButton().addActionListener(new ViewProfileListener());
        this.view.getSearchJobsButton().addActionListener(new SearchJobsListener());

        setApplyButtonListeners(view.getApplyButtonsMap());

        view.setVisible(true);
    }

    private void setApplyButtonListeners(Map<JButton, Job> applyButtonsMap) {
        for (Map.Entry<JButton, Job> entry : applyButtonsMap.entrySet()) {
            JButton button = entry.getKey();
            Job job = entry.getValue();

            button.addActionListener(e -> {
                int jobId = job.getJobId(); // you'll need to add this to your Job class if not already present
                boolean success = ApplicationsDAO.applyToJob(user.getUserId(), jobId, user.getResumeLink());

                if (success) {
                    button.setText("Applied");
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(view,
                            "You have successfully applied to \"" + job.getTitle() + "\" job.",
                            "Application Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view,
                            "You have already applied to this job.",
                            "Already Applied",
                            JOptionPane.WARNING_MESSAGE);
                }
            });
        }
    }

    private class ViewProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new UserProfileController(user);
            view.dispose();
        }
    }

    private class SearchJobsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new JobSearchController(user);
        }
    }
}