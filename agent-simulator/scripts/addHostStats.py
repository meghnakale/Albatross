#!/usr/bin/env python
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
from datetime import datetime
import sys
import matplotlib.cbook as cbook
import matplotlib.image as image
import matplotlib.pyplot as plt

def formGraph(list_timedelta):
    """ Draw a graph of the performance of host add response """
    plt.plot(list(map(lambda x:x.seconds, list_timedelta)))
    plt.ylabel("time(s) to add host")
    plt.xlabel("number of hosts")
    plt.title("Add Host Performance")
    plt.grid(True)
    plt.show()


if __name__=='__main__':
    time_file=open(sys.argv[1], 'r')
    timelist=[]
    diffs=[]
    for line in time_file.readlines():
        try:
            timelist.append(datetime.strptime(line.strip(), "%d %b %Y %H:%M:%S"))
        except ValueError:
            print "Unable to parse:",line

    stime=timelist[:-1]
    btime=timelist[1:]

    diffs=list(map(lambda x,y: y - x,stime,btime))
    
    formGraph(diffs)

    

