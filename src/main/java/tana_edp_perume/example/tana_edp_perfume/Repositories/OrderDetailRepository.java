package tana_edp_perume.example.tana_edp_perfume.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tana_edp_perume.example.tana_edp_perfume.Contracts.IOder_Detail_DTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.Order_Detail_DTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.ProductDTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;

import java.util.List;
import java.util.Objects;

@Repository
public interface OrderDetailRepository extends JpaRepository<Order_Details, Long> {

    String qur= "SELECT COUNT(1) as CheckProductExisted from \"tana_order_details\" WHERE \"order_id\"=:orderId AND \"product_id\" =:productId AND \"status\"=0";
    @Query(value = qur,nativeQuery = true)
    int CheckProductExisted(@Param("productId") Long productId,@Param("orderId") Long orderId);

    String qurUpdateQuantity= "UPDATE \"tana_order_details\" SET \"quantity\"=\"quantity\"+ :quantity, \"total_amount\"=(\"quantity\"+ :quantity)* \"price\"  WHERE \"order_id\"=:orderId AND \"product_id\"=:productId AND \"status\"=0";
    @Modifying
    @Transactional
    @Query(value = qurUpdateQuantity,nativeQuery = true)
    int UpdateQuantity(@Param("productId") Long productId,@Param("orderId") Long orderId,@Param("quantity") int quantity);

    String qurUpdateQuantityByInput= "UPDATE \"tana_order_details\" SET \"quantity\"=:quantity, \"total_amount\"=(:quantity)* \"price\"  WHERE \"order_id\"=:orderId AND \"product_id\"=:productId AND \"status\"=0";
    @Modifying
    @Transactional
    @Query(value = qurUpdateQuantityByInput,nativeQuery = true)
    int UpdateQuantityByInput(@Param("productId") Long productId,@Param("orderId") Long orderId,@Param("quantity") int quantity);

    String qurGetListByOrderId= "SELECT a.*,b.\"name\" as Product_Name,b.\"sku\" AS SKU, b.\"image_url\" as Image_Url FROM \"tana_order_details\" a \n" +
            "INNER JOIN \"tana_product\" b on a.\"product_id\"=b.\"id\" \n" +
            "WHERE a.\"order_id\" =:orderId";
    @Query(value = qurGetListByOrderId,nativeQuery = true)
    List<IOder_Detail_DTO> GetListByOrderId(@Param("orderId") Long orderId);

}
