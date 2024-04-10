package tana_edp_perume.example.tana_edp_perume.Domain.Entities.ProductSection;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Table(name = "TANA_PRODUCT_CATEGORY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)


public class Product_Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "Category_Id")
    Category category;
}
