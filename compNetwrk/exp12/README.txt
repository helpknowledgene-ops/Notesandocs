Experiment 12 - Simulation of a DNS Server Using Java

Files:
- DNSServer.java
- DNSClient.java
- Experiment-12.docx

Compile:
javac DNSServer.java
javac DNSClient.java

Run server first:
java DNSServer

Then run client in another terminal:
java DNSClient

The server uses port 5000 and a predefined HashMap DNS table. Enter a domain name in the client to receive its mapped IP address. Enter exit to terminate.

For two computers on the same LAN, replace localhost in DNSClient.java with the server computer's IP address.
