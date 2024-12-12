/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.mapper.impl;

import com.fioneer.homework.dto.CoverageDto;
import com.fioneer.homework.dto.PolicyDto;
import com.fioneer.homework.mapper.Mapper;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.model.Policy;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author natalija
 */

@Slf4j
@Component
public class PolicyMapper implements Mapper<PolicyDto, Policy>{
    
    private final InsuranceProductMapper insuranceProductMapper;
    private final CoverageMapper coverageMapper;

    public PolicyMapper(InsuranceProductMapper insuranceProductMapper, CoverageMapper coverageMapper) {
        this.insuranceProductMapper = insuranceProductMapper;
        this.coverageMapper = coverageMapper;
    }
   
    @Override
    public Policy toEntity(PolicyDto dto) {
        if (dto == null) {
            return null;
        }

        Policy policy = new Policy();
        policy.setId(dto.getId());
        policy.setFirstName(dto.getFirstName());
        policy.setLastName(dto.getLastName());
        policy.setTotalPremium(dto.getTotalPremium());

        if (dto.getInsuranceProductId()!= null) {
            InsuranceProduct insuranceProduct = new InsuranceProduct();
            insuranceProduct.setId(dto.getInsuranceProductId());
            policy.setInsuranceProduct(insuranceProduct);
        }

        if (dto.getSelectedCoverageIds() != null) {
            List<Coverage> coverages = dto.getSelectedCoverageIds().stream()
                    .map(id -> {
                        Coverage coverage = new Coverage();
                        coverage.setId(id);
                        return coverage; 
                    })
                    .collect(Collectors.toList());
             policy.setCoverages(coverages);
        }

        return policy;
    
    }

    @Override
    public PolicyDto toDto(Policy entity) {
        if (entity == null) {
            return null;
        }

        PolicyDto dto = new PolicyDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setTotalPremium(entity.getTotalPremium());

        if (entity.getInsuranceProduct() != null) {
            dto.setInsuranceProductId(entity.getInsuranceProduct().getId());
        }

        if (entity.getCoverages()!= null) {
            List<Long> coverageIds = entity.getCoverages().stream()
                    .map(Coverage::getId)
                    .collect(Collectors.toList());
            dto.setSelectedCoverageIds(coverageIds);
        }

        return dto;
    }   
}
