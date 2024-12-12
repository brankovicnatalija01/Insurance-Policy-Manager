/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.service.impl;

import com.fioneer.homework.dto.PolicyDto;
import com.fioneer.homework.enums.RuleType;
import com.fioneer.homework.mapper.impl.PolicyMapper;
import com.fioneer.homework.model.BusinessRule;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.model.Policy;
import com.fioneer.homework.repository.CoverageRepository;
import com.fioneer.homework.repository.InsuranceProductRepository;
import com.fioneer.homework.repository.PolicyRepository;
import com.fioneer.homework.service.BusinessRuleValidationService;
import com.fioneer.homework.service.PolicyService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author natalija
 */

@Slf4j
@Service
public class PolicyServiceImpl implements PolicyService{
    
    private final PolicyRepository policyRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final CoverageRepository coverageRepository;
    private final BusinessRuleValidationService businessRuleValidationService;
    private final PolicyMapper policyMapper;

    public PolicyServiceImpl(PolicyRepository policyRepository, InsuranceProductRepository insuranceProductRepository, CoverageRepository coverageRepository, BusinessRuleValidationService businessRuleValidationService, PolicyMapper policyMapper) {
        this.policyRepository = policyRepository;
        this.insuranceProductRepository = insuranceProductRepository;
        this.coverageRepository = coverageRepository;
        this.businessRuleValidationService = businessRuleValidationService;
        this.policyMapper = policyMapper;
    }

    @Override
    public PolicyDto createPolicy(PolicyDto dto) {
    
        Policy policy = policyMapper.toEntity(dto);

        InsuranceProduct insuranceProduct = insuranceProductRepository.findById(policy.getInsuranceProduct().getId())
                .orElseThrow(() -> {
                    log.error("Insurance product with ID '{}' not found", policy.getInsuranceProduct().getId());
                    return new IllegalArgumentException("Insurance product not found");
                });

        List<Coverage> coverages = coverageRepository.findAllById(
                policy.getCoverages().stream()
                        .map(Coverage::getId)
                        .collect(Collectors.toList()));
        
        for (Coverage coverage : coverages) {
            if (!coverage.getInsuranceProduct().getId().equals(insuranceProduct.getId())) {
                log.error("Coverage '{}' does not belong to the selected insurance product '{}'", 
                          coverage.getName(), insuranceProduct.getName());
                throw new IllegalArgumentException("Coverage " + coverage.getName() +
                        " does not belong to the selected insurance product.");
            }
    }
        
        List<String> validationErrors = businessRuleValidationService.validate(insuranceProduct);
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + String.join(", ", validationErrors));
        }
        
        for (Coverage coverage : coverages) {
            for (BusinessRule rule : coverage.getBusinessRules()) {
                if (!businessRuleValidationService.isRuleValid(rule, coverages)) {
                    throw new IllegalArgumentException("Validation failed: " + businessRuleValidationService.getValidationErrorMessage(rule));
                }
            }
    }

        double totalPremium = calculateTotalPremium(coverages);
        policy.setTotalPremium(totalPremium);
        policy.setCoverages(coverages);

        Policy savedPolicy = policyRepository.save(policy);
        log.info("Policy created successfully with ID '{}', total premium: {}", savedPolicy.getId(), totalPremium);

        return policyMapper.toDto(savedPolicy);
    }

    private double calculateTotalPremium(List<Coverage> coverages) {
        double totalPremium = 0.0;

        for (Coverage coverage : coverages) {
            double premium = coverage.getPremium();

            for (BusinessRule rule : coverage.getBusinessRules()) {
                if (rule.getRuleType() == RuleType.DISCOUNT) {
                    boolean isRequiredCoverageIncluded = coverages.stream()
                            .anyMatch(c -> c.getName().equals(rule.getRequiredCoverageName()));

                    if (isRequiredCoverageIncluded) {
                        premium *= (1 - rule.getDiscountPercentage() / 100);
                    }
                }
            }

            totalPremium += premium;
        }

        return totalPremium;
    }

    @Override
    public List<PolicyDto> getAllPolicies() {
        List<PolicyDto> policies = policyRepository.findAll()
                .stream()
                .map(policyMapper::toDto)
                .toList();
        
        log.info("Fetched {} policies.", policies.size());
        return policies;
    }

   
    @Override
    public void deletePolicy(Long id) {
        if (!policyRepository.existsById(id)) {
            log.error("Policy with ID '{}' not found", id);
            throw new IllegalArgumentException("Policy not found");
        }
        
        policyRepository.deleteById(id);
        log.info("Policy with ID '{}' deleted successfully.", id);
    }

    @Override
    public List<PolicyDto> searchPolicies(String firstName, String lastName, String insuranceProductName) {
        List<Policy> policies = policyRepository.searchPolicies(firstName, lastName, insuranceProductName);
        log.info("Found {} policies matching the criteria.", policies.size());
        
        return policies.stream()
                .map(policyMapper::toDto)
                .collect(Collectors.toList());
    }
    
    
}
