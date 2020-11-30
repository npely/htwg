#!/usr/bin/env python

import matplotlib.pyplot as plt
import numpy as np

x = [1, 2, 3, 4, 5]

yRand = [50.23, 78.09, 86.42, 90.37, 92.73]
yFIFO = [50.23, 78.24, 87.56, 91.33, 93.35]
yLRU = [50.23, 84.22, 89.47, 93.05, 94.64]
yOpt = [50.23, 84.83, 92.25, 94.93, 96.18]

plt.plot(x, yRand, 'blue')
plt.plot(x, yFIFO, 'green')
plt.plot(x, yLRU, 'red')
plt.plot(x, yOpt, 'yellow')
plt.legend(["Random", "FIFO", "LRU", "Optimal"])
plt.xlabel('Cache size')
plt.ylabel('Hitrate in %')
plt.show()