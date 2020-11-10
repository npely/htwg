#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void) {
    int* pointer = NULL;
    int value;
    //dereference it
    value = *pointer;
    printf("%d", value);
    return 0;
}