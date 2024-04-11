package tana_edp_perume.example.tana_edp_perfume.Contracts;


import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;

import java.sql.Date;
import java.util.Collection;

public class ProductDTO {

    private long Id;
    private String Name;
    private float Price;
    private int Quantity;
    private String SKU;
    private String ImageUrl;
    private Date CreationTime ;
    private Date LastModificationTime;
    private long Ingredient_Id;
    private String Ingredient_Name;
    private long Incense_Group_Id;
    private String Incense_Group_Name;
    private long Capacity_Id;
    private String Capacity_Name;
    private Collection<Order_Details> orderDetails;

}
