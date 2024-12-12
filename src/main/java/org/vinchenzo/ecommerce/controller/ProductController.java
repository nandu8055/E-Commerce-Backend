package org.vinchenzo.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.vinchenzo.ecommerce.Payload.ProductDTO;
import org.vinchenzo.ecommerce.Payload.ProductResponse;
import org.vinchenzo.ecommerce.service.ProductService;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable(name = "categoryId") Long categoryId) {
        return new ResponseEntity<>(productService.addProduct(categoryId,productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        
        ProductResponse productResponse = productService.getAllProducts();

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable(name = "categoryId") Long categoryId
//            ,@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
//            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
//            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
//            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder
            ) {
        ProductResponse productResponse =productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse =  productService.getProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@RequestBody ProductDTO productDTO,
                                                          @PathVariable Long productId) {
        ProductDTO savedproductDTO= productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.OK);
    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("Image") MultipartFile image) throws IOException {
        ProductDTO updatedProductDTO= productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }


}
