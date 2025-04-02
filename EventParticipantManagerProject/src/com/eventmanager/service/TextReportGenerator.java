package com.eventmanager.service;

import com.eventmanager.model.Participant;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * TextReportGenerator creates a simple .txt file of participant data.
 */
public class TextReportGenerator implements IReportGenerator {

    @Override
    public void generateReport(List<Participant> participants, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write a simple header line
            bw.write("List of Participants (Plain Text Report)");
            bw.newLine();
            bw.write("---------------------------------------");
            bw.newLine();
            
            // Write participant details, each participant in one or more lines
            for (Participant p : participants) {
                bw.write("Name: " + p.getName());
                bw.newLine();
                bw.write("Email: " + p.getEmail());
                bw.newLine();
                bw.write("Event: " + p.getEventName());
                bw.newLine();
                bw.write("---------------------------------------");
                bw.newLine();
            }
        }
    }
}
