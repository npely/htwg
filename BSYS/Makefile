#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 8.10.2019
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = p1 p2 p3 p4

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

p1: p1.c
	$(CC) p1 p1.c $(CFLAGS)

p2: p2.c
	$(CC) p2 p2.c $(CFLAGS)

p3: p3.c
	$(CC) p3 p3.c $(CFLAGS)

p4: p4.c
	$(CC) p4 p4.c $(CFLAGS)
