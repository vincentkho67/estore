package enigma.estore.service.impl;
import enigma.estore.dto.request.transaction.TransactionDTO.TransactionBasicFormat;
import enigma.estore.dto.request.transaction.TransactionDTO.CreateTransaction;
import enigma.estore.dto.request.transaction.TransactionDTO.CreateTransactionDetail;
import enigma.estore.model.Product;
import enigma.estore.model.Transaction;
import enigma.estore.model.TransactionDetail;
import enigma.estore.model.User;
import enigma.estore.repository.TransactionRepository;
import enigma.estore.service.EmailService;
import enigma.estore.service.ProductService;
import enigma.estore.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductService productService;
    private final EmailService emailService;

    @Override
    public Page<TransactionBasicFormat> index(Pageable pageable) {
        Page<Transaction> trans = transactionRepository.findAll(pageable);

        return trans.map(TransactionBasicFormat::from);
    }

    @Override
    @Transactional
    public TransactionBasicFormat create(CreateTransaction transaction, User user) {
        Transaction trans = new Transaction();
        trans.setUser(user);

        for (CreateTransactionDetail detail : transaction.getTransactionDetails()) {
            TransactionDetail transDetail = createDetail(detail);
            trans.addTransactionDetail(transDetail);
        }

        Double totalAmount = trans.getTransactionDetails().stream()
                .mapToDouble(TransactionDetail::getPrice)
                .sum();
        trans.setTotalAmount(totalAmount);

        Transaction savedTransaction = transactionRepository.save(trans);

        // Asynchronously send confirmation email
        emailService.sendTransactionConfirmationEmail(
                user.getEmail(), "Transaction ID: " + savedTransaction.getId() + ", Total Amount: " + totalAmount
        );

        return TransactionBasicFormat.from(savedTransaction);
    }

    private TransactionDetail createDetail(CreateTransactionDetail det) {
        Product product = productService.show(det.getProductId());
        return TransactionDetail.builder()
                .product(product)
                .quantity(det.getQuantity())
                .price(det.getQuantity() * product.getPrice())
                .build();
    }

}