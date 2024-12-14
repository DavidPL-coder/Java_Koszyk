import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CouponPromotionTests {
    @Test
    void applyPromotion_ShouldThrowIllegalStateException_WhenShoppingCartIsNull() {
        CouponPromotion promotion = new CouponPromotion("P001", 0.1);

        assertThrows(IllegalStateException.class, () -> promotion.applyPromotion(null), "Shopping cart cannot be null");
    }

    @Test
    void applyPromotion_ShouldNotApplyPromotion_WhenProductWithGivenCodeIsNotInCart() {
        CouponPromotion promotion = new CouponPromotion("P001", 0.1);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        Product[] products = {
                new Product("P002", "Product A", 50.0),
                new Product("P003", "Product B", 30.0)
        };
        when(shoppingCart.getProducts()).thenReturn(products);

        promotion.applyPromotion(shoppingCart);

        assertEquals(50.0, products[0].getDiscountPrice());
        assertEquals(30.0, products[1].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldApplyDiscount_WhenProductWithGivenCodeIsInCart() {
        CouponPromotion promotion = new CouponPromotion("P001", 0.2);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        Product[] products = {
                new Product("P001", "Product A", 100.0),
                new Product("P002", "Product B", 50.0)
        };
        when(shoppingCart.getProducts()).thenReturn(products);

        promotion.applyPromotion(shoppingCart);

        assertEquals(80.0, products[0].getDiscountPrice());
        assertEquals(50.0, products[1].getDiscountPrice());
    }
}
