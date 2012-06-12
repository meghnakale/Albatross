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

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseAsyncCmd;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.IPAddressResponse;
import com.cloud.api.response.SuccessResponse;
import com.cloud.event.EventTypes;
import com.cloud.exception.ConcurrentOperationException;
import com.cloud.exception.InsufficientCapacityException;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.Network;

@Implementation(description="Restarts the network; includes 1) restarting network elements - virtual routers, dhcp servers 2) reapplying all public ips 3) reapplying loadBalancing/portForwarding rules", responseObject=IPAddressResponse.class)
public class RestartNetworkCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(RestartNetworkCmd.class.getName());
    private static final String s_name = "restartnetworkresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    
    @IdentityMapper(entityTableName="networks")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, required=true, description="The id of the network to restart.")
    private Long id;

    @Parameter(name=ApiConstants.CLEANUP, type=CommandType.BOOLEAN, required=false, description="If cleanup old network elements")
    private Boolean cleanup;


    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////
    
    public Long getNetworkId() {
        Network network = _networkService.getNetwork(id);
        if (network == null) {
            throw new InvalidParameterValueException("Unable to find network by id " + id);
        } else {
            return network.getId();
        }
    }
    
    public Boolean getCleanup() {
        if (cleanup != null) {
            return cleanup;
        }
        return true;
    }


    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////


    @Override
    public String getCommandName() {
        return s_name;
    }

    public static String getResultObjectName() {
        return "addressinfo";
    }
    
    @Override
    public void execute() throws ResourceUnavailableException, ResourceAllocationException, ConcurrentOperationException, InsufficientCapacityException {
        boolean result = _networkService.restartNetwork(this, getCleanup());
        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to restart network");
        }
    }
    
    @Override
    public String getSyncObjType() {
        return BaseAsyncCmd.networkSyncObject;
    }

    @Override
    public Long getSyncObjId() {
        return id;
    }
    
    public String getEventDescription() {
        return  "Restarting network: " + getNetworkId();
    }
    
    @Override
    public String getEventType() {
        return EventTypes.EVENT_NETWORK_RESTART;
    }
    
    @Override
    public long getEntityOwnerId() {
        Network network = _networkService.getNetwork(id);
        if (network == null) {
            throw new InvalidParameterValueException("Networkd id=" + id + " doesn't exist");
        } else {
            return _networkService.getNetwork(id).getAccountId();
        }
    }
}
