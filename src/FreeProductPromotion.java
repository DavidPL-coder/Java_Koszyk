public class FreeProductPromotion implements Promotion {
    private int threshold;

    public FreeProductPromotion(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void applyPromotion(ShoppingCartService shoppingCart) {
        if (shoppingCart == null)
            throw new IllegalStateException("Shopping cart cannot be null");

        Product[] products = shoppingCart.getProducts();
        if (products.length == 0)
            return;

        if (products.length >= threshold) {
            Product freeProduct = shoppingCart.getCheapestProduct();
            freeProduct.setDiscountPrice(0);
        }
    }
}

