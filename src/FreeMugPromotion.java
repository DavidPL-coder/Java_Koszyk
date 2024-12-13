public class FreeMugPromotion implements Promotion {
    private static final String MUG_CODE = "MUG001";
    private static final String MUG_NAME = "Company Mug";
    private static final double MUG_PRICE = 5.0;
    private double threshold;

    public FreeMugPromotion(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public void applyPromotion(ShoppingCartService shoppingCart) {
        if (shoppingCart.getTotalPrice() < threshold)
            return;

        for (Product product : shoppingCart.getProducts()) {
            if (product.getCode().equals(MUG_CODE)) {
                product.setDiscountPrice(0);
                return;
            }
        }

        Product freeMug = new Product(MUG_CODE, MUG_NAME, MUG_PRICE);
        freeMug.setDiscountPrice(0);
        shoppingCart.addProduct(freeMug);
    }
}
