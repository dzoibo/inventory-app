package com.bptn.inventory_assistant.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
	
	@Test
	void testStockIncreaseAndDecrease() {
		Product p = new Product("ID1", "Test", ProductCategory.BOOKS, 10.0, 15.0, 5, "Supplier");
		p.increaseStock(10);
		assertEquals(15, p.getQuantityInStock());
		p.decreaseStock(3);
		assertEquals(12, p.getQuantityInStock());
	}

	@Test
	void testDecreaseBeyondStockDoesNothing() {
		Product p = new Product("ID2", "Test2", ProductCategory.ELECTRONICS, 20.0, 25.0, 2, "Supplier");
		p.decreaseStock(5);
		assertEquals(2, p.getQuantityInStock(), "Stock should remain unchanged when decreasing beyond available");
	}
}
