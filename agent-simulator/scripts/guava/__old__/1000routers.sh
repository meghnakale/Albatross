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



zoneid=$1
templateId=$2
serviceOfferingId=$3

for j in `seq 1 100`
do
	let add=0
	for i in `seq 1 10`
	do
		let account=$(($i+$add))
		echo Account Name: , $account
		query="GET	http://127.0.0.1/client/?command=deployVirtualMachine&zoneId=$1&hypervisor=Simulator&templateId=$2&serviceOfferingId=$3&account=DummyAccount$account&domainid=1	HTTP/1.0\n\n"
	  echo -e $query | nc -v -q 20 127.0.0.1 8096
	done
	let add=add+10
	sleep 60s
done
