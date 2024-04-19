package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tana_edp_perume.example.tana_edp_perfume.Commons.GenerateCodeWithLength;
import tana_edp_perume.example.tana_edp_perfume.Contracts.OrderDTO;
import tana_edp_perume.example.tana_edp_perfume.Contracts.Order_Detail_DTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.CartSection.Order_Details;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderDetailRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.ProductRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
    public class CartController {
    @Autowired
    private ProductRepository _productRepository;
    @Autowired
    private OrderRepository _orderRepository;
    @Autowired
    private OrderDetailRepository _orderdetailRepository;

    @GetMapping("/Cart")
    public String YourCart(Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var sessionUser = session.getAttribute("User");
        var sessionUserId = session.getAttribute("UserId");
        //

        //gioHangCTDTO là bao gồm các thông tin của 1 giỏ hàng bình
        //thường và thêm thêm 1 trường là tên của sản phẩm để khi show lên phần
        //giỏ hàng sẽ có tên của sản phẩm
        List<Order_Detail_DTO> lstgioHangCTDTO = new ArrayList<>();
        // trường hợp người dùng chưa đăng nhập
        if (sessionUser == null) {
            //lấy thông tin giỏ hàng chi tiết từ session
            var sessionGioHangCT = (List<Order_Details>) session.getAttribute("userGioHangCT");
            if (sessionGioHangCT != null) {
                sessionGioHangCT.forEach(x -> {
                    var ProductDetail = _productRepository.findById(x.getProduct().getId());
                    Order_Detail_DTO gioHangCT = new Order_Detail_DTO();
                    gioHangCT.setId(x.getId());
                    gioHangCT.setPrice(x.getPrice());
                    gioHangCT.setOrder_Id(x.getOrder().getId());
                    gioHangCT.setProduct_Id(x.getProduct().getId());
                    gioHangCT.setQuantity(x.getQuantity());
                    gioHangCT.setTotalAmount(x.getQuantity() * x.getPrice());
                    gioHangCT.setStatus(false);
                    gioHangCT.setProduct_Name(ProductDetail.get().getName());
                    gioHangCT.setSKU(ProductDetail.get().getSKU());
                    gioHangCT.setImageUrl(ProductDetail.get().getImageUrl());
                    //thêm vào giỏ hàng để hiển thị ra bên ngoài
                    lstgioHangCTDTO.add(gioHangCT);
                });
            }
            session.setAttribute("GioHangView", lstgioHangCTDTO);
            //tính tong tien
            OrderDTO orderDTO = new OrderDTO();
            GenerateCodeWithLength generateCodeWithLength = new GenerateCodeWithLength();
            orderDTO.setOrder_Detail_DTOS(lstgioHangCTDTO);
            orderDTO.setCode(generateCodeWithLength.GenerateCode(12));
            lstgioHangCTDTO.forEach(c -> {
                orderDTO.setTotalAmount(orderDTO.getTotalAmount()+c.getTotalAmount());
            });
            model.addAttribute("GioHangView",orderDTO);
            return "Cart";
        } else {
            // trường hợp người dùng đã đăng nhập
            var Id =(Long) session.getAttribute("UserId");
            //kiểm tra xem khách hàng này đã mua sản phầm nào trước đos hay chưa nếu có thì tiếp tục kiểm tra
            //lúc người ta chưa đăng nhập có mua sản phẩm nào nữa không
            var oder = _orderRepository.FindOrderByUserIdAndStatus((long) Id);
            if (oder != null) {

                //lấy ra id giỏ hàng của user
                var idGioHang = oder.getId();
                // kiểm tra xem session giỏ hàng có dữ liệu hay không
                var sessionGioHangCT = (List<Order_Details>) session.getAttribute("userGioHangCT");
                if (sessionGioHangCT != null) {
                    //convert giỏ hàng session về dạng list

                    // kiểm tra qua hết 1 lượt của giỏ hàng session xem có sản phẩm nào đã
                    // từng mua trước đó hay chưa nếu từng mua rồi thì cộng dồn số lượng lên
                    sessionGioHangCT.forEach(x -> {
                        //kiểm tra nếu sản phẩm trong giỏ hàng session đã từng mua thì sẽ dộng dồn số lượng
                        if (_orderdetailRepository.CheckProductExisted(x.getProduct().getId(), idGioHang)!=0) {
                            _orderdetailRepository.UpdateQuantity(x.getProduct().getId(), idGioHang, x.getQuantity());

                        } else {
                            // nếu sản phẩm trong giỏ hàng session chưa
                            // từng được mua thì tiến hành thêm mới sản phẩm vào cả giỏ hàng db
                            var ProductDetail = _productRepository.findById(x.getProduct().getId());
                            Order_Detail_DTO gioHangCT = new Order_Detail_DTO();
                            gioHangCT.setId(x.getId());
                            gioHangCT.setPrice(x.getPrice());
                            gioHangCT.setOrder_Id(idGioHang);
                            gioHangCT.setProduct_Id(x.getProduct().getId());
                            gioHangCT.setQuantity(x.getQuantity());
                            gioHangCT.setTotalAmount(x.getQuantity() * x.getPrice());
                            gioHangCT.setStatus(false);
                            gioHangCT.setProduct_Name(ProductDetail.get().getName());
                            gioHangCT.setSKU(ProductDetail.get().getSKU());
                            gioHangCT.setImageUrl(ProductDetail.get().getImageUrl());
                            //thêm sản phẩm vào giỏ hàng
                            _orderdetailRepository.save(x);
                            // thêm sản phầm vào list giỏ hàng có thêm tên của sản phẩm để hiển thị ra ngoài view
                            lstgioHangCTDTO.add(gioHangCT);
                        }
                    });

                    //kiểm tra xem dữ liệu trong giỏ hàng của user nếu có thì add thêm vào list giỏ hàng bên trên để hiển thị ra ngoài view
                    var lstGiohangCT = _orderdetailRepository.GetListByOrderId(oder.getId());
                    if (lstGiohangCT != null) {
                        lstGiohangCT.forEach(x -> {
                            Order_Detail_DTO gioHangCT = new Order_Detail_DTO();
                            gioHangCT.setId(x.getId());
                            gioHangCT.setPrice(x.getPrice());
                            gioHangCT.setOrder_Id(x.getOrder_Id());
                            gioHangCT.setProduct_Id(x.getProduct_Id());
                            gioHangCT.setQuantity(x.getQuantity());
                            gioHangCT.setTotalAmount(x.getQuantity() * x.getPrice());
                            gioHangCT.setStatus(false);
                            gioHangCT.setProduct_Name(x.getProduct_Name());
                            gioHangCT.setSKU(x.getSKU());
                            gioHangCT.setImageUrl(x.getImage_Url());
                            lstgioHangCTDTO.add(gioHangCT);
                        });

                    }


                } else {

                    var lstGiohangCT = _orderdetailRepository.GetListByOrderId(oder.getId());
                    if (lstGiohangCT != null) {
                        lstGiohangCT.forEach(x -> {
                            Order_Detail_DTO gioHangCT = new Order_Detail_DTO();
                            gioHangCT.setId(x.getId());
                            gioHangCT.setPrice(x.getPrice());
                            gioHangCT.setOrder_Id(x.getOrder_Id());
                            gioHangCT.setProduct_Id(x.getProduct_Id());
                            gioHangCT.setQuantity(x.getQuantity());
                            gioHangCT.setTotalAmount(x.getQuantity() * x.getPrice());
                            gioHangCT.setStatus(false);
                            gioHangCT.setProduct_Name(x.getProduct_Name());
                            gioHangCT.setSKU(x.getSKU());
                            gioHangCT.setImageUrl(x.getImage_Url());
                            lstgioHangCTDTO.add(gioHangCT);
                        });

                    }

                }
                //khi đã thêm xong dữ liệu của giỏ hàng session thì xoá hết dữ liệu này đi
                session.removeAttribute("userGioHangCT");
                session.removeAttribute("userGioHang");
                //
                session.setAttribute("GioHangView", lstgioHangCTDTO);
                model.addAttribute("GioHangView",lstgioHangCTDTO);
                OrderDTO orderDTO = new OrderDTO();
                GenerateCodeWithLength generateCodeWithLength = new GenerateCodeWithLength();
                orderDTO.setOrder_Detail_DTOS(lstgioHangCTDTO);
                orderDTO.setCode(generateCodeWithLength.GenerateCode(12));
                lstgioHangCTDTO.forEach(c -> {
                    orderDTO.setTotalAmount(orderDTO.getTotalAmount()+c.getTotalAmount());
                });
                model.addAttribute("GioHangView",orderDTO);
                return "Cart";
            } else {
                //trường hợp trước đó người dùng chưa từng mua sản phẩm nào thì check xem giỏ hàng session của user
                //đó có dữ liệu không nếu có thì add thêm vào list đẻ hiển thị ra ngoài vjew
                var sessionGioHang = (Order) session.getAttribute("userGioHang");
                var sessionGioHangCT = (List<Order_Details>) session.getAttribute("userGioHangCT");
                //check xem nếu người dùng đã có giỏ hàng session trước đó thì gán lại dữ liệu cho người dùng để thêm vào db
                if (sessionGioHang != null) {
                    //lấy thông tin  của giỏ hàng session để thêm vào database
                    // gán lại Id của user cho giỏ hàng
                    if (_orderRepository.FindOrderByUserIdAndStatus((long) sessionUserId) == null) {
                        Order order = new Order();
                        order.setCode(sessionGioHang.getCode());
                        order.setId(sessionGioHang.getId());
                        order.setFullAddress(sessionGioHang.getFullAddress());
                        order.setAddress(sessionGioHang.getAddress());
                        order.setPhoneNumber(sessionGioHang.getAddress());
                        order.setShippingDate(sessionGioHang.getShippingDate());
                        order.setStatus(sessionGioHang.getStatus());
                        order.setTotalAmount(sessionGioHang.getTotalAmount());
                        order.setNote(sessionGioHang.getNote());
                        _orderRepository.save(order);
                    }
                }
                //kiểm tra xem giỏ hàng chi tiết session có dữ liệu hay không
                if (sessionGioHangCT != null) {
                    sessionGioHangCT.forEach(x -> {
                        var ProductDetail = _productRepository.findById(x.getProduct().getId());
                        Order_Detail_DTO gioHangCT = new Order_Detail_DTO();
                        gioHangCT.setId(x.getId());
                        gioHangCT.setPrice(x.getPrice());
                        gioHangCT.setOrder_Id(x.getOrder().getId());
                        gioHangCT.setProduct_Id(x.getProduct().getId());
                        gioHangCT.setQuantity(x.getQuantity());
                        gioHangCT.setTotalAmount(x.getQuantity() * x.getPrice());
                        gioHangCT.setStatus(false);
                        gioHangCT.setProduct_Name(ProductDetail.get().getName());
                        gioHangCT.setSKU(ProductDetail.get().getSKU());
                        gioHangCT.setImageUrl(ProductDetail.get().getImageUrl());
                        //them va gio hang hien thi len view
                        lstgioHangCTDTO.add(gioHangCT);
                        // them vao database
                        _orderdetailRepository.save(x);
                    });


                }
            }
            //khi thêm xong thì xoá hết dữ liệu ở trong session này đi
            //khi đã thêm xong dữ liệu của giỏ hàng session thì xoá hết dữ liệu này đi
            session.removeAttribute("userGioHangCT");
            session.removeAttribute("userGioHang");
            //
            session.setAttribute("GioHangView", lstgioHangCTDTO);
            model.addAttribute("GioHangView",lstgioHangCTDTO);
            // tinh tong tien
            OrderDTO orderDTO = new OrderDTO();
            GenerateCodeWithLength generateCodeWithLength = new GenerateCodeWithLength();
            orderDTO.setOrder_Detail_DTOS(lstgioHangCTDTO);
            orderDTO.setCode(generateCodeWithLength.GenerateCode(12));
            lstgioHangCTDTO.forEach(c -> {
                orderDTO.setTotalAmount(orderDTO.getTotalAmount()+c.getTotalAmount());
            });
            model.addAttribute("GioHangView",orderDTO);
            return "Cart";
        }
    }


    public String IncreaseQuantity(Long productId, int quantity,Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var sessionGioHang =(OrderDTO)  model.getAttribute("GioHangView");
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

                return "/Cart";
            }
        return  "Success";
    }
    public String DecreaseQuantity(Long productId, int quantity,Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        var sessionGioHang =(OrderDTO)  model.getAttribute("GioHangView");
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

                return "/Cart";
            }
        return  "Success";
    }
}



