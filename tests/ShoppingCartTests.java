import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartTests {

    private ShoppingCart shoppingCart;
    private Product mockProduct;
    private Promotion mockPromotion;
    private Comparator<Product> productComparator;

    @BeforeEach
    void setUp() {
        productComparator = Comparator.comparing(Product::getPrice);
        shoppingCart = new ShoppingCart(productComparator);

        mockProduct = mock(Product.class);
        mockPromotion = mock(Promotion.class);
    }

    @Test
    void testAddProduct() {
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getDiscountPrice()).thenReturn(100.0);
        when(mockProduct.getCode()).thenReturn("P001");
        when(mockProduct.getName()).thenReturn("Test Product");

        shoppingCart.addProduct(mockProduct);

        assertEquals(1, shoppingCart.getProducts().length);
        assertEquals(mockProduct, shoppingCart.getProducts()[0]);
    }

    @Test
    void testAddProductWithNullComparator() {
        shoppingCart = new ShoppingCart(null);

        assertThrows(IllegalStateException.class, () -> shoppingCart.addProduct(mockProduct),
                "Product comparator not set");
    }

    @Test
    void testAddPromotion() {
        shoppingCart.addPromotion(mockPromotion);

        verify(mockPromotion).applyPromotion(shoppingCart);
    }

    @Test
    void testRemovePromotion() {
        shoppingCart.addPromotion(mockPromotion);

        shoppingCart.removePromotion(mockPromotion);

        assertEquals(0, shoppingCart.getPromotions().length);
    }

    @Test
    void testRemovePromotionNotFound() {
        assertThrows(IllegalArgumentException.class, () -> shoppingCart.removePromotion(mockPromotion),
                "Promotion not found");
    }

    @Test
    void testApplyPromotions() {
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getDiscountPrice()).thenReturn(100.0);
        when(mockProduct.getCode()).thenReturn("P001");
        when(mockProduct.getName()).thenReturn("Test Product");

        shoppingCart.addProduct(mockProduct);

        shoppingCart.addPromotion(mockPromotion);

        doNothing().when(mockPromotion).applyPromotion(shoppingCart);

        shoppingCart.applyPromotions();

        verify(mockPromotion).applyPromotion(shoppingCart);
    }

    @Test
    void testGetTotalPrice() {
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getDiscountPrice()).thenReturn(80.0);
        when(mockProduct.getCode()).thenReturn("P001");
        when(mockProduct.getName()).thenReturn("Test Product");

        shoppingCart.addProduct(mockProduct);

        assertEquals(80.0, shoppingCart.getTotalPrice(), 0.01);
    }

    @Test
    void testSortByPrice() {
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getDiscountPrice()).thenReturn(100.0);
        when(mockProduct.getCode()).thenReturn("P001");
        when(mockProduct.getName()).thenReturn("Test Product");

        Product mockProduct2 = mock(Product.class);
        when(mockProduct2.getPrice()).thenReturn(50.0);
        when(mockProduct2.getDiscountPrice()).thenReturn(50.0);
        when(mockProduct2.getCode()).thenReturn("P002");
        when(mockProduct2.getName()).thenReturn("Test Product 2");

        shoppingCart.addProduct(mockProduct);
        shoppingCart.addProduct(mockProduct2);

        shoppingCart.sortByPrice();

        Product[] products = shoppingCart.getProducts();
        assertEquals(mockProduct2, products[0]);
        assertEquals(mockProduct, products[1]);
    }

    @Test
    void testSortByName() {
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getDiscountPrice()).thenReturn(100.0);
        when(mockProduct.getCode()).thenReturn("P001");
        when(mockProduct.getName()).thenReturn("Apple");

        Product mockProduct2 = mock(Product.class);
        when(mockProduct2.getPrice()).thenReturn(50.0);
        when(mockProduct2.getDiscountPrice()).thenReturn(50.0);
        when(mockProduct2.getCode()).thenReturn("P002");
        when(mockProduct2.getName()).thenReturn("Banana");

        shoppingCart.addProduct(mockProduct);
        shoppingCart.addProduct(mockProduct2);

        shoppingCart.sortByName();

        Product[] products = shoppingCart.getProducts();
        assertEquals(mockProduct2, products[0]);
        assertEquals(mockProduct, products[1]);
    }
}
