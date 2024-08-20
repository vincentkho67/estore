package enigma.estore.dto.output.format;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BasicFormat<T> {
    private T data;
    private String message;
    private HttpStatus status;
}
