package com.eventmanager.service;

import com.eventmanager.model.Participant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * CSVReportGenerator creates a CSV file from participant data.
 */
public class CSVReportGenerator implements IReportGenerator {

    @Override
    public void generateReport(List<Participant> participants, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write CSV header
            bw.write("Name,Email,Event");
            bw.newLine();
            // Write participant details
            for (Participant p : participants) {
                bw.write(p.getName() + "," + p.getEmail() + "," + p.getEventName());
                bw.newLine();
            }
        }
    }
}
