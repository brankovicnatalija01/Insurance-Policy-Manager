/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.service.impl;


import com.fioneer.homework.dto.InsuranceProductDto;
import com.fioneer.homework.enums.RuleType;
import com.fioneer.homework.mapper.impl.InsuranceProductMapper;
import com.fioneer.homework.model.BusinessRule;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.repository.InsuranceProductRepository;
import com.fioneer.homework.repository.PolicyRepository;
import com.fioneer.homework.service.BusinessRuleValidationService;
import com.fioneer.homework.service.InsuranceProductService;
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
public class InsuranceProductServiceImpl implements InsuranceProductService{
    
    private final InsuranceProductRepository insuranceProductRepository;
    private final InsuranceProductMapper insuranceProductMapper;
    private final BusinessRuleValidationService businessRuleValidationService;
    private final PolicyRepository policyRepository;

    public InsuranceProductServiceImpl(InsuranceProductRepository insuranceProductRepository, InsuranceProductMapper insuranceProductMapper, BusinessRuleValidationService businessRuleValidationService, PolicyRepository policyRepository) {
        this.insuranceProductRepository = insuranceProductRepository;
        this.insuranceProductMapper = insuranceProductMapper;
        this.businessRuleValidationService = businessRuleValidationService;
        this.policyRepository = policyRepository;
    }

    @Override
    public InsuranceProductDto createProduct(InsuranceProductDto dto) {
        InsuranceProduct product = insuranceProductMapper.toEntity(dto);

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            log.error("Failed to create product. Reason: Product name cannot be empty or blank.");
            throw new IllegalArgumentException("Product name cannot be empty or blank.");
        }

        if (dto.getCoverages() == null || dto.getCoverages().isEmpty()) {
            log.error("Failed to create product. Reason: Product must have at least one coverage.");
            throw new IllegalArgumentException("Product must have at least one coverage.");
        }
        
        if (insuranceProductRepository.existsByName(dto.getName())) {
            log.error("Failed to create product. Reason: Product with the same name already exists: {}", dto.getName());
            throw new IllegalArgumentException("Product with the same name already exists: " + dto.getName());
        }
        
        // Validate individual coverages
        for (Coverage coverage : product.getCoverages()) {
            if (coverage.getPremium() < 0) {
                log.error("Failed to create product. Reason: Premium for coverage '{}' cannot be negative.", coverage.getName());
                throw new IllegalArgumentException("Premium for coverage '" + coverage.getName() + "' cannot be negative.");
            }

            if (coverage.getBenefitAmount() < 0) {
                log.error("Failed to create product. Reason: Benefit amount for coverage '{}' cannot be negative.", coverage.getName());
                throw new IllegalArgumentException("Benefit amount for coverage '" + coverage.getName() + "' cannot be negative.");
            }

            // Validate business rules for the entire product
            for (BusinessRule rule : coverage.getBusinessRules()) {
                if (rule.getRuleType() == RuleType.DISCOUNT) {
                    if (rule.getDiscountPercentage() < 0 || rule.getDiscountPercentage() > 100) {
                        log.error("Failed to create product. Reason: Discount percentage for business rule in coverage '{}' must be between 0 and 100.", coverage.getName());
                        throw new IllegalArgumentException("Discount percentage for business rule in coverage '" 
                                + coverage.getName() + "' must be between 0 and 100.");
                    }
                }
            }
        }

        List<String> validationErrors = businessRuleValidationService.validate(product);
        if (!validationErrors.isEmpty()) {
            log.error("Failed to create product. Validation errors: {}", validationErrors);
            throw new IllegalArgumentException("Validation failed: " + String.join(", ", validationErrors));
        }
        
        InsuranceProduct savedProduct = insuranceProductRepository.save(product);
        log.info("Insurance product '{}' created successfully with ID: {}", dto.getName(), savedProduct.getId());
        return insuranceProductMapper.toDto(savedProduct);
    }
    
    @Override
    public List<InsuranceProductDto> getAllProducts() {
        List<InsuranceProductDto> products = insuranceProductRepository.findAll().stream()
                .map(insuranceProductMapper::toDto)
                .collect(Collectors.toList());
        
        log.info("Fetched {} insurance products.", products.size());
        return products;
    }

    @Override
    public InsuranceProductDto updateProduct(Long id, InsuranceProductDto dto) {
        // Check for the existence of linked policies
        if (policyRepository.existsByInsuranceProductId(id)) {
            log.error("Cannot update product with ID: {} because it has issued policies.", id);
            throw new IllegalStateException("Cannot modify insurance product because there are issued policies for it.");
        }

        InsuranceProduct existingProduct = insuranceProductRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Failed to update product. Insurance product with ID: {} not found.", id);
                return new IllegalArgumentException("Insurance product not found");
            });

        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setType(dto.getType());

        InsuranceProduct updatedProduct = insuranceProductRepository.save(existingProduct);
        log.info("Insurance product with ID: {} updated successfully.", id);

        return insuranceProductMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!insuranceProductRepository.existsById(id)) {
            log.error("Failed to delete product. Reason: Product with ID: {} does not exist.", id);
            throw new IllegalArgumentException("Cannot delete insurance product because it does not exist.");
        }

        // Check for the existence of linked policies
        if (policyRepository.existsByInsuranceProductId(id)) {
            log.error("Cannot delete product with ID: {} because it has issued policies.", id);
            throw new IllegalStateException("Cannot delete insurance product because there are issued policies for it.");
        }
        insuranceProductRepository.deleteById(id);
        log.info("Insurance product with ID: {} deleted successfully.", id);
    }

    @Override
    public List<InsuranceProductDto> searchInsuranceProducts(String name) {
       List<InsuranceProduct> products = insuranceProductRepository.findByNameContainingIgnoreCase(name);
       log.info("Found {} insurance products matching the search criteria.", products.size());
       return products.stream()
               .map(insuranceProductMapper::toDto)
               .collect(Collectors.toList());
    }

    @Override
    public InsuranceProductDto getProductDetails(Long id) {
        InsuranceProduct product = insuranceProductRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Insurance product with ID: {} not found.", id);
                    return new IllegalArgumentException("Insurance product not found");
                });

        log.info("Fetched details for insurance product with ID: {}", id);
        return insuranceProductMapper.toDto(product);
    }
 
}

