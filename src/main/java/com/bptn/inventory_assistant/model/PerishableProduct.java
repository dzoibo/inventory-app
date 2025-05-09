package com.bptn.inventory_assistant.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a perishable product, which is a specialized type of Product. It
 * includes an expiry date in addition to the standard product properties.
 */

public class PerishableProduct extends Product {

	private LocalDate expiryDate;

	/**
	 * Constructs a new PerishableProduct.
	 *
	 * @param productId       The unique identifier for the product.
	 * @param name            The name of the product.
	 * @param category        The category of the product.
	 * @param purchasePrice   The price at which the product was purchased.
	 * @param sellingPrice    The price at which the product is sold.
	 * @param quantityInStock The current quantity of the product in stock.
	 * @param supplier        The supplier of the product.
	 * @param expiryDate      The expiry date of the perishable product.
	 */

	public PerishableProduct(String productId, String name, ProductCategory category, double purchasePrice,
			double sellingPrice, int quantiyInStock, String supplier, LocalDate expiryDate) {

		super(productId, name, category, purchasePrice, sellingPrice, quantiyInStock, supplier);
		this.expiryDate = expiryDate;

	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return String.format("%s, Expires: %s", super.toString(), expiryDate.format(DateTimeFormatter.ISO_DATE));
	}

}
