package tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.ShoppingSection.Discount;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "TANA_ORDER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String Address;
    private String FullAddress;
    private String FullName;
    private String PhoneNumber;
    private String Code;
    private Date ShippingDate;
    @CreationTimestamp
    private Date CreationTime;
    private String Note;
    private float TotalAmount;
    private int Status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude // Khong sử dụng trong toString()
    private Collection<Order_Details> orderDetails;


    @ManyToOne
    @JoinColumn(name = "User_Id") // thông qua khóa ngoại User_Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "Discount_Id") // thông qua khóa ngoại Discount_Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Discount discount;
}
