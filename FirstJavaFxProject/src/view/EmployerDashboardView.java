package view;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import model.Job;
import model.User;

public class EmployerDashboardView extends JFrame {

    private JLabel titleLabel;
    private JLabel welcomeLabel;
    private JButton editProfileButton;
    private JPanel jobsListPanel;
    private JButton postJobButton;

    private Map<JButton, Job> viewApplicantsButtonsMap = new HashMap<>();
    private User employer;

    public EmployerDashboardView(User employer, List<Job> postedJobs) {
        this.employer = employer;
        initializeComponents();
        welcomeLabel.setText("Welcome, " + employer.getName() + "!");
        setJobsList(postedJobs);
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Employer Dashboard");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("Employer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        editProfileButton = new JButton("Edit Profile");
        editProfileButton.setPreferredSize(new Dimension(160, 36));
        editProfileButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        editProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        topPanel.add(welcomeLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(editProfileButton);

        jobsListPanel = new JPanel();
        jobsListPanel.setLayout(new BoxLayout(jobsListPanel, BoxLayout.Y_AXIS));
        jobsListPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel jobsLabel = new JLabel("Your Posted Jobs:");
        jobsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jobsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jobsListPanel.add(jobsLabel);
        jobsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JScrollPane scrollPane = new JScrollPane(jobsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        postJobButton = new JButton("Post Job");
        postJobButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        postJobButton.setPreferredSize(new Dimension(140, 40));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(postJobButton);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setJobsList(List<Job> jobs) {
        jobsListPanel.removeAll();

        JLabel jobsLabel = new JLabel("Your Posted Jobs:");
        jobsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jobsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jobsListPanel.add(jobsLabel);
        jobsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        viewApplicantsButtonsMap.clear();

        int index = 1;
        for (Job job : jobs) {
            JPanel jobPanel = new JPanel(new BorderLayout(10, 10));
            jobPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            jobPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            jobPanel.setBackground(new Color(245, 245, 245));

            JLabel jobTitleLabel = new JLabel(index + ". " + job.getTitle());
            jobTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            jobTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            JButton viewApplicantsButton = new JButton("View Applicants");
            viewApplicantsButtonsMap.put(viewApplicantsButton, job);

            jobPanel.add(jobTitleLabel, BorderLayout.WEST);
            jobPanel.add(viewApplicantsButton, BorderLayout.EAST);

            jobsListPanel.add(jobPanel);
            jobsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            index++;
        }

        jobsListPanel.revalidate();
        jobsListPanel.repaint();
    }

    public JButton getEditProfileButton() {
        return editProfileButton;
    }

    public JButton getPostJobButton() {
        return postJobButton;
    }

    public Map<JButton, Job> getViewApplicantsButtonsMap() {
        return viewApplicantsButtonsMap;
    }
}
