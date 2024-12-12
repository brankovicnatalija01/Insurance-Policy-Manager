/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fioneer.homework.mapper;

/**
 *
 * @author natalija
 * 
 * Generic interface for mapping between DTOs and Entities.
 *
 * @param <Dto> the type of the Data Transfer Object
 * @param <Entity> the type of the Entity
 * 
 */
public interface Mapper<Dto, Entity> {
    
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
 
}
