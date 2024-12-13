import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductComparatorTests {
    @Test
    void compare_ShouldReturnNegative_WhenProduct1IsLessThanProduct2() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.compareTo(product2)).thenReturn(-1);
        ProductComparator productComparator = new ProductComparator();

        int result = productComparator.compare(product1, product2);

        assertEquals(-1, result, "Expected product1 to be less than product2");
    }

    @Test
    void compare_ShouldReturnPositive_WhenProduct1IsGreaterThanProduct2() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.compareTo(product2)).thenReturn(1);
        ProductComparator productComparator = new ProductComparator();

        int result = productComparator.compare(product1, product2);

        assertEquals(1, result, "Expected product1 to be greater than product2");
    }

    @Test
    void compare_ShouldReturnZero_WhenProductsAreEqual() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.compareTo(product2)).thenReturn(0);
        ProductComparator productComparator = new ProductComparator();

        int result = productComparator.compare(product1, product2);

        assertEquals(0, result, "Expected product1 and product2 to be equal");
    }

    @Test
    void compare_ShouldReturnNegative_WhenProduct1IsNull() {
        Product product1 = null;
        Product product2 = mock(Product.class);
        ProductComparator productComparator = new ProductComparator();

        assertThrows(NullPointerException.class, () -> productComparator.compare(product1, product2), "Expected NullPointerException when product1 is null");
    }

    @Test
    void compare_ShouldReturnPositive_WhenProduct2IsNull() {
        Product product1 = mock(Product.class);
        Product product2 = null;
        when(product1.compareTo(product2)).thenThrow(NullPointerException.class);
        ProductComparator productComparator = new ProductComparator();

        assertThrows(NullPointerException.class, () -> productComparator.compare(product1, product2), "Expected NullPointerException when product2 is null");
    }

    @Test
    void compare_ShouldReturnZero_WhenBothProductsAreNull() {
        Product product1 = null;
        Product product2 = null;
        ProductComparator productComparator = new ProductComparator();

        assertThrows(NullPointerException.class, () -> productComparator.compare(product1, product2), "Expected NullPointerException when both products are null");
    }
}
