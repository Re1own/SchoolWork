#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <pthread.h>
#include <semaphore.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/stat.h>


sem_t mutex, avail, full;	//信号量
time_t end_time;	//存放线程的起始时间



int main(void) {

	int ret = sem_init(&mutex, 0, 0);

	return 0;
}