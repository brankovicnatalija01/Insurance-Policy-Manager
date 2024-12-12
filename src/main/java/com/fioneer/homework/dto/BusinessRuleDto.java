/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author natalija
 */
public class BusinessRuleDto {
    private Long id;
    private String ruleType; 
    private String requiredCoverageName;  
    
    @JsonInclude(JsonInclude.Include.NON_NULL) 
    private Double discountPercentage; 

    public BusinessRuleDto() {
    }

    public BusinessRuleDto(Long id, String ruleType, String requiredCoverageName, Double discountPercentage) {
        this.id = id;
        this.ruleType = ruleType;
        this.requiredCoverageName = requiredCoverageName;
        this.discountPercentage = discountPercentage;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRequiredCoverageName() {
        return requiredCoverageName;
    }

    public void setRequiredCoverageName(String requiredCoverageName) {
        this.requiredCoverageName = requiredCoverageName;
    }

    public Double getDiscountPercentage() {
        if ("CONDITIONAL".equalsIgnoreCase(ruleType)) {
            return null;
        }
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
