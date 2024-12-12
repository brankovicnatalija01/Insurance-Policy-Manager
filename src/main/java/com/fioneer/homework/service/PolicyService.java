/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fioneer.homework.service;

import com.fioneer.homework.dto.PolicyDto;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author natalija
 */


public interface PolicyService {
    
    PolicyDto createPolicy(PolicyDto dto);
    public List<PolicyDto> getAllPolicies();
    
    List<PolicyDto> searchPolicies(String firstName, String lastName, String insuranceProductName);
    void deletePolicy(Long id);
    
}
