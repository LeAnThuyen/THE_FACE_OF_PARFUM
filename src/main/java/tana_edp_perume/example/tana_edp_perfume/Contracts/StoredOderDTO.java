package tana_edp_perume.example.tana_edp_perfume.Contracts;

import lombok.Data;

import java.sql.Date;
@Data
public class StoredOderDTO {
    private String Address;
    private String FullAddress;
    private String FullName;
    private String PhoneNumber;
    private String Code;
    private Date ShippingDate;
    private String Note;
}
