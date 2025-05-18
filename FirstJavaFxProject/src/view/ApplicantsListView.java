package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ApplicantsListView extends JFrame {
    private JList<String> applicantsJList;
    private JButton backToDashboardButton;

    public ApplicantsListView(List<User> applicants, String jobTitle, Runnable backToDashboardAction) {
        setTitle("Applicants for: " + jobTitle);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Heading label
        JLabel heading = new JLabel("Applicants for: " + jobTitle, SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // List model and JList setup
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (User applicant : applicants) {
            listModel.addElement(applicant.getName() + " (" + applicant.getEmail() + ")");
        }
        applicantsJList = new JList<>(listModel);
        applicantsJList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        applicantsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(applicantsJList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Back to Dashboard button
        backToDashboardButton = new JButton("Back to Dashboard");
        backToDashboardButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backToDashboardButton.setFocusPainted(false);
        backToDashboardButton.setBackground(Color.WHITE);               // changed to white background
        backToDashboardButton.setForeground(Color.BLACK);               // changed to black text
        backToDashboardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // black border
        backToDashboardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToDashboardButton.addActionListener(e -> {
            if (backToDashboardAction != null) {
                backToDashboardAction.run();
            }
            dispose(); // close this window
        });

        // Panel for button with padding
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        buttonPanel.add(backToDashboardButton);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(heading, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
