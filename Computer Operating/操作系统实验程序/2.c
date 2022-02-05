#include <sys/stat.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define FIFO_SERVER "/Users/zhouhuizhen/desktop/hello.txt"	//定义路径宏

int main(int argc, const char *argv[]) {
	char buf_r[100];
	int fd;
	int nread;
	if ((mkfifo(FIFO_SERVER, O_CREAT|O_EXCL) < 0) ) {
		printf("can't create fifoserver\n");
		exit(0);
	}
	printf("Preparing for reading bytes...\n");
	memset(buf_r, 0, sizeof(buf_r));
	fd = open(FIFO_SERVER, O_RDONLY|O_NONBLOCK, 0);
	if (fd == -1) {
		perror("open");
		exit(1);
	}
	while(1) {
		memset(buf_r, 0, sizeof(buf_r));
		if ((nread = read(fd, buf_r, 100)) == -1) {
			perror("read");
		}
		else if (nread == 0) {
			printf("no date yet\n");
		}
		else {
			printf("read %s from FIFO\n", buf_r);
		}
		if (strcmp(buf_r, "stop") == 0)	break;
		sleep(8);
	}
	printf("game over\n");
	getchar();
	unlink(FIFO_SERVER);
}
