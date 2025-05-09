package com.bptn.inventory_assistant.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Utility class for handling console input from the user. Provides methods to
 * safely read strings, integers, doubles, and dates, with basic validation and
 * error handling.
 */
public class InputUtil {
	private static Scanner scanner = new Scanner(System.in);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD

	// Private constructor to prevent instantiation of this utility class.
	private InputUtil() {
	}

	public static String getString(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	public static int getInt(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a whole number.");
			}
		}
	}

	public static double getDouble(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number (e.g., 12.34).");
			}
		}
	}

	public static LocalDate getDate(String prompt) throws DateTimeParseException {
		while (true) {
			try {
				System.out.print(prompt);
				return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD.");
			}
		}
	}

	public static void closeScanner() {
		scanner.close();
	}

}
