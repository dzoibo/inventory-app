package com.bptn.inventory_assistant.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SaleRecordTest {
	@Test
	void testAddItemsAndTotal() {
		SaleRecord sale = new SaleRecord("S1", LocalDate.now());
		Product p1 = new Product("P1", "Prod1", ProductCategory.BOOKS, 5, 10, 10, "Supplier");
		sale.addItem(p1, 2, p1.getSellingPrice());
		List<SoldItem> items = sale.getItemsSold();
		assertEquals(1, items.size());
		assertEquals(20.0, sale.getTotalAmount());
	}

	@Test
	void testEmptySale() {
		SaleRecord sale = new SaleRecord("S2", LocalDate.now());
		assertTrue(sale.getItemsSold().isEmpty());
		String repr = sale.toString();
		assertTrue(repr.contains("Sale ID: S2"));
	}
}
