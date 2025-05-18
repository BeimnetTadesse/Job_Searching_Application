package controller;

import database.ApplicationsDAO;
import database.JobDAO;
import model.Job;
import model.User;
import view.JobSearchView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class JobSearchController {
    private JobSearchView view;
    private List<Job> allJobs;
    private User user;

    public JobSearchController(User user) {
        this.user = user;
        this.view = new JobSearchView();
        this.allJobs = JobDAO.getAllJobs();

        // Show all jobs on launch
        view.displayJobs(allJobs, user);

        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = view.getSearchField().getText().trim().toLowerCase();
                List<Job> filtered = allJobs.stream()
                        .filter(job -> job.getTitle().toLowerCase().contains(query) ||
                                       job.getEmployerName().toLowerCase().contains(query) ||
                                       job.getDescription().toLowerCase().contains(query))
                        .collect(Collectors.toList());
                view.displayJobs(filtered, user);
            }
        });

        view.setVisible(true);
    }
}
