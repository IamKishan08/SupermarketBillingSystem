public class Product {
    private String productId;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private double discount;
    private double gstRate; // GST rate for the product

    public Product(String productId, String name, double price, int quantity, String category, double discount, double gstRate) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discount = discount;
        this.gstRate = gstRate;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public double getDiscount() {
        return discount;
    }

    public double getGstRate() {
        return gstRate;
    }
}
