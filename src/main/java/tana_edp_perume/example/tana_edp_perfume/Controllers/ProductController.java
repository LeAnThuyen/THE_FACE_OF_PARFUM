package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private ProductRepositoryWithQueries _productRepositoryWithQueries;
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
       var  getListProducts= _productRepository.FindAllProduct();
        return getListProducts;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity< Product> getProductById(@PathVariable(value = "id") Long productId)
            throws ResourceNotFoundException {
        Product product = _productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productId));
        return ResponseEntity.ok().body(product);
    }
    @PostMapping("/product")
    public  Product createProduct(@Valid @RequestBody Product product){
        return _productRepository.save(product);
    }


}
