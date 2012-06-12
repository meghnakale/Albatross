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
package com.cloud.server;


public class ManagementServerSimulatorImpl extends ManagementServerExtImpl {
    @Override
    public String[] getApiConfig() {
        String[] apis = super.getApiConfig();
        String[] newapis = new String[apis.length + 1];
        for (int i = 0; i < apis.length; i++) {
            newapis[i] = apis[i];
        }
        
        newapis[apis.length] = "commands-simulator.properties";
        return newapis;
    }
}
