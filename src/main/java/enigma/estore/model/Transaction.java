package enigma.estore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "total_amount")
    private Double totalAmount;

    // Lombok automatically set this immutable
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> transactionDetails = new ArrayList<>();

    // So we need to add some method to make it bidirectional
    public void addTransactionDetail(TransactionDetail detail) {
        transactionDetails.add(detail);
        detail.setTransaction(this);
    }

    public void removeTransactionDetail(TransactionDetail detail) {
        transactionDetails.remove(detail);
        detail.setTransaction(null);
    }
}
