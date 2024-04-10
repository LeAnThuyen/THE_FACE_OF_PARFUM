package tana_edp_perume.example.tana_edp_perume.Domain.Entities.CartSection;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tana_edp_perume.example.tana_edp_perume.Domain.Entities.ProductSection.Ingredient;
import tana_edp_perume.example.tana_edp_perume.Domain.Entities.ProductSection.Product;

import java.sql.Date;

@Entity
@Table(name = "TANA_ORDER_DETAILS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class Order_Details {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @CreationTimestamp
    private Date CreationTime ;

    private float Price;
    private int Quantity;
    private float TotalAmount;
    private boolean Status;

    @ManyToOne
    @JoinColumn(name = "Order_Id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;



}
