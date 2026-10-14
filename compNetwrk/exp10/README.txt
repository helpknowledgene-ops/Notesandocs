Experiment 10 - TELNET (Remote Login) Using Java

Required files:
- TelnetServer.java
- TelnetClient.java
- Experiment-10.docx

Compile:
javac TelnetServer.java
javac TelnetClient.java

Run the server first:
java TelnetServer

Then run the client:
java TelnetClient

For two computers on a LAN, replace localhost in TelnetClient.java
with the server computer's IP address.

Example:
Socket socket = new Socket("192.168.1.100", 6000);

Note:
This is a TELNET-like educational implementation. It sends data in
plain text and does not provide authentication or encryption.
