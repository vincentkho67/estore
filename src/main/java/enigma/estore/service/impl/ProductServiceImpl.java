package enigma.estore.service.impl;

import enigma.estore.dto.request.product.ProductDTO;
import enigma.estore.dto.request.product.ProductSearchDTO;
import enigma.estore.model.Product;
import enigma.estore.repository.ProductRepository;
import enigma.estore.service.CategoryService;
import enigma.estore.service.ProductService;
import enigma.estore.utils.specification.ProductSpecification;
import enigma.estore.utils.strings.ErrorResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Cacheable(value = "productList", key = "#criteria.toString() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ProductDTO> index(
            ProductSearchDTO criteria,
            Pageable pageable
    ) {
        Specification<Product> spec = Specification.where(null);

        spec = spec.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withName(criteria.getName()),
                c -> criteria.getName() != null && !criteria.getName().isEmpty()
        ));

        spec = spec.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withMinPrice(criteria.getMin_price()),
                c -> criteria.getMin_price() != null
        ));

        spec = spec.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withMaxPrice(criteria.getMax_price()),
                c -> criteria.getMax_price() != null
        ));

        spec = spec.and(ProductSpecification.specificationFromPredicate(
                ProductSpecification.withCategoryId(criteria.getCategory_id()),
                c -> criteria.getCategory_id() != null
        ));
        return productRepository.findAll(spec, pageable).map(ProductDTO::from);
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public Product show(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    @CacheEvict(value = {"product", "productList"}, allEntries = true)
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
        productRepository.deleteById(id); // this now calls softDelete
    }
}
