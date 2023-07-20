import socket
import struct
import sys
from random import random

if __name__=='__main__':
    try:
        tcp_socket=socket.socket()

        ip="192.168.1.186"
        port=7777

        adress=(ip,port)

        tcp_socket.connect(adress)
    except socket.error as msg:
        print("Error",msg.strerror)
        exit(-1)

    while True:
        number_of_digits=struct.unpack("!I",tcp_socket.recv(4))[0]
        print("digits: ",number_of_digits)

        poz=input("poz:")
        tcp_socket.send(b"poz")

        guessed_pos = []






