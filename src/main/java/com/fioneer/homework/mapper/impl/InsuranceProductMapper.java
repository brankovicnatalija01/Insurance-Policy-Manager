/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.mapper.impl;

import com.fioneer.homework.dto.CoverageDto;
import com.fioneer.homework.dto.InsuranceProductDto;
import com.fioneer.homework.dto.InsuranceProductDto;
import com.fioneer.homework.mapper.Mapper;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author natalija
 */

@Component
public class InsuranceProductMapper implements Mapper<InsuranceProductDto, InsuranceProduct>{

    private final CoverageMapper coverageMapper;

    public InsuranceProductMapper(CoverageMapper coverageMapper) {
        this.coverageMapper = coverageMapper;
    }
    
    
    @Override
    public InsuranceProduct toEntity(InsuranceProductDto dto) {
        InsuranceProduct product = new InsuranceProduct();
        product.setId(dto.getId());
        product.setType(dto.getType());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());

        if (dto.getCoverages() != null) {
            List<Coverage> coverages = dto.getCoverages().stream()
                .map(coverageMapper::toEntity)
                .collect(Collectors.toList());
            product.setCoverages(coverages);
            
            // Set bidirectional relationship between product and its coverages
            coverages.forEach(coverage -> coverage.setInsuranceProduct(product));
        }

        return product;
    }

    @Override
    public InsuranceProductDto toDto(InsuranceProduct entity) {
        InsuranceProductDto dto = new InsuranceProductDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        if (entity.getCoverages() != null) {
            List<CoverageDto> coverages = entity.getCoverages().stream()
                .map(coverageMapper::toDto)
                .collect(Collectors.toList());
            dto.setCoverages(coverages);
        }

        return dto;
    }
    
}
