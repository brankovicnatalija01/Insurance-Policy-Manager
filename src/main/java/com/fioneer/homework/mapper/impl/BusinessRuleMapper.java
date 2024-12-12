/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.mapper.impl;

import com.fioneer.homework.dto.BusinessRuleDto;
import com.fioneer.homework.enums.RuleType;
import com.fioneer.homework.mapper.Mapper;
import com.fioneer.homework.model.BusinessRule;
import org.springframework.stereotype.Component;

/**
 *
 * @author natalija
 */

@Component
public class BusinessRuleMapper implements Mapper<BusinessRuleDto, BusinessRule>{
    
 @Override
    public BusinessRule toEntity(BusinessRuleDto dto) {
        if (dto == null) {
            return null;
        }

        BusinessRule entity = new BusinessRule();
        entity.setId(dto.getId());

        if (dto.getRuleType() != null) {
            try {
                entity.setRuleType(RuleType.valueOf(dto.getRuleType().toUpperCase())); // Convert string to ENUM
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid ruleType: " + dto.getRuleType());
            }
        }

        entity.setRequiredCoverageName(dto.getRequiredCoverageName());
        
        if (entity.getRuleType() != RuleType.CONDITIONAL) {
            entity.setDiscountPercentage(dto.getDiscountPercentage());
        }

        return entity;
    }

    @Override
    public BusinessRuleDto toDto(BusinessRule entity) {
        if (entity == null) {
            return null;
        }

        BusinessRuleDto dto = new BusinessRuleDto();
        dto.setId(entity.getId());

        if (entity.getRuleType() != null) {
            dto.setRuleType(entity.getRuleType().name());
        }

        dto.setRequiredCoverageName(entity.getRequiredCoverageName());
        dto.setDiscountPercentage(entity.getDiscountPercentage());

        return dto;
    }
    
}
