#!/usr/bin/python

iliste = [70,67,65,63,60,57,55,52,50,47,43,40,37,33,30,27,22,18,14,10]

for x in range(0, 20):
        with open("./WellenformenVersuch1/" + str(iliste[x]) + ".csv", "r+") as f:
            text = f.read()
            f.seek(0)
            f.truncate()
            f.write(text.replace(',', '.'))




