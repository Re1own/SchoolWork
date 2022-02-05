#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#define file_name "Hello.txt"

void lock_set(int fd, int type) {
	struct flock lock;
	lock.l_whence = SEEK_SET;
	lock.l_start = 0;
	lock.l_len = 0;

	while(1) {
		lock.l_type = type;
		if ((fcntl(fd, F_SETLK, &lock)) == 0) {
			if (lock.l_type == F_RDLCK) {
				printf("read lock set by %d\n", getpid());
			}
			else if (lock.l_type == F_WRLCK) {
				printf("write lock set by %d\n", getpid());
			}
			else if (lock.l_type == F_UNLCK) {
				printf("release lock by %d\n", getpid());
			}
			return;
		}
		fcntl(fd, F_GETLK, &lock);
		if (lock.l_type != F_UNLCK) {
			if (lock.l_type == F_RDLCK) {
				printf("read lock set by another process %d\n", lock.l_pid);
			}
			else if (lock.l_type == F_WRLCK) {
				printf("write lock set by another process %d\n", lock.l_pid);
			}
			getchar();
 		}
	}

}

int write_file(int fd, char * str) {
	int size, len = strlen(str);
	if ((size = write(fd, str, len)) < 0) {
		perror("写入文件失败的原因:");
		return 0;
	}
	else {
		printf("成功的写入了字符串%s\n", str);
		return 1;
	}
}

int read_file(int fd) {
	int size;
	char str_r[20];	//存放读的内容
	if ((size = read(fd, str_r, 10)) < 0) {
		perror("读文件出错的原因:");
		exit(1);
	}
	else {
		printf("读取了文件中的%s\n", str_r);
	}
	if (close(fd) < 0) {
		perror("关闭文件出错原因:");
		return 0;
	}
	else {
		printf("成功关闭文件\n");
	}
	return 1;
}



int main(void) {
	pid_t process_father, process_child;
	int fd;
	char str[20];	//待要写入文件的字符串
	strcpy(str, "Hello");
	if((fd = open(file_name, O_CREAT | O_TRUNC | O_RDWR, 0666)) < 0) {	//打开文件
		perror("打开失败原因:");
		exit(1);
	}
	else {
		printf("成功打开文件hello.txt %d\n", fd);
	}

	lock_set(fd, F_WRLCK);
	write_file(fd, str);
	printf("输入任意键继续\n");
	getchar();

	lock_set(fd, F_UNLCK);
	read_file(fd);
	printf("输入任意键继续\n");
	getchar();

	close(fd);
	return 0;
}