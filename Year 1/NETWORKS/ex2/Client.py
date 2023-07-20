import select
import socket
import struct
import sys


def clientSelect(client, bufferSize):
    inputs = [0, client]

    outputs = []

    messageQueue = []

    while inputs:
        print('\nwaiting for next event')
        readable, writable,_ = select.select(inputs, outputs, [])

        # Handle inputs
        for s in readable:
            # Received a message in the socket (from the server)
            if s == client:
                message = client.recv(bufferSize)
                if message:
                    '''
                    senderIP = client.recv(bufferSize)
                    senderPort = struct.unpack('!H', client.recv(8))
                    sender = (senderIP.decode(), senderPort)
                    print(client.getsockname(), ' : received ', message.decode(), ' from ', sender)
                    '''
                    print(client.getsockname(), ' received ', message.decode())
                else:
                    print(' closing socket ', client.getsockname())
                    inputs.remove(s)
                    client.close()
                    break
            # User input a message and we want to send it to the server
            elif s == 0:
                message = sys.stdin.readline()
                if message:
                    client.send(message.encode())

        # Handle outputs
        for s in writable:
            pass

        for s in exceptional:
            pass


def receiveMessage(client, bufferSize):
    message = client.recv(bufferSize)
    senderIP = client.recv(bufferSize)
    senderPort = struct.unpack('!i', client.recv(4))
    sender = (senderIP.decode(), senderPort.decode())
    print(client.getsockname(), ' : received ', message.decode(), ' from ', sender)
    if not message:
        print(' closing socket ', client.getsockname())
        client.close()
        return False
    return True


def sendMessage(client, bufferSize):
    message = input()

    # Send message
    print(client.getsockname(), ' : sending ', message)
    client.send(message.encode())


def clientWorker(client, bufferSize):
    message = input()

    # Send message
    print(client.getsockname(), ' : sending ', message)
    client.send(message.encode())

    # Read responses
    return receiveMessage(client, bufferSize)


def mainClient(serverIP, port, bufferSize):
    serverAddress = (serverIP, port)

    # Create a TCP/IP socket
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect the socket to the port where the server is listening
    print(' connecting to ', serverIP, ' port ', port)
    client.connect(serverAddress)

    clientSelect(client, bufferSize)

    '''
    while(True):
        connectionActive = clientWorker(client, bufferSize)
        if not connectionActive:
            break
    '''


if __name__ == '__main__':
    serverIP, port = ('localhost', 10000)
    bufferSize = 1024
    mainClient(serverIP, port, bufferSize)
