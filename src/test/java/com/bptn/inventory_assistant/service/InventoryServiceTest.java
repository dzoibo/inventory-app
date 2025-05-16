package com.bptn.inventory_assistant.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bptn.inventory_assistant.exception.InsufficientStockException;
import com.bptn.inventory_assistant.exception.ProductNotFoundException;
import com.bptn.inventory_assistant.model.Product;
import com.bptn.inventory_assistant.model.ProductCategory;
import com.bptn.inventory_assistant.model.SaleRecord;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {
    private InventoryService svc;

    @BeforeEach
    void setUp() {
        svc = new InventoryService();
    }

    @Test
    void testAddAndFindProduct() {
        Product p = new Product("ID3", "Book", ProductCategory.BOOKS, 10, 15, 5, "Sup");
        svc.addProduct(p);
        assertTrue(svc.findProductById("ID3").isPresent());
    }

    @Test
    void testAddProductInvalidNameOrSupplier() {
        Product p = new Product("ID4", "", ProductCategory.BOOKS, 10, 15, 5, "");
        svc.addProduct(p);
        assertTrue(svc.getAllProducts().isEmpty());
    }

    @Test
    void testRecordSaleSuccess() throws Exception {
        Product p = new Product("ID5", "Item", ProductCategory.CLOTHING, 5, 10, 5, "Sup");
        svc.addProduct(p);
        Map<String,Integer> items = new HashMap<>();
        items.put("ID5", 3);
        SaleRecord r = svc.recordSale(items);
        assertNotNull(r);
        assertEquals(2, p.getQuantityInStock());
    }

    @Test
    void testRecordSaleInsufficientStock() {
        Product p = new Product("ID6", "Item2", ProductCategory.BOOKS, 5, 10, 2, "Sup");
        svc.addProduct(p);
        Map<String,Integer> items = new HashMap<>();
        items.put("ID6", 5);
        assertThrows(InsufficientStockException.class, () -> svc.recordSale(items));
    }

    @Test
    void testRestockProductSuccess() throws Exception {
        Product p = new Product("ID7", "Item3", ProductCategory.SPORTS, 5, 10, 1, "Sup");
        svc.addProduct(p);
        svc.restockProduct("ID7", 4);
        assertEquals(5, p.getQuantityInStock());
    }

    @Test
    void testRestockProductInvalidQuantity() throws Exception {
        Product p = new Product("ID8", "Item4", ProductCategory.BEAUTY, 5, 10, 1, "Sup");
        svc.addProduct(p);
        svc.restockProduct("ID8", 0);
        assertEquals(1, p.getQuantityInStock());
    }

    @Test
    void testUpdateProductPriceSuccess() throws Exception {
        Product p = new Product("ID9", "Item5", ProductCategory.BOOKS, 5, 10, 1, "Sup");
        svc.addProduct(p);
        svc.updateProductPrice("ID9", 20);
        assertEquals(20, p.getSellingPrice());
    }

    @Test
    void testUpdateProductPriceNegative() throws Exception {
        Product p = new Product("ID10", "Item6", ProductCategory.BOOKS, 5, 10, 1, "Sup");
        svc.addProduct(p);
        svc.updateProductPrice("ID10", -5);
        assertEquals(10, p.getSellingPrice());
    }

    @Test
    void testDeleteProductSuccess() throws Exception {
        Product p = new Product("ID11", "Item7", ProductCategory.BOOKS, 5, 10, 1, "Sup");
        svc.addProduct(p);
        svc.deleteProduct("ID11");
        assertTrue(svc.getAllProducts().isEmpty());
    }

    @Test
    void testDeleteProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> svc.deleteProduct("UNKNOWN"));
    }
    
    
}
