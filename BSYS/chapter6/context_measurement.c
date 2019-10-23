#define _POSIX_C_SOURCE 199309L
#define _GNU_SOURCE
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sched.h>

#define BILLION 1000000000L

int main(void)
{
    struct timespec start, stop;

    //int pipefd1[2];
    //int pipefd2[2];
    pid_t cpid;

    long currentTimeStart1;
    long currentTimeStop1;
    long currentTimeStart2;
    long currentTimeStop2;
    long currentTime1;
    long currentTime2;

    cpu_set_t process;
    int length = sizeof(process);
    CPU_ZERO(&process);
	CPU_SET(2, &process);

    //pipe(pipefd1);
    //pipe(pipefd2);

    cpid = fork();

    if (cpid == -1)
    {
        fprintf(stderr, "fork failed");
        exit(1);
    }


    if ((sched_setaffinity(getpid(), length, &process)) < 1)
    {   
        
        if (cpid == 0)
        {
            //closing write from fd2
            //close(pipefd2[1]);
            //closing read from fd1
            //close(pipefd1[0]);

            //clock_gettime(CLOCK_MONOTONIC_RAW, &stop);  //stop1

            //currentTimeStart1 = (start.tv_sec * BILLION) + start.tv_nsec;
            //currentTimeStop1 = (stop.tv_sec * BILLION) + stop.tv_nsec;

                //write(pipefd1[1], NULL, 1);
            //clock_gettime(CLOCK_MONOTONIC_RAW, &start); //start2
            printf("Hello");
            sched_yield();
            //read(pipefd2[0], currentTime, sizeof(long) * 2);

            //closing read
            //close(pipefd1[0]);

            exit(1);
        }
        else
        {
            //closing read
            //close(pipefd2[0]);//close pipe2 read
            //close(pipefd1[1]);//close pipe1 write

            clock_gettime(CLOCK_MONOTONIC_RAW, &start); //start1
                sched_yield();
                //read(pipefd1[0], NULL, sizeof(long) * 2);
            clock_gettime(CLOCK_MONOTONIC_RAW, &stop); //stop2

            currentTimeStart2 = (start.tv_sec * BILLION) + start.tv_nsec;
            currentTimeStop2 = (stop.tv_sec * BILLION) + stop.tv_nsec;
            
        }

        //currentTime1 = currentTimeStop1 - currentTimeStart1;
        currentTime2 = currentTimeStop2 - currentTimeStart2;

        //printf("test1 %ldns\n", currentTime1);
        printf("test2 %ldns\n", currentTime2);

    }
    return 0;
}