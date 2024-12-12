/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author natalija
 */

@Entity
@Table(name = "coverage")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    @Column(name = "benefit_amount")
    private Double benefitAmount;
    private Double premium;
    private String description;

    @ManyToOne // A coverage belongs to one insurance product
    @JoinColumn(name = "insurance_product_id") // Foreign key links coverage to its product
    @JsonBackReference
    private InsuranceProduct insuranceProduct; 

    @OneToMany(mappedBy = "dependentCoverage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BusinessRule> businessRules = new ArrayList<>(); 
  
}
