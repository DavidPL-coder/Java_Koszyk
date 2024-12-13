import java.util.Comparator;

public interface ShoppingCartService {
    void addProduct(Product product);
    void addPromotion(Promotion promotion);
    void removePromotion(Promotion promotion);
    void applyPromotions();
    void setSortingCriteria(Comparator<Product> productComparator);
    Product[] getProducts();
    double getTotalPrice();
    void sortByPrice();
    void sortByName();
    Product getCheapestProduct();
    Product getMostExpensiveProduct();
    Product[] getCheapestProducts(int count);
    Product[] getMostExpensiveProducts(int count);
}