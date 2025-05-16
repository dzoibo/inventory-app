package com.bptn.inventory_assistant.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdGeneratorTest {
	@Test
	void testGenerateProductIdUniqueness() {
		String id1 = IdGenerator.generateProductId();
		String id2 = IdGenerator.generateProductId();
		assertNotNull(id1);
		assertNotNull(id2);
		assertNotEquals(id1, id2);
		assertTrue(id1.startsWith("PROD-"));
		assertTrue(id2.startsWith("PROD-"));
	}

	@Test
	void testGenerateSaleIdFormat() {
		String saleId = IdGenerator.generateSaleId();
		assertNotNull(saleId);
		assertTrue(saleId.startsWith("SALE-"));
	}
}
