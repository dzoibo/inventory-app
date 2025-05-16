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
	
	public static String getNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
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
	            System.out.println("Input cannot be empty. Please try again.");
	        } else if (!input.matches("[A-Za-z0-9 ]+")) {
	            System.out.println("Input must be alphanumeric (letters and numbers only). Please try again.");
	            input = "";
	        } else if (!input.matches(".*[A-Za-z].*")) {
	            System.out.println("Input must contain at least one letter. Please try again.");
	            input = "";
	        }
	    } while (input.isEmpty());
	    return input;
	}

//	public static int getInt(String prompt) {
//		while (true) {
//			System.out.print(prompt);
//			String input = scanner.nextLine();
//			try {
//				return Integer.parseInt(input);
////				if (value > 0) {
////					return value;
////				}
////				System.out.println("value must be positive.");
//			} catch (NumberFormatException e) {
//				System.out.println("Invalid input. Please enter a whole number.");
//			}
//		}
//	}

	public static int getIntInRange(String prompt, int min, int max) {
		while (true) {
			try {
				System.out.print(prompt);
				int value = Integer.parseInt(scanner.nextLine());
				
				if (value >= min && value <= max) return value;
				
				System.out.println("Invalid choice. Enter a number between " + min + " and " + max + ".");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a whole number.");
			}
		}
	}

	public static double getDouble(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				double value = Double.parseDouble(scanner.nextLine());
	
				if (value > 0) return value;
				System.out.println("Invalid input. Please enter a positive number (e.g., 12.34).");
				
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
