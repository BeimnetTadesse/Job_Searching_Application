package view;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import model.Job;

public class JobSeekerDashboardView extends JFrame {

    private JButton viewProfileButton;
    private JButton searchJobsButton;
    private JPanel jobsPanel;
    private JLabel welcomeLabel;
    private JPanel buttonPanel;
    private JLabel jobsTitle;

    // Expose buttons and jobs
    private Map<JButton, Job> applyButtonsMap = new HashMap<>();

    public JobSeekerDashboardView(String userName, List<Job> jobs) {
        initializeComponents();
        welcomeLabel.setText("Welcome, " + userName);
        setJobList(jobs);
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Job Seeker Dashboard");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        viewProfileButton = new JButton("View Profile");
        searchJobsButton = new JButton("Search Jobs");

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(viewProfileButton);
        buttonPanel.add(searchJobsButton);

        jobsTitle = new JLabel("📌 Available Jobs");
        jobsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jobsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jobsTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jobsPanel = new JPanel();
        jobsPanel.setLayout(new BoxLayout(jobsPanel, BoxLayout.Y_AXIS));
        jobsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(jobsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(welcomeLabel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(jobsTitle, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setJobList(List<Job> jobs) {
        jobsPanel.removeAll();
        applyButtonsMap.clear();

        for (Job job : jobs) {
            JPanel jobItemPanel = new JPanel(new BorderLayout());
            jobItemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            jobItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
            jobItemPanel.setBackground(new Color(245, 245, 245));

            JTextArea jobArea = new JTextArea(
                    "🧑‍💻 Job: " + job.getTitle() + "\n" +
                    "🏢 Employer: " + job.getEmployerName() + "\n" +
                    "📄 Description: " + job.getDescription()
            );
            jobArea.setEditable(false);
            jobArea.setLineWrap(true);
            jobArea.setWrapStyleWord(true);
            jobArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            jobArea.setBackground(new Color(245, 245, 245));
            jobArea.setBorder(BorderFactory.createEmptyBorder());

            JButton applyButton = new JButton("Apply");
            applyButtonsMap.put(applyButton, job);

            jobItemPanel.add(jobArea, BorderLayout.CENTER);
            jobItemPanel.add(applyButton, BorderLayout.EAST);

            jobsPanel.add(jobItemPanel);
            jobsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        jobsPanel.revalidate();
        jobsPanel.repaint();
    }

    public JButton getViewProfileButton() {
        return viewProfileButton;
    }

    public JButton getSearchJobsButton() {
        return searchJobsButton;
    }
    public Map<JButton, Job> getApplyButtonsMap() {
        return applyButtonsMap;
    }
}