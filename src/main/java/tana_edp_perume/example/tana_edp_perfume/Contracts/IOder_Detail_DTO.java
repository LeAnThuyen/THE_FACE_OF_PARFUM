package tana_edp_perume.example.tana_edp_perfume.Contracts;




public interface IOder_Detail_DTO {
      long   getId();

      float  getPrice();

      int   getQuantity();

      float   getTotalAmount();

      boolean  getStatus();

      long   getOrder_Id();

      long   getProduct_Id();

      String   getProduct_Name();

      String   getSKU();

      String   getImage_Url();
}
