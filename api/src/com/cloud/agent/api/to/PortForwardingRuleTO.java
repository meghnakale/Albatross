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

import com.cloud.network.rules.FirewallRule;
import com.cloud.network.rules.PortForwardingRule;
import com.cloud.utils.net.NetUtils;

/**
 * PortForwardingRuleTO specifies one port forwarding rule.
 * 
 *
 */
public class PortForwardingRuleTO extends FirewallRuleTO {
    String dstIp;
    int[] dstPortRange;
    
    protected PortForwardingRuleTO() {
        super();
    }
    
    public PortForwardingRuleTO(PortForwardingRule rule, String srcVlanTag, String srcIp) {
        super(rule, srcVlanTag, srcIp);
        this.dstIp = rule.getDestinationIpAddress().addr();
        this.dstPortRange = new int[] { rule.getDestinationPortStart(), rule.getDestinationPortEnd() };
    }
    
    protected PortForwardingRuleTO(long id, String srcIp, int srcPortStart, int srcPortEnd, String dstIp, int dstPortStart, int dstPortEnd, String protocol, boolean revoked, boolean brandNew) {
        super(id, srcIp,null, protocol, srcPortStart, srcPortEnd, revoked, brandNew, FirewallRule.Purpose.PortForwarding, null,0,0);
        this.dstIp = dstIp;
        this.dstPortRange = new int[] { dstPortStart, dstPortEnd };
    }

    public String getDstIp() {
        return dstIp;
    }

    public int[] getDstPortRange() {
        return dstPortRange;
    }

    public String getStringDstPortRange() {
        return NetUtils.portRangeToString(dstPortRange);
    }
    
}
