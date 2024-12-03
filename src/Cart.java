import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cartItems;
    private Inventory inventory;

    public Cart(Inventory inventory) {
        this.cartItems = new HashMap<>();
        this.inventory = inventory;
    }

    public void addToCart(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            if (cartItems.containsKey(product)) {
                cartItems.put(product, cartItems.get(product) + quantity);
            } else {
                cartItems.put(product, quantity);
            }
            // Deduct stock from inventory
            inventory.updateProduct(product.getProductId(), product.getQuantity() - quantity);
            System.out.println("Added " + product.getName() + " (x" + quantity + ") to cart.");
        } else {
            System.out.println("Insufficient stock for " + product.getName());
        }
    }

    public void removeFromCart(Product product) {
        if (cartItems.containsKey(product)) {
            int quantity = cartItems.remove(product);
            // Restock inventory when item is removed from the cart
            inventory.updateProduct(product.getProductId(), product.getQuantity() + quantity);
        }
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += (product.getPrice() - product.getDiscount()) * quantity;
        }
        return total;
    }

    public double calculateGST() {
        double gst = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            gst += (product.getPrice() - product.getDiscount()) * quantity * product.getGstRate() / 100;
        }
        return gst;
    }
}
