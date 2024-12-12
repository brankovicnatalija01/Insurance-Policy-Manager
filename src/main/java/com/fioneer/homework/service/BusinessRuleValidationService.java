/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fioneer.homework.service;

import com.fioneer.homework.model.BusinessRule;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import java.util.List;

/**
 *
 * @author natalija
 * 
 * Service interface for validating business rules associated with insurance products and their coverages
 */
public interface BusinessRuleValidationService {
    List<String> validate(InsuranceProduct product);
    boolean isRuleValid(BusinessRule rule, List<Coverage> selectedCoverages);
    String getValidationErrorMessage(BusinessRule rule);
    
}
