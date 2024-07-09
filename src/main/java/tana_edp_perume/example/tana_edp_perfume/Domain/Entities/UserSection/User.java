package tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.RoleSection.Role;

import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "TANA_USER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private String Address;
    private String FullAddress;
    private String Password;
    private String Avatar;
    @CreationTimestamp
    private Date CreationTime ;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Order> orders;

    @ManyToOne
    @JoinColumn(name = "Role_Id") // thông qua khóa ngoại Role_Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role role;

}
