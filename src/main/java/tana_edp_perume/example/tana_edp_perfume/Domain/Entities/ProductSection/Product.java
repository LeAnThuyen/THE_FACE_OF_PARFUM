package tana_edp_perume.example.tana_edp_perfume.Domain.Entities.ProductSection;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;


import java.sql.Date;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "TANA_PRODUCT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String Name;
    private float Price;
    private int Quantity;
    private String SKU;
    private String ImageUrl;

    @CreationTimestamp
    private Date CreationTime ;
    @UpdateTimestamp
    private Date LastModificationTime;

    @OneToMany(mappedBy = "product")
    Set<Product_Category> Product_Category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude // Khong sử dụng trong toString()
    private Collection<Order_Details> orderDetails;

    @ManyToOne
    @JoinColumn(name = "Ingredient_Id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "Incense_Group_Id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Incense_Group incense_group;

    @ManyToOne
    @JoinColumn(name = "Capacity_Id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Capacity capacity;

}
