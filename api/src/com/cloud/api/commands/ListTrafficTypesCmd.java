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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseListCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.ListResponse;
import com.cloud.api.response.ProviderResponse;
import com.cloud.api.response.TrafficTypeResponse;
import com.cloud.network.PhysicalNetworkTrafficType;
import com.cloud.user.Account;


@Implementation(description="Lists traffic types of a given physical network.", responseObject=ProviderResponse.class, since="3.0.0")
public class ListTrafficTypesCmd extends BaseListCmd {
    public static final Logger s_logger = Logger.getLogger(ListTrafficTypesCmd.class.getName());
    private static final String _name = "listtraffictypesresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    @IdentityMapper(entityTableName="physical_network")
    @Parameter(name=ApiConstants.PHYSICAL_NETWORK_ID, type=CommandType.LONG, required=true, description="the Physical Network ID")
    private Long physicalNetworkId;
    
    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////
    
    public void setPhysicalNetworkId(Long physicalNetworkId) {
        this.physicalNetworkId = physicalNetworkId;
    }

    public Long getPhysicalNetworkId() {
        return physicalNetworkId;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////
    @Override
    public String getCommandName() {
        return _name;
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }
    
    @Override
    public void execute(){
        List<? extends PhysicalNetworkTrafficType> trafficTypes = _networkService.listTrafficTypes(getPhysicalNetworkId());
        ListResponse<TrafficTypeResponse> response = new ListResponse<TrafficTypeResponse>();
        List<TrafficTypeResponse> trafficTypesResponses = new ArrayList<TrafficTypeResponse>();
        for (PhysicalNetworkTrafficType trafficType : trafficTypes) {
            TrafficTypeResponse trafficTypeResponse = _responseGenerator.createTrafficTypeResponse(trafficType);
            trafficTypesResponses.add(trafficTypeResponse);
        }

        response.setResponses(trafficTypesResponses);
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}
