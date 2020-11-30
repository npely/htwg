#!/usr/bin/env python

import matplotlib.pyplot as plt
import numpy as np

x = np.arange(0, 10)

y1000 = [892, 1965, 1866, 1648, 1953, 1796, 1894, 1783, 1864, 1849]

y4000 = [899, 1890, 1917, 1921, 1920, 1885, 1944, 1926, 1949, 1951]

y8000 = [892, 99, 102, 111, 108, 114, 114, 115, 114, 114]

y12000 = [612, 115, 116, 116, 117, 118, 120, 121, 118, 118]

plt.plot(x, y1000, 'blue')
plt.plot(x, y4000, 'green')
plt.plot(x, y8000, 'red')
plt.plot(x, y12000, 'yellow')
plt.legend(["1000MB", "4000MB", "8000MB", "12000MB"])
plt.xlabel('Loops')
plt.ylabel('Bandwidth in MB/s')
plt.show()