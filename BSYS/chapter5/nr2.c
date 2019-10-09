// Program which opens a file and lets a praent and child process access it.
//
// Author: Niklas Pelz
// Date: 8.10.19

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>

int main(int argc, char *argv[])
{
    open("./nr2.txt", O_CREAT | O_WRONLY | O_TRUNC);
    int rc = fork();

    
}