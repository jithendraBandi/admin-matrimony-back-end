package projects.blue_whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="QUANTITY")
    private Integer quantity = 0;

    @Column(name="PRICE")
    private Double price;

    @Column(name="PURCHASE_PRICE")
    private Double purchasePrice;

    @Column(name="COMPANY")
    private String company;

    @Column(name="CATEGORY_ID")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID", insertable = false, updatable = false)
    private Category category;
}
