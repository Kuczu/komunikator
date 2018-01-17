import socket
import base64

TCP_IP = '127.0.0.1'
TCP_PORT = 7777
BUFFER_SIZE = 1024
MAGIC_START = "MAGIC_START_REQUEST: "
MAGIC_STOP = "MAGIC_STOP.\n"
#MAGIC_STOP = "MAGIC_STOP."

PATH = "/register"

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))

# data = s.recv(BUFFER_SIZE)
# s.send(str.encode(MAGIC_START))

readed_from_terminal = ""
#for i in range(500):
	#readed_from_terminal += input('napisz wiadomosc: ')
msg = str.encode(MAGIC_START) + base64.b64encode(str.encode("{'apiPath':'/register', 'data': 'dupsko'}")) + b' ' + str.encode(MAGIC_STOP)
print(msg)
print(len(msg))
s.send(msg)

#s.send(str.encode(MAGIC_STOP))

data = s.recv(BUFFER_SIZE)
print(data)

_, d, _ = str(data, 'utf-8').split(" ")

d = base64.b64decode(bytes(d, 'utf-8'))

print(d)

s.close()