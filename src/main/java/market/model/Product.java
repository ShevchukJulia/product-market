package market.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    private BigDecimal price;
    private String sku;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
