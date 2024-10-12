package projects.blue_whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projects.blue_whale.converter.TransactionItemDetailsConverter;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="TRADE_TYPE")
    private String tradeType;

    @Column(name="MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name="DATE")
    private String date;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="AMOUNT")
    private Double amount;

    @Convert(converter = TransactionItemDetailsConverter.class)
    @Column(name="TRANSACTION_ITEM_DETAILS", columnDefinition = "TEXT")
    private List<TransactionItemDetails> transactionItemDetails;

    @Data
    @NoArgsConstructor
    public static class TransactionItemDetails {
        private Long itemId;
        private String itemName;
        private String categoryName;
        private Integer quantity;
        private Double cost;
    }

}
