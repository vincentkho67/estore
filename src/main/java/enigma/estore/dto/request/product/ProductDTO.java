package enigma.estore.dto.request.product;
import enigma.estore.model.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private Double price;
    private Integer category_id;

    public static ProductDTO from(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category_id(product.getCategory().getId())
                .build();
    }
}
