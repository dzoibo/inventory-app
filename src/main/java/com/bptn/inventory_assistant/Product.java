package com.bptn.inventory_assistant;

public class Product {
	
	private String name;
	private double price;
	private int stock;

	public Product(String name, double price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	public void addStock(int quantity) {
		this.stock += quantity;
	}

	public void reduceStock(int quantity) {
		this.stock -= quantity;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public String toString() {
		return name + " - $" + price + " - Stock: " + stock;
	}
}
