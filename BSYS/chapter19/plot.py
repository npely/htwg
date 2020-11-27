#!/usr/bin/env python

import matplotlib.pyplot as plt
import numpy as np

data = np.genfromtxt("tlb.csv", delimiter=",", names=["x", "y"])

plt.plot(data["x"], data["y"])

plt.xscale('log')

plt.xlabel("Number of Pages")
plt.ylabel("Time in ns")

plt.show()