package tana_edp_perume.example.tana_edp_perume.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perume.Domain.Entities.UserSection.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
