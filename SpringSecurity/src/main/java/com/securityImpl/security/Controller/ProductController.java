package com.securityImpl.security.Controller;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Product")
public class ProductController {

     List<Product> products = new ArrayList<>(List.of(
            new Product(1,"Iphone",
                    99000.0),
            new Product(2,"mac",
                    199000.0),
            new Product(3,"Fridge",
                    55648.0)
    )
    );

    private record Product(Integer productId,String productname,double price)
    {}

    @GetMapping
    public List<Product> getprpducts() {
        return products;
    }

    @PostMapping
    public Product SaveProduct(@RequestBody Product product) {
        products.add(product);
        return product;
 }
}
