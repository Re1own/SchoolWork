#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int Process_number;	//进程个数

typedef struct Process {
	char name;
	int arrive_time;	//到达时间	
	int serve_time;		//服务时间	
	int start_time;		//开始执行时间
	int finish_time;	//完成时间
	int round_time;		//周转时间
	int t_round_time;	//带权周转时间

}Process;

void input_process(Process * P);
void print_process(Process * P);
void quick_sort(Process * P, int l, int r);
void swap(Process * a, Process * b);
void calculate_process(Process * P);