// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.api.commands;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseAsyncCmd;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.ProviderResponse;
import com.cloud.async.AsyncJob;
import com.cloud.event.EventTypes;
import com.cloud.network.PhysicalNetworkServiceProvider;
import com.cloud.user.Account;

@Implementation(description="Updates a network serviceProvider of a physical network", responseObject=ProviderResponse.class, since="3.0.0")
public class UpdateNetworkServiceProviderCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(UpdateNetworkServiceProviderCmd.class.getName());

    private static final String s_name = "updatenetworkserviceproviderresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    @Parameter(name=ApiConstants.STATE, type=CommandType.STRING, description="Enabled/Disabled/Shutdown the physical network service provider")
    private String state;
    
    @IdentityMapper(entityTableName="physical_network_service_providers")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, required=true, description="network service provider id")
    private Long id;    

    @Parameter(name=ApiConstants.SERVICE_LIST, type=CommandType.LIST, collectionType = CommandType.STRING, description="the list of services to be enabled for this physical network service provider")
    private List<String> enabledServices;
    
    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public String getState() {
        return state;
    }
    
    private Long getId() {
        return id;
    }    
    
    public List<String> getEnabledServices() {
        return enabledServices;
    }    
    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }
    
    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }
    
    @Override
    public void execute(){
        PhysicalNetworkServiceProvider result = _networkService.updateNetworkServiceProvider(getId(), getState(), getEnabledServices());
        if (result != null) {
            ProviderResponse response = _responseGenerator.createNetworkServiceProviderResponse(result);
            response.setResponseName(getCommandName());
            this.setResponseObject(response);
        }else {
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to update service provider");
        }
    }

    @Override
    public String getEventType() {
        return EventTypes.EVENT_SERVICE_PROVIDER_UPDATE;
    }

    @Override
    public String getEventDescription() {
        return  "Updating physical network ServiceProvider: " + getId();
    }
    
    @Override
    public AsyncJob.Type getInstanceType() {
        return AsyncJob.Type.PhysicalNetworkServiceProvider;
    }

}
