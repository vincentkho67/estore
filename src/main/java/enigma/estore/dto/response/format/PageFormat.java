package enigma.estore.dto.response.format;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageFormat<T> {
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
}
