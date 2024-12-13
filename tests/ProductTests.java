import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTests {
    @Test
    void compareTo_ShouldReturnNegative_WhenCurrentProductHasHigherPrice() {
        Product product1 = new Product("P001", "Product A", 200.0);
        Product product2 = new Product("P002", "Product B", 150.0);

        int result = product1.compareTo(product2);

        assertTrue(result < 0, "Expected product1 to be less than product2");
    }

    @Test
    void compareTo_ShouldReturnPositive_WhenOtherProductHasHigherPrice() {
        Product product1 = new Product("P001", "Product A", 150.0);
        Product product2 = new Product("P002", "Product B", 200.0);

        int result = product1.compareTo(product2);

        assertTrue(result > 0, "Expected product1 to be greater than product2");
    }

    @Test
    void compareTo_ShouldReturnZero_WhenPricesAreEqualAndNamesAreEqual() {
        Product product1 = new Product("P001", "Product A", 200.0);
        Product product2 = new Product("P002", "Product A", 200.0);

        int result = product1.compareTo(product2);

        assertEquals(0, result, "Expected product1 and product2 to be equal");
    }

    @Test
    void compareTo_ShouldReturnNegative_WhenPricesAreEqualButOtherProductNameIsGreater() {
        Product product1 = new Product("P001", "Product A", 200.0);
        Product product2 = new Product("P002", "Product B", 200.0);

        int result = product1.compareTo(product2);

        assertTrue(result < 0, "Expected product1 to be less than product2 based on name");
    }

    @Test
    void compareTo_ShouldReturnPositive_WhenPricesAreEqualButOtherProductNameIsLower() {
        Product product1 = new Product("P001", "Product B", 200.0);
        Product product2 = new Product("P002", "Product A", 200.0);

        int result = product1.compareTo(product2);

        assertTrue(result > 0, "Expected product1 to be greater than product2 based on name");
    }

    @Test
    void compareTo_ShouldThrowNullPointerException_WhenCurrentProductIsNull() {
        Product product1 = null;
        Product product2 = new Product("P002", "Product A", 200.0);

        assertThrows(NullPointerException.class, () -> product1.compareTo(product2), "Expected NullPointerException when current product is null");
    }

    @Test
    void compareTo_ShouldThrowNullPointerException_WhenOtherProductIsNull() {
        Product product1 = new Product("P001", "Product A", 200.0);

        assertThrows(NullPointerException.class, () -> product1.compareTo(null), "Expected NullPointerException when comparing with null");
    }

    @Test
    void compareTo_ShouldThrowNullPointerException_WhenBothProductsAreNull() {
        Product product1 = null;
        Product product2 = null;

        assertThrows(NullPointerException.class, () -> product1.compareTo(product2), "Expected NullPointerException when both products are null");
    }
}
