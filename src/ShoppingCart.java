import java.util.Arrays;
import java.util.Comparator;

public class ShoppingCart implements ShoppingCartService {
    private Product[] products;
    private int productsCount;
    private Promotion[] promotions;
    private int promotionsCount;
    private Comparator<Product> productComparator;

    public ShoppingCart(Comparator<Product> productComparator) {
        this.products = new Product[5];
        this.productsCount = 0;
        this.promotions = new Promotion[0];
        this.promotionsCount = 0;
        this.productComparator = productComparator;
    }

    @Override
    public void addProduct(Product product) {
        checkProduct(product);

        if (productComparator == null)
            throw new IllegalStateException("Product comparator not set");

        if (productsCount == products.length)
            products = Arrays.copyOf(products, products.length * 2);

        products[productsCount] = product;
        productsCount++;

        applyPromotions();
    }

    @Override
    public void addPromotion(Promotion promotion) {
        if (promotion == null)
            throw new IllegalArgumentException("Promotion cannot be null");

        promotions = Arrays.copyOf(promotions, promotionsCount + 1);
        promotions[promotionsCount] = promotion;
        promotionsCount++;
        promotion.applyPromotion(this);
        Arrays.sort(products, 0, productsCount, productComparator);
    }

    @Override
    public void removePromotion(Promotion promotion) {
        if (promotion == null)
            throw new IllegalArgumentException("Promotion cannot be null");

        int promotionIndex = -1;
        for (int i = 0; i < promotionsCount; i++) {
            if (promotions[i].equals(promotion)) {
                promotionIndex = i;
                break;
            }
        }

        if (promotionIndex == -1)
            throw new IllegalArgumentException("Promotion not found");

        Promotion[] newPromotions = new Promotion[promotionsCount - 1];
        int index = 0;

        for (int i = 0; i < promotionsCount; i++) {
            if (promotionIndex != i) {
                newPromotions[index] = promotions[i];
                index++;
            }
        }

        promotions = newPromotions;
        promotionsCount--;
        applyPromotions();
    }

    @Override
    public void applyPromotions() {
        for (int i = 0; i < productsCount; i++)
            products[i].setDiscountPrice(products[i].getPrice());

        for (int i = 0; i < promotionsCount; i++)
            promotions[i].applyPromotion(this);

        Arrays.sort(products, 0, productsCount, productComparator);
    }

    @Override
    public Promotion[] getPromotions() {
        return Arrays.copyOf(promotions, promotionsCount);
    }

    @Override
    public void setSortingCriteria(Comparator<Product> productComparator) {
        if (productComparator == null)
            throw new IllegalArgumentException("Product comparator cannot be null");

        Arrays.sort(products, 0, productsCount, productComparator);
    }

    @Override
    public Product[] getProducts() {
        return Arrays.copyOf(products, productsCount);
    }

    @Override
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < productsCount; i++)
            total += products[i].getDiscountPrice();

        return total;
    }

    @Override
    public void sortByPrice() {
        Arrays.sort(products, 0, productsCount, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
    }

    @Override
    public void sortByName() {
        Arrays.sort(products, 0, productsCount, (p1, p2) -> p1.getName().compareTo(p2.getName()));
    }

    @Override
    public Product getCheapestProduct() {
        if (productsCount == 0)
            return null;

        Product cheapest = products[0];
        for (int i = 1; i < productsCount; i++) {
            if (products[i].getPrice() < cheapest.getPrice())
                cheapest = products[i];
        }
        return cheapest;
    }

    @Override
    public Product getMostExpensiveProduct() {
        if (productsCount == 0)
            return null;

        Product mostExpensive = products[0];
        for (int i = 1; i < productsCount; i++) {
            if (products[i].getPrice() > mostExpensive.getPrice())
                mostExpensive = products[i];
        }
        return mostExpensive;
    }

    @Override
    public Product[] getCheapestProducts(int count) {
        Product[] sortedProducts = Arrays.copyOf(products, productsCount);
        Arrays.sort(sortedProducts, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        return Arrays.copyOfRange(sortedProducts, 0, Math.min(count, sortedProducts.length));
    }

    @Override
    public Product[] getMostExpensiveProducts(int count) {
        Product[] sortedProducts = Arrays.copyOf(products, productsCount);
        Arrays.sort(sortedProducts, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
        return Arrays.copyOfRange(sortedProducts, 0, Math.min(count, sortedProducts.length));
    }

    private void checkProduct(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null");

        if (product.getPrice() < 0)
            throw new IllegalArgumentException("Product price cannot be negative");

        if (product.getCode() == null || product.getCode().trim().isEmpty())
            throw new IllegalArgumentException("Product code cannot be empty");

        if (product.getName() == null || product.getName().trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be empty");

        if (product.getDiscountPrice() < 0)
            throw new IllegalArgumentException("Product discount price cannot be negative");

        for (int i = 0; i < productsCount; i++)
            if (products[i].getCode().equals(product.getCode()))
                throw new IllegalArgumentException("Product code cannot be duplicated");
    }
}