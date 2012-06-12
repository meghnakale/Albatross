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
import com.cloud.api.response.RegisterResponse;
import com.cloud.user.Account;
import com.cloud.user.User;

@Implementation(responseObject=RegisterResponse.class, description="This command allows a user to register for the developer API, returning a secret key and an API key. This request is made through the integration API port, so it is a privileged command and must be made on behalf of a user. It is up to the implementer just how the username and password are entered, and then how that translates to an integration API request. Both secret key and API key should be returned to the user")
public class RegisterCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(RegisterCmd.class.getName());

    private static final String s_name = "registeruserkeysresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    
    @IdentityMapper(entityTableName="user")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, required=true, description="User id")
    private Long id;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    public String getCommandName() {
        return s_name;
    }
    
    @Override
    public long getEntityOwnerId() {
        User user = _entityMgr.findById(User.class, getId());
        if (user != null) {
            return user.getAccountId();
        }

        return Account.ACCOUNT_ID_SYSTEM; // no account info given, parent this command to SYSTEM so ERROR events are tracked
    }

    @Override
    public void execute(){
        String[] keys = _accountService.createApiKeyAndSecretKey(this);
        RegisterResponse response = new RegisterResponse();
        response.setApiKey(keys[0]);
        response.setSecretKey(keys[1]);
        response.setObjectName("userkeys");
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}
