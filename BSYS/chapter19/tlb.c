#define _GNU_SOURCE
#include <sched.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>
#include <fcntl.h>
#include <errno.h>

void setSchedAffinity() {
    cpu_set_t set;
    CPU_ZERO(&set);
    CPU_SET(3, &set);
    if(sched_setaffinity(0, sizeof(set), &set)){
         perror("error setting sched_affinity");
         _exit(EXIT_FAILURE);
    }
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        fprintf(stderr, "You need to put in the numbr of pages and number of iterations\n");
        return -1;
    }

    if (argc > 3) {
        fprintf(stderr, "Too many arguments");
        return -1;
    }

    if (argc == 3) {

        long num_pages = atoi(argv[1]);
        long iterations = atoi(argv[2]);

        if (num_pages < 0 || iterations < 0) {
            fprintf(stderr, "No negative values!");
            return -1;
        }

        int pagesize = getpagesize();
        int jump = pagesize / sizeof(int);

        setSchedAffinity();
        
        struct timespec start, stop;
        struct timespec clock_overhead_stop;

        const unsigned long bil = 1000000000;

        unsigned long total_access_time = 0;

        // calloc um sich die Initialisierung des Arrays zu sparen
        int* array = (int*) calloc(num_pages * jump, sizeof(int));

        if (array == NULL) {
            fprintf(stderr, "Error allocating memory!");
            return -1;
        }

        // Loop zur Messung der Dauer des Speicherzugriffes
        // ----------------------------------------------------------------------------------------------------------------------------------------------------
        for(long i = 0; i < iterations; i++) {
            long pages_total_time = 0;
            for(int j = 0; j < num_pages*jump; j += jump) {
                clock_gettime(CLOCK_MONOTONIC_RAW, &start);
                clock_gettime(CLOCK_MONOTONIC_RAW, &clock_overhead_stop);
                array[j] += 1;
                clock_gettime(CLOCK_MONOTONIC_RAW, &stop);

                long pages_access_time_start_nano = ((start.tv_sec * bil) + start.tv_nsec);
                long pages_access_time_stop_nano = ((stop.tv_sec * bil) + stop.tv_nsec);
                long clock_overhead_stop_nano = ((clock_overhead_stop.tv_sec * bil) + clock_overhead_stop.tv_nsec);

                pages_total_time += (pages_access_time_stop_nano - pages_access_time_start_nano) - (clock_overhead_stop_nano - pages_access_time_start_nano);
            }

            total_access_time += pages_total_time / num_pages;
        }
        // ------------------------------------------------------------------------------------------------------------------------------------------------------

        unsigned long final_value = total_access_time / iterations;

        printf("Accessing %lu pages takes %lu ns\n", num_pages, final_value);

        FILE* fptr;

        fptr = fopen("tlb.csv", "a+");

        if (fptr == NULL) {
            fprintf(stderr, "Error with File Pointer");
            return -1;
        }

        fprintf(fptr, "%ld, %ld\n", num_pages, final_value);

        free(array);

        fflush(fptr);
        fclose(fptr);
        
        return 0;
    }
}