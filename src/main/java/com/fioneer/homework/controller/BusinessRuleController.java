/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fioneer.homework.controller;

import com.fioneer.homework.service.BusinessRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author natalija
 */

@RestController
public class BusinessRuleController {
    
    @Autowired
    private BusinessRuleService businessRuleService;
    
}
