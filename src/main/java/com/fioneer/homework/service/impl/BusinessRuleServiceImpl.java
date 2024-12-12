/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.service.impl;

import com.fioneer.homework.repository.BusinessRuleRepository;
import com.fioneer.homework.service.BusinessRuleService;
import org.springframework.stereotype.Service;

/**
 *
 * @author natalija
 */

@Service
public class BusinessRuleServiceImpl implements BusinessRuleService{
    private final BusinessRuleRepository businessRuleRepository;

    public BusinessRuleServiceImpl(BusinessRuleRepository businessRuleRepository) {
        this.businessRuleRepository = businessRuleRepository;
    }
    
    
    
}
