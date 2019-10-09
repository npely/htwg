#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 8.10.2019
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = nr1 nr2 nr3 nr4

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

nr1: nr1.c
	$(CC) nr1 nr1.c $(CFLAGS)

nr2: nr2.c
	$(CC) nr2 nr2.c $(CFLAGS)

nr3: nr3.c
	$(CC) nr3 nr3.c $(CFLAGS)

nr4: nr4.c
	$(CC) nr4 nr4.c $(CFLAGS)