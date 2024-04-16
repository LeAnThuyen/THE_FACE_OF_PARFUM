package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;
import tana_edp_perume.example.tana_edp_perfume.Commons.GenerateCodeWithLength;
import tana_edp_perume.example.tana_edp_perfume.Contracts.Order_Detail_DTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.ProductDTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderDetailRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private ProductRepository _productRepository;
    private OrderRepository _orderRepository;
    private OrderDetailRepository _orderdetailRepository;


    @RequestMapping(value="/addToCart/{id}")
    public RedirectView addToCart(@PathVariable Long id) {
        //kiểm tra xem user đó đã đăng nhập hay chưa
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);


        var sessionUser = session.getAttribute("User");
        var sessionUserId = session.getAttribute("UserId");

        //lấy ra thông tin của sản phẩm muốn thêm vào giỏ hàng
        var productAdd = _productRepository.findById(id);
        //TH user chưa đăng nhập
        if (sessionUser == null) {
            // lấy dữ liệu của giỏ hàng session
            var sessionGioHang = (Order) session.getAttribute("userGioHang");
            // nếu giỏ hàng đã có dữ liệu
            if (sessionGioHang != null) {
                //lấy dữ liệu của giỏ hàng chi tiết rồi convert về list
                var sessionLstGioHangCT = (List<Order_Details>) session.getAttribute("userGioHangCT");
                if (sessionLstGioHangCT != null) {
                    //check xem nếu giỏ hàng đã có sản phẩm đó rồi thì số lượng sẽ cập nhật lên thêm 1 sản phẩm
                   var listStreamGioHangCT =sessionLstGioHangCT ;
                    if (sessionLstGioHangCT.stream().anyMatch(c -> c.getProduct().getId() == id))
                    {
                        listStreamGioHangCT.forEach(c -> {
                            c.setQuantity(c.getQuantity() + 1);
                        });
                    }
                    else {
                        //nếu chưa có thì thêm mới sản phầm này vào giỏ hàng
                        Order_Details gioHangCT = new Order_Details();
                        gioHangCT.setId(new Date().getTime());
                        gioHangCT.setPrice(productAdd.get().getPrice());
                        gioHangCT.setQuantity(1);
                        gioHangCT.setStatus(false);
                        gioHangCT.setTotalAmount(productAdd.get().getPrice() * 1);
                        gioHangCT.setProduct(productAdd.get());
                        Order order = new Order();
                        order.setId(sessionGioHang.getId());
                        gioHangCT.setOrder(order);
                        listStreamGioHangCT.add(gioHangCT);
                    }

                    session.setAttribute("userGioHangCT", listStreamGioHangCT);
                }

            }
            else {
                //TH nêus người dùng chưa có gior hàng thì tạo giỏ hàng cho user đó
                GenerateCodeWithLength generateCodeWithLength = new GenerateCodeWithLength();
                Order order = new Order();
                order.setCode(generateCodeWithLength.GenerateCode(8));
                order.setId(new Date().getTime());
                order.setFullAddress("");
                order.setAddress("");
                order.setPhoneNumber("");
                order.setShippingDate(null);
                order.setStatus(0);
                order.setTotalAmount(0);


                //lưu thông tin giỏ hàng vào session

                session.setAttribute("userGioHang", order);
                // lưu sản phẩm vào giỏ hàng
                Order_Details gioHangCT = new Order_Details();
                gioHangCT.setPrice(productAdd.get().getPrice());
                gioHangCT.setQuantity(1);
                gioHangCT.setStatus(false);
                gioHangCT.setId(new Date().getTime());
                gioHangCT.setTotalAmount(productAdd.get().getPrice() * 1);
                gioHangCT.setProduct(productAdd.get());
                gioHangCT.setOrder(order);
                // tạo ra 1 list giỏ hàng để thêm sản phẩm vừa thêm vào vào trong session
                List<Order_Details> lstGioHangChiTiet = new ArrayList<>();
                lstGioHangChiTiet.add(gioHangCT);
                // convert list giỏ hàng để lưu lại vào trong session

                session.setAttribute("userGioHangCT", lstGioHangChiTiet);
            }
        }
        else
        {
                //TH user đã đăng nhập
                //lấy ra user id đã được lưu lại sau khi đăng nhập

                // lấy ra thông tin giỏ hàng được lưu trong database xem có dữ liệu hay không
                var thongtingiohangByUserId = _orderRepository.FindOrderByUserIdAndStatus((long) sessionUserId);
                //nếu có dữ liệu
                if (thongtingiohangByUserId != null) {

                    //kiểm tra xem trong giỏ hàng được luư trong database đã có sản phẩm này chưa nnếu có rồi thì số lượng sẽ cộng thêm 1
                    //nếu không có thì thêm mới sản phầm đó với số lượng là 1 vào trong giỏ hàng
                    if (_orderdetailRepository.CheckProductExisted(id, thongtingiohangByUserId.getId())) {
                        _orderdetailRepository.UpdateQuantity(id, thongtingiohangByUserId.getId(), 1);
                    }
                    else {

                        Order_Details gioHangCT = new Order_Details();
                        gioHangCT.setPrice(productAdd.get().getPrice());
                        gioHangCT.setQuantity(1);
                        gioHangCT.setStatus(false);
                        gioHangCT.setId(new Date().getTime());
                        gioHangCT.setTotalAmount(productAdd.get().getPrice() * 1);
                        gioHangCT.setProduct(productAdd.get());
                        Order order = new Order();
                        order.setId(thongtingiohangByUserId.getId());
                        gioHangCT.setOrder(order);
                        //thêm sản phẩm vào giỏ hàng
                        _orderdetailRepository.save(gioHangCT);

                    }

                } else {
                    //TH giỏ hàng không có dữ liệu
                    //lấy thông tin giỏ hàng chi tiết
                    var thongTinGioHangCT = (List<Order_Details>) session.getAttribute("userGioHangCT");
                    //lấy thông tin giỏ hàng
                    var thongTinGioHang = (Order) session.getAttribute("userGioHang");

                    if (thongTinGioHangCT != null) {
                        //kiểm tra xem nếu không có dữ liệu giỏ hàng được luư trong database trước đây thì tiến hàng thêm mới
                        if (_orderRepository.FindOrderByUserIdAndStatus((long) sessionUserId) != null) {
                            User user = new User();

                            thongTinGioHang.setUser(thongTinGioHang.getUser());
                            _orderRepository.save(thongTinGioHang);
                        }


                    }
                    Order_Details gioHangCT = new Order_Details();
                    gioHangCT.setPrice(productAdd.get().getPrice());
                    gioHangCT.setQuantity(1);
                    gioHangCT.setStatus(false);
                    gioHangCT.setId(new Date().getTime());
                    gioHangCT.setTotalAmount(productAdd.get().getPrice() * 1);
                    gioHangCT.setProduct(productAdd.get());
                    Order order = new Order();
                    order.setId(thongTinGioHang.getId());
                    gioHangCT.setOrder(order);
                    //thêm sản phẩm vào giỏ hàng
                    _orderdetailRepository.save(gioHangCT);

                }

            }

        return new RedirectView("/");
    }


}
