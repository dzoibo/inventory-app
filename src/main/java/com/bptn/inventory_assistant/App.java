package com.bptn.inventory_assistant;

import com.bptn.inventory_assistant.exception.InsufficientStockException;
import com.bptn.inventory_assistant.exception.ProductNotFoundException;
import com.bptn.inventory_assistant.model.*;
import com.bptn.inventory_assistant.service.InventoryService;
import com.bptn.inventory_assistant.service.OpenAIService;
import com.bptn.inventory_assistant.util.IdGenerator;
import com.bptn.inventory_assistant.util.Util;
import static com.bptn.inventory_assistant.util.Util.printWarning;
import static com.bptn.inventory_assistant.util.Util.printSuccess;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class App {

	private static InventoryService inventoryService = new InventoryService();
	private static OpenAIService openAIService;

	private static void printMenu() {
		System.out.println("\n--- Smart Inventory Assistant Menu ---");
		System.out.println("1. Add New Product");
		System.out.println("2. View All Products");
		System.out.println("3. Record Sale");
		System.out.println("4. Restock Product");
		System.out.println("5. Update Product Price");
		System.out.println("6. View Sales History");
		System.out.println("7. Delete Product");
		if (openAIService.isAIAvailable()) {
			System.out.println("8. AI: Generate Sales Summary & Forecast");
			System.out.println("9. AI: Generate Promotional Tagline for a Product");
		}
		System.out.println("0. Exit");

	}

	public static void main(String[] args) {

		openAIService = new OpenAIService();

		System.out.println("Welcome to Smart Inventory Assistant!");

		initializeData(); // Add some sample data

		boolean running = true;
		while (running) {
			printMenu();

			int maxOption = openAIService.isAIAvailable() ? 9 : 7;

			int choice = Util.getIntInRange("Enter your choice: ", 0, maxOption);
			if (!openAIService.isAIAvailable() && (choice == 8 || choice == 9)) {
				printWarning("Invalid choice. Please try again.");
				continue;
			}

			try {
				switch (choice) {
				case 1 -> addProduct();
				case 2 -> viewAllProducts();
				case 3 -> recordSale();
				case 4 -> restockProduct();
				case 5 -> updateProductPrice();
				case 6 -> viewSalesHistory();
				case 7 -> deleteProduct();
				case 8 -> generateAISalesSummary();
				case 9 -> generateAIPromoTagline();
				case 0 -> {
					running = false;
					System.out.println("Exiting system. Goodbye!");
				}
				default -> printWarning("Invalid choice. Please try again.");
				}

			} catch (ProductNotFoundException | InsufficientStockException | DateTimeParseException e) {

				printWarning("Error: " + e.getMessage());

			} catch (Exception e) {
				printWarning("An unexpected error occurred: " + e.getMessage());
				// e.printStackTrace(); // For debugging
			}

		}
		Util.closeScanner();

	}

	private static void addProduct() {
		System.out.println("\n--- Add New Product ---");
		String name = Util.getAlphaNumericString("Enter product name: ");
		System.out.println("Select Category (Enter a number 1-9):");
		ProductCategory[] categories = ProductCategory.values();
		for (int i = 0; i < categories.length; i++) {
			System.out.println((i + 1) + ". " + categories[i]);
		}

		int catChoice = Util.getIntInRange("Category choice: ", 1, categories.length);

		ProductCategory category = categories[catChoice - 1];

		double purchasePrice = Util.getDouble("Enter purchase price: ");
		double sellingPrice = Util.getDouble("Enter selling price: ");
		int quantity = Util.getIntInRange("Enter initial quantity: ", 1, Integer.MAX_VALUE);
		String supplier = Util.getAlphaNumericString("Enter supplier name: ");
		String productId = IdGenerator.generateProductId();

		System.out.println("Is this a special type of product? (1. Regular, 2. Perishable, 3. Electronics)");
		int typeChoice = Util.getIntInRange("Type choice: ", 1, 3);

		Product newProduct;

		if (typeChoice == 2) { // Perishable
			LocalDate expiryDate = Util.getDate("Enter expiry date (YYYY-MM-DD): ");
			newProduct = new PerishableProduct(productId, name, category, purchasePrice, sellingPrice, quantity,
					supplier, expiryDate);
		} else if (typeChoice == 3) { // Electronics
			String serialNumber = Util.getString("Enter serial number (optional, press Enter to skip): ");
			int warrantyMonths = Util.getIntInRange("Enter warranty period in months (0 if none): ", 0,
					Integer.MAX_VALUE);
			newProduct = new ElectronicsProduct(productId, name, category, purchasePrice, sellingPrice, quantity,
					supplier, serialNumber.isEmpty() ? null : serialNumber, warrantyMonths);
		} else { // Regular
			newProduct = new Product(productId, name, category, purchasePrice, sellingPrice, quantity, supplier);
		}
		inventoryService.addProduct(newProduct);
	}

	private static void viewAllProducts() {

		printSuccess("\n--- All Products in Inventory ---");
		List<Product> products = inventoryService.getAllProducts();
		if (products.isEmpty()) {
			printWarning("No products in inventory.");
			return;
		}
		products.forEach(System.out::println);
	}

	private static void viewAllProductSummaries() {
		printSuccess("\n--- Product List (ID and Name) ---");
		List<Product> products = inventoryService.getAllProducts();
		if (products.isEmpty()) {
			printWarning("No products in inventory.");
			return;
		}
		
		// Display only ID and Name for each product
		products.forEach(
				product -> System.out.println("ID: " + product.getProductId() + ", Name: " + product.getName()));
	}

	private static void recordSale() throws ProductNotFoundException, InsufficientStockException {

		System.out.println("\n--- Record Sale ---");
		Map<String, Integer> itemsToSell = new HashMap<>();

		while (true) {
			viewAllProductSummaries();
			String productId = Util.getNonEmptyString("\nEnter Product ID to sell (or 'done' to finish): ");
			if (productId.equalsIgnoreCase("done")) {
				break;
			}

			Optional<Product> productOpt = inventoryService.findProductById(productId);
			if (productOpt.isEmpty()) {
				printWarning("Product ID not found. Please try again.");
				continue;
			}

			Product product = productOpt.get();
			int alreadyInCart = itemsToSell.getOrDefault(productId, 0);

			int availableStock = product.getQuantityInStock() - alreadyInCart;

			if (availableStock <= 0) {
				printWarning("No stock remaining for " + product.getName() + ".");
				continue;
			}

			printSuccess("Selected: " + product.getName() + " (Available: " + availableStock + ")");

			int quantity = Util.getIntInRange("Enter quantity to sell: ", 1, availableStock);

			itemsToSell.put(productId, alreadyInCart + quantity);
			printSuccess(product.getName() + " x" + quantity + " added to cart.");

		}

		if (itemsToSell.isEmpty()) {
			printWarning("No items selected for sale. Sale cancelled.");
			return;
		}

		inventoryService.recordSale(itemsToSell);

	}

	private static void restockProduct() throws ProductNotFoundException {
		System.out.println("\n--- Restock Product ---");
		String productId = Util.getNonEmptyString("Enter Product ID to restock: ");
		int quantity = Util.getIntInRange("Enter quantity to add: ", 1, Integer.MAX_VALUE);
		inventoryService.restockProduct(productId, quantity);
	}

	private static void updateProductPrice() throws ProductNotFoundException {
		System.out.println("\n--- Update Product Price ---");
		String productId = Util.getString("Enter Product ID to update price: ");
		double newPrice = Util.getDouble("Enter new selling price: ");
		inventoryService.updateProductPrice(productId, newPrice);
	}

	private static void viewSalesHistory() {
		printSuccess("\n--- Sales History ---");
		List<SaleRecord> sales = inventoryService.getSalesHistory();
		if (sales.isEmpty()) {
			printWarning("No sales recorded yet.");
			return;
		}
		sales.forEach(sale -> printSuccess(sale.toString()));
	}

	private static void deleteProduct() {
		System.out.println("\n--- Delete Product ---");
		String productId = Util.getNonEmptyString("Enter Product ID to delete: ");

		try {
			Optional<Product> opt = inventoryService.findProductById(productId);
			if (opt.isEmpty()) {
				printWarning("Product ID not found.");
				return;
			}
			Product p = opt.get();
			String confirm = Util.getNonEmptyString(
					String.format("Confirm delete %s (ID: %s)? (y/n): ", p.getName(), p.getProductId()));
			if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
				inventoryService.deleteProduct(productId);
			} else {
				System.out.println("Delete cancelled.");
			}
		} catch (ProductNotFoundException e) {
			printWarning("Error: " + e.getMessage());
		}
	}

	private static void generateAIPromoTagline() {
		if (!openAIService.isAIAvailable()) {
			printWarning("AI features disabled. Please set API_KEY in .env.");
			return;
		}

		viewAllProducts();

		String productId = Util.getString("Enter the Product ID for AI tagline: ");
		Optional<Product> productOpt = inventoryService.findProductById(productId);

		if (productOpt.isPresent()) {
			openAIService.generatePromoTagline(productOpt.get());
		} else {
			printWarning("Invalid Product ID. Please try again.");
		}
	}

	private static void generateAISalesSummary() {

		if (!openAIService.isAIAvailable()) {
			printWarning("AI features disabled. Please set API_KEY in .env.");
			return;
		}
		List<SaleRecord> sales = inventoryService.getSalesHistory();
		if (sales.isEmpty()) {
			printWarning("No sales records to summarize.");
			return;
		}

		openAIService.generateSalesSummary(sales);

	}

	private static void initializeData() {

		System.out.println("\n=========================================");
		System.out.println("🚀 Initializing Sample Inventory Data...");
		System.out.println("=========================================");

		inventoryService.addProduct(new Product(IdGenerator.generateProductId(), "Laptop X200",
				ProductCategory.ELECTRONICS, 800, 1200, 10, "TechSupplier Inc."));
		inventoryService.addProduct(new PerishableProduct(IdGenerator.generateProductId(), "Organic Apples",
				ProductCategory.GROCERIES, 1, 2.5, 50, "FarmFresh Co.", LocalDate.now().plusDays(14)));
		inventoryService.addProduct(new Product(IdGenerator.generateProductId(), "Java Programming Book",
				ProductCategory.BOOKS, 20, 45, 25, "EduPublishers"));
		inventoryService.addProduct(new ElectronicsProduct(IdGenerator.generateProductId(), "Smart Watch Z",
				ProductCategory.ELECTRONICS, 150, 250, 15, "GadgetGlobal", "SN-SWZ-001", 12));

		System.out.println("=========================================");
		System.out.println("✅ Sample Data Initialization Complete!");
		System.out.println("=========================================\n");

	}
}
