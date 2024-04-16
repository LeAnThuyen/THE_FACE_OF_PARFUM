package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tana_edp_perume.example.tana_edp_perfume.Commons.ResourceNotFoundException;
import tana_edp_perume.example.tana_edp_perfume.Contracts.ProductDTO;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.ProductSection.Product;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;
import tana_edp_perume.example.tana_edp_perfume.Repositories.ProductRepository;
import tana_edp_perume.example.tana_edp_perfume.Repositories.Queries.ProductRepositoryWithQueries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductRepository _productRepository;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {

        return _productRepository.FindAllProduct();
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable(value = "id") Long productId)
            throws ResourceNotFoundException {
        var productStored = _productRepository.FindProductById(productId);


        //session
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute("product", productStored);
        var  xxx=(ProductDTO) session.getAttribute("product");
        return xxx;
    }
    @PostMapping("/product")
    public  Product createProduct(@Valid @RequestBody Product product){
        return _productRepository.save(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity < Product > updateProduct(@PathVariable(value = "id") Long productId,
                                              @Valid @RequestBody Product productDetails) throws ResourceNotFoundException {
        Product product = _productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));


        final Product updatedProduct = _productRepository.save(productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public Map < String, Boolean > deleteProduct(@PathVariable(value = "id") Long productId)
            throws ResourceNotFoundException {
        Product product = _productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));

        _productRepository.delete(product);
        Map< String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
