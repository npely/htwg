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