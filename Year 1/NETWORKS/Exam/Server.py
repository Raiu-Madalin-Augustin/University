import socket
import threading
import random
import struct
import sys
import time

random.seed()
start=1; stop=2**17-1
my_num= random.randint(start,stop)
mylock = threading.Lock()
client_guessed=False
winner_thread=0
e = threading.Event()
e.clear()
threads = []
client_count=0

def client(cs):
    global mylock, client_guessed, my_num, winner_thread, client_count,e

    my_idcount=client_count
    print('client #',client_count,'from: ',cs.getpeername(), cs )
    cs.send(struct.pack("!I",len(str(my_num))))

    while not client_guessed:
        try:
            cnumber=cs.recv(4)
            cnumber=int.from_bytes(cnumber,"big")
            str_num=str(cnumber)
            str_my=str(my_num)
            all_pos=[pos for pos, digit in enumerate(str_my) if digit == str_num]

            #send len of nr
            cs.send(struct.pack("!I",len(all_pos)))
        
          #  for pos in all_pos:
           #     cs.send(struct.pack("!I",pos))

        except socket.error as msg:
            print('Error:',msg.strerror)
            break




def resetSrv():
    global mylock, client_guessed, winner_thread, my_num, threads,e, client_count
    while True:
        e.wait()
        for t in threads:
            t.join()
        print("all threads are finished now")
        e.clear()
        mylock.acquire()
        threads = []
        client_guessed = False
        winner_thread=-1
        client_count = 0
        my_num = random.randint(start,stop)
        mylock.release()


if __name__=='__main__':
    try:
        rs=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rs.bind( ('0.0.0.0',7777) )
        rs.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)
    t=threading.Thread(target=resetSrv, daemon=True)
    t.start()
    while True:
        client_socket,addrc = rs.accept()
        t = threading.Thread(target=client, args=(client_socket,) )
        threads.append(t)
        client_count+=1
        t.start()