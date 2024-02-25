package com.jwt.demo.Springjwtauthentication.api.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductApi {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product){

       Product savedProduct  =  productRepository.save(product);

       URI productURI = URI.create("/products/"+savedProduct.getId());

       return  ResponseEntity.created(productURI).body(savedProduct);

    }
}
