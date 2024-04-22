package tana_edp_perume.example.tana_edp_perfume.Contracts;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class OrderDTO implements Serializable {
    private long Id;
    private String Address;
    private String FullName;
    private String PhoneNumber;
    private String Code;
    private Date ShippingDate;
    private Date CreationTime;
    private String Note;
    private float TotalAmount;
    private int Status;
    private List<Order_Detail_DTO> Order_Detail_DTOS;
    private long User_Id;
    private long Discount_Id;

}
