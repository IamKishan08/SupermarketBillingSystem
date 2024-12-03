import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String INVENTORY_FILE = "inventory.txt";
    private static final String SALES_FILE = "sales.txt";

    // Load inventory from file
    public static Map<String, Product> loadInventory() {
        Map<String, Product> inventory = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String productId = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    String category = parts[4];
                    double discount = Double.parseDouble(parts[5]);
                    double gstRate = Double.parseDouble(parts[6]);
                    inventory.put(productId, new Product(productId, name, price, quantity, category, discount, gstRate));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
        return inventory;
    }

    // Save inventory to file
    public static void saveInventory(Map<String, Product> inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Product product : inventory.values()) {
                writer.write(product.getProductId() + "," + product.getName() + "," + product.getPrice() + "," +
                        product.getQuantity() + "," + product.getCategory() + "," + product.getDiscount() + "," +
                        product.getGstRate());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Update a specific product in the inventory file
    public static void updateProductInInventory(Product updatedProduct) {
        List<String> lines = new ArrayList<>();
        boolean productExists = false;

        // Read the existing inventory file
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(updatedProduct.getProductId())) {
                    // Update the product details if the ID matches
                    lines.add(updatedProduct.getProductId() + "," + updatedProduct.getName() + "," +
                            updatedProduct.getPrice() + "," + updatedProduct.getQuantity() + "," +
                            updatedProduct.getCategory() + "," + updatedProduct.getDiscount() + "," +
                            updatedProduct.getGstRate());
                    productExists = true;
                } else {
                    lines.add(line); // Keep the existing line
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }

        // If the product does not exist, add it as a new line
        if (!productExists) {
            lines.add(updatedProduct.getProductId() + "," + updatedProduct.getName() + "," +
                    updatedProduct.getPrice() + "," + updatedProduct.getQuantity() + "," +
                    updatedProduct.getCategory() + "," + updatedProduct.getDiscount() + "," +
                    updatedProduct.getGstRate());
        }

        // Write the updated lines back to the inventory file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing inventory file: " + e.getMessage());
        }
    }

    // Save sales data to file
    public static void saveSales(Bill bill) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALES_FILE, true))) {
            writer.write("Bill ID: " + bill.getBillId());
            writer.newLine();
            writer.write("Date: " + bill.getGeneratedAt());
            writer.newLine();
            writer.write("Items:");
            for (Map.Entry<Product, Integer> entry : bill.getCart().getCartItems().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                writer.write(product.getName() + " - " + quantity + " x " + product.getPrice());
                writer.newLine();
            }
            writer.write("Total: " + bill.getTotalAmount() + ", GST: " + bill.getGstAmount() +
                    ", Payment Method: " + bill.getPaymentMethod() + ", Balance Returned: " + bill.getBalance());
            writer.newLine();
            writer.write("---------------------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving sales: " + e.getMessage());
        }
    }

    // Load sales data from file
    public static void loadSales() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SALES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading sales data: " + e.getMessage());
        }
    }
}
