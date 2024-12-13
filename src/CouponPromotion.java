public class CouponPromotion implements Promotion {
    private String productCode;
    private double discountRate;

    public CouponPromotion(String productCode, double discountRate) {
        this.productCode = productCode;
        this.discountRate = discountRate;
    }

    @Override
    public void applyPromotion(ShoppingCartService shoppingCart) {
        if (shoppingCart == null)
            throw new IllegalStateException("Shopping cart cannot be null");

        for (Product product : shoppingCart.getProducts()) {
            if (product.getCode().equals(productCode)) {
                double discountedPrice = product.getPrice() * (1 - discountRate);
                product.setDiscountPrice(discountedPrice);
            }
        }
    }
}
