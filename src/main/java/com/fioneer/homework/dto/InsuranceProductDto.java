/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 *
 * @author natalija
 */
public class InsuranceProductDto {
    private Long id;
    
    @NotBlank(message = "Type is required.")
    @NotEmpty
    private String type;
    
    @NotBlank(message = "Name is required.")
    @NotEmpty
    private String name;
    private String description;
    
    @NotEmpty(message = "At least one coverage is required.")
    private List<CoverageDto> coverages;

    public InsuranceProductDto() {
    }

    public InsuranceProductDto(Long id, String type, String name, String description, List<CoverageDto> coverages) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.coverages = coverages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CoverageDto> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<CoverageDto> coverages) {
        this.coverages = coverages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    
}
