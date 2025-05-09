package com.bptn.inventory_assistant.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a single sales transaction. It contains details about the items
 * sold, the total amount, and the date of the sale.
 */
public class SaleRecord {

	private String saleId;
	private List<SoldItem> itemsSold; // To handle multiple items per sale
	private double totalAmount;
	private LocalDate saleDate;

	/**
	 * Constructs a new SaleRecord.
	 *
	 * @param saleId   The unique identifier for this sale.
	 * @param saleDate The date on which the sale occurred.
	 */
	public SaleRecord(String saleId, LocalDate saleDate) {
		this.saleId = saleId;
		this.itemsSold = new ArrayList<>();
		this.totalAmount = 0.0;
		this.saleDate = saleDate;
	}

	/**
	 * Adds an item to this sale record. The total amount of the sale is updated
	 * automatically.
	 *
	 * @param product     The product that was sold.
	 * @param quantity    The quantity of the product sold.
	 * @param priceAtSale The selling price of the product at the time of sale
	 */
	public void addItem(Product product, int quantity, double priceAtSale) {
		this.itemsSold.add(new SoldItem(product.getProductId(), product.getName(), quantity, priceAtSale));
		this.totalAmount += (priceAtSale * quantity);
	}

	// Getters
	public String getSaleId() {
		return saleId;
	}

	public List<SoldItem> getItemsSold() {
		return new ArrayList<>(itemsSold);
	} // Return copy

	public double getTotalAmount() {
		return totalAmount;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Sale ID: %s, Date: %s, Total: $%.2f\nItems:\n", saleId, saleDate.toString(),
				totalAmount));	
		for (SoldItem item : itemsSold) {
			sb.append(String.format("  - %s (ID: %s), Qty: %d, Price: $%.2f\n", item.getProductName(),
					item.getProductId(), item.getQuantitySold(), item.getPriceAtSale()));
		}
		return sb.toString();
	}

}
