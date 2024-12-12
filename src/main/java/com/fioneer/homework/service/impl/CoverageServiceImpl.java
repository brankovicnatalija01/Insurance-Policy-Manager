/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.service.impl;

import com.fioneer.homework.repository.CoverageRepository;
import com.fioneer.homework.service.CoverageService;
import org.springframework.stereotype.Service;

/**
 *
 * @author natalija
 */

@Service
public class CoverageServiceImpl implements CoverageService{
    private final CoverageRepository coverageRepository;

    public CoverageServiceImpl(CoverageRepository coverageRepository) {
        this.coverageRepository = coverageRepository;
    }
    
    
    
}
