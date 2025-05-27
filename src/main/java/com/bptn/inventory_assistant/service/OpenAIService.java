package com.bptn.inventory_assistant.service;

import java.util.List;

import com.bptn.inventory_assistant.model.Product;
import com.bptn.inventory_assistant.model.SaleRecord;

import static com.bptn.inventory_assistant.util.Util.printWarning;
import static com.bptn.inventory_assistant.util.Util.printSuccess;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.errors.PermissionDeniedException;
import com.openai.errors.UnauthorizedException;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;

import io.github.cdimascio.dotenv.Dotenv;

public class OpenAIService {

	private OpenAIClient client;
	private final ChatModel chatModel = ChatModel.GPT_4_1;

	public OpenAIService() {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		String apiKey = dotenv.get("API_KEY");

		if (apiKey == null || apiKey.isEmpty()) {
			printWarning("OPENAI_API_KEY not found in .env file", "AI features will be disabled.",
					"Please set the API_KEY to enable AI functionalities.");
			this.client = null;

		} else {
			this.client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();

		}
	}

	public boolean isAIAvailable() {
		return this.client != null;
	}

	public void generateSalesSummary(List<SaleRecord> salesHistory) {

		if (!isAIAvailable()) {
			printWarning("AI is not available. Please check your API key.");
			return;
		}

		try {

			StringBuilder prompt = new StringBuilder();

			prompt.append(
					"You are an inventory assistant. Summarize the following sales history and provide a a 3-point sales forecast.\n\n");

			for (SaleRecord record : salesHistory) {
				prompt.append("Sale ID: ").append(record.getSaleId()).append(", Date: ").append(record.getSaleDate())
						.append(", Total: $").append(String.format("%.2f", record.getTotalAmount())).append("\n");
				record.getItemsSold().forEach(item -> {
					prompt.append("  - ").append(item.getProductName()).append(", Qty: ").append(item.getQuantitySold())
							.append(", Price: $").append(String.format("%.2f", item.getPriceAtSale())).append("\n");
				});
				prompt.append("\n");
			}

			ResponseCreateParams params = ResponseCreateParams.builder().input(prompt.toString()).model(this.chatModel)
					.build();

			printSuccess("\nLoading AI Response....................................................................\n");

			client.responses().create(params).output().stream().flatMap(item -> item.message().stream())
					.flatMap(message -> message.content().stream()).flatMap(content -> content.outputText().stream())
					.forEach(outputText -> {
						
						// Remove all occurrences of * and #
				        String cleanText = outputText.text().replace("*", "").replace("#", "");
				        System.out.println(cleanText);
					});

		} catch (PermissionDeniedException | UnauthorizedException e) {
			printWarning("Invalid or unauthorized API_KEY during request.", "AI features will be disabled.",
					"Please check your API key and restart the application.");
			this.client = null;
		} catch (RuntimeException e) {
			printWarning("Unexpected error while calling OpenAI API:", e.getMessage(), "AI features will be disabled.");
			this.client = null;
		}
	}

	public void generatePromoTagline(Product product) {

		if (!isAIAvailable()) {
			printWarning("AI is not available. Please check your API key.");
			return;
		}

		try {
			String prompt = String.format(
					"You are a marketing copywriter. Write a short, catchy promotional tagline for the following product:\n\nName: %s\nCategory: %s\nDescription: Supplier: %s, Current Stock: %d, Selling Price: $%.2f",
					product.getName(), product.getCategory(), product.getSupplier(), product.getQuantityInStock(),
					product.getSellingPrice());

			ResponseCreateParams params = ResponseCreateParams.builder().input(prompt).model(this.chatModel).build();

			printSuccess("\nLoading AI Response....................................................................\n");

			client.responses().create(params).output().stream().flatMap(item -> item.message().stream())
					.flatMap(message -> message.content().stream()).flatMap(content -> content.outputText().stream())
					.forEach(outputText -> {
						
						// Remove all occurrences of * and #
				        String cleanText = outputText.text().replace("*", "").replace("#", "");
				        System.out.println(cleanText);
					});

		} catch (PermissionDeniedException | UnauthorizedException e) {
			printWarning("Error generating AI Promotional Tagline:", e.getMessage(), "AI features will be disabled.");
			this.client = null;

		} catch (RuntimeException e) {
			printWarning("Unexpected error while calling OpenAI API:", e.getMessage(), "AI features will be disabled.");
			this.client = null;
		}
	}

}
