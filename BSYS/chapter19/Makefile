#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 8.10.2019
#

CC = gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic -O0
STD = c11

TARGET = tlb

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

tlb: tlb.c
	$(CC) tlb tlb.c $(CFLAGS)