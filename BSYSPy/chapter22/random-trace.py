#! /usr/bin/env python

import random

random.seed(random.random())
numaddrs = 20
maxpages = 10
addrsList = []

for i in range(numaddrs):
    address = int(maxpages * random.random())
    addrsList.append(address)

print(addrsList)
