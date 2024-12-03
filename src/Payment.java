public class Payment {
    public enum PaymentType {
        CASH, CARD, UPI, WALLET
    }

    private PaymentType paymentType;
    private double amountPaid;

    public Payment(PaymentType paymentType, double amountPaid) {
        this.paymentType = paymentType;
        this.amountPaid = amountPaid;
    }

    public boolean processPayment(double totalAmount) {
        return amountPaid >= totalAmount;
    }

    public double calculateBalance(double totalAmount) {
        return amountPaid - totalAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public double getAmountPaid() {
        return amountPaid;
    }
}
