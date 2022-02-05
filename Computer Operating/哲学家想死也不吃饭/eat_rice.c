#include "rice.h"

void *eat_think(void *arg) {
	char phi = *(char *)arg;
	int right = phi - 'A' + 1;
	int left = (phi - 'A' + 5 - 1)%5;
	while(1) {
		printf("哲学家%c开始思考2秒\n",phi);
		sleep(2); //思考
		pthread_mutex_lock(&chopstick[left]); //拿起左手的筷子
		printf("哲学家 %c 左手拿起了编号为%d的筷子\n", phi, left);
		if (pthread_mutex_trylock(&chopstick[right]) == EBUSY) { //拿起右手的筷子	
			pthread_mutex_unlock(&chopstick[left]); //如果右边筷子被拿走放下左手的筷子
			continue;
		}
		printf("哲学家 %c 右手拿起了编号为%d的筷子\n", phi, right);
		printf("哲学家 %c 正在吃饭\n",phi);
		sleep(2); //吃饭
		pthread_mutex_unlock(&chopstick[left]); //放下左手的筷子
		printf("哲学家 %c 左手放下了编号为%d的筷子\n", phi, left);
		pthread_mutex_unlock(&chopstick[right]); //放下右手的筷子
		printf("哲学家 %c 右手放下了编号为%d的筷子\n", phi, right);
	}
}