import queue
import select
import socket
import sys


def setupSelect(server, bufferSize):
    # All client sockets
    clients = {}

    # Sockets from which we expect to read
    inputs = [server]

    # Sockets to which we expect to write
    ouputs = []

    # Outgoing message queues (socket:Queue)
    messageQueues = {}

    while inputs:
        # Wait for at least one of the sockets to be ready for processing
        print('\nwaiting for next event')
        readable, writable, exceptional = select.select(inputs, ouputs, inputs)

        # Handle inputs
        for s in readable:

            if s is server:
                # A "readable" server socket is ready to accept a connection
                connection, clientAddress = s.accept()
                print('new connection from', clientAddress)
                connection.setblocking(0)
                inputs.append(connection)

                # Give the connection a queue for data we want to send
                messageQueues[connection] = queue.Queue()

                # Add the client socket to the clients
                clients[connection] = 0

            else:
                data = s.recv(bufferSize)
                if data:
                    # A readable client socket has data
                    data = data.decode()
                    print('recieved ', data, ' from ', s.getpeername())

                    for client in clients:
                        if client != s:
                            # Put the data + the sender in all the other client queues
                            messageQueues[client].put((data, s.getpeername()))

                            # Add output channel for response
                            if client not in ouputs:
                                ouputs.append(client)

                else:
                    # Interpret empty result as closed connection
                    print('closing ', clientAddress, ' after reading no data')
                    # Stop listening to input on the connection
                    inputs.remove(s)
                    s.close()

                    # Remove message queue
                    del messageQueues[s]

                    # Remove the client from the clients 'dictionary'
                    del clients[s]

        # Handle outputs
        for s in writable:
            try:
                nextMessage = messageQueues[s].get_nowait()
            except queue.Empty:
                # No messages waiting so stop checking for writability.
                print(' output queue for ', s.getpeername(), ' is empty')
                ouputs.remove(s)
            else:
                message = (nextMessage[1][0] + ':' + str(nextMessage[1][1]) + 'sent::: ' + nextMessage[0])
                messageToSend = message.encode()
                '''
                message = nextMessage[0].encode()
                senderIP = nextMessage[1][0].encode()
                senderPort = struct.pack('!H', nextMessage[1][1])

                messageLen = len(message)
                senderIPLen = len(senderIP)
                '''

                print('sending ', message, ' to ', s.getpeername())

                # Send message + sender info to all the clients except for sender
                s.send(messageToSend)

                # We sent the message, so we remove the client from the outputs
                ouputs.remove(s)

        # Handle exceptional conditions
        for s in exceptional:
            print(sys.stderr, ' handling exceptional condition for ', s.getpeername())
            # Stop listening for input on the connection
            inputs.remove(s)
            if s in ouputs:
                ouputs.remove(s)
            s.close()

            # Remove message queue
            del messageQueues[s]

            del clients[s]


def mainServer(IP, port, bufferSize):
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setblocking(0)

    serverAddress = (IP, port)
    print('starting up on ', IP, ' port ', port)
    # server.close()
    server.bind(serverAddress)

    server.listen(5)

    setupSelect(server, bufferSize)


if __name__ == "__main__":
    IP, port = ('localhost', 10000)
    bufferSize = 1024
    mainServer(IP, port, bufferSize)
