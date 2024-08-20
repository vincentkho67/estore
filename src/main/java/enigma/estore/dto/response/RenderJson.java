package enigma.estore.dto.response;

import enigma.estore.dto.response.format.BasicFormat;
import enigma.estore.dto.response.format.PageFormat;
import org.springframework.data.domain.Page;
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

    public static <T> ResponseEntity<?> pageFormat(Page<T> page, String message, HttpStatus httpStatus) {
        PageFormat<T> pageFormat = PageFormat.<T>builder()
                .content(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .page(page.getNumber())
                .size(page.getSize())
                .build();

        BasicFormat<PageFormat<T>> response = BasicFormat.<PageFormat<T>>builder()
                .data(pageFormat)
                .status(httpStatus)
                .message(message)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
