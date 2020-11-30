#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 8.10.2019
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = mem

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

mem: mem.c
	$(CC) mem mem.c $(CFLAGS)
