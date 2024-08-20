package enigma.estore.service;

import enigma.estore.dto.request.ProductDTO;
import enigma.estore.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> index();
    List<Product> searchByCategoryId(Integer id);
    Product show(Integer id);
    Product create(ProductDTO product);
    Product update(Integer id, Product product);
    void delete(Integer id);
}
