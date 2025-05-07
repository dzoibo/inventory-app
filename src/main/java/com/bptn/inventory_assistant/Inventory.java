package com.bptn.inventory_assistant;

import java.util.*;

public class Inventory {
	
	private Map<String, Product> products = new HashMap<>();
	
	public void addProduct(Product p) {
        products.put(p.getName(), p);
    }
	
	public void showProducts() {
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }
	

}
