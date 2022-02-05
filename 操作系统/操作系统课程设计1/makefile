.PHONY:clean
FCFS:FCFS.o function.o
	gcc -o FCFS FCFS.o function.o
FCFS.o:FCFS.c
	gcc -c FCFS.c
function.o:function.c
	gcc -c function.c
clean:
	rm -f FCFS.o FCFS function.o
