package org.vinchenzo.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;
import org.vinchenzo.ecommerce.Payload.ProductDTO;
import org.vinchenzo.ecommerce.Payload.ProductResponse;

import java.io.IOException;

public interface ProductService {


   ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getProductsByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

 ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

}
//    , Integer pageNumber, Integer pageSize, String sortBy, String sortOrder