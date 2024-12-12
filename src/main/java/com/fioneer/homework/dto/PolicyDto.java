/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.dto;

import java.util.List;

/**
 *
 * @author natalija
 */
public class PolicyDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long insuranceProductId;
    private List<Long> selectedCoverageIds;
    private Double totalPremium;

    public PolicyDto() {
    }

    public PolicyDto(Long id, String firstName, String lastName, Long insuranceProductId, List<Long> selectedCoverageIds, Double totalPremium) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.insuranceProductId = insuranceProductId;
        this.selectedCoverageIds = selectedCoverageIds;
        this.totalPremium = totalPremium;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getInsuranceProductId() {
        return insuranceProductId;
    }

    public void setInsuranceProductId(Long insuranceProductId) {
        this.insuranceProductId = insuranceProductId;
    }

    public List<Long> getSelectedCoverageIds() {
        return selectedCoverageIds;
    }

    public void setSelectedCoverageIds(List<Long> selectedCoverageIds) {
        this.selectedCoverageIds = selectedCoverageIds;
    }


    public Double getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(Double totalPremium) {
        this.totalPremium = totalPremium;
    }
    
    
    
}
