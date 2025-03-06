package org.vinchenzo.ecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vinchenzo.ecommerce.Payload.AddressDTO;
import org.vinchenzo.ecommerce.exception.NoActiveUserException;
import org.vinchenzo.ecommerce.model.User;
import org.vinchenzo.ecommerce.service.AddressService;
import org.vinchenzo.ecommerce.util.AuthUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AddressService addressService;
    @Autowired
    AuthUtil authUtil;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        if(user == null) {
            throw new NoActiveUserException("Login to add the address");
        }
        AddressDTO addressSaved =addressService.addAddress(addressDTO,user);
        return new ResponseEntity<>(addressSaved, HttpStatus.CREATED);
    }
    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addressDTOS = addressService.getAllAddresses();
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Long userId = authUtil.loggedInUserId();
        AddressDTO addressDTOS = addressService.getAddressByAddressId(userId,id);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }
    @GetMapping("/address/user")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        long userId = authUtil.loggedInUserId();
        List<AddressDTO> addressDTOS = addressService.getUserAddresses(userId);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @PutMapping("/address/update/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        long userId = authUtil.loggedInUserId();
        AddressDTO savedAddressDTO = addressService.updateAddress(id,addressDTO,userId);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.OK);
    }
    @DeleteMapping("/address/delete/{id}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long id) {
        long userId = authUtil.loggedInUserId();
        AddressDTO deletedAddress = addressService.deleteAddress(id,userId);
        return new ResponseEntity<>(deletedAddress, HttpStatus.OK);
    }
}
