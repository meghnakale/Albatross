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
package com.cloud.api.response;

import com.cloud.api.ApiConstants;
import com.cloud.utils.IdentityProxy;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProjectInvitationResponse extends BaseResponse implements ControlledEntityResponse{
    @SerializedName(ApiConstants.ID) @Param(description="the id of the invitation")
    private IdentityProxy id = new IdentityProxy("project_invitations");
    
    @SerializedName(ApiConstants.PROJECT_ID) @Param(description="the id of the project")
    private IdentityProxy projectId = new IdentityProxy("projects");
    
    @SerializedName(ApiConstants.PROJECT) @Param(description="the name of the project")
    private String projectName;
    
    @SerializedName(ApiConstants.DOMAIN_ID) @Param(description="the domain id the project belongs to")
    private IdentityProxy domainId = new IdentityProxy("domain");
    
    @SerializedName(ApiConstants.DOMAIN) @Param(description="the domain name where the project belongs to")
    private String domainName;
    
    @SerializedName(ApiConstants.ACCOUNT) @Param(description="the account name of the project's owner")
    private String accountName;
    
    @SerializedName(ApiConstants.EMAIL) @Param(description="the email the invitation was sent to")
    private String email;
    
    @SerializedName(ApiConstants.STATE) @Param(description="the invitation state")
    private String invitationState;
    
    public void setId(Long id) {
        this.id.setValue(id);
    }

    public void setProjectId(Long projectId) {
        this.projectId.setValue(projectId);
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDomainId(Long domainId) {
        this.domainId.setValue(domainId);
    }

    public void setDomainName(String domain) {
        this.domainName = domain;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setInvitationState(String invitationState) {
        this.invitationState = invitationState;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
