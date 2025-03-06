package org.vinchenzo.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AddressId;

    @NotBlank
    @Size(min=5,message = "Street name must be at-least 5 characters")
    private String street;

    @NotBlank
    @Size(min = 3,message = "building name must be at-least 3 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 3,message = "city name must be at-least 3 characters")
    private String city;

    @NotBlank
    @Size(min = 2,message = "state name must be at-least 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2,message = "country name must be at-least 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6,message = "pin code  must be at-least 5 characters")
    private String pinCode;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(String street, String buildingName, String city, String state, String country, String pinCode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
    }
}
