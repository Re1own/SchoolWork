#include "rice.h"


int main(){
	pthread_t A,B,C,D,E; //5个哲学家
	int i;
	for (i = 0; i < 5; i++) {
		pthread_mutex_init(&chopstick[i],NULL);
	}
	pthread_create(&A,NULL, eat_think, "A");
	pthread_create(&B,NULL, eat_think, "B");
	pthread_create(&C,NULL, eat_think, "C");
	pthread_create(&D,NULL, eat_think, "D");
	pthread_create(&E,NULL, eat_think, "E");
 
	pthread_join(A,NULL);
	pthread_join(B,NULL);
	pthread_join(C,NULL);
	pthread_join(D,NULL);
	pthread_join(E,NULL);
	return 0;
}