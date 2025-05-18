package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Job;

public class JobDAO {

    // Fetch all jobs with employer email
    public static List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT j.job_id, j.job_title, j.description, u.email FROM Job j JOIN User u ON j.employer_id = u.user_id";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int jobId = rs.getInt("job_id");
                String title = rs.getString("job_title");
                String description = rs.getString("description");
                String employerEmail = rs.getString("email");

                jobs.add(new Job(jobId, title, description, employerEmail));
            }

        } catch (Exception e) {
            System.out.println("Error fetching jobs: " + e.getMessage());
        }

        return jobs;
    }

    // Fetch jobs posted by specific employer with email
    public static List<Job> getJobsPostedByEmployer(int employerId) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT j.job_id, j.job_title, j.description, u.email " +
                     "FROM Job j JOIN User u ON j.employer_id = u.user_id " +
                     "WHERE j.employer_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int jobId = rs.getInt("job_id");
                String title = rs.getString("job_title");
                String description = rs.getString("description");
                String employerEmail = rs.getString("email");

                jobs.add(new Job(jobId, title, description, employerEmail));
            }

        } catch (Exception e) {
            System.out.println("Error fetching employer's jobs: " + e.getMessage());
        }
        
        return jobs;
    }

    // Add a new job posted by employer (by employer ID)
    public static boolean addJob(Job job, int employerId) {
        String sql = "INSERT INTO Job (job_title, description, employer_id) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, job.getTitle());
            pstmt.setString(2, job.getDescription());
            pstmt.setInt(3, employerId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (Exception e) {
            System.out.println("Error adding job: " + e.getMessage());
            return false;
        }
    }
}
