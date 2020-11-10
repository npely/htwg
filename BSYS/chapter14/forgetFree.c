#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void) {
    char* c_pointer = (char*) malloc(sizeof(char) * 10);
    printf("Address of pointer: %p\n", c_pointer);
    return 0;
}