package org.vinchenzo.ecommerce.service;

import jakarta.validation.Valid;
import org.vinchenzo.ecommerce.Payload.AddressDTO;
import org.vinchenzo.ecommerce.model.User;

import java.util.List;

public interface AddressService {

    AddressDTO addAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressByAddressId(Long userId, Long id);

    List<AddressDTO> getUserAddresses(Long userId);

    AddressDTO updateAddress(Long id, @Valid AddressDTO addressDTO, long userId);

    AddressDTO deleteAddress(Long id, long userId);


//    List<AddressDTO> getAddressesByUserId(Long userId);
}
