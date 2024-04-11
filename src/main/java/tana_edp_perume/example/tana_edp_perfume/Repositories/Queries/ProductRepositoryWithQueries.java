package tana_edp_perume.example.tana_edp_perfume.Repositories.Queries;

import org.springframework.data.jpa.repository.Query;
import tana_edp_perume.example.tana_edp_perfume.Contracts.ProductDTO;

import java.util.List;

public interface ProductRepositoryWithQueries {
    String qur= "select a.*,b.\"name\" as ingredient_name, c.\"name\" as incense_group_name  from \"tana_product\" a \n" +
            "inner join \"tana_ingredient\" b on a.\"ingredient_id\"= b.\"id\"\n" +
            "inner join \"tana_incense_group\" c on a.\"incense_group_id\"= c.\"id\"\n" +
            "inner join \"tana_capacity\" d on a.\"capacity_id\"= d.\"id\"";
    @Query(qur)
    List<ProductDTO> FindAllProduct();
}
