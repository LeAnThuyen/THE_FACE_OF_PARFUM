package tana_edp_perume.example.tana_edp_perume.Domain.Entities.ShoppingSection;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tana_edp_perume.example.tana_edp_perume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perume.Domain.Entities.CartSection.Order_Details;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "TANA_DISCOUNT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private int DiscountPercentage;
    @CreationTimestamp
    private Date CreationTime;
    private Date ExpiryDate;
    private int MaxUserCanUse;
    private int UserUsed;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Order> order;

}
