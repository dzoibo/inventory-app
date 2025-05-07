package com.bptn.inventory_assistant;


public class App
{
    public static void main( String[] args )
    {
    	Inventory inventory = new Inventory();
    	
    	inventory.addProduct(new Product("Pen", 1.5, 50));
        inventory.addProduct(new Product("Notebook", 3.0, 30));
        inventory.addProduct(new Product("Eraser", 0.5, 100));
        
        inventory.showProducts();
    	
    }
}
