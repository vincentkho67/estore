package enigma.estore.service.impl;

import enigma.estore.model.Product;
import enigma.estore.repository.ProductRepository;
import enigma.estore.service.ProductService;
import enigma.estore.utils.strings.ErrorResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> index() {
        return productRepository.findAll();
    }

    @Override
    public Product show(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product create(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product price");
        }
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Integer id, Product product) {
        Product existingProduct = show(id);
        if (product.getName() != null && !product.getName().trim().isEmpty()) {
            existingProduct.setName(product.getName().trim());
        }
        if (product.getPrice() != null && product.getPrice() >= 0) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            existingProduct.setCategory(product.getCategory());
        }
        return productRepository.save(existingProduct);
    }

    @Override
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }
}
