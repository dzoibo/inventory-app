package com.bptn.inventory_assistant.exception;

/**
 * Custom exception thrown when an operation (e.g., recording a sale) cannot be
 * completed due to insufficient stock of a product.
 */
public class InsufficientStockException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String message) {
		super(message);
	}

}
