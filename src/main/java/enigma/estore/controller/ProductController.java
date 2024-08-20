package enigma.estore.controller;

import enigma.estore.dto.request.product.ProductDTO;
import enigma.estore.dto.request.product.ProductSearchDTO;
import enigma.estore.dto.response.RenderJson;
import enigma.estore.model.Product;
import enigma.estore.service.ProductService;
import enigma.estore.utils.strings.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.BASE_URL + ApiUrl.PRODUCTS)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> index(
            ProductSearchDTO params,
            Pageable pageable
    ) {
        return RenderJson.pageFormat(
                productService.index(params, pageable),
                "OK",
                HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        return RenderJson.basicFormat(productService.show(id), "OK", HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO product) {
        return RenderJson.basicFormat(productService.create(product), "created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Product product) {
        return RenderJson.basicFormat(productService.update(id, product), "Updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }
}
