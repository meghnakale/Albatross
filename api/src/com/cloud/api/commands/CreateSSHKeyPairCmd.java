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
import com.cloud.api.response.SSHKeyPairResponse;
import com.cloud.user.SSHKeyPair;
import com.cloud.user.UserContext;

@Implementation(description="Create a new keypair and returns the private key", responseObject=SSHKeyPairResponse.class) 
public class CreateSSHKeyPairCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(CreateSSHKeyPairCmd.class.getName());
    private static final String s_name = "createsshkeypairresponse";
    
    
    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    
	@Parameter(name=ApiConstants.NAME, type=CommandType.STRING, required=true, description="Name of the keypair") 
	private String name;
	
    //Owner information
    @Parameter(name=ApiConstants.ACCOUNT, type=CommandType.STRING, description="an optional account for the ssh key. Must be used with domainId.")
    private String accountName;
    
    @IdentityMapper(entityTableName="domain")
    @Parameter(name=ApiConstants.DOMAIN_ID, type=CommandType.LONG, description="an optional domainId for the ssh key. If the account parameter is used, domainId must also be used.")
    private Long domainId;
    
    @IdentityMapper(entityTableName="projects")
    @Parameter(name=ApiConstants.PROJECT_ID, type=CommandType.LONG, description="an optional project for the ssh key")
    private Long projectId;
	
    
    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////
    
	public String getName() {
		return name;
	}
	
    public String getAccountName() {
        return accountName;
    }
    
    public Long getDomainId() {
        return domainId;
    }

    public Long getProjectId() {
        return projectId;
    }
	
    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
	/////////////////////////////////////////////////////
    @Override
    public long getEntityOwnerId() {
        Long accountId = finalyzeAccountId(accountName, domainId, projectId, true);
        if (accountId == null) {
            return UserContext.current().getCaller().getId();
        }
        
        return accountId;
    }   

	@Override
	public void execute() {
		SSHKeyPair r = _mgr.createSSHKeyPair(this);
		SSHKeyPairResponse response = new SSHKeyPairResponse(r.getName(), r.getFingerprint(), r.getPrivateKey());
		response.setResponseName(getCommandName());
		response.setObjectName("keypair");
		this.setResponseObject(response);
	}

	@Override
	public String getCommandName() {
		return s_name;
	}
}
