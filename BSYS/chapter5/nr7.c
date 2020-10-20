// Program which sets a value before splitting up in a parent and child process
//
// Author: Niklas Pelz
// Date: 16.10.20

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(void)
{
    int rc = fork();

    if (rc < 0)
    {
        fprintf(stderr, "fork failed");
        exit(1);
    }

    else if (rc == 0)
    {
        close(STDOUT_FILENO);
        printf("I am the child and closed the STDOUT_FILENO\n");
    }
    
    else
    {
        printf("I am the parent\n");
    }
    
    


}