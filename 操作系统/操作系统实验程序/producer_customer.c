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

time_t end_time;	//存放线程的起始时间
sem_t * mutex, * avail, * full;
int fd;

/*生产者进程*/
void *producer(void * arg) {
	int real_write;
	int delay_time = 0;
 
/*当结束时间点没有到来之前，一直循环*/
	while(time(NULL) < end_time) {
/*生产者的延迟时间*/
		delay_time = (int)(rand()*5.0/(RAND_MAX)/2.0) + 1;
		sleep(delay_time);
/*P操作信号量avail，mutex*/
		sem_wait(avail);	//看看有没有空位进行生产
		sem_wait(mutex);	//互斥
		printf("\n生产者等待时间:%ds\n",delay_time);
/*向管道中写数据*/
		if((real_write=write(fd,"hello",5)) == -1) {	//hello占5个字节
			if( errno == EAGAIN )
				printf("管道中没有商品\n");
		}
		else {
			printf("向管道中生产了%d字节的商品\n",real_write);
		}
/*V操作信号量full，mutex*/
		sem_post(mutex);
		sem_post(full);	//生产出来一个产品就让full+1
	}
	
	pthread_exit(NULL);
}



/*消费者线程*/
void *customer(void * arg)
{
	unsigned char read_buffer[5];
	int real_read;
	int delay_time;
 
	while(time(NULL) < end_time) {
		delay_time = (int)(rand()*5/(RAND_MAX)) + 1;
		sleep(delay_time);
		sem_wait(full);	//看看现在有没有商品可以买
		sem_wait(mutex);	//互斥，不能贪多，每次只能买一个产品
		memset(read_buffer,0,5);
		printf("\n消费者等待时间:%ds\n",delay_time);
		if( (real_read=read(fd,read_buffer,5)) == -1 )
		{
			if( errno == EAGAIN )
				printf("No data yet!\n");
		}
		else
			printf("从管道中拿走消费了%d个字节的商品\n", real_read);
		sem_post(mutex);	
		sem_post(avail);	//买完了，空间腾出来了，该生产者开始生产了
	}
	
	pthread_exit(NULL);
}

int main(void) {

	srand(time(NULL));
	end_time = time(NULL) + 30;	//设置运行时间12s
	

	/*创建有名管道*/
	if((mkfifo("tp",O_CREAT | O_EXCL) < 0) && (errno != EEXIST)) {
		printf("有名管道创建失败\n");
		return errno;
	}

	fd = open("tp", O_RDWR);	//fd标记有名管道
	if (fd == -1) {
		printf("打开有名管道失败\n");
		return fd;
	}
	
	/*初始化信号量mutex, avail, full*/
	/*我这里用的sem_open, 有名通信，还有一种无名通信(信号量),sem_init,*/
	/*由于我是Mac OS系统，只能用sem_open,sem_init也可以在linux中跑，而且功能更强大*/
	mutex = sem_open("mutex", O_CREAT, 0644, 1);	//初始化互斥信号量mutex = 1
	avail = sem_open("avail", O_CREAT, 0644, 2);	//初始化同步信号量avail = 2
	full = sem_open("full", O_CREAT, 0644, 0);	//初始化同步信号量full = 0

	int ret;	//用来判断后面各个初始化函数是否ok的flag
	/*创建生产者和消费者进程*/
	pthread_t thrd_pro_id, thrd_cus_id;
	ret = pthread_create(&thrd_pro_id, NULL, producer, NULL);
	if (ret != 0) {
		printf("生产者进程创建失败\n");
		return 0;
	}

	ret = pthread_create(&thrd_cus_id, NULL, customer, NULL);
	if (ret != 0) {
		printf("消费者进程创建失败\n");
		return 0;
	}

	/*开始线程的运行*/
	pthread_join(thrd_pro_id, NULL);
	pthread_join(thrd_pro_id, NULL);

	close(fd);	//关闭有名管道
	return 0;
}