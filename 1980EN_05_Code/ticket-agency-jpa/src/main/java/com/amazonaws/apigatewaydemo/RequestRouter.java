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
package com.amazonaws.apigatewaydemo;

import com.amazonaws.apigatewaydemo.action.DemoAction;
import com.amazonaws.apigatewaydemo.exception.BadRequestException;
import com.amazonaws.apigatewaydemo.exception.InternalErrorException;
import com.amazonaws.apigatewaydemo.logic.Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This class contains the main event handler for the Lambda function.
 */
@Path("/pets")
public class RequestRouter {
    @Inject  Service petService;
    
    @POST
    @Consumes( {MediaType.APPLICATION_JSON})
    @Produces( {MediaType.APPLICATION_JSON})
    public  String lambdaHandler(InputStream request) throws BadRequestException, InternalErrorException {
       

        JsonParser parser = new JsonParser();
        JsonObject inputObj;
        try {
            inputObj = parser.parse(IOUtils.toString(request)).getAsJsonObject();
        } catch (IOException e) {
           System.out.println("Error while reading request\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }

        if (inputObj == null || inputObj.get("action") == null || inputObj.get("action").getAsString().trim().equals("")) {
           System.out.println("Invald inputObj, could not find action parameter");
            throw new BadRequestException("Could not find action value in request");
        }

        String actionClass = inputObj.get("action").getAsString();
        DemoAction action;

        try {
            action = DemoAction.class.cast(Class.forName(actionClass).newInstance());
        } catch (final InstantiationException e) {
           System.out.println("Error while instantiating action class\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        } catch (final IllegalAccessException e) {
           System.out.println("Illegal access while instantiating action class\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        } catch (final ClassNotFoundException e) {
           System.out.println("Action class could not be found\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }

        if (action == null) {
           System.out.println("Action class is null");
            throw new BadRequestException("Invalid action class");
        }

        JsonObject body = null;
        if (inputObj.get("body") != null) {
            body = inputObj.get("body").getAsJsonObject();
        }

        String output = action.handle(body, petService);

        return output;
    }
}
