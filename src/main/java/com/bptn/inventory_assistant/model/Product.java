package com.bptn.inventory_assistant.model;

/**
 * Represents a generic product in the inventory system. This class encapsulates
 * common properties of a product like ID, name, price, and stock quantity.
 */
public class Product {

	private String productId;
	private String name;
	private ProductCategory category;
	private double purchasePrice;
	private double sellingPrice;
	private int quantityInStock;
	private String supplier;

	/**
	 * Constructs a new Product.
	 *
	 * @param productId       The unique identifier for the product.
	 * @param name            The name of the product.
	 * @param category        The category of the product.
	 * @param purchasePrice   The price at which the product was purchased.
	 * @param sellingPrice    The price at which the product is sold.
	 * @param quantityInStock The current quantity of the product in stock.
	 * @param supplier        The supplier of the product.
	 */
	public Product(String productId, String name, ProductCategory category, double purchasePrice, double sellingPrice,
			int quantiyInStock, String supplier) {
		this.productId = productId;
		this.name = name;
		this.category = category;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
		this.quantityInStock = quantiyInStock;
		this.supplier = supplier;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantiyInStock) {
		this.quantityInStock = quantiyInStock;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Increases the stock quantity of the product.
	 *
	 * @param quantity The amount to increase the stock by. Must be positive.
	 */

	public void increaseStock(int quantity) {

		if (quantity > 0) {

			this.quantityInStock += quantity;
		}

	}

	/**
	 * Decreases the stock quantity of the product. This method assumes validation
	 * (e.g., sufficient stock) is done by the calling service.
	 *
	 * @param quantity The amount to decrease the stock by. Must be positive.
	 */

	public void decreaseStock(int quantity) {

		if (quantity > 0 && this.quantityInStock >= quantity) {
			this.quantityInStock -= quantity;
		}
	}

	@Override
	public String toString() {
		return String.format("ID: %s, Name: %s, Category: %s, Price: $%.2f, Stock: %d", productId, name, category,
				sellingPrice, quantityInStock);
	}
}
