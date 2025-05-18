package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PostJobView extends JDialog {

    private JTextField jobTitleField;
    private JTextArea descriptionArea;
    private JButton postJobButton;
    private JButton cancelButton;

    private boolean jobPosted = false;  // To indicate success

    public PostJobView(Frame parent) {
        super(parent, "Post a New Job", true);
        initializeComponents();
        setSize(400, 350);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        // Title input
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JLabel("Job Title:"), BorderLayout.NORTH);
        jobTitleField = new JTextField();
        titlePanel.add(jobTitleField, BorderLayout.CENTER);

        // Description input
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
        descriptionArea = new JTextArea(6, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        descPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        postJobButton = new JButton("Post Job");
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(postJobButton);
        buttonsPanel.add(cancelButton);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(descPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        add(panel);

        // Initially disable post button
        postJobButton.setEnabled(false);

        // Document listener to enable/disable post button based on input
        DocumentListener docListener = new DocumentListener() {
            private void updateButton() {
                boolean enabled = !jobTitleField.getText().trim().isEmpty()
                        && !descriptionArea.getText().trim().isEmpty();
                postJobButton.setEnabled(enabled);
            }
            public void insertUpdate(DocumentEvent e) { updateButton(); }
            public void removeUpdate(DocumentEvent e) { updateButton(); }
            public void changedUpdate(DocumentEvent e) { updateButton(); }
        };
        jobTitleField.getDocument().addDocumentListener(docListener);
        descriptionArea.getDocument().addDocumentListener(docListener);

        // Button actions
        cancelButton.addActionListener(e -> {
            jobPosted = false;
            dispose();
        });

        postJobButton.addActionListener(e -> {
            String title = jobTitleField.getText().trim();
            String desc = descriptionArea.getText().trim();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the job title.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the job description.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            jobPosted = true;
            dispose();
        });

        // Set Enter key to trigger Post Job button
        getRootPane().setDefaultButton(postJobButton);

        // Set Escape key to trigger Cancel button action
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        getRootPane().registerKeyboardAction(e -> {
            jobPosted = false;
            dispose();
        }, escapeKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            jobTitleField.setText("");
            descriptionArea.setText("");
            jobPosted = false;
            postJobButton.setEnabled(false);
        }
        super.setVisible(b);
    }

    public boolean isJobPosted() {
        return jobPosted;
    }

    public String getJobTitle() {
        return jobTitleField.getText().trim();
    }

    public String getJobDescription() {
        return descriptionArea.getText().trim();
    }
}
