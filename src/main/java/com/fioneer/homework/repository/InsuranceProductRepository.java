/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fioneer.homework.repository;

import com.fioneer.homework.model.InsuranceProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author natalija
 */

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long>{

    List<InsuranceProduct> findByNameContainingIgnoreCase(String name);
    
    boolean existsByName(String name);
    
    boolean existsByIdAndPoliciesNotEmpty(Long id);
    
}
