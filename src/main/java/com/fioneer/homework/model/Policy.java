/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name = "policy")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Policy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "total_premium")
    private Double totalPremium;
    
    @ManyToOne // A policy belongs to one insurance product
    @JoinColumn(name = "insurance_product_id") // Foreign key links policy to its product
    @JsonBackReference
    private InsuranceProduct insuranceProduct;

    @ManyToMany // Many-to-many relationship between policies and coverages
    @JoinTable(
        name = "policy_coverage",
        joinColumns = @JoinColumn(name = "policy_id"),
        inverseJoinColumns = @JoinColumn(name = "coverage_id")
    )
    private List<Coverage> coverages;

}
