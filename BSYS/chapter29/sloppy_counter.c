#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#define NUMCPUS 4

typedef struct __counter_t {
	int global;
	pthread_mutex_t glock;
	int local[NUMCPUS];
	pthread_mutex_t llock[NUMCPUS];
	int threshold;
} counter_t;

typedef struct __args_u {
	counter_t *c;
	int threadID;
	int amt;
} args_u;

void init(counter_t *c, int threshold) {
	c -> threshold = threshold;
	c -> global = 0;
	printf("init\n", c -> global);

	pthread_mutex_init(&c -> glock, NULL);
	for (int i = 0; i < NUMCPUS; i++) {
		c -> local[i] = 0;
		pthread_mutex_init(&c -> llock[i], NULL);
	}
}

void update(void *arg) {
	args_u *args = (args_u *) arg;
	int threadID = (int) args -> threadID;
	counter_t *c = (counter_t *) args -> c;
	int amt = (int) args -> amt;

	int cpu = threadID % NUMCPUS;
	pthread_mutex_lock(&c -> llock[cpu]);
	c -> local[cpu] += amt;
	if (c -> local[cpu] >= c -> threshold) {
		pthread_mutex_lock(&c -> glock);
		c -> global += c -> local[cpu];
		pthread_mutex_unlock(&c -> glock);
		c -> local[cpu] = 0;
	}
   	pthread_mutex_unlock(&c->llock[cpu]);
}

void *repeat(void *arg) {
    while(1) {    
        update(arg);
    }
}

int get(counter_t *c) {
	pthread_mutex_lock(&c -> glock);
	int val = c -> global;
	pthread_mutex_unlock(&c -> glock);
	return val;
}

int main(void) {
	pthread_t p1, p2, p3, p4;
	counter_t *c1 = (counter_t *) malloc(sizeof(counter_t));
    	counter_t *c2 = (counter_t *) malloc(sizeof(counter_t));
    	counter_t *c3 = (counter_t *) malloc(sizeof(counter_t));
    	counter_t *c4 = (counter_t *) malloc(sizeof(counter_t));

	init(c1, 5);
    	init(c2, 5);
    	init(c3, 5);
    	init(c4, 5);

	args_u u1 = { c1, 0, 1 };
	args_u u2 = { c2, 1, 1 };
	args_u u3 = { c3, 2, 1 };
	args_u u4 = { c4, 3, 1 };

	pthread_create(&p1, NULL, repeat, &u1);
	pthread_create(&p2, NULL, repeat, &u2);
	pthread_create(&p3, NULL, repeat, &u3);
	pthread_create(&p4, NULL, repeat, &u4);

	for (int i = 0;; i++) {
		int thread = i % NUMCPUS;
		int global_timer = 0;
		if (thread == 0)
			global_timer = get(c1);
		else if (thread == 1)
			global_timer = get(c2);
		else if (thread == 2)
			global_timer = get(c3);
		else if (thread == 3)
			global_timer = get(c4);
        if (i % 50 == 0) {
	    	printf("%d -> global clock %d\n", i, global_timer);
        }
	}
	return 0;
}