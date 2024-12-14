import java.util.Comparator;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTests {
    @Test
    void addProduct_ShouldAddProduct_WhenValidProductIsAdded() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("P001", "Product 1", 100.0);

        shoppingCart.addProduct(product);

        Product[] products = shoppingCart.getProducts();
        assertEquals(1, products.length);
        assertEquals("Product 1", products[0].getName());
        assertEquals("P001", products[0].getCode());
        assertEquals(100, products[0].getPrice());
        assertEquals(100, products[0].getDiscountPrice());
    }

    @Test
    void addProduct_ShouldThrowIllegalStateException_WhenComparatorIsNotSet() {
        ShoppingCart shoppingCart = new ShoppingCart(null);
        Product product = new Product("P001", "Product 1", 100.0);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            shoppingCart.addProduct(product);
        });

        assertEquals("Product comparator not set", exception.getMessage());
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentException_WhenProductIsNull() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addProduct(null);
        });

        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentException_WhenProductPriceIsNegative() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("P001", "Product 1", -100.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addProduct(product);
        });

        assertEquals("Product price cannot be negative", exception.getMessage());
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentException_WhenProductCodeIsEmpty() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("", "Product 1", 100.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addProduct(product);
        });

        assertEquals("Product code cannot be empty", exception.getMessage());
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentException_WhenProductCodeIsDuplicated() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        shoppingCart.addProduct(product1);

        Product product2 = new Product("P001", "Product 2", 200.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addProduct(product2);
        });

        assertEquals("Product code cannot be duplicated", exception.getMessage());
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentException_WhenProductNameIsEmpty() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("P001", "", 100.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addProduct(product);
        });

        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void addProduct_ShouldThrowIllegalArgumentException_WhenProductDiscountPriceIsNegative() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("P001", "Product 1", 100.0);
        product.setDiscountPrice(-50.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addProduct(product);
        });

        assertEquals("Product discount price cannot be negative", exception.getMessage());
    }

    @Test
    void addPromotion_ShouldThrowIllegalArgumentException_WhenPromotionIsNull() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.addPromotion(null);
        });

        assertEquals("Promotion cannot be null", exception.getMessage());
    }

    @Test
    void addPromotion_ShouldApplyPromotion_WhenPromotionIsAdded() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("P001", "Product 1", 100.0);
        shoppingCart.addProduct(product);

        Promotion promotion = mock(Promotion.class);
        shoppingCart.addPromotion(promotion);

        assertEquals(1, shoppingCart.getPromotions().length);
        verify(promotion, times(1)).applyPromotion(shoppingCart);
    }

    @Test
    void removePromotion_ShouldRemovePromotion_WhenPromotionExists() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);
        Product product = new Product("P001", "Product 1", 100.0);
        shoppingCart.addProduct(product);
        Promotion promotion = mock(Promotion.class);

        doAnswer(invocation -> {
            product.setDiscountPrice(product.getPrice() * 0.9);
            return null;
        }).when(promotion).applyPromotion(shoppingCart);
        shoppingCart.addPromotion(promotion);
        assertEquals(90.0, product.getDiscountPrice());

        shoppingCart.removePromotion(promotion);

        assertEquals(100.0, product.getDiscountPrice());
    }

    @Test
    void removePromotion_ShouldThrowIllegalArgumentException_WhenPromotionIsNull() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.removePromotion(null);
        });

        assertEquals("Promotion cannot be null", exception.getMessage());
    }

    @Test
    void removePromotion_ShouldThrowIllegalArgumentException_WhenPromotionNotFound() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Promotion promotion = new DiscountPromotion(50, 0.1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.removePromotion(promotion);
        });

        assertEquals("Promotion not found", exception.getMessage());
    }

    @Test
    void applyPromotions_ShouldApplyPromotions_WhenPromotionsExist() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product = new Product("P001", "Product 1", 100.0);
        shoppingCart.addProduct(product);

        Promotion promotion = new DiscountPromotion(50, 0.1);
        shoppingCart.addPromotion(promotion);

        shoppingCart.applyPromotions();

        assertEquals(90.0, shoppingCart.getProducts()[0].getDiscountPrice());
    }

    @Test
    void setSortingCriteria_ShouldThrowIllegalArgumentException_WhenComparatorIsNull() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingCart.setSortingCriteria(null);
        });

        assertEquals("Product comparator cannot be null", exception.getMessage());
    }

    @Test
    void setSortingCriteria_ShouldSortProducts_WhenComparatorIsSet() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 200.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        shoppingCart.setSortingCriteria((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));

        assertEquals("Product 2", shoppingCart.getProducts()[0].getName());
    }

    @Test
    void getTotalPrice_ShouldReturnCorrectTotal_WhenMultipleProductsExist() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 200.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        assertEquals(300.0, shoppingCart.getTotalPrice());
    }

    @Test
    void sortByPrice_ShouldSortProductsByPriceDescending() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 200.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        shoppingCart.sortByPrice();

        assertEquals("P002", shoppingCart.getProducts()[0].getCode());
        assertEquals("P001", shoppingCart.getProducts()[1].getCode());
    }

    @Test
    void sortByName_ShouldSortProductsByName() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Banana", 100.0);
        Product product2 = new Product("P002", "Apple", 200.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        shoppingCart.sortByName();

        assertEquals("Apple", shoppingCart.getProducts()[0].getName());
        assertEquals("Banana", shoppingCart.getProducts()[1].getName());
    }

    @Test
    void getCheapestProduct_ShouldReturnCheapestProduct() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 50.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        Product cheapestProduct = shoppingCart.getCheapestProduct();
        assertEquals("Product 2", cheapestProduct.getName());
    }

    @Test
    void getMostExpensiveProduct_ShouldReturnMostExpensiveProduct() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 200.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);

        Product expensiveProduct = shoppingCart.getMostExpensiveProduct();
        assertEquals("Product 2", expensiveProduct.getName());
    }

    @Test
    void getCheapestProducts_ShouldReturnCorrectNumberOfCheapestProducts() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 50.0);
        Product product3 = new Product("P003", "Product 3", 150.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);
        shoppingCart.addProduct(product3);

        Product[] cheapestProducts = shoppingCart.getCheapestProducts(2);
        assertEquals(2, cheapestProducts.length);
        assertEquals("Product 2", cheapestProducts[0].getName());
        assertEquals("Product 1", cheapestProducts[1].getName());
    }

    @Test
    void getMostExpensiveProducts_ShouldReturnCorrectNumberOfMostExpensiveProducts() {
        Comparator<Product> productComparator = mock(Comparator.class);
        ShoppingCart shoppingCart = new ShoppingCart(productComparator);

        Product product1 = new Product("P001", "Product 1", 100.0);
        Product product2 = new Product("P002", "Product 2", 200.0);
        Product product3 = new Product("P003", "Product 3", 150.0);
        shoppingCart.addProduct(product1);
        shoppingCart.addProduct(product2);
        shoppingCart.addProduct(product3);

        Product[] mostExpensiveProducts = shoppingCart.getMostExpensiveProducts(2);
        assertEquals(2, mostExpensiveProducts.length);
        assertEquals("Product 2", mostExpensiveProducts[0].getName());
        assertEquals("Product 3", mostExpensiveProducts[1].getName());
    }
}