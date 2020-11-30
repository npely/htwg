#!/usr/bin/env python

import matplotlib.pyplot as plt
import numpy as np

x = np.arange(3, 12)

y = [1921.40, 1830.14, 1922.48, 1922.37, 1925.42, 1850.89, 144.83, 61.98, 31.02]

plt.plot(x, y)
plt.xlabel('Memory in GB')
plt.ylabel('Average Bandwidth in MB/s')
plt.show()
