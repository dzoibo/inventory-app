package com.bptn.inventory_assistant.model;

import java.time.LocalDate;

/**
 * Represents a record of a product return, including details about the return,
 * the product, and the outcome of the return process.
 */
public class ReturnRecord {
	private String returnId;
	private String saleId;
	private String productId;
	private String productName;
	private String supplier;
	private int quantity;
	private double refundAmount;
	private String returnDate;
	private boolean restocked;
	private String reasonText; 
	private ReturnReason reason;
	private ReturnOutcome outcome;
	
	public ReturnRecord(String returnId, String saleId, String productId, String productName, String supplier,
			int quantity, double refundAmount, String returnDate, boolean restocked, String reasonText,
			ReturnReason reason, ReturnOutcome outcome) {
		this.returnId = returnId;
		this.saleId = saleId;
		this.productId = productId;
		this.productName = productName;
		this.supplier = supplier;
		this.quantity = quantity;
		this.refundAmount = refundAmount;
		this.returnDate = returnDate;
		this.restocked = restocked;
		this.reasonText = reasonText;
		this.reason = reason;
		this.outcome = outcome;
	}
	
	
	public String getReturnId() {
		return returnId;
	}
	public String getSaleId() {
		return saleId;
	}
	public String getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public String getSupplier() {
		return supplier;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public boolean isRestocked() {
		return restocked;
	}
	public String getReasonText() {
		return reasonText;
	}
	public ReturnReason getReason() {
		return reason;
	}
	public ReturnOutcome getOutcome() {
		return outcome;
	}
	
	LocalDate getRefundDate() {
		return LocalDate.parse(returnDate);
	}
	
	@Override
	public String toString() {
	    return String.format(
	            "Return ID: %s, Date: %s, Sale: %s\n"
	          + "  - %s (ID: %s), Qty: %d\n"
	          + "  Outcome: %s, Refund: $%.2f, Restocked: %s\n"
	          + "  Reason: %s (\"%s\")\n",
	            returnId, returnDate, saleId,
	            productName, productId, quantity,
	            outcome, refundAmount, restocked ? "yes" : "no",
	            reason, reasonText);
	}
	
}
