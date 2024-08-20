package enigma.estore.dto.request.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private String name;
    private Double price;
    private Integer category_id;
}
