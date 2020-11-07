#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 06.11.2020
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = memory-user

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

memory-user: memory-user.c
	$(CC) memory-user memory-user.c $(CFLAGS)