package enigma.estore.dto.output;

import enigma.estore.dto.output.format.BasicFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RenderJson {
    public static <T> ResponseEntity<?> basicFormat(T data, String message, HttpStatus httpStatus) {
        BasicFormat<T> response = BasicFormat.<T>builder()
                .data(data)
                .status(httpStatus)
                .message(message)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
