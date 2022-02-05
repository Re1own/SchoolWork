#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define FIFO_PATH "/Users/zhouhuizhen/desktop/操作系统实验/hellow.txt"	//定义路径宏

int main(int argc, const char *argv[]) {
	int fd = open(FIFO_PATH, O_RDONLY);
	//写端
	int outfd = open("hellow.txt", O_WRONLY|O_CREAT);
	if (outfd == -1) {
		perror("open");
		exit(1);
	}

	//读端
	int infd = open("tp", O_RDONLY);
	if (infd == -1) {
		perror("open");
		exit(1);
	}

	char buf[1024];
	int n;

	while((n = read(infd, buf, 1024)) > 0) {
		write(outfd, buf, n);
	}
	close(fd);
	close(outfd);
	unlink("tp");
	return 0;
}