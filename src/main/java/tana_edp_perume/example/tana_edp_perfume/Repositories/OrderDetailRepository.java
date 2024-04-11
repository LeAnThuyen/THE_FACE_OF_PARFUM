package tana_edp_perume.example.tana_edp_perfume.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;
@Repository
public interface OrderDetailRepository extends JpaRepository<Order_Details, Long> {
}
