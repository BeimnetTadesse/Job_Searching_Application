package controller;

import database.ApplicationsDAO;
import database.JobDAO;
import model.Job;
import model.User;
import view.ApplicantsListView;

import java.util.List;

public class ViewApplicantsController {
    private User employer;

    // Constructor takes the employer and the job whose applicants to view
    public ViewApplicantsController(User employer, Job job) {
        this.employer = employer;

        // Get the applicants for the given job
        List<User> applicants = ApplicationsDAO.getApplicantsForJob(job.getJobId());

        // Pass a Runnable that reloads the employer's jobs and opens EmployerDashboardController on back button
        new ApplicantsListView(applicants, job.getTitle(), () -> {
            // Fetch fresh list of jobs posted by this employer
            List<Job> postedJobs = JobDAO.getJobsPostedByEmployer(employer.getUserId());

            // Open the dashboard with updated jobs list
            new EmployerDashboardController(employer, postedJobs);
        });
    }
}
