package com.truelayer.interview.funpokedex.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilServiceTest {

    private UtilService utilService;

    @BeforeEach
    public void setUp() {
        utilService = new UtilService();
    }

    @Test
    public void testSanitizeForLoggingWithNullInput() {
        String result = utilService.sanitizeForLogging(null);
        assertEquals("null", result);
    }

    @Test
    public void testSanitizeForLoggingWithCarriageReturn() {
        String input = "test\rstring";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("test_string", result);
    }

    @Test
    public void testSanitizeForLoggingWithNewline() {
        String input = "test\nstring";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("test_string", result);
    }

    @Test
    public void testSanitizeForLoggingWithTab() {
        String input = "test\tstring";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("test_string", result);
    }

    @Test
    public void testSanitizeForLoggingWithControlCharacters() {
        String input = "test\u0000\u0001string";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("teststring", result);
    }

    @Test
    public void testSanitizeForLoggingWithLongInput() {
        String input = "a".repeat(150);
        String result = utilService.sanitizeForLogging(input);
        assertEquals(100, result.length());
        assertEquals("a".repeat(100), result);
    }

    @Test
    public void testSanitizeForLoggingWithExactly100Characters() {
        String input = "a".repeat(100);
        String result = utilService.sanitizeForLogging(input);
        assertEquals(100, result.length());
        assertEquals("a".repeat(100), result);
    }

    @Test
    public void testSanitizeForLoggingWithNormalString() {
        String input = "normal string";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("normal string", result);
    }

    @Test
    public void testSanitizeForLoggingWithMultipleSpecialCharacters() {
        String input = "test\r\n\tstring\u0000end";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("test___stringend", result);
    }

    @Test
    public void testSanitizeForLoggingWithEmptyString() {
        String input = "";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("", result);
    }

    @Test
    public void testSanitizeForLoggingWithOnlySpecialCharacters() {
        String input = "\r\n\t";
        String result = utilService.sanitizeForLogging(input);
        assertEquals("___", result);
    }
}
