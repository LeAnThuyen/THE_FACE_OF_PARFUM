package tana_edp_perume.example.tana_edp_perfume.Contracts;

import lombok.Data;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
@Data
public class CreateUpdateProductDTO implements Serializable  {

    private long Id;
    private String Name;
    private float Price;
    private int Quantity;
    private String SKU;
    private String ImageUrl;
    private Collection<CapacityDTO> CapacityDTO;
    private Collection<IngredientDTO> IngredientDTO;
    private Collection<IncenseGroupDTO> IncenseGroupDTO;
    private Collection<CategoryDTO> CategoryDTO;

}
