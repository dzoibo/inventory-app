package com.bptn.inventory_assistant.model;

import static com.bptn.inventory_assistant.util.Util.printWarning;

/**
 * Represents an electronics product, a specialized type of Product. It includes
 * properties like serial number and warranty period.
 */

public class ElectronicsProduct extends Product {

	private String serialNumber;
	private int warrantyPeriodMonths; // 0 if no warranty

	/**
	 * Constructs a new ElectronicsProduct.
	 *
	 * @param productId            The unique identifier for the product.
	 * @param name                 The name of the product.
	 * @param category             The category of the product (should ideally be
	 *                             {ProductCategory#ELECTRONICS}).
	 * @param purchasePrice        The price at which the product was purchased.
	 * @param sellingPrice         The price at which the product is sold.
	 * @param quantityInStock      The current quantity of the product in stock.
	 * @param supplier             The supplier of the product.
	 * @param serialNumber         The serial number of the electronics product (can
	 *                             be null or empty).
	 * @param warrantyPeriodMonths The warranty period in months (0 if no warranty).
	 */
	public ElectronicsProduct(String productId, String name, ProductCategory category, double purchasePrice,
			double sellingPrice, int quantityInStock, String supplier, String serialNumber, int warrantyPeriodMonths) {

		super(productId, name, category, purchasePrice, sellingPrice, quantityInStock, supplier);

		if (category != ProductCategory.ELECTRONICS) {
			printWarning("Warning: Electronics Product created with non-ELECTRONICS category: " + category);
		}

		this.serialNumber = serialNumber;
		this.warrantyPeriodMonths = warrantyPeriodMonths;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getWarrantyPeriodMonths() {
		return warrantyPeriodMonths;
	}

	public void setWarrantyPeriodMonths(int warrantyPeriodMonths) {
		this.warrantyPeriodMonths = warrantyPeriodMonths;
	}

	@Override
	public String toString() {
		String warrantyInfo = warrantyPeriodMonths > 0 ? ", Warranty: " + warrantyPeriodMonths + " months" : "";
		String serialInfo = (serialNumber != null && !serialNumber.isEmpty()) ? ", S/N: " + serialNumber : "";
		return String.format("%s%s%s", super.toString(), serialInfo, warrantyInfo);
	}

}
