package com.truelayer.interview.funpokedex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;



@Service
public class UtilService {
    
   
    /**
     * Sanitizes input for safe logging to prevent log injection attacks
     * @param input the input string to sanitize
     * @return sanitized string safe for logging
     */
    public String sanitizeForLogging(String input) {
        if (input == null) {
            return "null";
        }
        // Remove potential log injection characters
        return input.replaceAll("[\r\n\t]", "_")
                   .replaceAll("\\p{Cntrl}", "")
                   .substring(0, Math.min(input.length(), 100)); // Limit length
    }
}