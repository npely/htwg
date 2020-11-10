#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 06.11.2020
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = null forgetFree array100 array100WithFree

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

null: null.c
	$(CC) null null.c $(CFLAGS)

forgetFree: forgetFree.c
	$(CC) forgetFree forgetFree.c $(CFLAGS)

array100: array100.c
	$(CC) array100 array100.c $(CFLAGS)

array100WithFree: array100WithFree.c
	$(CC) array100WithFree array100WithFree.c $(CFLAGS)