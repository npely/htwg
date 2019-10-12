// Program which opens a file and lets a praent and child process access it.
//
// Author: Niklas Pelz
// Date: 8.10.19

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>

int main(void)
{
    int fd = open("./nr2.txt", O_WRONLY | O_APPEND);
    write(fd, "new program start\n", 18);
    int rc = fork();

    if (fd < 0 || rc < 0)
    {
        fprintf(stderr ,"failed");
        return 1;
    }
    else if (rc == 0)
    {
        write(fd, "Child writing to file nr2.txt\n", 30);
    }
    else
    {
        write(fd, "Parent writing to file nr2.txt\n", 31);
    }
    
    return 0;
}