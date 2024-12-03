import java.util.Map;
import java.util.Scanner;

public class Checkout {
    private Inventory inventory;

    public Checkout(Inventory inventory) {
        this.inventory = inventory;
    }

    public Bill checkout(Cart cart) {
        double totalAmount = cart.calculateTotal() + cart.calculateGST();
        Payment payment = null;
        boolean paymentSuccessful = false;
        String paymentMethod = null;
        double balance = 0;

        System.out.println("\nTotal (Including GST): " + totalAmount);

        while (!paymentSuccessful) {
            try {
                System.out.print("\nChoose Payment Method (CASH, CARD, UPI, WALLET): ");
                paymentMethod = new Scanner(System.in).nextLine().toUpperCase();
                Payment.PaymentType paymentType = Payment.PaymentType.valueOf(paymentMethod);

                System.out.print("Enter payment amount: ");
                double paymentAmount = new Scanner(System.in).nextDouble();

                payment = new Payment(paymentType, paymentAmount);

                if (payment.processPayment(totalAmount)) {
                    paymentSuccessful = true;
                    balance = payment.calculateBalance(totalAmount);
                } else {
                    System.out.println("Insufficient payment. Please retry.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid payment method. Please try again.");
            }
        }

        Bill bill = new Bill(java.util.UUID.randomUUID().toString(), cart, balance, paymentMethod);

        // Update inventory quantities and save to file
        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
            Product product = entry.getKey();
            int purchasedQuantity = entry.getValue();
            product.setQuantity(product.getQuantity() - purchasedQuantity);
            FileHandler.updateProductInInventory(product); // Update inventory file
        }

        return bill;
    }
}
