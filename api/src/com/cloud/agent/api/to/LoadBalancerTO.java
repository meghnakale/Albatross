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
package com.cloud.agent.api.to;


import java.util.List;
import com.cloud.utils.Pair;
import com.cloud.network.lb.LoadBalancingRule.LbDestination;
import com.cloud.network.lb.LoadBalancingRule.LbStickinessPolicy;


public class LoadBalancerTO {
    String srcIp;
    int srcPort;
    String protocol;
    String algorithm;    
    boolean revoked;
    boolean alreadyAdded;
    DestinationTO[] destinations;
    private StickinessPolicyTO[] stickinessPolicies;
    final static int MAX_STICKINESS_POLICIES = 1; 
   
    public LoadBalancerTO (String srcIp, int srcPort, String protocol, String algorithm, boolean revoked, boolean alreadyAdded, List<LbDestination> destinations) {
        this.srcIp = srcIp;
        this.srcPort = srcPort;
        this.protocol = protocol;
        this.algorithm = algorithm;     
        this.revoked = revoked;
        this.alreadyAdded = alreadyAdded;
        this.destinations = new DestinationTO[destinations.size()];
        this.stickinessPolicies = null;
        int i = 0;
        for (LbDestination destination : destinations) {
            this.destinations[i++] = new DestinationTO(destination.getIpAddress(), destination.getDestinationPortStart(), destination.isRevoked(), false);
        }
    }
    
    public LoadBalancerTO (String srcIp, int srcPort, String protocol, String algorithm, boolean revoked, boolean alreadyAdded, List<LbDestination> arg_destinations, List<LbStickinessPolicy> stickinessPolicies) {
        this(srcIp, srcPort, protocol, algorithm, revoked, alreadyAdded, arg_destinations);
        this.stickinessPolicies = null;
        if (stickinessPolicies != null && stickinessPolicies.size()>0) {
            this.stickinessPolicies = new StickinessPolicyTO[MAX_STICKINESS_POLICIES];
            int index = 0;
            for (LbStickinessPolicy stickinesspolicy : stickinessPolicies) {
                if (!stickinesspolicy.isRevoked()) {
                    this.stickinessPolicies[index] = new StickinessPolicyTO(stickinesspolicy.getMethodName(), stickinesspolicy.getParams());
                    index++;
                    if (index == MAX_STICKINESS_POLICIES) break;
                }
            }
            if (index == 0) this.stickinessPolicies = null;
        }
    }
    
    
    protected LoadBalancerTO() {
    }
    
    public String getSrcIp() {
        return srcIp;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getProtocol() {
        return protocol;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public boolean isAlreadyAdded() {
        return alreadyAdded;
    }
    
    public StickinessPolicyTO[] getStickinessPolicies() {
        return stickinessPolicies;
    }
    
    public DestinationTO[] getDestinations() {
        return destinations;
    }
    
    public static class StickinessPolicyTO {
        private String _methodName;
        private List<Pair<String, String>> _paramsList;

        public String getMethodName() {
            return _methodName;
        }

        public List<Pair<String, String>> getParams() {
            return _paramsList;
        }

        public StickinessPolicyTO(String methodName, List<Pair<String, String>> paramsList) {
            this._methodName = methodName;
            this._paramsList = paramsList;
        }
    }
    
    public static class DestinationTO {
        String destIp;
        int destPort;
        boolean revoked;
        boolean alreadyAdded;
        public DestinationTO(String destIp, int destPort, boolean revoked, boolean alreadyAdded) {
            this.destIp = destIp;
            this.destPort = destPort;
            this.revoked = revoked;
            this.alreadyAdded = alreadyAdded;
        }
        
        protected DestinationTO() {
        }
        
        public String getDestIp() {
            return destIp;
        }
        
        public int getDestPort() {
            return destPort;
        }
        
        public boolean isRevoked() {
            return revoked;
        }

        public boolean isAlreadyAdded() {
            return alreadyAdded;
        }
    }

}
