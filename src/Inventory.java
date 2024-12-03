import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;

    public Inventory() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    public void updateProduct(String productId, int newQuantity) {
        if (products.containsKey(productId)) {
            products.get(productId).setQuantity(newQuantity);
        }
    }


    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public Map<String, Product> getAllProducts() {
        return products;
    }
}
