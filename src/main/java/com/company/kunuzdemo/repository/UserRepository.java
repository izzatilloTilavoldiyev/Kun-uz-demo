package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("from users u where not u.deleted and u.id = :userId")
    Optional<User> getUserByID(@Param("userId") UUID id);
    @Query("from users u where not u.deleted")
    Page<User> findAllUsers(Pageable pageable);

    @Query("select u from users u where u.role = :userRole")
    Page<User> filterByRole(@Param("userRole") UserRole userRole, Pageable pageable);

    @Query("from users u where u.deleted = true ")
    Page<User> findAllDeleted(Pageable pageable);

    @Query("select u from users u where u.email =:email and not u.deleted")
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Query(value = """
           select * from users u where lower(u.email) like 
           lower(concat(:email, '%') and not u.deleted)
           """, nativeQuery = true)
    Page<User> searchByEmail(String email, Pageable pageable);

}
