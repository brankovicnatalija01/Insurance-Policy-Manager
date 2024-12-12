/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *
 * @author natalija
 */

@Entity
@Table(name = "insurance_product")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class InsuranceProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(nullable = false)
    private String type;
    
    @Column(unique = true, nullable = false)  // Ensures the name is unique and cannot be null
    private String name;
    
    private String description;
    
    @OneToMany(mappedBy = "insuranceProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Coverage> coverages; // An insurance product can have multiple coverages

    @OneToMany(mappedBy = "insuranceProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Policy> policies; // An insurance product can have multiple policies
    
   
}
