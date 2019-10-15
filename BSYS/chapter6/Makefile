CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = system_measurement1 system_measurement100

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

$(TARGET): $(TARGET).c
	$(CC) $(TARGET) $(TARGET).c $(CFLAGS)

system_measurement1: system_measurement1.c
	$(CC) system_measurement1 system_measurement1.c $(CFLAGS)

system_measurement100: system_measurement100.c
	$(CC) system_measurement100 system_measurement100.c $(CFLAGS)