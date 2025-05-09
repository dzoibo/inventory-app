package com.bptn.inventory_assistant.model;

/**
 * Represents an individual item that was part of a SaleRecord. It stores
 * details like the product ID, name, quantity sold, and the price at which it
 * was sold. This class is primarily used as a data holder within SaleRecord.
 */

public class SoldItem {

	private String productId;
	private String productName;
	private int quantitySold;
	private double priceAtSale; // Price at the time of sale

	/**
	 * Constructs a SoldItem.
	 *
	 * @param productId    The ID of the product sold.
	 * @param productName  The name of the product sold.
	 * @param quantitySold The quantity of this product sold in the transaction.
	 * @param priceAtSale  The price per unit at which the product was sold.
	 */
	public SoldItem(String productId, String productName, int quantitySold, double priceAtSale) {
		this.productId = productId;
		this.productName = productName;
		this.quantitySold = quantitySold;
		this.priceAtSale = priceAtSale;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public double getPriceAtSale() {
		return priceAtSale;
	}

	@Override
	public String toString() {
		return String.format("%s (ID: %s) - Qty: %d @ $%.2f each", productName, productId, quantitySold, priceAtSale);
	}

}
