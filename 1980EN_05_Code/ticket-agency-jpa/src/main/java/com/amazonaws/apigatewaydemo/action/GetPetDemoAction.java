/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.amazonaws.apigatewaydemo.action;

import com.amazonaws.apigatewaydemo.logic.Service;
import com.amazonaws.apigatewaydemo.configuration.ExceptionMessages;
import com.amazonaws.apigatewaydemo.exception.BadRequestException;
import com.amazonaws.apigatewaydemo.exception.InternalErrorException;
import com.amazonaws.apigatewaydemo.logic.PetService;
import com.amazonaws.apigatewaydemo.model.action.GetPetRequest;
import com.amazonaws.apigatewaydemo.model.pet.Pet;
import com.google.gson.JsonObject;

/**
 * Action that extracts a pet from the data store based on the given petId
 * <p/>
 * GET to /pets/{petId}
 */
public class GetPetDemoAction extends AbstractDemoAction {
  

    @Override
    public String handle(JsonObject request, Service service) throws BadRequestException, InternalErrorException {
      

        GetPetRequest input = getGson().fromJson(request, GetPetRequest.class);

        if (input == null ||
                input.getPetId() == null ||
                input.getPetId().trim().equals("")) {
           System.out.println("Invalid input passed to " + this.getClass().getName());
            throw new BadRequestException(ExceptionMessages.EX_INVALID_INPUT);
        }

       
        Pet pet;
        try {
            
           PetService petService = (PetService) service;
           pet = petService.getPetById("00001");
           
        } catch (final Exception e) {
           System.out.println("Error while fetching pet with id " + input.getPetId() + "\n" + e.getMessage());
            throw new InternalErrorException(ExceptionMessages.EX_DAO_ERROR);
        }

        return getGson().toJson(pet);
    }
}
