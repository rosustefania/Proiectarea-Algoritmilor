CC=g++ -std=c++11 -Wall -Wextra

all: build

build: poduri adrese lego

poduri: poduri.o
	$(CC) $^ -o $@

poduri.o: poduri.cpp
	$(CC) $^ -c

adrese: adrese.o
	$(CC) $^ -o $@

adrese.o: adrese.cpp
	$(CC) $^ -c

lego: lego.o
	$(CC) $^ -o $@

lego.o: lego.cpp
	$(CC) $^ -c

run-p1: 
	./poduri

run-p2: 
	./adrese

run-p3: 
	./lego     

run-p4: 

clean:
	rm *.o poduri adrese lego
