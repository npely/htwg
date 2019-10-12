// Program which overwrites a process with exec()
//
// Author: Niklas Pelz
// Date: 8.10.19

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
    int rc = fork();

    //char* execv_args[] = { "/bin/ls", "-l", NULL};
    //execv(execv_args[0], execv_args);

    char* execvp_args[] = { "ls", "-l", NULL};
    execvp(execvp_args[0], execvp_args);
}