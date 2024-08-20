package enigma.estore.repository;

import enigma.estore.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Method queries (derived query methods)
    List<Product> findByCategory_Id(Integer id);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByNameContainingIgnoreCase(String name);

    // Manual JPQL query
    @Query("SELECT p FROM Product p WHERE p.price > :price ORDER BY p.name")
    List<Product> findExpensiveProducts(@Param("price") Double price);

    // Manual Native SQL query
    @Query(value = "SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))", nativeQuery = true)
    List<Product> findProductsByCategoryNameContaining(@Param("categoryName") String categoryName);

    // Gaperlu di demoin semua karena nanti akan pakai specification
}
