package enigma.estore.controller;

import enigma.estore.dto.request.transaction.TransactionDTO;
import enigma.estore.dto.response.RenderJson;
import enigma.estore.model.User;
import enigma.estore.service.TransactionService;
import enigma.estore.utils.strings.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.BASE_URL + ApiUrl.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody TransactionDTO.CreateTransaction req,
            @AuthenticationPrincipal User user
            ) {
        return RenderJson.basicFormat(
                transactionService.create(req, user),
                "Success",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> index(Pageable pageable) {
        return RenderJson.pageFormat(
                transactionService.index(pageable),
                "OK",
                HttpStatus.OK
        );
    }
}
