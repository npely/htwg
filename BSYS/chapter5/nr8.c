#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>

int main(void) {

    int fd[2];

    pipe(fd);

    int rc = fork();

    if(rc < 0) {
            fprintf(stderr, "fork 1 failed!\n");
            exit(1);
    }
    else if(rc == 0) {
        //Child 1
        dup2(fd[1], 1);
        close(fd[0]);
        fprintf(stdout, "Hello i am the first child!\n");
        close(fd[1]);
        exit(0);
    }
    else {
        //Parent
        wait(NULL);
        int rc2 = fork();
        if(rc2 < 0) {
            fprintf(stderr, "fork 2 failed!\n");
            exit(1);
        }
        else if (rc2 == 0) {
            //Child 2
            dup2(fd[0], 0);
            close(fd[1]);
            printf("Second childs echo: ");
            char b;
            while (read(0, &b, 1) > 0) {
                printf("%c", b);
                if(b == '\n')
                    break;
            }
            close(fd[0]);
            exit(0);
        }
        else {
            wait(NULL);
        }
    }
    close(fd[0]); 
    close(fd[1]);
    return 0;
}