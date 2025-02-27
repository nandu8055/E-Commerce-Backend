package org.vinchenzo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinchenzo.ecommerce.model.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
