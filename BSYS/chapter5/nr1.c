// Program which sets a value before splitting up in a parent and child process
//
// Author: Niklas Pelz
// Date: 8.10.19

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(void)
{
    int x;
    x = 100;
    printf("The original value is %d\n", x);
    int rc = fork();

    if (rc < 0)
    {
        fprintf(stderr, "fork failed");
        exit(1);
    }

    else if (rc == 0)
    {
        x = 50;
        printf("I am the child, the value is %d\n", x);
    }
    
    else
    {
        x = 10;
        printf("I am the parent, the value is %d\n", x);
    }
    
    


}
