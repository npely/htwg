#
# Makefile
#
# Autor: Niklas Pelz
# Erstellt am: 8.10.2019
#

CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic -pthread
STD= c11

TARGET = simple_counter sloppy_counter linked_list hash

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

simple_counter: simple_counter.c
	$(CC) simple_counter simple_counter.c $(CFLAGS)

sloppy_counter: sloppy_counter.c
	$(CC) sloppy_counter sloppy_counter.c $(CFLAGS)

linked_list: linked_list.c
	gcc -c linked_list.c -pthread

hash: hash.c
	gcc -g hash.c linked_list.o -pthread -o hash
