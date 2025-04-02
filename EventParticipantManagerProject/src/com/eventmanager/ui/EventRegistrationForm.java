package com.eventmanager.ui;

import com.eventmanager.model.EventManager;
import com.eventmanager.model.Participant;
import com.eventmanager.service.IReportGenerator;
import com.eventmanager.service.CSVReportGenerator;
import com.eventmanager.service.TextReportGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * EventRegistrationForm creates a Swing GUI for managing participants.
 * It uses an EventManager for CRUD, and generates reports as CSV or TXT.
 */
public class EventRegistrationForm extends JFrame {

    // Core manager handling all participant logic
    private EventManager eventManager;

    // --- Registration Tab components ---
    private JTextField regNameField;
    private JTextField regEmailField;
    private JTextField regEventField;
    private JButton regAddButton;
    private JTextArea participantsArea;

    // --- Management Tab (Update) ---
    private JTextField updateEmailField;
    private JTextField updateNameField;
    private JTextField updateEventField;
    private JButton updateButton;

    // --- Management Tab (Delete) ---
    private JTextField deleteEmailField;
    private JButton deleteButton;

    // --- Management Tab (Search) ---
    private JTextField searchNameField;
    private JButton searchButton;

    // --- Management Tab (Report) ---
    private JComboBox<String> reportFormatCombo;
    private JButton reportButton;

    /**
     * Main constructor sets up the EventManager and GUI layout.
     */
    public EventRegistrationForm() {
        eventManager = new EventManager();  // Basic CRUD manager
        initUI();
    }

    /**
     * Initializes the overall UI with a tabbed pane for:
     * 1) Registration
     * 2) Management (update, delete, search, generate report)
     */
    private void initUI() {
        setTitle("Digital Event Participant Manager");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Registration", createRegistrationPanel());
        tabbedPane.addTab("Management", createManagementPanel());

        add(tabbedPane);
    }

    /**
     * Registration tab: add participants + display them.
     */
    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Name
        inputPanel.add(new JLabel("Name:"));
        regNameField = new JTextField();
        inputPanel.add(regNameField);

        // Email
        inputPanel.add(new JLabel("Email:"));
        regEmailField = new JTextField();
        inputPanel.add(regEmailField);

        // Event
        inputPanel.add(new JLabel("Event Name:"));
        regEventField = new JTextField();
        inputPanel.add(regEventField);

        // Button
        inputPanel.add(new JLabel(""));
        regAddButton = new JButton("Add Participant");
        inputPanel.add(regAddButton);

        // Participants display area
        participantsArea = new JTextArea();
        participantsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(participantsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Participants List"));

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Action: add participant
        regAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParticipant();
            }
        });

        return panel;
    }

    /**
     * Management tab: update, delete, search, and generate report.
     */
    private JPanel createManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Update section
        JPanel updatePanel = new JPanel(new GridLayout(4, 2, 10, 10));
        updatePanel.setBorder(BorderFactory.createTitledBorder("Update Participant"));
        updatePanel.add(new JLabel("Email:"));
        updateEmailField = new JTextField();
        updatePanel.add(updateEmailField);

        updatePanel.add(new JLabel("New Name:"));
        updateNameField = new JTextField();
        updatePanel.add(updateNameField);

        updatePanel.add(new JLabel("New Event:"));
        updateEventField = new JTextField();
        updatePanel.add(updateEventField);

        updatePanel.add(new JLabel(""));
        updateButton = new JButton("Update Participant");
        updatePanel.add(updateButton);

        panel.add(updatePanel);
        panel.add(Box.createVerticalStrut(10));

        // Delete section
        JPanel deletePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Participant"));
        deletePanel.add(new JLabel("Email:"));
        deleteEmailField = new JTextField();
        deletePanel.add(deleteEmailField);

        deletePanel.add(new JLabel(""));
        deleteButton = new JButton("Delete Participant");
        deletePanel.add(deleteButton);

        panel.add(deletePanel);
        panel.add(Box.createVerticalStrut(10));

        // Search section
        JPanel searchPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Participant"));
        searchPanel.add(new JLabel("Name:"));
        searchNameField = new JTextField();
        searchPanel.add(searchNameField);

        searchPanel.add(new JLabel(""));
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        panel.add(searchPanel);
        panel.add(Box.createVerticalStrut(10));

        // Report section
        JPanel reportPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        reportPanel.setBorder(BorderFactory.createTitledBorder("Generate Report"));

        // Choose format: CSV or TXT
        reportPanel.add(new JLabel("Format:"));
        reportFormatCombo = new JComboBox<>(new String[]{"CSV", "TXT"});
        reportPanel.add(reportFormatCombo);

        reportPanel.add(new JLabel(""));
        reportButton = new JButton("Generate Report");
        reportPanel.add(reportButton);

        panel.add(reportPanel);

        // Hook up button actions
        updateButton.addActionListener(e -> updateParticipant());
        deleteButton.addActionListener(e -> deleteParticipant());
        searchButton.addActionListener(e -> searchParticipant());
        reportButton.addActionListener(e -> generateReport());

        return panel;
    }

    /**
     * Action: Add participant from the Registration tab fields.
     */
    private void addParticipant() {
        String name = regNameField.getText().trim();
        String email = regEmailField.getText().trim();
        String event = regEventField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || event.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Participant participant = new Participant(name, email, event);
        eventManager.addParticipant(participant);
        refreshParticipantList();
        regNameField.setText("");
        regEmailField.setText("");
        regEventField.setText("");
    }

    /**
     * Updates a participant by matching email in the Management tab fields.
     */
    private void updateParticipant() {
        String email = updateEmailField.getText().trim();
        String newName = updateNameField.getText().trim();
        String newEvent = updateEventField.getText().trim();

        if (email.isEmpty() || newName.isEmpty() || newEvent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all update fields.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean updated = eventManager.updateParticipant(email, newName, newEvent);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Participant updated successfully.",
                    "Update", JOptionPane.INFORMATION_MESSAGE);
            refreshParticipantList();
        } else {
            JOptionPane.showMessageDialog(this, "Participant not found.", 
                    "Update Error", JOptionPane.ERROR_MESSAGE);
        }

        updateEmailField.setText("");
        updateNameField.setText("");
        updateEventField.setText("");
    }

    /**
     * Deletes a participant by matching email.
     */
    private void deleteParticipant() {
        String email = deleteEmailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the participant email.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean deleted = eventManager.deleteParticipant(email);
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Participant deleted successfully.", 
                    "Delete", JOptionPane.INFORMATION_MESSAGE);
            refreshParticipantList();
        } else {
            JOptionPane.showMessageDialog(this, "Participant not found.", 
                    "Delete Error", JOptionPane.ERROR_MESSAGE);
        }

        deleteEmailField.setText("");
    }

    /**
     * Searches participants by name and displays results in a dialog.
     */
    private void searchParticipant() {
        String query = searchNameField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Participant> results = eventManager.searchParticipant(query);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No participants found with that name.",
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder();
            for (Participant p : results) {
                sb.append(p).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(),
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
        searchNameField.setText("");
    }

    /**
     * Generates a report (CSV or TXT) based on user selection.
     */
    private void generateReport() {
        // Check which format the user selected (CSV or TXT)
        String format = (String) reportFormatCombo.getSelectedItem();

        // Decide the default file name based on the format
        String defaultFileName;
        if ("TXT".equals(format)) {
            defaultFileName = "report.txt";
        } else {
            // Default to CSV
            defaultFileName = "report.csv";
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setSelectedFile(new java.io.File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        java.io.File fileToSave = fileChooser.getSelectedFile();
        String path = fileToSave.getAbsolutePath();

        // If the user didn’t type an extension, ensure we append the correct one
        if ("TXT".equals(format) && !path.toLowerCase().endsWith(".txt")) {
            path += ".txt";
        } else if ("CSV".equals(format) && !path.toLowerCase().endsWith(".csv")) {
            path += ".csv";
        }

        // Pick the right generator
        IReportGenerator generator;
        if ("TXT".equals(format)) {
            generator = new TextReportGenerator();
        } else {
            generator = new CSVReportGenerator();
        }

        try {
            generator.generateReport(eventManager.getAllParticipants(), path);
            JOptionPane.showMessageDialog(this,
                    "Report generated successfully at:\n" + path,
                    "Report",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error generating report: " + e.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refreshes the main text area in the Registration tab.
     */
    private void refreshParticipantList() {
        StringBuilder sb = new StringBuilder();
        for (Participant p : eventManager.getAllParticipants()) {
            sb.append(p).append("\n");
        }
        participantsArea.setText(sb.toString());
    }

    /**
     * Main method to run the GUI application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventRegistrationForm form = new EventRegistrationForm();
            form.setVisible(true);
        });
    }
}
