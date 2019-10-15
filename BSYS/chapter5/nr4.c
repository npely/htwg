// Program which overwrites a process with exec()
//
// Author: Niklas Pelz
// Date: 8.10.19

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(void)
{
    int rc = fork();

    if(rc < 0) {
        fprintf(stderr, "fork failed\n");
        exit(1);
    }

    else if (rc == 0)
    {
    //char* execv_args[] = { "/bin/ls", "-l", NULL};
    //execv(execv_args[0], execv_args);

    char* execvp_args[] = { "ls", "-l", NULL};
    execvp(execvp_args[0], execvp_args);
    }

    else
    {
        int rc_wait = wait(NULL);
        printf("Hello I am the parent (rc_wait:%d)\n", rc_wait);
    }
    
    return 0;

}