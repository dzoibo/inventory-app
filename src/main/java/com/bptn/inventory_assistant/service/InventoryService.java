package com.bptn.inventory_assistant.service;

import com.bptn.inventory_assistant.exception.InsufficientStockException;
import com.bptn.inventory_assistant.exception.ProductNotFoundException;
import com.bptn.inventory_assistant.model.Product;
import com.bptn.inventory_assistant.model.SaleRecord;
import com.bptn.inventory_assistant.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for managing the inventory of products and sales
 * records. It handles operations like adding products, recording sales,
 * restocking, and retrieving inventory data.
 */

public class InventoryService {

	private List<Product> products;
	private List<SaleRecord> salesHistory;

	/**
	 * Constructs an InventoryService, initializing empty lists for products and
	 * sales history.
	 */

	public InventoryService() {
		this.products = new ArrayList<>();
		this.salesHistory = new ArrayList<>();
	}

	/**
	 * Adds a new product to the inventory.
	 *
	 * @param product The product to add.
	 */

	public void addProduct(Product product) {

		// Optional: Check for duplicate ID if not strictly enforced by IdGenerator
		if (products.stream().anyMatch(p -> p.getProductId().equals(product.getProductId()))) {
			System.err.println("Warning: Product with ID " + product.getProductId() + " already exists. Product '"
					+ product.getName() + "' not added again.");
			return;
		}

		products.add(product);
		System.out.println("Product added: " + product.getName());
	}

	/**
	 * Finds a product in the inventory by its ID.
	 *
	 * @param productId The ID of the product to find.
	 * @return the product if found, or an empty Optional otherwise.
	 */

	public Optional<Product> findProductById(String productId) {
		return products.stream().filter(p -> p.getProductId().equalsIgnoreCase(productId)).findFirst();
	}

	/**
	 * Retrieves a list of all products currently in the inventory.
	 *
	 * @return A new list containing all products (a defensive copy).
	 */

	public List<Product> getAllProducts() {
		return new ArrayList<>(products); // Return copy
	}

	/**
	 * Retrieves the history of all sales recorded.
	 *
	 * @return A new list containing all sale records (a defensive copy).
	 */

	public List<SaleRecord> getSalesHistory() {
		return new ArrayList<>(salesHistory); // Return copy
	}

	/**
	 * Records a new sale transaction. This involves validating stock, decreasing
	 * stock for sold items, and creating a SaleRecord.
	 *
	 * @param itemsToSell A map where keys are product IDs and values are quantities
	 *                    to sell.
	 * @return The generated SaleRecord if the sale was successful.
	 * @throws ProductNotFoundException   If any product ID in itemsToSell is not
	 *                                    found in the inventory.
	 * @throws InsufficientStockException If there is not enough stock for any
	 *                                    requested item.
	 */

	public SaleRecord recordSale(Map<String, Integer> itemsToSell)
			throws ProductNotFoundException, InsufficientStockException {

		SaleRecord saleRecord = new SaleRecord(IdGenerator.generateSaleId(), LocalDate.now());
		Map<Product, Integer> validatedProductsForSale = new HashMap<>();

		// First pass: validate all items and quantities
		for (Map.Entry<String, Integer> entry : itemsToSell.entrySet()) {
			String productId = entry.getKey();
			int quantityToSell = entry.getValue();

			if (quantityToSell <= 0) {
				// Or throw an IllegalArgumentException
				System.err.println("Warning: Invalid quantity " + quantityToSell + " for product ID " + productId
						+ ". Skipping item.");
				continue;
			}

			Product product = findProductById(productId)
					.orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));

			if (product.getQuantityInStock() < quantityToSell) {
				throw new InsufficientStockException("Insufficient stock for " + product.getName() + ". Available: "
						+ product.getQuantityInStock() + ", Requested: " + quantityToSell);
			}
			validatedProductsForSale.put(product, quantityToSell);
		}

		if (validatedProductsForSale.isEmpty()) {
			System.out.println("No valid items to sell. Sale cancelled.");
			return null;
		}

		// Second pass: process the sale if all validations passed
		for (Map.Entry<Product, Integer> entry : validatedProductsForSale.entrySet()) {
			Product product = entry.getKey();
			int quantitySold = entry.getValue();

			product.decreaseStock(quantitySold);
			saleRecord.addItem(product, quantitySold, product.getSellingPrice());
		}

		salesHistory.add(saleRecord);
		System.out.println("\nSale recorded successfully.");
		System.out.println(saleRecord); // Print sale details

		return saleRecord;
	}

	/**
	 * Restocks a product in the inventory by increasing its quantity.
	 *
	 * @param productId The ID of the product to restock.
	 * @param quantity  The quantity to add to the stock. Must be positive.
	 * @throws ProductNotFoundException If the product ID is not found.
	 */

	public void restockProduct(String productId, int quantity) throws ProductNotFoundException {

		if (quantity <= 0) {
			System.err.println("Restock quantity must be positive. Product ID: " + productId);
			return;
		}

		Product product = findProductById(productId).orElseThrow(
				() -> new ProductNotFoundException("Product with ID " + productId + " not found for restocking."));
		product.increaseStock(quantity);
		System.out.println(
				"Restocked " + product.getName() + " by " + quantity + ". New stock: " + product.getQuantityInStock());
	}

	/**
	 * Updates the selling price of a specific product.
	 *
	 * @param productId       The ID of the product whose price is to be updated.
	 * @param newSellingPrice The new selling price. Must be non-negative.
	 * @throws ProductNotFoundException If the product ID is not found.
	 */

	public void updateProductPrice(String productId, double newSellingPrice) throws ProductNotFoundException {

		if (newSellingPrice < 0) {
			System.err.println("Selling price cannot be negative. Product ID: " + productId);
			return; // Or throw IllegalArgumentException
		}
		
		Product product = findProductById(productId).orElseThrow(
				() -> new ProductNotFoundException("Product with ID " + productId + " not found for price update."));
		product.setSellingPrice(newSellingPrice);
		System.out.println("Price for " + product.getName() + " updated to $" + newSellingPrice);
	}

}
