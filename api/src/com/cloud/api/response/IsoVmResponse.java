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

import com.cloud.utils.IdentityProxy;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

public class IsoVmResponse extends BaseResponse {
    @SerializedName("id") @Param(description="the ISO ID")
    private IdentityProxy id = new IdentityProxy("vm_template");

    @SerializedName("name") @Param(description="the ISO name")
    private String name;

    @SerializedName("displaytext") @Param(description="the ISO display text")
    private String displayText;

    @SerializedName("bootable") @Param(description="true if the ISO is bootable, false otherwise")
    private Boolean bootable;

    @SerializedName("isfeatured") @Param(description="true if this template is a featured template, false otherwise")
    private Boolean featured;

    @SerializedName("ostypeid") @Param(description="the ID of the OS type for this template.")
    private IdentityProxy osTypeId = new IdentityProxy("guest_os");

    @SerializedName("ostypename") @Param(description="the name of the OS type for this template.")
    private String osTypeName;

    @SerializedName("virtualmachineid") @Param(description="id of the virtual machine")
    private IdentityProxy virtualMachineId = new IdentityProxy("vm_instance");

    @SerializedName("vmname") @Param(description="name of the virtual machine")
    private String virtualMachineName;

    @SerializedName("vmdisplayname") @Param(description="display name of the virtual machine")
    private String virtualMachineDisplayName;

    @SerializedName("vmstate") @Param(description="state of the virtual machine")
    private String virtualMachineState;

    
    public Long getOsTypeId() {
        return osTypeId.getValue();
    }

    public void setOsTypeId(Long osTypeId) {
        this.osTypeId.setValue(osTypeId);
    }

    public String getOsTypeName() {
        return osTypeName;
    }

    public void setOsTypeName(String osTypeName) {
        this.osTypeName = osTypeName;
    }

    public Long getId() {
        return id.getValue();
    }

    public void setId(Long id) {
        this.id.setValue(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Boolean isBootable() {
        return bootable;
    }

    public void setBootable(Boolean bootable) {
        this.bootable = bootable;
    }

    public Boolean isFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Long getVirtualMachineId() {
        return virtualMachineId.getValue();
    }

    public void setVirtualMachineId(Long virtualMachineId) {
        this.virtualMachineId.setValue(virtualMachineId);
    }

    public String getVirtualMachineName() {
        return virtualMachineName;
    }

    public void setVirtualMachineName(String virtualMachineName) {
        this.virtualMachineName = virtualMachineName;
    }

    public String getVirtualMachineDisplayName() {
        return virtualMachineDisplayName;
    }

    public void setVirtualMachineDisplayName(String virtualMachineDisplayName) {
        this.virtualMachineDisplayName = virtualMachineDisplayName;
    }

    public String getVirtualMachineState() {
        return virtualMachineState;
    }

    public void setVirtualMachineState(String virtualMachineState) {
        this.virtualMachineState = virtualMachineState;
    }
}
