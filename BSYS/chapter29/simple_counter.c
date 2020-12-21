#define _GNU_SOURCE
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

typedef struct __counter_t {
	int value;
	pthread_mutex_t glock;
} counter_t;

typedef struct __counter_arg {
	int tid;
	counter_t *c;
} counter_arg;

void init(counter_t *c, int init) {
	c->value = init;
	pthread_mutex_init(&c->glock, NULL);
}

void increment(counter_t *c, int i) {
	pthread_mutex_lock(&c->glock);
	c->value = c->value + 1;
	pthread_mutex_unlock(&c->glock);
}

void *run(void *arg) {
	counter_arg *args = (counter_arg *) arg;
	while (1) {
		//printf("%d updating to %d\n", args->tid, args->c->value + 1);
		increment(args->c, 1);
		pthread_yield();
	}
}

void get(counter_t *c, int *value) {
	pthread_mutex_lock(&c->glock);
	*value = c->value;
	pthread_mutex_unlock(&c->glock);
}

int main(void) {
	pthread_t p1, p2, p3, p4;
	counter_t *c = (counter_t *) malloc(sizeof(counter_t));
	init(c, 0);
	int value;

	counter_arg a1 = {0, c};
	counter_arg a2 = {1, c};
	counter_arg a3 = {2, c};
	counter_arg a4 = {3, c};

	pthread_create(&p1, NULL, run, &a1);
	pthread_create(&p2, NULL, run, &a2);
	pthread_create(&p3, NULL, run, &a3);
	pthread_create(&p4, NULL, run, &a4);

	while(1) {
		get(c, &value);
		printf("reading %d\r", value);
		pthread_yield();
	}
	return 0;
}