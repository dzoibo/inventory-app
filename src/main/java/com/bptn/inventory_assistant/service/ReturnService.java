package com.bptn.inventory_assistant.service;
 
import com.bptn.inventory_assistant.model.ReturnRecord;
import java.time.LocalDate;
import java.util.Optional;

import com.bptn.inventory_assistant.exception.ProductNotFoundException;
import com.bptn.inventory_assistant.exception.ReturnNotAllowedException;
import com.bptn.inventory_assistant.exception.SaleNotFoundException;
import com.bptn.inventory_assistant.model.Product;
import com.bptn.inventory_assistant.model.ReturnOutcome;
import com.bptn.inventory_assistant.model.ReturnReason;
import com.bptn.inventory_assistant.model.SaleRecord;
import com.bptn.inventory_assistant.model.SoldItem;
import com.bptn.inventory_assistant.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;


public class ReturnService {
	 	private List<ReturnRecord> returns; // List to store return records
	 	private InventoryService inventory; // Reference to InventoryService for product details
	 	
		public ReturnService(InventoryService inventory) {
			this.inventory = inventory;
			this.returns = new ArrayList<>();
		}
	

		public List<ReturnRecord> getReturnsHistory() {
			return new ArrayList<>(returns); // Return copy
		}

		private static final int RETURN_WINDOW_DAYS = 30;

		public ReturnRecord processReturn(String saleId, String productId, int quantity,
				String reasonText, ReturnReason reason)
				throws SaleNotFoundException, ReturnNotAllowedException, ProductNotFoundException {

					// check 1: the sale must exist
					SaleRecord sale = inventory.findSaleById(saleId)
							.orElseThrow(() -> new SaleNotFoundException("Sale " + saleId + " not found."));
		
					// check 2: the product must be in that sale
					SoldItem item = null;
					for (SoldItem i : sale.getItemsSold()) {
						if (i.getProductId().equalsIgnoreCase(productId)) {
							item = i;
						}
					}
					
					if (item == null) {
						throw new ReturnNotAllowedException("Product " + productId + " is not in sale " + saleId + ".");// the item returned must be part of the original sale
					}
		
					// check 3: can't return more than what is left
					int remaining = item.getQuantitySold() - getReturnedQuantity(saleId, productId);
					if (quantity <= 0 || quantity > remaining) {
						throw new ReturnNotAllowedException("You can return between 1 and " + remaining + " of this item."); 
					}
		
					// check 4: within 30 days of the sale
					if (LocalDate.now().isAfter(sale.getSaleDate().plusDays(RETURN_WINDOW_DAYS))) {
						throw new ReturnNotAllowedException("Return window of " + RETURN_WINDOW_DAYS + " days has passed.");
					}
		
					// check 5: refund at sale price, restock unless defective or damaged
					double refund = item.getPriceAtSale() * quantity;
					Optional<Product> product = inventory.findProductById(productId);
					boolean restock = product.isPresent()
							&& reason != ReturnReason.DEFECTIVE
							&& reason != ReturnReason.DAMAGED;
					if (restock) {
						inventory.restockProduct(productId, quantity);
					}
		
					String supplier = product.isPresent() ? product.get().getSupplier() : "unknown";
					ReturnRecord record = new ReturnRecord(IdGenerator.generateReturnId(), sale.getSaleId(),
							item.getProductId(), item.getProductName(), supplier, quantity, refund,
							LocalDate.now().toString(), restock, reasonText, reason, ReturnOutcome.REFUNDED);
		
					returns.add(record);
					return record;
				}
		/**
		 * How many units of this product were already returned for this sale.
		 */
		public int getReturnedQuantity(String saleId, String productId) {
			int total = 0;
			for (ReturnRecord eachReturn : returns) {
				if (eachReturn.getSaleId().equalsIgnoreCase(saleId) && eachReturn.getProductId().equalsIgnoreCase(productId)) {
					total += eachReturn.getQuantity();
				}
			}
			return total;
		}
}
