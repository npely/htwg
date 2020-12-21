#ifndef LINKED_LIST
#define LINKED_LIST
	#include <pthread.h>
	#include <stdlib.h>
	#include <stdio.h>

	typedef struct __node_t {
        	int key;
        	struct __node_t *next;
        } node_t;

	typedef struct __list_t {
        	node_t *head;
       		pthread_mutex_t lock;
	} list_t;


	void List_Init(list_t *L);
	void List_Insert(list_t *, int key);
	int List_Lookup(list_t *, int key);

#endif