import java.time.LocalDateTime;
import java.util.Map;

public class Bill {
    private String billId;
    private Cart cart;
    private double totalAmount;
    private double gstAmount;
    private double balance;

    public String getBillId() {
        return billId;
    }

    public Cart getCart() {
        return cart;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getGstAmount() {
        return gstAmount;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    private String paymentMethod;
    private LocalDateTime generatedAt;

    public Bill(String billId, Cart cart, double balance, String paymentMethod) {
        this.billId = billId;
        this.cart = cart;
        this.totalAmount = cart.calculateTotal();
        this.gstAmount = cart.calculateGST();
        this.balance = balance;
        this.paymentMethod = paymentMethod;
        this.generatedAt = LocalDateTime.now();
    }



    public void printBill() {
        System.out.println("Bill ID: " + billId);
        System.out.println("Generated At: " + generatedAt);
        System.out.println("\nItems:");
        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.getName() + " - " + quantity + " x " +
                    product.getPrice() + " (Discount: " + product.getDiscount() + ")");
        }
        System.out.println("\nSubtotal: " + totalAmount);
        System.out.println("GST: " + gstAmount);
        System.out.println("Total Amount (Including GST): " + (totalAmount + gstAmount));
        System.out.println("Payment Method: " + paymentMethod);
        if (balance > 0) {
            System.out.println("Balance Returned: " + balance);
        }
    }
}
