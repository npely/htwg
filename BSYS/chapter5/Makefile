#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 8.10.2019
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = nr1 nr2 nr3 nr4 nr5 nr6 nr7 nr8

.PHONY: all clean

all: $(TARGET)

clean:
	rm $(TARGET) $(TARGET).o

nr1: nr1.c
	$(CC) nr1 nr1.c $(CFLAGS)

nr2: nr2.c
	$(CC) nr2 nr2.c $(CFLAGS)

nr3: nr3.c
	$(CC) nr3 nr3.c $(CFLAGS)

nr4: nr4.c
	$(CC) nr4 nr4.c $(CFLAGS)

nr5: nr5.c
	$(CC) nr5 nr5.c $(CFLAGS)

nr6: nr6.c
	$(CC) nr6 nr6.c $(CFLAGS)

nr7: nr7.c
	$(CC) nr7 nr7.c $(CFLAGS)

nr8: nr8.c
	$(CC) nr8 nr8.c $(CFLAGS)