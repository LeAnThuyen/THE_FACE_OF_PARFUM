package tana_edp_perume.example.tana_edp_perume.Domain.Entities.ProductSection;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tana_edp_perume.example.tana_edp_perume.Domain.Entities.CartSection.Order;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "TANA_INGREDIENT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class Ingredient {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String Name;
    private String Code;
    private String Description;
    @CreationTimestamp
    private Date CreationTime ;


    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Product> product;
}
