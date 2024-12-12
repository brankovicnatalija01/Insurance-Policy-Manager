/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.controller;

import com.fioneer.homework.dto.InsuranceProductDto;
import com.fioneer.homework.dto.InsuranceProductDto;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.service.InsuranceProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author natalija
 */

@RestController
@RequestMapping("/api/products")
@Validated
public class InsuranceProductController {
    
    @Autowired
    private InsuranceProductService insuranceProductService;
    
    @GetMapping("/all")
    public ResponseEntity<List<InsuranceProductDto>> getAllProducts() {
        return ResponseEntity.ok(insuranceProductService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<InsuranceProductDto>> searchProducts(@RequestParam String name) {
        List<InsuranceProductDto> products = insuranceProductService.searchInsuranceProducts(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceProductDto> getProductDetails(@PathVariable Long id) {
        InsuranceProductDto product = insuranceProductService.getProductDetails(id);
        return ResponseEntity.ok(product);
    }
 
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody InsuranceProductDto dto) {
            InsuranceProductDto createdProduct = insuranceProductService.createProduct(dto);
            return ResponseEntity.ok(createdProduct);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceProductDto> updateProduct(@PathVariable Long id, @RequestBody InsuranceProductDto dto) {
        InsuranceProductDto updatedProduct = insuranceProductService.updateProduct(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        insuranceProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
