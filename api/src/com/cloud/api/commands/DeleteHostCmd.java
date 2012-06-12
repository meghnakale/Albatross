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
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.SuccessResponse;
import com.cloud.user.Account;

@Implementation(description = "Deletes a host.", responseObject = SuccessResponse.class)
public class DeleteHostCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(DeleteHostCmd.class.getName());

    private static final String s_name = "deletehostresponse";

    // ///////////////////////////////////////////////////
    // ////////////// API parameters /////////////////////
    // ///////////////////////////////////////////////////

    @IdentityMapper(entityTableName="host")
    @Parameter(name = ApiConstants.ID, type = CommandType.LONG, required = true, description = "the host ID")
    private Long id;

    @Parameter(name = ApiConstants.FORCED, type = CommandType.BOOLEAN, description = "Force delete the host. All HA enabled vms running on the host will be put to HA; HA disabled ones will be stopped")
    private Boolean forced;
    
    @Parameter(name = ApiConstants.FORCED_DESTROY_LOCAL_STORAGE, type = CommandType.BOOLEAN, description = "Force destroy local storage on this host. All VMs created on this local storage will be destroyed")
    private Boolean forceDestroyLocalStorage;

    // ///////////////////////////////////////////////////
    // ///////////////// Accessors ///////////////////////
    // ///////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public boolean isForced() {
        return (forced != null) ? forced : false;
    }
    
    public boolean isForceDestoryLocalStorage() {
        return (forceDestroyLocalStorage != null) ? forceDestroyLocalStorage : true;
    }

    // ///////////////////////////////////////////////////
    // ///////////// API Implementation///////////////////
    // ///////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }

    @Override
    public void execute() {
        boolean result = _resourceService.deleteHost(getId(), isForced(), isForceDestoryLocalStorage());
        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to delete host");
        }
    }
}