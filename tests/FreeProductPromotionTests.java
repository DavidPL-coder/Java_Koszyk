import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FreeProductPromotionTests {
    @Test
    void applyPromotion_ShouldThrowIllegalStateException_WhenShoppingCartIsNull() {
        FreeProductPromotion promotion = new FreeProductPromotion(3);

        assertThrows(IllegalStateException.class, () -> promotion.applyPromotion(null), "Shopping cart cannot be null");
    }

//    @Test
//    void applyPromotion_ShouldNotApplyPromotion_WhenShoppingCartIsEmpty() {
//        FreeProductPromotion promotion = new FreeProductPromotion(3);
//        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
//
//        when(shoppingCart.getProducts()).thenReturn(new Product[0]);
//
//        // Testowanie przypadku, kiedy koszyk jest pusty
//        promotion.applyPromotion(shoppingCart);
//
//        // Sprawdzamy, czy metoda getCheapestProduct nie została wywołana
//        verify(shoppingCart, never()).getCheapestProduct();
//    }

    @Test
    void applyPromotion_ShouldNotApplyPromotion_WhenProductCountIsBelowThreshold() {
        FreeProductPromotion promotion = new FreeProductPromotion(3);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("P001", "Product A", 100.0) };
        when(shoppingCart.getProducts()).thenReturn(products);

        promotion.applyPromotion(shoppingCart);

        assertEquals(100.0, products[0].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldApplyPromotion_WhenProductCountIsAboveThreshold() {
        FreeProductPromotion promotion = new FreeProductPromotion(2);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        Product cheapestProduct = new Product("P003", "Product C", 50.0);
        Product[] products = {
                new Product("P001", "Product A", 200.0),
                new Product("P002", "Product B", 100.0),
                cheapestProduct
        };
        when(shoppingCart.getProducts()).thenReturn(products);
        when(shoppingCart.getCheapestProduct()).thenReturn(cheapestProduct);

        promotion.applyPromotion(shoppingCart);

        assertEquals(200.0, products[0].getDiscountPrice());
        assertEquals(100.0, products[1].getDiscountPrice());
        assertEquals(0.0, products[2].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldApplyPromotion_WhenProductCountIsEqualToThreshold() {
        FreeProductPromotion promotion = new FreeProductPromotion(3);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        Product cheapestProduct = new Product("P003", "Product C", 50.0);
        Product[] products = {
                new Product("P001", "Product A", 200.0),
                new Product("P002", "Product B", 100.0),
                cheapestProduct
        };
        when(shoppingCart.getProducts()).thenReturn(products);
        when(shoppingCart.getCheapestProduct()).thenReturn(cheapestProduct);

        promotion.applyPromotion(shoppingCart);

        assertEquals(200.0, products[0].getDiscountPrice());
        assertEquals(100.0, products[1].getDiscountPrice());
        assertEquals(0.0, products[2].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldNotThrowException_WhenThresholdIsZero() {
        FreeProductPromotion promotion = new FreeProductPromotion(0);
        ShoppingCartService shoppingCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("P001", "Product A", 100.0) };
        when(shoppingCart.getProducts()).thenReturn(products);
        when(shoppingCart.getCheapestProduct()).thenReturn(products[0]);

        promotion.applyPromotion(shoppingCart);

        assertEquals(0.0, products[0].getDiscountPrice());
    }
}
