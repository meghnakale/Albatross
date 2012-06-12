#!/usr/bin/env bash
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
 

workers=$1
x=$2 #CIDR - 16bytes
y=$3 #CIDR - 8 bytes

for name in `seq 1 $workers`
do
	pod_query="GET  http://10.91.30.219:8096/client/?command=createPod&zoneId=1&name=RP$name&cidr=182.$x.$y.0%2F24&startIp=182.$x.$y.2&endIp=182.$x.$y.252&gateway=182.$x.$y.1	HTTP/1.0\n\n"
	vlan_query="GET http://10.91.30.219:8096/client/?command=createVlanIpRange&vlan=untagged&zoneid=1&podId=$((name+250))&forVirtualNetwork=false&gateway=182.$y.$x.1&netmask=255.255.255.0&startip=182.$y.$x.2&endip=182.$y.$x.252        HTTP/1.0\n\n"
	so_query="GET	http://10.91.30.219:8096/client/?command=createServiceOffering&name=RP$name&displayText=RP$name&storageType=local&cpuNumber=1&cpuSpeed=1000&memory=512&offerha=false&usevirtualnetwork=false&hosttags=RP$name	HTTP/1.0\n\n"

	echo -e $pod_query | nc -v -q 20 10.91.30.219 8096
	echo -e $vlan_query | nc -v -q 20 10.91.30.219 8096
	echo -e $so_query | nc -v -q 20 10.91.30.219 8096

	let x+=1
	let y+=1
done



