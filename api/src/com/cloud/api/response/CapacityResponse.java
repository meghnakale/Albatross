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

public class CapacityResponse extends BaseResponse {
    @SerializedName(ApiConstants.TYPE) @Param(description="the capacity type")
    private Short capacityType;

    @SerializedName(ApiConstants.ZONE_ID) @Param(description="the Zone ID")
    private IdentityProxy zoneId = new IdentityProxy("data_center");

    @SerializedName(ApiConstants.ZONE_NAME) @Param(description="the Zone name")
    private String zoneName;

    @SerializedName(ApiConstants.POD_ID) @Param(description="the Pod ID")
    private IdentityProxy podId = new IdentityProxy("host_pod_ref");

    @SerializedName("podname") @Param(description="the Pod name")
    private String podName;
    
    @SerializedName(ApiConstants.CLUSTER_ID) @Param(description="the Cluster ID")
    private IdentityProxy clusterId = new IdentityProxy("cluster");

    @SerializedName("clustername") @Param(description="the Cluster name")
    private String clusterName;

    @SerializedName("capacityused") @Param(description="the capacity currently in use")
    private Long capacityUsed;

    @SerializedName("capacitytotal") @Param(description="the total capacity available")
    private Long capacityTotal;

    @SerializedName("percentused") @Param(description="the percentage of capacity currently in use")
    private String percentUsed;

    public Short getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Short capacityType) {
        this.capacityType = capacityType;
    }

    public Long getZoneId() {
        return zoneId.getValue();
    }

    public void setZoneId(Long zoneId) {
        this.zoneId.setValue(zoneId);
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Long getPodId() {
        return podId.getValue();
    }

    public void setPodId(Long podId) {
        this.podId.setValue(podId);
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public Long getClusterId() {
		return clusterId.getValue();
	}

	public void setClusterId(Long clusterId) {
		this.clusterId.setValue(clusterId);
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public Long getCapacityUsed() {
        return capacityUsed;
    }

    public void setCapacityUsed(Long capacityUsed) {
        this.capacityUsed = capacityUsed;
    }

    public Long getCapacityTotal() {
        return capacityTotal;
    }

    public void setCapacityTotal(Long capacityTotal) {
        this.capacityTotal = capacityTotal;
    }

    public String getPercentUsed() {
        return percentUsed;
    }

    public void setPercentUsed(String percentUsed) {
        this.percentUsed = percentUsed;
    }
}
