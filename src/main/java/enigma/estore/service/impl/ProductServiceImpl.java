package enigma.estore.service.impl;

import enigma.estore.dto.request.ProductDTO;
import enigma.estore.model.Product;
import enigma.estore.repository.ProductRepository;
import enigma.estore.service.CategoryService;
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
    private final CategoryService categoryService;

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
    public Product create(ProductDTO productDTO) {
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .category(categoryService.show(productDTO.getCategory_id()))
                .build();
        return productRepository.save(newProduct);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }
}
