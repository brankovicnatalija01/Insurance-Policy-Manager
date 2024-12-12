/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fioneer.homework.service;

import com.fioneer.homework.dto.InsuranceProductDto;
import java.util.List;

/**
 *
 * @author natalija
 */
public interface InsuranceProductService {
    
    InsuranceProductDto createProduct(InsuranceProductDto dto);
    List<InsuranceProductDto> getAllProducts();
    
    InsuranceProductDto updateProduct(Long id, InsuranceProductDto dto);
    void deleteProduct(Long id);
    
    List<InsuranceProductDto> searchInsuranceProducts(String name);
    InsuranceProductDto getProductDetails(Long id);
 
}
