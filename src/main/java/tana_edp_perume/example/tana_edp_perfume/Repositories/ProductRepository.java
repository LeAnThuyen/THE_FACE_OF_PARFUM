package tana_edp_perume.example.tana_edp_perfume.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Contracts.ProductDTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.ProductSection.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    String qur= "select a.*,b.\"name\" as ingredient_name, c.\"name\" as incense_group_name,d.\"name\" as capacity_name  from \"tana_product\" a \n" +
            "inner join \"tana_ingredient\" b on a.\"ingredient_id\"= b.\"id\"\n" +
            "inner join \"tana_incense_group\" c on a.\"incense_group_id\"= c.\"id\"\n" +
            "inner join \"tana_capacity\" d on a.\"capacity_id\"= d.\"id\"";
    @Query(value = qur,nativeQuery = true)
    List<ProductDTO> FindAllProduct();


    String qurDetail= "select a.*,b.\"name\" as ingredient_name, c.\"name\" as incense_group_name,d.\"name\" as capacity_name  from \"tana_product\" a \n" +
            "inner join \"tana_ingredient\" b on a.\"ingredient_id\"= b.\"id\"\n" +
            "inner join \"tana_incense_group\" c on a.\"incense_group_id\"= c.\"id\"\n" +
            "inner join \"tana_capacity\" d on a.\"capacity_id\"= d.\"id\" where a.\"id\" =:productId";
    @Query(value = qurDetail,nativeQuery = true)
   ProductDTO FindProductById(@Param("productId") Long productId);

}
