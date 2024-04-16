package tana_edp_perume.example.tana_edp_perfume.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderDetailRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.OrderRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.ProductRepository;


@Controller

public class Home {
    @Autowired
    private ProductRepository _productRepository;
    private OrderRepository _orderRepository;
    private OrderDetailRepository _orderdetailRepository;
    @GetMapping("/")
    public String HomePage(Model model){

        model.addAttribute("lstProduct",_productRepository.FindAllProduct());
        return  "Home";
    }




}