CC= gcc -o
CFLAGS = -g -W -Wall -std=$(STD) -pedantic
STD= c11

TARGET = system_measurement context_measurement

.PHONY: all clean

all: $(TARGET)

clean:
	$(RM) $(TARGET) $(TARGET).o

system_measurement: system_measurement.c
	$(CC) system_measurement system_measurement.c $(CFLAGS)

context_measurement: context_measurement.c
	$(CC) context_measurement context_measurement.c $(CFLAGS)
