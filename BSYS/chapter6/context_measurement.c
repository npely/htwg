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

    int pipefd[2];
    pid_t cpid;
    char readbuf;

    long result;
    long resultstart;
    long resultstop;

    cpu_set_t process;
    int length = sizeof(process);
    CPU_ZERO(&process);
	CPU_SET(2, &process);

    pipe(pipefd);

    cpid = fork();

    if (cpid == -1)
    {
        fprintf(stderr, "fork failed");
        exit(1);
    }

    clock_gettime(CLOCK_MONOTONIC_RAW, &start);

    if ((sched_setaffinity(getpid(), length, &process)) < 1)
    {
        if (cpid == 0)
        {
            //closing write
            close(pipefd[1]);

            read(pipefd[0], readbuf, sizeof(readbuf));
            printf("Read: %s\n", readbuf);

            //closing read
            close(pipefd[0]);
            exit(0);
        }
        else
        {
            //closing read
            close(pipefd[0]);
            write(pipefd[1], "bitte komm an\n", 1);
            //stopping time
            clock_gettime(CLOCK_MONOTONIC_RAW, &stop);
            //closing write
            close(pipefd[1]);
            wait(NULL);
        }

        resultstart = (start.tv_sec * BILLION) + start.tv_nsec;
        resultstop = (stop.tv_sec * BILLION) + stop.tv_nsec;

        result = resultstop - resultstart;
        printf("time taken by context switch: %ldns\n", result);

    }
    

    return 0;
}