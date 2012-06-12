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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseListProjectAndAccountResourcesCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.EventResponse;
import com.cloud.api.response.ListResponse;
import com.cloud.event.Event;

@Implementation(description="A command to list events.", responseObject=EventResponse.class)
public class ListEventsCmd extends BaseListProjectAndAccountResourcesCmd {
    public static final Logger s_logger = Logger.getLogger(ListEventsCmd.class.getName());

    private static final String s_name = "listeventsresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="event")
    @Parameter(name=ApiConstants.ID, type=CommandType.LONG, description="the ID of the event")
    private Long id;
    
    @Parameter(name=ApiConstants.DURATION, type=CommandType.INTEGER, description="the duration of the event")
    private Integer duration;

    @Parameter(name=ApiConstants.END_DATE, type=CommandType.DATE, description="the end date range of the list you want to retrieve (use format \"yyyy-MM-dd\" or the new format \"yyyy-MM-dd HH:mm:ss\")")
    private Date endDate;

    @Parameter(name=ApiConstants.ENTRY_TIME, type=CommandType.INTEGER, description="the time the event was entered")
    private Integer entryTime;

    @Parameter(name=ApiConstants.LEVEL, type=CommandType.STRING, description="the event level (INFO, WARN, ERROR)")
    private String level;

    @Parameter(name=ApiConstants.START_DATE, type=CommandType.DATE, description="the start date range of the list you want to retrieve (use format \"yyyy-MM-dd\" or the new format \"yyyy-MM-dd HH:mm:ss\")")
    private Date startDate;

    @Parameter(name=ApiConstants.TYPE, type=CommandType.STRING, description="the event type (see event types)")
    private String type;

    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }
    
    public Integer getDuration() {
        return duration;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getEntryTime() {
        return entryTime;
    }

    public String getLevel() {
        return level;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getType() {
        return type;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public void execute(){
        List<? extends Event> result = _mgr.searchForEvents(this);
        ListResponse<EventResponse> response = new ListResponse<EventResponse>();
        List<EventResponse> eventResponses = new ArrayList<EventResponse>();
        for (Event event : result) {
            eventResponses.add(_responseGenerator.createEventResponse(event));
        }

        response.setResponses(eventResponses);
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }
}
