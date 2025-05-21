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
public class Util {
	private static Scanner scanner = new Scanner(System.in);
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD

	static final String GREEN = "\u001B[32m"; // used for printing purpose
	static final String RED = "\u001B[31m";
	static final String RESET = "\u001B[0m";

	// Private constructor to prevent instantiation of this utility class.
	private Util() {
	}

	public static String getString(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	public static String getNonEmptyString(String prompt) {
		String input;
		do {
			System.out.print(prompt);
			input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				printWarning("Input cannot be empty. Please try again.");
			}
		} while (input.isEmpty());
		return input;
	}

	public static String getAlphaNumericString(String prompt) {
		String input;
		do {
			System.out.print(prompt);
			input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				printWarning("Input cannot be empty. Please try again.");
			} else if (!input.matches("[A-Za-z0-9 ]+")) {
				printWarning("Input must be alphanumeric (letters and numbers only). Please try again.");
				input = "";
			} else if (!input.matches(".*[A-Za-z].*")) {
				printWarning("Input must contain at least one letter. Please try again.");
				input = "";
			}
		} while (input.isEmpty());
		return input;
	}

	public static int getIntInRange(String prompt, int min, int max) {
		while (true) {
			try {
				System.out.print(prompt);
				int value = Integer.parseInt(scanner.nextLine());

				if (value >= min && value <= max)
					return value;

				printWarning("Invalid choice. Enter a number between " + min + " and " + max + ".");
			} catch (NumberFormatException e) {
				printWarning("Invalid input. Please enter a whole number.");
			}
		}
	}

	public static double getDouble(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				double value = Double.parseDouble(scanner.nextLine());

				if (value > 0)
					return value;
				printWarning("Invalid input. Please enter a positive number (e.g., 12.34).");

			} catch (NumberFormatException e) {
				printWarning("Invalid input. Please enter a valid number (e.g., 12.34).");
			}
		}
	}

	public static LocalDate getDate(String prompt) throws DateTimeParseException {
		while (true) {
			try {
				System.out.print(prompt);
				return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				printWarning("Invalid date format. Please use YYYY-MM-DD.");
			}
		}
	}

	public static void printColored(String colorCode, String... messages) {
        System.out.print(colorCode);
        for (String message : messages) {
            System.out.println(message);
            System.out.flush();
        }
        System.out.print(RESET);
    }

    public static void printWarning(String... messages) {
        printColored(RED, messages);
    }

    public static void printSuccess(String... messages) {
        printColored(GREEN, messages);
    }

	public static void closeScanner() {
		scanner.close();
	}

}
