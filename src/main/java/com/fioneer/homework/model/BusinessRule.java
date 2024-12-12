/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fioneer.homework.enums.RuleType;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author natalija
 */

@Entity
@Table(name = "business_rule")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class BusinessRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Maps the enum as a String in the databas
    private RuleType ruleType;

    private String requiredCoverageName; // Foreign key linking to the required coverage 
    
    @ManyToOne
    @JoinColumn(name = "dependent_coverage_id")
    @JsonBackReference
    private Coverage dependentCoverage; // Foreign key linking to the dependent coverage 

    private Double discountPercentage; 

}
