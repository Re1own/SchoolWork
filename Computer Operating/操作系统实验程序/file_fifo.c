#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <string.h>

#define FIFO_PATH "/Users/zhouhuizhen/desktop/操作系统实验/hello.txt"	//定义路径宏

int main(int argc, const char *argv[]) {
	//创建命名管道
	mkfifo("tp", O_RDONLY);	//我的管道名叫tp
	//读端
	int infd;
	infd = open(FIFO_PATH, O_RDONLY);
	if (infd == -1) {
		perror("open");
		exit(1);
	}
	//写端
	int outfd;
	outfd = open("tp", O_WRONLY);
	if (outfd == -1) {
		perror("open");
		exit(1);
	}

	char buf[1024];
	int n;
	while((n = read(infd, buf, 1024)) > 0) {
		write(outfd, buf, n);
	}

	close(infd);
	close(outfd);
}