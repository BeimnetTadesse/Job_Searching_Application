package view;

import model.Job;
import model.User;
import database.ApplicationsDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JobSearchView extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JPanel resultPanel;
    private JScrollPane scrollPane;

    public JobSearchView() {
        setTitle("Search Jobs");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void displayJobs(List<Job> jobs, User user) {
        resultPanel.removeAll();

        if (jobs.isEmpty()) {
            JLabel noResultLabel = new JLabel("No jobs found.");
            noResultLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            resultPanel.add(noResultLabel);
        } else {
            for (Job job : jobs) {
                JPanel jobPanel = new JPanel(new BorderLayout());
                jobPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                jobPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                jobPanel.setBackground(new Color(245, 245, 245));

                JTextArea jobArea = new JTextArea(
                        "🧑‍💻 " + job.getTitle() + "\n" +
                        "🏢 " + job.getEmployerName() + "\n" +
                        "📄 " + job.getDescription()
                );
                jobArea.setEditable(false);
                jobArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                jobArea.setBackground(new Color(245, 245, 245));
                jobArea.setLineWrap(true);
                jobArea.setWrapStyleWord(true);
                jobArea.setBorder(null);

                JButton applyButton = new JButton("Apply");

                // Apply logic
                applyButton.addActionListener(e -> {
                    int jobId = job.getJobId();
                    boolean success = ApplicationsDAO.applyToJob(user.getUserId(), jobId, user.getResumeLink());

                    if (success) {
                        applyButton.setText("Applied");
                        applyButton.setEnabled(false);
                        JOptionPane.showMessageDialog(this,
                                "You have successfully applied to \"" + job.getTitle() + "\" job.",
                                "Application Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "You have already applied to this job.",
                                "Already Applied",
                                JOptionPane.WARNING_MESSAGE);
                    }
                });

                jobPanel.add(jobArea, BorderLayout.CENTER);
                jobPanel.add(applyButton, BorderLayout.EAST);
                resultPanel.add(jobPanel);
                resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }
}