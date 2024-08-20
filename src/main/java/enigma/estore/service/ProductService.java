package enigma.estore.service;

import enigma.estore.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> index();
    Product show(Integer id);
    Product create(Product product);
    Product update(Integer id, Product product);
    void delete(Integer id);
}
