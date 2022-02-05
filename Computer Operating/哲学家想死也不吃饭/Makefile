.PHONY:clean
never_eat_rice:never_eat_rice.o eat_rice.o
	gcc -o never_eat_rice never_eat_rice.o eat_rice.o
eat_rice.o:eat_rice.c
	gcc -c eat_rice.c
never_eat_rice.o:never_eat_rice.c
	gcc -c never_eat_rice.c
clean:
	rm -f never_eat_rice.o eat_rice.o never_eat_rice
