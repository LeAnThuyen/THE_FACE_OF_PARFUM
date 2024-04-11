package tana_edp_perume.example.tana_edp_perfume.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
