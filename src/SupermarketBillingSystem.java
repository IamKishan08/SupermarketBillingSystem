import java.util.Map;
import java.util.Scanner;

public class SupermarketBillingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            boolean authenticated = false;
            String role = null;

            // Retry login until authenticated or exit attempt
            while (!authenticated) {
                System.out.println("-----------------------------------");
                System.out.println("Welcome to Super Market Application");
                System.out.println("-----------------------------------");
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                role = UserAuth.authenticate(username, password);

                if (role == null) {
                    System.out.println("Invalid credentials. Please try again.");
                } else {
                    authenticated = true;
                }
            }

            System.out.println("Welcome, " + role.toUpperCase() + " user!");

            boolean logout = false;

            while (!logout) {
                if (role.equals("admin")) {
                    // Admin Menu
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. Update Inventory");
                    System.out.println("2. View Inventory");
                    System.out.println("3. View Sales Details");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            // Update Inventory
                            System.out.println("\nUpdate Inventory:");
                            System.out.print("Enter Product ID: ");
                            String productId = scanner.nextLine();
                            System.out.print("Enter Product Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter Price: ");
                            double price = scanner.nextDouble();
                            System.out.print("Enter Quantity: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter Category: ");
                            String category = scanner.nextLine();
                            System.out.print("Enter Discount: ");
                            double discount = scanner.nextDouble();
                            System.out.print("Enter GST Rate: ");
                            double gstRate = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline

                            Product product = new Product(productId, name, price, quantity, category, discount, gstRate);
                            FileHandler.updateProductInInventory(product);
                            System.out.println("Product updated successfully.");
                            break;

                        case 2:
                            // View Inventory
                            System.out.println("\nInventory:");
                            Map<String, Product> inventoryData = FileHandler.loadInventory();
                            for (Product prod : inventoryData.values()) {
                                System.out.println(prod.getProductId() + " - " + prod.getName() +
                                        " | Price: " + prod.getPrice() + " | Stock: " + prod.getQuantity() +
                                        " | Discount: " + prod.getDiscount() + " | GST: " + prod.getGstRate());
                            }
                            break;

                        case 3:
                            // View Sales Details
                            System.out.println("\nSales Details:");
                            FileHandler.loadSales();
                            break;

                        case 4:
                            // Exit
                            System.out.println("Logging out...");
                            logout = true; // Break inner loop to go back to authentication
                            break;

                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                } else if (role.equals("cashier")) {
                    // Cashier Menu
                    System.out.println("\nCashier Menu:");
                    System.out.println("1. Sales");
                    System.out.println("2. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            // Sales Process
                            System.out.println("\nSales:");
                            Map<String, Product> inventoryData = FileHandler.loadInventory();
                            Inventory inventory = new Inventory();
                            for (Map.Entry<String, Product> entry : inventoryData.entrySet()) {
                                inventory.addProduct(entry.getValue());
                            }
                            Cart cart = new Cart(inventory);

                            Checkout checkout = new Checkout(new Inventory());

                            boolean salesExit = false;
                            while (!salesExit) {
                                System.out.println("\n1. Add Product to Cart");
                                System.out.println("2. View Cart");
                                System.out.println("3. Checkout");
                                System.out.println("4. Exit Sales");
                                System.out.print("Enter your choice: ");

                                int salesChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                switch (salesChoice) {
                                    case 1:
                                        System.out.println("\nAvailable Products:");
                                        Map<String, Product> products = FileHandler.loadInventory();
                                        for (Product product : products.values()) {
                                            System.out.println(product.getProductId() + " - " + product.getName() +
                                                    " | Price: " + product.getPrice() + " | Discount: " + product.getDiscount() +
                                                    " | Stock: " + product.getQuantity() + " | GST: " + product.getGstRate() + "%");
                                        }

                                        System.out.print("\nEnter Product ID: ");
                                        String prodId = scanner.nextLine();
                                        Product selectedProduct = products.get(prodId);

                                        if (selectedProduct != null) {
                                            System.out.print("Enter Quantity: ");
                                            int qty = scanner.nextInt();
                                            scanner.nextLine(); // Consume newline

                                            cart.addToCart(selectedProduct, qty);
                                        } else {
                                            System.out.println("Invalid Product ID.");
                                        }
                                        break;

                                    case 2:
                                        System.out.println("\nYour Cart:");
                                        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
                                            Product prod = entry.getKey();
                                            int qty = entry.getValue();
                                            System.out.println(prod.getName() + " - " + qty + " x " +
                                                    prod.getPrice() + " (Discount: " + prod.getDiscount() + ")");
                                        }
                                        System.out.println("Subtotal: " + cart.calculateTotal());
                                        System.out.println("GST: " + cart.calculateGST());
                                        System.out.println("Total (Including GST): " + (cart.calculateTotal() + cart.calculateGST()));
                                        break;

                                    case 3:
                                        // Checkout
                                        Bill bill = checkout.checkout(cart);
                                        if (bill != null) {
                                            System.out.println("\nBill Generated:");
                                            bill.printBill();
                                            FileHandler.saveSales(bill);
                                        }
                                        salesExit = true;
                                        break;

                                    case 4:
                                        salesExit = true;
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Try again.");
                                }
                            }
                            break;

                        case 2:
                            System.out.println("Logging out...");
                            logout = true; // Break inner loop to go back to authentication
                            break;

                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                }
            }

        }
    }
}
