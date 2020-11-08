#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void) {
    int* data = (int*) malloc(sizeof(int) * 100);

    free(&data[50]);

    printf("%d\n", data[50]);
}