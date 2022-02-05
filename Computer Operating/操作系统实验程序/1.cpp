#include <iostream>
#include <string>
#include <stdlib.h>
#include <cstring>
#include <algorithm>
#include <stack>
#include <cstdio>

using namespace std;

int cnt[10];	//统计答案每个数字的个数
int num[10];	//0~9每个数字的个数
int len, flag;
const int maxn = 1e5+5;
char s[maxn];


void dfs(int now, int k, int res) {			//k表示剩下几个数还要选
	if (!now) {	//走完后判断总情况,只用考虑0要不要放入答案了
		if (res || k > num[0])	{		//res == 0才表示当前字符串和能被3整除，k > num[0]就没法往下选了
			flag = 0;
			return;	
		}
		cnt[now] = k;	//剩余的都给0好了
		if (k == 1) {
			flag = 1;
			return;
		}
		for (int i = 1; i <= 9; i++) {
			if (cnt[i])	{
				flag = 1;	//排除只有零的情况
				return;
			}
		}
		cnt[now] = 0;
		flag = 0;
		return;
	}
	int r = min(num[now], k), l = max(0, r - 2);	//r是表示剩下要选的次数还有多少次，l是对三整除后0,1,2余数的循环需要的次数
	for (int i = r; i >= l; i--) {
		cnt[now] = i;	//假设当前的数，能成功选上那么就设它的答案个数为i
		dfs(now - 1, k - i, (res + i*now)%3);
		if (flag)	return;
		cnt[now] = 0;	//函数返回到这一步时就说明没成功，那么这个数就选不上了
	}
	flag = 0;
	return;
}
int main(void) {
	int T;
	scanf("%d", &T);
	while(T--) {
		memset(num, 0, sizeof(num));
        memset(cnt, 0, sizeof(cnt));
		scanf("%s %d", s, &len);
		int k = strlen(s);
		for (int i = 0; i < k; i++) {
			num[s[i]-'0']++;
		}
		dfs(9, len, 0);
		if (flag == 0) {
			printf("-1");
		}
		else {
			for (int i = 9; i >= 0; i--) {
				for (int j = 1; j <= cnt[i]; j++) {
					printf("%d", i);
				}
			}
		}
		printf("\n");
		flag = 0;
	}
	return 0;
}