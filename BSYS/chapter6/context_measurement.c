// Program for measuring the cost of a context switch
//
// Author: Niklas Pelz
// Date: 20.10.2019

#define _GNU_SOURCE
#include <sched.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>
#include <errno.h>

//Help for qsort
int comp(const void *p, const void *q)
{
    return (*(int*) p - *(int*) q);
}

int main (void)
{
    struct timespec start, end;
    int cycles = 10000;
    unsigned long sec[cycles], nsec[cycles];
    const unsigned long bil = 1000000000;


    // for time
    unsigned long timeTakenByForLoop;
    struct timespec start2, stop2;
    if (clock_gettime(CLOCK_MONOTONIC_RAW, &start2) < 0) {
        printf("clock fail\n");
        exit(1);
    }
    for(int i = 0; i < cycles - 8000; ++i) {}
    if (clock_gettime(CLOCK_MONOTONIC_RAW, &stop2) < 0) {
        printf("clock fail\n");
        exit(1);
    }
    if (start2.tv_nsec > stop2.tv_nsec)
    {
        timeTakenByForLoop = (((stop2.tv_sec - 1) - start2.tv_sec) * bil) + ((stop2.tv_nsec + bil) - start2.tv_nsec);
    }
    else
    {
        timeTakenByForLoop = (stop2.tv_sec - start2.tv_sec) + (stop2.tv_nsec - start2.tv_nsec);
    }
    unsigned long for_time = timeTakenByForLoop / (cycles - 200);


    int rc = fork();

    cpu_set_t set;
    CPU_ZERO(&set);
    CPU_SET(0, &set);
    if(sched_setaffinity(0, sizeof(set), &set)){
        perror("error setting sched_affinity");
        _exit(EXIT_FAILURE);
    }

    //fork failed
    if (rc < 0)
    {
        fprintf(stderr, "fork failed\n");
        return 1;
    }
    else if (rc == 0) //child
    {
        for (int i = 0; i < cycles; ++i) {
            clock_gettime(CLOCK_MONOTONIC_RAW, &start);
            sched_yield(); // divide by 2 ?
            clock_gettime(CLOCK_MONOTONIC_RAW, &end);
            sec[i] = end.tv_sec - start.tv_sec;
            if (end.tv_nsec < start.tv_nsec)
            {
                nsec[i] = (end.tv_nsec + bil) - start.tv_nsec;
                sec[i] = sec[i] - bil;
            }
            else
            {
                nsec[i] = end.tv_nsec - start.tv_nsec;
            }
        }
        unsigned long diff[cycles];
        unsigned long sum = 0;
        for(int i = 0; i < cycles; ++i) {
            diff[i] = sec[i] * bil + nsec[i];
            // diff[i] = diff[i] ; //two switches
        }
        //sort diff array
        qsort(diff, cycles, sizeof(unsigned long), comp);
        //add all together
        for (int j = 4000; j < (cycles-4000); ++j) {
            sum += diff[j];
        }
        printf("The context switch takes %lu ns\n", sum/((cycles - 8000) * 2) - 2*for_time); //two context switches at once
    } else
    {
        //parent:
        for (int i = 0; i < cycles; ++i) {
            sched_yield();
        }
        wait(NULL);
    }
    exit(0);
}
