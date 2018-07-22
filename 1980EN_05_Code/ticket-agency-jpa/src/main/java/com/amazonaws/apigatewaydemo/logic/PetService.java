/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.apigatewaydemo.logic;

import com.amazonaws.apigatewaydemo.model.pet.Pet;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 *
 * @author Ming
 */
@Stateless
@Default
public class PetService implements Service {
    
    public Pet getPetById( String petId){
        
         Pet pet = new Pet();
         pet.setPetId("0001");
         return pet;
    }
    
    
}
