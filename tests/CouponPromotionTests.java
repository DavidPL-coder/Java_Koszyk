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

//    @Test
//    void applyPromotion_ShouldApplyDiscountToAllProductsWithGivenCode_WhenMultipleProductsWithSameCode() {
//        CouponPromotion promotion = new CouponPromotion("P001", 0.2);
//        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
//        Product[] products = {
//                new Product("P001", "Product A", 100.0),
//                new Product("P001", "Product B", 50.0),
//                new Product("P003", "Product C", 30.0)
//        };
//        when(shoppingCart.getProducts()).thenReturn(products);
//
//        // Stosowanie promocji
//        promotion.applyPromotion(shoppingCart);
//
//        // Sprawdzamy, czy cena wszystkich produktów o kodzie "P001" została obniżona o rabat
//        verify(products[0]).setDiscountPrice(100.0 * 0.8); // Cena po rabacie 20%
//        verify(products[1]).setDiscountPrice(50.0 * 0.8);  // Cena po rabacie 20%
//        // Sprawdzamy, że produkt o kodzie "P003" nie został zmieniony
//        verify(products[2], never()).setDiscountPrice(anyDouble());
//    }

//    @Test
//    void applyPromotion_ShouldNotApplyDiscount_WhenShoppingCartIsEmpty() {
//        CouponPromotion promotion = new CouponPromotion("P001", 0.1);
//        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
//        when(shoppingCart.getProducts()).thenReturn(new Product[0]);
//
//        promotion.applyPromotion(shoppingCart);
//
//        verify(shoppingCart, never()).getProducts();
//    }
}
