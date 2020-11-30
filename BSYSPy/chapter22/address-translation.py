#! /usr/bin/env python3

output = open('valgrind-output.txt', 'r')
vpn = open('page-numbers.txt', 'w')

for line in output:
    vpn.write(str(int(line, 16) >> 12) + "\n")

output.close()
vpn.close()