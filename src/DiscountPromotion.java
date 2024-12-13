public class DiscountPromotion implements Promotion {
    private double threshold;
    private double discountRate;

    public DiscountPromotion(double threshold, double discountRate) {
        this.threshold = threshold;
        this.discountRate = discountRate;
    }

    @Override
    public void applyPromotion(ShoppingCartService shoppingCart) {
        if (shoppingCart == null)
            throw new IllegalStateException("Shopping cart cannot be null");

        if (shoppingCart.getTotalPrice() > threshold) {
            for (Product product : shoppingCart.getProducts()) {
                double discountedPrice = product.getPrice() * (1 - discountRate);
                product.setDiscountPrice(discountedPrice);
            }
        }
    }
}
