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
import com.cloud.api.response.SuccessResponse;
import com.cloud.event.EventTypes;
import com.cloud.exception.InvalidParameterValueException;
import com.cloud.projects.Project;
import com.cloud.user.UserContext;

@Implementation(description="Deletes a project", responseObject=SuccessResponse.class, since="3.0.0")
public class DeleteProjectCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(DeleteProjectCmd.class.getName());

    private static final String s_name = "deleteprojectresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="projects")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, required=true, description="id of the project to be deleted")
    private Long id;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////


    public Long geId() {
        return id;
    }

    @Override
    public String getCommandName() {
        return s_name;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public void execute(){
        UserContext.current().setEventDetails("Project Id: " + id);
        boolean result = _projectService.deleteProject(id);
        if (result) {
            SuccessResponse response = new SuccessResponse(getCommandName());
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to delete project");
        }
    }
    
    @Override
    public String getEventType() {
        return EventTypes.EVENT_PROJECT_DELETE;
    }
    
    @Override
    public String getEventDescription() {
        return  "Deleting project: " + id;
    }
    
    @Override
    public long getEntityOwnerId() {
        Project project= _projectService.getProject(id);
        //verify input parameters
        if (project == null) {
            throw new InvalidParameterValueException("Unable to find project by id " + id);
        } 
        
        return _projectService.getProjectOwner(id).getId(); 
    }
    
}