#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <pthread.h>
#include <errno.h>
#include <math.h>
#include <unistd.h>

//筷子作为mutex
pthread_mutex_t chopstick[6];

void *eat_think(void *arg);