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

#define BILLION 1000000000L;

int main(void)
{
    struct timespec start, stop;
    double result = 0;
    size_t cycles = 10;
    //clockid_t clk_id;
    //clk_id = CLOCK_REALTIME;
    //char c;
    //int fd = open("./test.txt", O_RDONLY);
    
    if (clock_gettime(CLOCK_REALTIME, &start) == -1)
    {
        fprintf(stderr, "start of measurement failed\n");
        exit(1);
    }

    for (size_t i = 0; i < cycles; i++)
    {
        read(0, NULL, 0);
    }

    if (clock_gettime(CLOCK_REALTIME, &stop) == -1)
    {
        fprintf(stderr, "stopping the measurement failed\n");
        exit(1);
    }
    
    result = (double)(stop.tv_sec - start.tv_sec) + ((double)(stop.tv_nsec - start.tv_nsec) / 1000000000L);
    result = result / (double)cycles;
    printf("%.9f\n", result);

    return 0;
    
}