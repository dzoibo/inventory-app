package com.bptn.inventory_assistant.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class for generating unique identifiers for various entities in the
 * application, such as products and sales records. It provides simple
 * sequential ID generation and can also generate UUIDs.
 */
public class IdGenerator {

	// Private constructor to prevent instantiation of this utility class.
	private IdGenerator() {
	}

	private static AtomicInteger productCounter = new AtomicInteger(1000);
	private static AtomicInteger saleCounter = new AtomicInteger(5000);

	public static String generateProductId() {
		// More robust might involve a prefix + counter or UUID
		return "PROD-" + productCounter.getAndIncrement();
	}

	public static String generateSaleId() {
		return "SALE-" + saleCounter.getAndIncrement();
	}

	// For truly unique IDs, especially if distributed or persisted long-term (not used in our project)
	public static String generateUUID() {
		return UUID.randomUUID().toString();

	}

}
