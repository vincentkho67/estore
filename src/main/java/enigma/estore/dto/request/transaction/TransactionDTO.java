package enigma.estore.dto.request.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import enigma.estore.dto.request.product.ProductDTO;
import enigma.estore.model.Transaction;
import enigma.estore.model.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDTO {
    @Data
    @Builder
    public static class TransactionBasicFormat {
        private Integer id;
        private Double totalAmount;
        private LocalDateTime createdAt;
        private List<TransactionDetailBasicFormat> details;

        public static TransactionBasicFormat from(Transaction transaction) {
            return TransactionBasicFormat.builder()
                    .id(transaction.getId())
                    .totalAmount(transaction.getTotalAmount())
                    .details(TransactionDetailBasicFormat.mapper(transaction.getTransactionDetails()))
                    .createdAt(transaction.getCreatedAt())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateTransaction {
        @JsonProperty("transaction_detail")
        private List<CreateTransactionDetail> transactionDetails;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransactionDetail {
        @JsonProperty("product_id")
        private Integer productId;
        private Integer quantity;
    }

    @Data
    @Builder
    public static class TransactionDetailBasicFormat {
        private ProductDTO product;
        private Integer quantity;
        private Double price;

        public static TransactionDetailBasicFormat from(TransactionDetail transactionDetail) {
            return TransactionDetailBasicFormat.builder()
                    .product(ProductDTO.from(transactionDetail.getProduct()))
                    .quantity(transactionDetail.getQuantity())
                    .price(transactionDetail.getPrice())
                    .build();
        }

        public static List<TransactionDetailBasicFormat> mapper(List<TransactionDetail> arr) {
            return arr.stream()
                    .map(TransactionDetailBasicFormat::from)
                    .collect(Collectors.toList());
        }
    }
}
