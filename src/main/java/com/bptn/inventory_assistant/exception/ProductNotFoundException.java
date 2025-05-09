package com.bptn.inventory_assistant.exception;

/**
 * Custom exception thrown when a product cannot be found in the inventory,
 * typically when searching by ID or name.
 */
public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}

}
