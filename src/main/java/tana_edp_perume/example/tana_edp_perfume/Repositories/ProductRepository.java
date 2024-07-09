package tana_edp_perume.example.tana_edp_perfume.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Contracts.*;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.ProductSection.Product;

import java.util.Collection;
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

    String qurUpdate= "Update \"tana_product\" set \"quantity\"=\"quantity\"+ :quantity where \"id\"=:productId";
    @Modifying
    @Transactional
    @Query(value = qurUpdate,nativeQuery = true)
    int UpdateQuantity(@Param("productId") Long productId,@Param("quantity") int quantity);


    String qurIngredient= "select \"id\",\"name\" from \"tana_ingredient\"";
    @Query(value = qurIngredient,nativeQuery = true)
    Collection<IngredientDTO> getListIngredient();

    String qurCapacity= "select \"id\",\"name\" from \"tana_capacity\"";
    @Query(value = qurCapacity,nativeQuery = true)
    Collection<CapacityDTO> getListCapacity();

    String qurIncenseGroup= "select \"id\",\"name\" from \"tana_incense_group\"";
    @Query(value = qurIncenseGroup,nativeQuery = true)
    Collection<IncenseGroupDTO> getListIncenseGroup();

    String qurCategory= "select \"id\",\"name\" from \"tana_category\"";
    @Query(value = qurCategory,nativeQuery = true)
    Collection<CategoryDTO> getListCategory();

//    String qurCreate= "insert into \"tana_product\" (\"id\",\"name\",\"price\",\"quantity\",\"sku\",\"image_url\",\"capacity_id\",\"incense_group_id\",\"ingredient_id\")\n" +
//            "values(:productId,:productName,:price,:quantity,:sku',:image_url,:capacity_id,:incense_group_id,:ingredient_id)";
//    @Modifying
//    @Transactional
//    @Query(value = qurCreate,nativeQuery = true)
//    int CreateProduct(@Param("productId") int productId,@Param("productName") String productName,@Param("quantity") int quantity,
//                      @Param("price") int price,@Param("sku") String sku,
//                      @Param("image_url") String image_url,@Param("capacity_id") int capacity_id,
//                      @Param("incense_group_id") int incense_group_id,@Param("ingredient_id") int ingredient_id);




}
