import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FreeMugPromotionTests {
    @Test
    void applyPromotion_ShouldAddFreeMug_WhenTotalPriceIsAboveThreshold() {
        ShoppingCartService mockCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("P001", "Test Product", 250.0) };
        when(mockCart.getTotalPrice()).thenReturn(250.0);
        when(mockCart.getProducts()).thenReturn(products);
        FreeMugPromotion promotion = new FreeMugPromotion(200.0);

        promotion.applyPromotion(mockCart);

        verify(mockCart).addProduct(argThat(product ->
                "MUG001".equals(product.getCode()) && product.getDiscountPrice() == 0.0
        ));
    }

    @Test
    void applyPromotion_ShouldAddFreeMug_WhenTotalPriceIsEqualToThreshold() {
        ShoppingCartService mockCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("P001", "Test Product", 200.0) };
        when(mockCart.getTotalPrice()).thenReturn(200.0);
        when(mockCart.getProducts()).thenReturn(products);
        FreeMugPromotion promotion = new FreeMugPromotion(200.0);

        promotion.applyPromotion(mockCart);

        verify(mockCart).addProduct(argThat(product ->
                "MUG001".equals(product.getCode()) && product.getDiscountPrice() == 0.0
        ));
    }

    @Test
    void applyPromotion_ShouldNotApplyPromotion_WhenTotalPriceIsBelowThreshold() {
        ShoppingCartService mockCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("P001", "Test Product", 50.0) };
        when(mockCart.getTotalPrice()).thenReturn(50.0);
        when(mockCart.getProducts()).thenReturn(products);
        FreeMugPromotion promotion = new FreeMugPromotion(200.0);

        promotion.applyPromotion(mockCart);

        verify(mockCart, never()).addProduct(any(Product.class));
        assertEquals(50.0, products[0].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldSetMugAsFree_WhenTotalPriceIsAboveThreshold() {
        ShoppingCartService mockCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("MUG001", "Company Mug", 5.0) };
        when(mockCart.getTotalPrice()).thenReturn(5.0);
        when(mockCart.getProducts()).thenReturn(products);
        FreeMugPromotion promotion = new FreeMugPromotion(1.0);

        promotion.applyPromotion(mockCart);

        verify(mockCart, never()).addProduct(any(Product.class));
        assertEquals(0.0, products[0].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldSetMugAsFree_WhenTotalPriceIsEqualToThreshold() {
        ShoppingCartService mockCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("MUG001", "Company Mug", 5.0) };
        when(mockCart.getTotalPrice()).thenReturn(5.0);
        when(mockCart.getProducts()).thenReturn(products);
        FreeMugPromotion promotion = new FreeMugPromotion(5.0);

        promotion.applyPromotion(mockCart);

        verify(mockCart, never()).addProduct(any(Product.class));
        assertEquals(0.0, products[0].getDiscountPrice());
    }

    @Test
    void applyPromotion_ShouldNotApplyPromotion_WhenTotalPriceWithExistingMugIsBelowThreshold() {
        ShoppingCartService mockCart = mock(ShoppingCartService.class);
        Product[] products = { new Product("MUG001", "Company Mug", 5.0) };
        when(mockCart.getTotalPrice()).thenReturn(5.0);
        when(mockCart.getProducts()).thenReturn(products);
        FreeMugPromotion promotion = new FreeMugPromotion(200.0);

        promotion.applyPromotion(mockCart);

        verify(mockCart, never()).addProduct(any(Product.class));
        assertEquals(5.0, products[0].getDiscountPrice());
    }
}
