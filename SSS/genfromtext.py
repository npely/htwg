#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Oct 21 16:57:33 2019

@author: niklas13
"""

import numpy as np
import matplotlib.pyplot as plt


liste = []

x = "./WellenformenVersuch1/{}.csv"

# Abstände, also x Achse!!!
z = ["70","67","65","63","60","57","55","52","50","47"
     ,"43","40","37","33","30","27","22","18","14","10"]

iliste = [70,67,65,63,60,57,55,52,50,47,43,40,37,33,30,27,22,18,14,10]

# Daten die ins Array eingelesen werden
for i in range(0, 20):
    data = (np.genfromtxt(x.format(z[i]), max_rows=1000, delimiter=";", skip_header = 1000, usecols=([1])))
    # Durchschnitt als Wert der y Achse
    #np.average(data)
    #plt.plot(int(z[i]), np.average(data))
    liste.insert(i, np.average(data))
    
# Plotten
print(data)
plt.xlabel("Abstand in cm")
plt.ylabel("Voltage in V")
plt.title("SSS Abstandssensor")
plt.plot(iliste, liste, '.b')
plt.grid(True)
plt.show()