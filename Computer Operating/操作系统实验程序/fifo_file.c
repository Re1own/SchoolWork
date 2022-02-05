#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>


int main(int argc, const char *argv[]) {
	int fd = open("tp", O_RDONLY);	//读取管道tp中的内容
	//写端
	int outfd = open("new_hello.txt", O_WRONLY|O_CREAT);
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