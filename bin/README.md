# AI Mini Project - Smart Inventory Assistant

A simple console-based inventory management application with optional AI-powered
features built using OpenAI's API. The project is intended as a learning tool
for Obsidi Academy learners and demonstrates how traditional Java applications
can integrate with modern AI services.

## Features

- Manage a list of products including electronics and perishable goods.
- Record sales and keep a history of all transactions.
- Restock items and update product prices.
- Generate unique product and sale IDs.
- Optional AI capabilities:
  - Summarize sales history and provide a short sales forecast.
  - Produce a promotional tagline for a selected product.

## Project Structure

```
src/
  main/java/com/bptn/inventory_assistant/...     Application source code
  test/java/com/bptn/inventory_assistant/...     Unit tests
```

The entry point is `com.bptn.inventory_assistant.App`, which provides a
command-line menu for interacting with the system.

## Prerequisites

- Java 17 or later
- Eclipse IDE with Maven support

To use AI features you also need an OpenAI API key.

## Setup

1. **Clone the repository**

      ```bash
   git clone <repo-url>
   ```
2. **Import the project**

   From **File → Import** select **Existing Maven Projects** and point to the cloned
   folder. Eclipse will resolve dependencies automatically.

3. **Configure your environment**

   Create a `.env` file in the project root and provide your API key:

   ```
   API_KEY=sk-xxxxxxxxxxxxxxxxxxxxx
   ```

   If the key is absent, the application runs without AI functionality.

## Running the application

Right-click `App.java` in the `com.bptn.inventory_assistant` package and choose
**Run As → Java Application**. Follow the on-screen menu to manage your
inventory.

## Running tests

Unit tests are implemented with JUnit 5. In the *Package Explorer*, right-click
the `src/test/java` folder or a specific test class and choose **Run As → JUnit
Test**.

## Notes

- This project stores data only in memory; it is not persisted across runs.
- Ensure you comply with OpenAI terms of service when using the API.

## Contributing

Pull requests are welcome! For major changes, please open an issue first to
discuss what you would like to change.

## License

This project is provided for educational purposes and does not include an
explicit license.