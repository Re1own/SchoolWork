#include "head.h"

void swap(Process * a, Process * b) {
	Process temp = *a;
	*a = *b;
	*b = temp;

}
void input_process(Process * P) {
	for (int i = 0; i < Process_number; i++) {
		printf("请依次输入第%d个进程的\n进程名   到达时间    服务时间\n", i+1);
		setbuf(stdin, NULL);	//将输入的写入缓冲区(不用这个方法输入的时候值传入会有问题)
		scanf("%c %d %d", &P[i].name, &P[i].arrive_time, &P[i].serve_time);
	}
}

void print_process(Process * P) {
	for (int i = 0; i < Process_number; i++) {
		printf("进程名  到达时间  服务时间  开始执行时间  完成时间  周转时间  代权周转时间\n");
		printf("%c       %d          %d          %d              %d           %d           %d\n", P[i].name, P[i].arrive_time, P[i].serve_time, P[i].start_time, P[i].finish_time, P[i].round_time, P[i].t_round_time);
	}
}

void calculate_process(Process * P) {
	for (int i = 0; i < Process_number; i++) {
		if (i == 0) {	//第一个特殊处理下
			P[i].start_time = P[i].arrive_time;	//开始执行时间
			P[i].finish_time = P[i].start_time + P[i].serve_time;	//完成时间
			P[i].round_time = P[i].finish_time - P[i].arrive_time;	//周转时间 = 完成时间 - 到达时间
			P[i].t_round_time = P[i].round_time / P[i].serve_time;	//带权周转时间 = 周转时间/服务时间
		}
		else {
			if (P[i].start_time <= P[i-1].finish_time) {	//注意要分情况
				P[i].start_time = P[i-1].finish_time;	//开始执行时间 = 上一个进程结束的时间
			}
			else {
				P[i].start_time = P[i].arrive_time;
			}
			P[i].finish_time = P[i].start_time + P[i].serve_time;
			P[i].round_time = P[i].finish_time - P[i].arrive_time;
			P[i].t_round_time = P[i].round_time / P[i].serve_time;
		}
	}
}

void quick_sort(Process * P, int l, int r) {
	int i = l, j = r;
	if (i < j) {
		while(i < j) {
			while(P[i].arrive_time <= P[j].arrive_time && i < j) {
				i++;
			}
			if (i < j) {
				swap(&P[i], &P[j]);
			}
			while(P[i].arrive_time < P[j].arrive_time && i < j) {
				j--;
			}
			if (i < j) {
				swap(&P[i], &P[j]);
			}
		}
		quick_sort(P, l, i-1);
		quick_sort(P, j, r);
	}
	
}