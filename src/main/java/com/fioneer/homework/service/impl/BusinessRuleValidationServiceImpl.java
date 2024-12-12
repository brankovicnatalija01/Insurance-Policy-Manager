/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.service.impl;

import com.fioneer.homework.model.BusinessRule;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.service.BusinessRuleValidationService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author natalija
 */

@Slf4j
@Service
public class BusinessRuleValidationServiceImpl implements BusinessRuleValidationService{

    @Override
    public List<String> validate(InsuranceProduct insuranceProduct) {
        List<String> errors = new ArrayList<>();

        for (Coverage coverage : insuranceProduct.getCoverages()) {
            log.debug("Validating coverage '{}'", coverage.getName());
            List<BusinessRule> businessRules = coverage.getBusinessRules();
            
            if (businessRules != null) { 
                for (BusinessRule rule : businessRules) {
                    if (!isRuleValid(rule, insuranceProduct.getCoverages())) { // Check if the rule is valid; add error message if it isn't
                        log.warn("Validation failed for rule '{}", rule.getRuleType());
                        errors.add(getValidationErrorMessage(rule));
                    }
                }
            }
        }

        return errors;
    }

    @Override
    public boolean isRuleValid(BusinessRule businessRule, List<Coverage> selectedCoverages) {
        switch (businessRule.getRuleType()) {
            //check if the required coverage is included in the selected coverages
            case CONDITIONAL:
                return selectedCoverages.stream()
                        .anyMatch(coverage -> coverage.getName().equals(businessRule.getRequiredCoverageName()));
            case DISCOUNT:
                return true;
            default:
                log.warn("Unknown rule type '{}'. Assuming valid.", businessRule.getRuleType());
                return true; 
        }
    }

    @Override
    public String getValidationErrorMessage(BusinessRule businessRule) { // Generates a descriptive error message for an invalid business rule
        String message;
        
        switch (businessRule.getRuleType()) {
            case CONDITIONAL:
                message = "Coverage " + businessRule.getDependentCoverage().getName() + 
                          " requires " + businessRule.getRequiredCoverageName();
                break;
            case DISCOUNT:
                message = "Coverage " + businessRule.getDependentCoverage().getName() + 
                          " has a discount if " + businessRule.getRequiredCoverageName() + " is included";
                break;
            default:
                message = "Unknown rule type";
                break;
        }
        
        log.debug("Generated validation error message: {}", message);
        return message;
    }
    
}
