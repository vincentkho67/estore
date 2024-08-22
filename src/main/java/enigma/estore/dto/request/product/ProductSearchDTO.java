package enigma.estore.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSearchDTO {
    private String name;
    private Double min_price;
    private Double max_price;
    private Integer category_id;

    @Override
    public String toString() {
        return String.format("ProductSearch[name=%s,min=%s,max=%s,category=%s]",
                name, min_price, max_price, category_id);
    }
}
