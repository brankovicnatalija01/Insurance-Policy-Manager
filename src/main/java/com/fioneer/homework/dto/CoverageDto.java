/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

/**
 *
 * @author natalija
 */
public class CoverageDto {
    private Long id;
    
    @NotBlank(message = "Coverage name is required.")
    private String name;
    
    @Positive(message = "Benefit amount must be greater than 0.")
    private Double benefitAmount;
    
    @Positive(message = "Premium must be greater than 0.")
    private Double premium;
    
    private String description;
    private List<BusinessRuleDto> businessRules;

    public CoverageDto() {
    }

    public CoverageDto(Long id, String name, Double benefitAmount, Double premium, String description, List<BusinessRuleDto> businessRules) {
        this.id = id;
        this.name = name;
        this.benefitAmount = benefitAmount;
        this.premium = premium;
        this.description = description;
        this.businessRules = businessRules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBenefitAmount() {
        return benefitAmount;
    }

    public void setBenefitAmount(Double benefitAmount) {
        this.benefitAmount = benefitAmount;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BusinessRuleDto> getBusinessRules() {
        return businessRules;
    }

    public void setBusinessRules(List<BusinessRuleDto> businessRules) {
        this.businessRules = businessRules;
    }
    
    
}
