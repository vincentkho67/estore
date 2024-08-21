package enigma.estore.service;

import enigma.estore.dto.request.transaction.TransactionDTO;
import enigma.estore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    Page<TransactionDTO.TransactionBasicFormat> index(Pageable pageable);
    TransactionDTO.TransactionBasicFormat create(TransactionDTO.CreateTransaction transaction, User user);
}
