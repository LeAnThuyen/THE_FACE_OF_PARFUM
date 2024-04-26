package tana_edp_perume.example.tana_edp_perfume.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Contracts.ProductDTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    String qur= "SELECT * from \"tana_order\" WHERE \"user_id\"=:userId AND \"status\"=0 AND ROWNUM=1";
    @Query(value = qur,nativeQuery = true)
    Order FindOrderByUserIdAndStatus(@Param("userId") Long userId);

    String qurLst= "SELECT * from \"tana_order\" WHERE \"user_id\"=:userId AND \"status\"=1";
    @Query(value = qurLst,nativeQuery = true)
    List<Order> GetListOrderByUserId(@Param("userId") Long userId);



}
