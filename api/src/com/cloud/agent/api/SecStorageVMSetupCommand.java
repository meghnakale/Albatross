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

public class SecStorageVMSetupCommand extends Command {
	String [] allowedInternalSites = new String[0];
	String copyUserName;
	String copyPassword;
	
	public SecStorageVMSetupCommand() {
		super();
	}
	
	@Override
	public boolean executeInSequence() {
		return true;
	}

	public String[] getAllowedInternalSites() {
		return allowedInternalSites;
	}

	public void setAllowedInternalSites(String[] allowedInternalSites) {
		this.allowedInternalSites = allowedInternalSites;
	}

	public String getCopyUserName() {
		return copyUserName;
	}

	public void setCopyUserName(String copyUserName) {
		this.copyUserName = copyUserName;
	}

	public String getCopyPassword() {
		return copyPassword;
	}

	public void setCopyPassword(String copyPassword) {
		this.copyPassword = copyPassword;
	}

}
