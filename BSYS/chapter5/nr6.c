// Program which lets the parent and child process print different things
//
// Author: Niklas Pelz
// Date: 8.10.19

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <syscall.h>

int main(void)
{
    int rc = fork();

    if (rc < 0)
    {
        fprintf(stderr, "fork failed\n");
        exit(1);
    }

    else if (rc == 0)
    {
        printf("hello, I am the child (pid:%d)\n", getpid());
    }

    else
    {
        int rc_wait = waitpid(-1, NULL, 0);
        printf("goodbye, I am the parent (pid:%d) (rc_wait:%d)\n", getpid(), rc_wait);
    }

    return 0;
}
