#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Oct 21 16:57:33 2019

@author: niklas13
"""

import numpy as np
import matplotlib.pyplot as plt


liste = []

s = "./WellenformenVersuch1/{}.csv"

# Abstände, also x Achse

listeAbstand = [70,67,65,63,60,57,55,52,50,47,43,40,37,33,30,27,22,18,14,10]

# Daten die ins Array eingelesen werden
for i in range(len(listeAbstand)):
    data = (np.genfromtxt(s.format(str(listeAbstand[i])), max_rows=1000, delimiter=";", skip_header = 1000, usecols=([1])))
    # Durchschnitt der Werte als Wert der y Achse
    liste.insert(i, np.average(data))
    
# Mittelwert von x 
xM = 0

for i in range(len(listeAbstand)):
    
    xM += listeAbstand[i]

xM = xM / len(listeAbstand)

# Mittelwert von y
yM = 0

for i in range(len(liste)):
    
    yM += liste[i]
    
yM = yM / len(liste)


# Werte für die lineare Regression
lRo = 0
lRu = 0

for i in range(0, 20):
    
    lRo += ((listeAbstand[i] - xM) * (liste[i] - yM))
    lRu += ((listeAbstand[i] - xM) * (listeAbstand[i] - xM))

a = lRo / lRu
  
b = yM - a * xM

############################################ lineare Regressionsfunktion

x = np.linspace(10, 70, 60)

y = a * x + b

############################################ lineare Regressionsfunktion rücklogarithimiert

x2 = np.linspace(10, 70, 60)

y2 = (np.e ** b) * (x ** a)

############################################ Graphen plotten
    
# Plotten normal
#print(data)
plt.xlabel("Abstand in cm")
plt.ylabel("Voltage in V")
plt.title("SSS Abstandssensor")
plt.plot(listeAbstand, liste, '-b')
plt.grid(True)
plt.show()

# Plotten lineare Regression
#print(data)
plt.xlabel("Abstand in cm")
plt.ylabel("Voltage in V")
plt.title("SSS Abstandssensor")
plt.plot(x2, y2, '-y')
plt.grid(True)
plt.show()

# Plotten log
#print(data)
plt.xlabel("Abstand in cm")
plt.ylabel("Voltage in V")
plt.title("SSS Abstandssensor")
plt.plot(np.log(listeAbstand), np.log(liste), '-r')
plt.grid(True)
plt.show()

# Plotten lineare Regression log
#print(data)
plt.xlabel("Abstand in cm")
plt.ylabel("Voltage in V")
plt.title("SSS Abstandssensor")
plt.plot(x, y, '-g')
plt.grid(True)
plt.show()

