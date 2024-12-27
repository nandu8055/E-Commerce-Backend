package org.vinchenzo.ecommerce.Payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank
    @Size(min = 3, message = "product name must contain at-least 3 characters")
    private String productName;
    private String image;
    @NotBlank
    @Size(min = 6, message = "product description must contain at-least 6 characters")
    private String productDescription;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;


}
