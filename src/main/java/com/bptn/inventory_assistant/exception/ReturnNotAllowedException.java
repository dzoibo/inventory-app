package com.bptn.inventory_assistant.exception;

/**
 * Custom exception thrown when a return is refused for whatever reason.
 */
public class ReturnNotAllowedException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReturnNotAllowedException(String message) {
		super(message);
	}

}
