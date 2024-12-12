/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.mapper.impl;

import com.fioneer.homework.dto.BusinessRuleDto;
import com.fioneer.homework.dto.CoverageDto;
import com.fioneer.homework.mapper.Mapper;
import com.fioneer.homework.model.BusinessRule;
import com.fioneer.homework.model.Coverage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author natalija
 */

@Component
public class CoverageMapper implements Mapper<CoverageDto, Coverage>{
    
    private final BusinessRuleMapper businessRuleMapper;

    public CoverageMapper(BusinessRuleMapper businessRuleMapper) {
        this.businessRuleMapper = businessRuleMapper;
    }
    
    @Override
    public Coverage toEntity(CoverageDto dto) {
        Coverage coverage = new Coverage();
        coverage.setId(dto.getId());
        coverage.setName(dto.getName());
        coverage.setBenefitAmount(dto.getBenefitAmount());
        coverage.setPremium(dto.getPremium());
        coverage.setDescription(dto.getDescription());

        if (dto.getBusinessRules() != null) {
            List<BusinessRule> businessRules = dto.getBusinessRules().stream()
                .map(businessRuleMapper::toEntity)
                .collect(Collectors.toList());
            coverage.setBusinessRules(businessRules);

            // Set bidirectional relationship between coverage and its business rules
            businessRules.forEach(rule -> rule.setDependentCoverage(coverage));
        } else {
            coverage.setBusinessRules(new ArrayList<>());
        }

        return coverage;
    }

    @Override
    public CoverageDto toDto(Coverage entity) {
        CoverageDto dto = new CoverageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBenefitAmount(entity.getBenefitAmount());
        dto.setPremium(entity.getPremium());
        dto.setDescription(entity.getDescription());

        if (entity.getBusinessRules() != null) {
            List<BusinessRuleDto> businessRules = entity.getBusinessRules().stream()
                .map(businessRuleMapper::toDto)
                .collect(Collectors.toList());
            dto.setBusinessRules(businessRules);
        }

        return dto;
    }
    
}
