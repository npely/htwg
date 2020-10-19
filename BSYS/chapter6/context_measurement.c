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

int comp(const void *p, const void *q)
{
    return (*(int*) p - *(int*) q);
}

void showSchedAffinity() {
    cpu_set_t mask;
    int nproc = sysconf(_SC_NPROCESSORS_ONLN);
    sched_getaffinity(0, sizeof(mask), &mask);
    printf("[%d] sched_getaffinity = ", getpid());
    for (int i = 0; i < nproc; i++) {
        printf("%d ", CPU_ISSET(i, &mask));
    }
    printf("\n");
}

void setSchedAffinity() {
    cpu_set_t set;
    CPU_ZERO(&set);
    CPU_SET(0, &set);
    // if(sched_setaffinity(0, sizeof(set), &set)){
    //     perror("error setting sched_affinity");
    //     _exit(EXIT_FAILURE);
    // }
    sched_setaffinity(0, sizeof(set), &set);
}

int main (void)
{
    struct timespec start, end;
    int cycles = 10000;
    unsigned long sec[cycles], nsec[cycles];
    const unsigned long bil = 1000000000;
    unsigned long timeTakenByForLoop;
    struct timespec startForLoop, stopForLoop;

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
        showSchedAffinity();
        setSchedAffinity();

        for (int i = 0; i < cycles; ++i) {
            clock_gettime(CLOCK_MONOTONIC_RAW, &start);
            sched_yield();
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
        }

        //Sortieren des Arrays
        qsort(diff, cycles, sizeof(unsigned long), comp);

        //Die Values aufaddieren um sie später wieder durch die Anzahl zu teilen 
        for (int j = 4000; j < (cycles-4000); ++j) {
            sum += diff[j];
        }

        printf("The context switch takes %lu ns\n", sum/((cycles - 8000) * 2) - 2 * forLoopTime); //Da zwei Context Switches auf einmal ausgeführt werden
    } else //Parent
    {
        showSchedAffinity();
        setSchedAffinity();

        for (int i = 0; i < cycles; ++i) {
            sched_yield();
        }

        wait(NULL);
    }
    exit(0);
}