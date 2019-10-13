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
    double result;
    //clockid_t clk_id;
    //clk_id = CLOCK_REALTIME;
    char c;
    int fd = open("./test.txt", O_RDONLY);

    if (clock_gettime(CLOCK_REALTIME, &start) == -1)
    {
        fprintf(stderr, "start of measurement failed\n");
        exit(1);
    }

    for (int i = 0; i < 100; i++)
    {
        read(fd, &c, 1);
    }

    if (clock_gettime(CLOCK_REALTIME, &stop) == -1)
    {
        fprintf(stderr, "stopping the measurement failed\n");
        exit(1);
    }

    result = (stop.tv_sec - start.tv_sec) + (stop.tv_nsec - stop.tv_nsec) / BILLION;
    result = result / 100;
    printf("%lf\n", result);

    return 0;
    
}