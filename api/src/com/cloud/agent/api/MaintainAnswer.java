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
package com.cloud.agent.api;

public class MaintainAnswer extends Answer {
	boolean willMigrate;
	
    public MaintainAnswer() {
    }
    
    public MaintainAnswer(MaintainCommand cmd) {
        this(cmd, true, null);
    }
    
    public MaintainAnswer(MaintainCommand cmd, boolean willMigrate) {
        this(cmd, true, null);
        this.willMigrate = willMigrate;
    }
    
    public MaintainAnswer(MaintainCommand cmd, String details) {
        this(cmd, true, details);
    }
    
    public MaintainAnswer(MaintainCommand cmd, boolean result, String details) {
        super(cmd, result, details);
        this.willMigrate = true;
    }
    
    public boolean getMigrate() {
    	return this.willMigrate;
    }
}
