#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {

    if (argc == 2) {
        unsigned long million = 1000000;
        int expectedMegaBytes = atoi(argv[1]);
        unsigned long expectedMegaBytesInBytes = expectedMegaBytes * million;

        char *c_array;

        c_array = (char*) malloc(sizeof(char) * expectedMegaBytesInBytes);

        if (c_array == NULL) {
            printf("malloc of size %lu failed!\n", expectedMegaBytesInBytes);
            exit(-1);
        }

        printf("PID: %d\n", getpid());
        printf("Size of allocated bytes: %lu\n", expectedMegaBytesInBytes);

        unsigned long i = 0;

        while (i < expectedMegaBytesInBytes) {
            c_array[i] = 'a';
            //printf("%lu, %c\n", i, c_array[i]);
            i++;
            if(i == expectedMegaBytesInBytes) {
                i = 0;
            }
        }

        free(c_array);
    }
    else if (argc > 2) {
        printf("Too many arguments supplied\n");
    }
    else {
        printf("One argument expected:\n How much megabytes of memory you want to use\n");
    }
}