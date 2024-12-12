/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fioneer.homework.repository;

import com.fioneer.homework.model.Policy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author natalija
 */

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long>{
    
    List<Policy> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Policy> findByInsuranceProduct_NameContainingIgnoreCase(String productName);
    
    boolean existsByInsuranceProductId(Long insuranceProductId);
    
    @Query("SELECT p FROM Policy p " +
           "WHERE (:firstName IS NULL OR p.firstName LIKE %:firstName%) " +
           "AND (:lastName IS NULL OR p.lastName LIKE %:lastName%) " +
           "AND (:productName IS NULL OR p.insuranceProduct.name LIKE %:productName%)")
    List<Policy> searchPolicies(@Param("firstName") String firstName,
                                @Param("lastName") String lastName,
                                @Param("productName") String productName);
    
}
