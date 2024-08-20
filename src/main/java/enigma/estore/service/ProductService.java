package enigma.estore.service;

import enigma.estore.dto.request.ProductDTO;
import enigma.estore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> index(Pageable pageable);
    List<Product> searchByCategoryId(Integer id);
    Product show(Integer id);
    Product create(ProductDTO product);
    Product update(Integer id, Product product);
    void delete(Integer id);
}
