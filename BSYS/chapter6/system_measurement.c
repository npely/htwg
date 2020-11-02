// // Program for measuring the cost of a system call
// //
// // Author: Niklas Pelz
// // Date: 13.10.2019
//
#define _POSIX_C_SOURCE 199309L
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>


int main (void) {

    struct timespec start, stop;
    const int cycles = 1000000;
    const unsigned long bil = 1000000000;
    unsigned long timeTakenBySysCall;
    unsigned long timeTakenByForLoop;

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &start) < 0) {
        printf("clock fail\n");
        exit(1);
    }

    for (int i = 0; i < cycles; ++i) {
        getpid();
    }

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &stop) < 0) {
        printf("clock fail\n");
        exit(1);
    }

    struct timespec start2, stop2;

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &start2) < 0) {
        printf("clock fail\n");
        exit(1);
    }

    for(int j = 0; j < cycles; ++j)
    {

    }

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &stop2) < 0) {
        printf("clock fail\n");
        exit(1);
    }

    if (start.tv_nsec > stop.tv_nsec)
    {
        timeTakenBySysCall = (((stop.tv_sec - 1) - start.tv_sec) * bil) + ((stop.tv_nsec + bil) - start.tv_nsec);
    }
    else
    {
        timeTakenBySysCall = (stop.tv_sec - start.tv_sec) + (stop.tv_nsec - start.tv_nsec);
    }

    if (start2.tv_nsec > stop2.tv_nsec)
    {
        timeTakenByForLoop = (((stop2.tv_sec - 1) - start2.tv_sec) * bil) + ((stop2.tv_nsec + bil) - start2.tv_nsec);
    }
    else
    {
        timeTakenByForLoop = (stop2.tv_sec - start2.tv_sec) + (stop2.tv_nsec - start2.tv_nsec);
    }

    unsigned long time = (timeTakenBySysCall / cycles) - (timeTakenByForLoop / cycles);
    printf( "The system call takes %ld ns\n", time);
    return 0;
}
