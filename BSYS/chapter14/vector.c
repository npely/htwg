#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


typedef struct vector_struct {
    int* data;
    size_t capacity;
    size_t size;
} vector;

int init(vector* v, size_t capacity) {
    v -> data = malloc(capacity * sizeof(int));
    v -> capacity = capacity;

    return 0;
}

int add(vector* v, int element) {
    if(v -> size >= v -> capacity) {
        int newCapacity = v -> capacity * 2;
        v -> data = realloc(v -> data, newCapacity);
        v -> capacity = newCapacity;
    }
    v -> data[v -> size] = element;
    v -> size = v -> size + 1;

    return 0;
}

int delete(vector* v) {
    free(v);

    return 0;
}