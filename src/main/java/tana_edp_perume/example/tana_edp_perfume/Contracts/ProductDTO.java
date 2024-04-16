package tana_edp_perume.example.tana_edp_perfume.Contracts;


import lombok.Data;
import org.springframework.http.ResponseEntity;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;

import java.sql.Date;
import java.util.Collection;


public interface ProductDTO  {

     long getId();
     String getName();
     float getPrice();
     int getQuantity();
     String getSKU();
     String getImage_Url();
     Date getCreationTime();
     Date getLastModificationTime();
     long getIngredient_Id();
     String getIngredient_Name();
     long getIncense_Group_Id();
     String getIncense_Group_Name();
     long getCapacity_Id();
     String getCapacity_Name();
     Collection<Order_Details> getorderDetails();

}
