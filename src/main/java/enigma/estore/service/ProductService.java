package enigma.estore.service;

import enigma.estore.dto.request.product.ProductDTO;
import enigma.estore.dto.request.product.ProductSearchDTO;
import enigma.estore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> index(ProductSearchDTO searchDTO, Pageable pageable);
    Product show(Integer id);
    Product create(ProductDTO product);
    Product update(Integer id, Product product);
    void delete(Integer id);
}
