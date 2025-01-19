package org.vinchenzo.ecommerce.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinchenzo.ecommerce.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(@NotBlank String userName);

    boolean existsByUserName(@NotBlank String username);

    boolean existsByEmail(@NotBlank @Email String email);
}
