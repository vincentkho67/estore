package enigma.estore.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import enigma.estore.dto.request.product.ProductDTO;
import enigma.estore.dto.request.product.ProductSearchDTO;
import enigma.estore.model.Product;
import enigma.estore.repository.ProductRepository;
import enigma.estore.service.CategoryService;
import enigma.estore.service.ProductService;
import enigma.estore.utils.RedisUtil;
import enigma.estore.utils.specification.ProductSpecification;
import enigma.estore.utils.strings.ErrorResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final RedisUtil redisUtil;

    @Override
    public Page<ProductDTO> index(ProductSearchDTO criteria, Pageable pageable) {
        String cacheKey = "productList:" + criteria.toString() + '_' + pageable.getPageNumber() + '_' + pageable.getPageSize();
        Page<ProductDTO> cachedResult = redisUtil.getCachedData(cacheKey, Page.class);
        if (cachedResult != null) {
            return cachedResult;
        }
        
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

        Page<ProductDTO> result = productRepository.findAll(spec, pageable).map(ProductDTO::from);

        redisUtil.cacheData(cacheKey, result);

        return result;
    }

    @Override
    public Product show(Integer id) {
        String cacheKey = "product:" + id;

        Product cachedProduct = redisUtil.getCachedData(cacheKey, Product.class);
        if (cachedProduct != null) {
            return cachedProduct;
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.PRODUCT_NOT_FOUND));

        redisUtil.cacheData(cacheKey, product);

        return product;
    }

    @Override
    public Product create(ProductDTO productDTO) {
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .category(categoryService.show(productDTO.getCategory_id()))
                .build();
        Product saved = productRepository.save(newProduct);
        redisUtil.invalidateCaches("productList:*");
        return saved;
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
        Product updated = productRepository.save(existingProduct);
        redisUtil.invalidateCache("product:" + id);
        redisUtil.invalidateCaches("productList:*");
        return updated;
    }

    @Override
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorResponseMessage.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id); // this now calls softDelete
        redisUtil.invalidateCache("product:" + id);
        redisUtil.invalidateCaches("productList:*");
    }
}
