package market.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", unique = true)
    private String name;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Product> products;

    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

}
