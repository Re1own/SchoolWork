#include "head.h"

int main(void) {
	printf("请输入进程的个数:");
	scanf("%d", &Process_number);
	Process * P = (Process *)malloc(sizeof(Process)*Process_number);
	input_process(P);	//输入创建进程
	quick_sort(P, 0, Process_number-1);	//快速排序处理
	calculate_process(P);	//计算
	print_process(P);	//展示结果
	return 0;
}