package com.eventmanager.service;

import com.eventmanager.model.Participant;
import java.io.IOException;
import java.util.List;

/**
 * IReportGenerator is an interface demonstrating Abstraction.
 * We can implement it differently (CSV, XLSX, etc.).
 */
public interface IReportGenerator {

    /**
     * Generates a report file for the given list of participants.
     *
     * @param participants The list to include in the report.
     * @param filePath Where the report should be saved on disk.
     * @throws IOException if there's a file I/O error.
     */
	 abstract void generateReport(List<Participant> participants, String filePath) throws IOException;
	}
