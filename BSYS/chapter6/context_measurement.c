#define _POSIX_C_SOURCE 199309L
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sched.h>

#define BILLION 1000000000L

int main(int argc, char *argv[])
{
    struct timespec start, stop;

    int pipefd[2];
    pid_t cpid;
    char buf;
    int rbytes;

    long result;
    long resultstart;
    long resultstop;

    cpu_set_t process;
    int length = sizeof(process);


    if (pipe(pipefd) == -1)
    {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    cpid = fork();

    if (cpid == -1)
    {
        fprintf(stderr, "fork failed");
        exit(1);
    }

    if (clock_gettime(CLOCK_MONOTONIC_RAW, &start) == -1)
    {
        fprintf(stderr, "start of measurement failed\n");
        exit(1);
    }

    if ((sched_setaffinity(0, length, &process)) < 1)
    {
        if (cpid == 0)
        {
            close(pipefd[1]);
            rbytes = read(pipefd[0], buf, sizeof(buf));
            printf("Read: %s\n", buf);
            exit(0);
        }
        else
        {
            close(pipefd[0]);
            write(pipefd[1], "Test!\n", 8);

            if (clock_gettime(CLOCK_MONOTONIC_RAW, &stop) == -1)
            {
                fprintf(stderr, "stopping the measurement failed\n");
                exit(1);
            }

            resultstart = (start.tv_sec * BILLION) + start.tv_nsec;
            resultstop = (stop.tv_sec * BILLION) + stop.tv_nsec;

            result = resultstop - resultstart;
            printf("time taken by a context switch: %ldns\n", result);

            close(pipefd[1]);
            wait(NULL);
        }
        
    }

    return 0;
}