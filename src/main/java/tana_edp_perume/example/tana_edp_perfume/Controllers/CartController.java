package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tana_edp_perume.example.tana_edp_perfume.Commons.GenerateCodeWithLength;
import tana_edp_perume.example.tana_edp_perfume.Contracts.IOder_Detail_DTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.OrderDTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.Order_Detail_DTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.StoredOderDTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderDetailRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.ProductRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
    public class CartController {
    @Autowired
    private ProductRepository _productRepository;
    @Autowired
    private OrderRepository _orderRepository;
    @Autowired
    private OrderDetailRepository _orderdetailRepository;



    @RequestMapping(value="/IncreaseQuantity/{productId}/{quantity}", method = RequestMethod.POST)
    public String IncreaseQuantity(@PathVariable Long productId,@PathVariable int quantity, Model model,@RequestBody OrderDTO storedDataSend) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var sessionGioHang =(OrderDTO)  session.getAttribute("GioHangView");
        //check số lượng còn lại
        var userId =(Long) session.getAttribute("UserId");

        var spCheck = _productRepository.findById(productId);

            if (spCheck != null) {
                var SpDetail=spCheck.get();
                if (SpDetail.getQuantity() < quantity + 1)
                {
                    return  MessageFormat.format("Sản phẩm {0} chỉ còn {1} mặt hàng",spCheck.get().getName(),spCheck.get().getQuantity());

                }
                if (SpDetail.getQuantity()>0)
                {
                    sessionGioHang.getOrder_Detail_DTOS().forEach(c->{
                        if (c.getProduct_Id()==productId){
                            c.setQuantity(c.getQuantity()+1);
                            c.setTotalAmount((c.getQuantity()+1)*c.getPrice());
                        }
                    });
                    //neu user da dang nhap thi cap nhat lai so luong cho user
                    if (userId!=null){
                        _orderdetailRepository.UpdateQuantity(productId,sessionGioHang.getId(),1);
                    }
                    //set lai tong tien cho gio hang

                    sessionGioHang.setTotalAmount((float) sessionGioHang.getOrder_Detail_DTOS().stream().mapToDouble(c->c.getPrice()).sum());
                    session.setAttribute("GioHangView", sessionGioHang);
                    model.addAttribute("GioHangView", sessionGioHang);
                }

                return "Success";
            }
        return  "Failed";
    }
    @RequestMapping(value="/OnKeyUpQuantity/{productId}/{quantity}")
    public String OnKeyUpQuantity(@PathVariable Long productId,@PathVariable int quantity, Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var sessionGioHang =(OrderDTO)  session.getAttribute("GioHangView");
        //check số lượng còn lại
        var userId =(Long) session.getAttribute("UserId");

        var spCheck = _productRepository.findById(productId);

            if (spCheck != null) {
                var SpDetail=spCheck.get();
                if (SpDetail.getQuantity() < quantity )
                {
                    return  MessageFormat.format("Sản phẩm {0} chỉ còn {1} mặt hàng",spCheck.get().getName(),spCheck.get().getQuantity());

                }
                if (SpDetail.getQuantity()>0)
                {
                    sessionGioHang.getOrder_Detail_DTOS().forEach(c->{
                        if (c.getProduct_Id()==productId){
                            c.setQuantity(quantity);
                            c.setTotalAmount((quantity)*c.getPrice());
                        }
                    });
                    //neu user da dang nhap thi cap nhat lai so luong cho user
                    if (userId!=null){
                        _orderdetailRepository.UpdateQuantityByInput(productId,sessionGioHang.getId(),quantity);
                    }
                    //set lai tong tien cho gio hang

                    sessionGioHang.setTotalAmount((float) sessionGioHang.getOrder_Detail_DTOS().stream().mapToDouble(c->c.getPrice()).sum());
                    session.setAttribute("GioHangView", sessionGioHang);
                    model.addAttribute("GioHangView", sessionGioHang);
                }

                return "Success";
            }
        return  "Failed";
    }
    @RequestMapping(value="/DecreaseQuantity/{productId}/{quantity}")
    public String DecreaseQuantity(@PathVariable Long productId,@PathVariable int quantity,Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var sessionGioHang =(OrderDTO)  session.getAttribute("GioHangView");
        //check số lượng còn lại
        var userId =(Long) session.getAttribute("UserId");

        var spCheck = _productRepository.findById(productId);

            if (spCheck != null) {
                var SpDetail=spCheck.get();
                if (SpDetail.getQuantity() < quantity -1)
                {
                    return  MessageFormat.format("Sản phẩm {0} chỉ còn {1} mặt hàng",spCheck.get().getName(),spCheck.get().getQuantity());

                }
                if (SpDetail.getQuantity()>0)
                {
                    sessionGioHang.getOrder_Detail_DTOS().forEach(c->{
                        if (c.getProduct_Id()==productId){
                            c.setQuantity(c.getQuantity()-1);
                            c.setTotalAmount((c.getQuantity()-1)*c.getPrice());
                        }
                    });
                    //neu user da dang nhap thi cap nhat lai so luong cho user
                    if (userId!=null){
                        _orderdetailRepository.UpdateQuantity(productId,sessionGioHang.getId(),-1);
                    }
                    //set lai tong tien cho gio hang

                    sessionGioHang.setTotalAmount((float) sessionGioHang.getOrder_Detail_DTOS().stream().mapToDouble(c->c.getPrice()).sum());
                    session.setAttribute("GioHangView", sessionGioHang);
                    model.addAttribute("GioHangView", sessionGioHang);
                }

                return "Success";
            }
        return  "Failed";
    }
}



