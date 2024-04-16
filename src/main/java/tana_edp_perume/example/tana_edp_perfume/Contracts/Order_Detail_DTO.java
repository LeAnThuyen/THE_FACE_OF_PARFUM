package tana_edp_perume.example.tana_edp_perfume.Contracts;

import lombok.Data;
@Data
public class Order_Detail_DTO {

    private  long   Id;

    private  float  Price;

    private  int   Quantity;

    private  float   TotalAmount;

    private  boolean  Status;

    private  long   Order_Id;

    private  long   Product_Id;

    private String   Product_Name;

    private  String   SKU;

    private  String   ImageUrl;


}
