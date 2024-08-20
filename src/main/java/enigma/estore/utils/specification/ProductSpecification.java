package enigma.estore.utils.specification;

import enigma.estore.dto.request.product.ProductSearchDTO;
import enigma.estore.model.Product;
import org.springframework.data.jpa.domain.Specification;
import java.util.function.Predicate;

public class ProductSpecification {

    public static Specification<Product> withName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> withMinPrice(Double minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> withMaxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> withCategoryId(Integer categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> withCriteria(ProductSearchDTO criteria) {
        return Specification.where(withName(criteria.getName()))
                .and(withMinPrice(criteria.getMin_price()))
                .and(withMaxPrice(criteria.getMax_price()))
                .and(withCategoryId(criteria.getCategory_id()));
    }

    // Predicate to determine if a specification should be applied
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<T> predicate) {
        return (root, query, cb) -> {
            return predicate.test(null) ? spec.toPredicate(root, query, cb) : null;
        };
    }
}
