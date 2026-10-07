package com.bptn.inventory_assistant.exception;

/**
 * Custom exception thrown when a sale cannot be found in the system, typically
 * when searching by sale ID or other criteria.
 */
public class SaleNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public SaleNotFoundException(String message) {
		super(message);
	}

}
