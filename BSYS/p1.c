#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void)
{
    printf("hello world (pid:%d)\n", (int) getpid());
    int rc = fork();

    if (rc < 0)
    {
        // fork failed, return value is -1
        fprintf(stderr, "fork failed\n");
        exit(1);
    }
    else if (rc == 0)
    {
        // fork return value == 0 = child (new process)
        printf("hello, I am child (pid:%d)\n", (int) getpid());
    }
    else
    {
        // parent continues if fork return value is greater than 0
        printf("hello, I am parent of %d (pid:%d)\n", rc, (int) getpid());
    }
    return 0;
}