package tana_edp_perume.example.tana_edp_perfume.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String qur= "select * from \"tana_user\" where LOWER(\"email\")=:email and rownum=1";
    @Query(value = qur,nativeQuery = true)
    User FindUserByEmail(@Param("email") String email);
}
