// Program for measuring the cost of a system call
//
// Author: Niklas Pelz
// Date: 13.10.2019

//The value 199309L or greater additionally exposes definitions for POSIX.1b (real-time extensions).
#define _POSIX_C_SOURCE 199309L
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <time.h>
#include <fcntl.h>

#define BILLION 1000000000L

int main(void)
{
    struct timespec start, stop;
    long sysresult = 0;
    long loopresult = 0;
    size_t cycles = 100;
    long result = 0;
    //clockid_t clk_id;
    //clk_id = CLOCK_REALTIME;
    //char c;
    //int fd = open("./test.txt", O_RDONLY);
    //clock_getres
    
    if (clock_gettime(CLOCK_MONOTONIC_RAW, &start) == -1)
    {
        fprintf(stderr, "start of measurement failed\n");
        exit(1);
    }

    for (size_t i = 0; i < cycles; i++)
    {
        getpid();
    }

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &stop) == -1)
    {
        fprintf(stderr, "stopping the measurement failed\n");
        exit(1);
    }

    sysresult = ((stop.tv_sec - start.tv_sec) * BILLION) + (stop.tv_nsec - start.tv_nsec);

    sysresult = sysresult / cycles;


    if (clock_gettime(CLOCK_MONOTONIC_RAW, &start) == -1)
    {
        fprintf(stderr, "start of measurement failed\n");
        exit(1);
    }

    for (size_t i = 0; i < cycles; i++)
    {
    }

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &stop) == -1)
    {
        fprintf(stderr, "stopping the measurement failed\n");
        exit(1);
    }

    loopresult = ((stop.tv_sec - start.tv_sec) * BILLION) + (stop.tv_nsec - start.tv_nsec);

    loopresult = loopresult / cycles;

    result = sysresult - loopresult;

    printf("%ld\n", result);

    return 0;
    
}