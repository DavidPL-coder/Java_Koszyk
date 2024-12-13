import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiscountPromotionTests {
    @Test
    void applyPromotion_ShouldThrowIllegalStateException_WhenShoppingCartIsNull() {
        DiscountPromotion promotion = new DiscountPromotion(100, 0.1);

        assertThrows(IllegalStateException.class, () -> promotion.applyPromotion(null), "Shopping cart cannot be null");
    }

    @Test
    void applyPromotion_ShouldNotApplyPromotion_WhenTotalPriceIsBelowThreshold() {
        DiscountPromotion promotion = new DiscountPromotion(100, 0.1);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        when(shoppingCart.getTotalPrice()).thenReturn(50.0);
        Product[] products = {
                new Product("P001", "Product A", 30.0),
                new Product("P002", "Product B", 20.0)
        };
        when(shoppingCart.getProducts()).thenReturn(products);

        promotion.applyPromotion(shoppingCart);

        assertEquals(30.0, products[0].getDiscountPrice());
        assertEquals(20.0, products[1].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldApplyDiscount_WhenTotalPriceIsAboveThreshold() {
        DiscountPromotion promotion = new DiscountPromotion(100, 0.1);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        when(shoppingCart.getTotalPrice()).thenReturn(150.0);
        Product[] products = {
                new Product("P001", "Product A", 60.0),
                new Product("P002", "Product B", 40.0)
        };
        when(shoppingCart.getProducts()).thenReturn(products);

        promotion.applyPromotion(shoppingCart);

        assertEquals(54.0, products[0].getDiscountPrice());
        assertEquals(36.0, products[1].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldNotApplyDiscount_WhenTotalPriceIsEqualToThreshold() {
        DiscountPromotion promotion = new DiscountPromotion(100, 0.1);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        when(shoppingCart.getTotalPrice()).thenReturn(100.0);
        Product[] products = {
                new Product("P001", "Product A", 60.0),
                new Product("P002", "Product B", 40.0)
        };
        when(shoppingCart.getProducts()).thenReturn(products);

        promotion.applyPromotion(shoppingCart);

        assertEquals(60.0, products[0].getDiscountPrice());
        assertEquals(40.0, products[1].getDiscountPrice());
    }
}
