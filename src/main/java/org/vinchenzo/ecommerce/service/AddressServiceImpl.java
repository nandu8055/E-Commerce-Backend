package org.vinchenzo.ecommerce.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinchenzo.ecommerce.Payload.AddressDTO;
import org.vinchenzo.ecommerce.exception.APIException;
import org.vinchenzo.ecommerce.exception.ResourceNotFoundException;
import org.vinchenzo.ecommerce.model.Address;
import org.vinchenzo.ecommerce.model.User;
import org.vinchenzo.ecommerce.repository.AddressRepository;
import org.vinchenzo.ecommerce.repository.UserRepository;
import org.vinchenzo.ecommerce.util.AuthUtil;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public AddressDTO addAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> address = addressRepository.findAll();
        return address.stream().map(a -> modelMapper.map(a, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressByAddressId(Long userId, Long id) {
        Optional<Address> address = addressRepository.findById(id);

        if (address.isPresent()) {
            Long addressOwnerId = address.get().getUser().getUserId();
            if (addressOwnerId.equals(userId)) {
                return modelMapper.map(address.get(), AddressDTO.class);
            }
            throw new APIException("Current user does not have access to this address");
        }
        throw new APIException("Address not found");
    }

    @Override
    public List<AddressDTO> getUserAddresses(Long userId) {
        List<Address> addressList = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId))
                .getAddresses();

        return addressList.stream().map(a -> modelMapper.map(a, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO, long userId) {
        Address addressFromDatabase = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", id));        if(addressFromDatabase.getUser().getUserId() != userId){
            throw new APIException("Current user does not have access to this address");
        }
        addressFromDatabase.setCity(addressDTO.getCity());
        addressFromDatabase.setState(addressDTO.getState());
        addressFromDatabase.setStreet(addressDTO.getStreet());
        addressFromDatabase.setBuildingName(addressDTO.getBuildingName());
        addressFromDatabase.setCountry(addressDTO.getCountry());
        addressFromDatabase.setPinCode(addressDTO.getPinCode());
        Address savedAddress = addressRepository.save(addressFromDatabase);
        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(id));
        user.getAddresses().add(savedAddress);
        userRepository.save(user);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO deleteAddress(Long id, long userId) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", id));
        if(address.getUser().getUserId() != userId){
            throw new APIException("Current user does not have access to this address");
        }
        User user = address.getUser();
        user.getAddresses().removeIf(a -> a.getAddressId().equals(id));
        userRepository.save(user);
        addressRepository.delete(address);
        return modelMapper.map(address, AddressDTO.class);
    }
}

