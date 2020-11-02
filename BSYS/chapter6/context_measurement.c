// Program for measuring the cost of a context switch
//
// Author: Niklas Pelz
// Date: 16.10.2020

#define _GNU_SOURCE
#include <sched.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>
#include <fcntl.h>
#include <errno.h>

int comp(const void *p, const void *q)
{
    return (*(int*) p - *(int*) q);
}

void setSchedAffinity() {
    cpu_set_t set;
    CPU_ZERO(&set);
    CPU_SET(3, &set);
    if(sched_setaffinity(0, sizeof(set), &set)){
         perror("error setting sched_affinity");
         _exit(EXIT_FAILURE);
    }
}

int main (void)
{
    struct timespec start, stop;
    struct timespec startGetTime, stopGetTime;
    struct timespec startForLoop, stopForLoop;
    int cycles = 100000;
    unsigned long sec[cycles], nsec[cycles];
    const unsigned long bil = 1000000000;
    unsigned long timeTakenByForLoop;
    unsigned long timeTakenByGetTime[cycles], timeTakenByGetTimeNSEC[cycles];
    
    if (clock_gettime(CLOCK_MONOTONIC_RAW, &startForLoop) < 0) {
        printf("clock fail\n");
        exit(1);
    }
    for(int i = 0; i < cycles; ++i) {}
    if (clock_gettime(CLOCK_MONOTONIC_RAW, &stopForLoop) < 0) {
        printf("clock fail\n");
        exit(1);
    }
    if (startForLoop.tv_nsec > stopForLoop.tv_nsec)
    {
        timeTakenByForLoop = (((stopForLoop.tv_sec - 1) - startForLoop.tv_sec) * bil) + ((stopForLoop.tv_nsec + bil) - startForLoop.tv_nsec);
    }
    else
    {
        timeTakenByForLoop = (stopForLoop.tv_sec - startForLoop.tv_sec) + (stopForLoop.tv_nsec - startForLoop.tv_nsec);
    }
    unsigned long forLoopTime = timeTakenByForLoop / (cycles);

    int rc = fork();

    if (rc < 0)
    {
        fprintf(stderr, "fork failed\n");
        return 1;
    }
    else if (rc == 0) //Child
    {
        setSchedAffinity();

        for (int i = 0; i < cycles; ++i) {
            clock_gettime(CLOCK_MONOTONIC_RAW, &start);
            sched_yield();
            clock_gettime(CLOCK_MONOTONIC_RAW, &stop);
            sec[i] = stop.tv_sec - start.tv_sec;

            if (start.tv_nsec > stop.tv_nsec)
            {
                nsec[i] = (stop.tv_nsec + bil) - start.tv_nsec;
                sec[i] = sec[i] - bil;
            }
            else
            {
                nsec[i] = stop.tv_nsec - start.tv_nsec;
            }

            clock_gettime(CLOCK_MONOTONIC_RAW, &startGetTime);
            clock_gettime(CLOCK_MONOTONIC_RAW, &stopGetTime);

            if (startGetTime.tv_nsec > stopGetTime.tv_nsec)
            {
                timeTakenByGetTimeNSEC[i] = (stopGetTime.tv_nsec + bil) - startGetTime.tv_nsec;
                timeTakenByGetTime[i] = timeTakenByGetTimeNSEC[i] - bil;
            }
            else
            {
                timeTakenByGetTime[i] = stopGetTime.tv_nsec - startGetTime.tv_nsec;
            }
        }

        unsigned long diff[cycles];
        unsigned long sum = 0;

        for(int i = 0; i < cycles; ++i) {
            diff[i] = (sec[i] * bil + nsec[i]) - timeTakenByGetTime[i];
        }

        //Sortieren des Arrays
        qsort(diff, cycles, sizeof(unsigned long), comp);

        //Die Values aufaddieren um sie später wieder durch die Anzahl zu teilen 
        for (int j = 4000; j < (cycles - 4000); ++j) {
            sum += diff[j];
        }

        unsigned long finalValue = (sum/((cycles - 8000) * 2) - forLoopTime * 2);

        printf("The context switch takes %lu ns\n", finalValue); //Da zwei Context Switches auf einmal ausgeführt werden
    } else //Parent
    {
        setSchedAffinity();

        for (int i = 0; i < cycles; ++i) {
            sched_yield();
        }

        wait(NULL);
    }
    exit(0);
}