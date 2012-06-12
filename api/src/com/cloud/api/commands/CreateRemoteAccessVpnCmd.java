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
import com.cloud.api.BaseAsyncCreateCmd;
import com.cloud.api.BaseCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.RemoteAccessVpnResponse;
import com.cloud.event.EventTypes;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.exception.NetworkRuleConflictException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.IpAddress;
import com.cloud.network.RemoteAccessVpn;

@Implementation(description="Creates a l2tp/ipsec remote access vpn", responseObject=RemoteAccessVpnResponse.class)
public class CreateRemoteAccessVpnCmd extends BaseAsyncCreateCmd {
    public static final Logger s_logger = Logger.getLogger(CreateRemoteAccessVpnCmd.class.getName());

    private static final String s_name = "createremoteaccessvpnresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////
    @IdentityMapper(entityTableName="user_ip_address")
    @Parameter(name=ApiConstants.PUBLIC_IP_ID, type=CommandType.LONG, required=true, description="public ip address id of the vpn server")
    private Long publicIpId;

    @Parameter(name="iprange", type=CommandType.STRING, required=false, description="the range of ip addresses to allocate to vpn clients. The first ip in the range will be taken by the vpn server")
    private String ipRange;
    
    @Deprecated
    @Parameter(name=ApiConstants.ACCOUNT, type=CommandType.STRING, description="an optional account for the VPN. Must be used with domainId.")
    private String accountName;

    @Deprecated
    @IdentityMapper(entityTableName="domain")
    @Parameter(name=ApiConstants.DOMAIN_ID, type=CommandType.LONG, description="an optional domainId for the VPN. If the account parameter is used, domainId must also be used.")
    private Long domainId;
    
    @Parameter(name = ApiConstants.OPEN_FIREWALL, type = CommandType.BOOLEAN, description = "if true, firewall rule for source/end pubic port is automatically created; if false - firewall rule has to be created explicitely. Has value true by default")
    private Boolean openFirewall;
    
    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public String getEntityTable() {
    	return "user_ip_address";
    }
    
    public Long getPublicIpId() {
		return publicIpId;
	}

	public String getAccountName() {
		return accountName;
	}

	public Long getDomainId() {
		return domainId;
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}
	
	public Boolean getOpenFirewall() {
	    if (openFirewall != null) {
	        return openFirewall;
	    } else {
	        return true;
	    }
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
	    IpAddress ip = _networkService.getIp(publicIpId);
	    
	    if (ip == null) {
	        throw new InvalidParameterValueException("Unable to find ip address by id=" + publicIpId);
	    }
	    
	    return ip.getAccountId();
    }

	@Override
	public String getEventDescription() {
		return "Create Remote Access VPN for account " + getEntityOwnerId() + " using public ip id=" + publicIpId;
	}

	@Override
	public String getEventType() {
		return EventTypes.EVENT_REMOTE_ACCESS_VPN_CREATE;
	}
	
    @Override
    public void create() {
        try {
            RemoteAccessVpn vpn = _ravService.createRemoteAccessVpn(publicIpId, ipRange, getOpenFirewall());
            if (vpn != null) {
                this.setEntityId(vpn.getServerAddressId());
            } else {
                throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to create remote access vpn");
            }
        } catch (NetworkRuleConflictException e) {
            s_logger.info("Network rule conflict: " + e.getMessage());
            s_logger.trace("Network Rule Conflict: ", e);
            throw new ServerApiException(BaseCmd.NETWORK_RULE_CONFLICT_ERROR, e.getMessage());
        }
    }

    @Override
    public void execute(){
        try {
            RemoteAccessVpn result = _ravService.startRemoteAccessVpn(publicIpId, getOpenFirewall());
            if (result != null) {
                RemoteAccessVpnResponse response = _responseGenerator.createRemoteAccessVpnResponse(result);
                response.setResponseName(getCommandName());
                this.setResponseObject(response);
            } else {
                throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to create remote access vpn");
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Exception: ", ex);
            throw new ServerApiException(BaseCmd.RESOURCE_UNAVAILABLE_ERROR, ex.getMessage());
        }
    }
    
    
    @Override
    public String getSyncObjType() {
        return BaseAsyncCmd.networkSyncObject;
    }

    @Override
    public Long getSyncObjId() {
        return getIp().getAssociatedWithNetworkId();
    }

    private IpAddress getIp() {
        IpAddress ip = _networkService.getIp(publicIpId);
        if (ip == null) {
            throw new InvalidParameterValueException("Unable to find ip address by id " + publicIpId);
        }
        return ip;
    }
}
