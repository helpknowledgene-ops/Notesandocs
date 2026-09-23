Experiment 7 - HTTP Web Page Upload & Download Using Java

Required files:
1. HTTPServer.java
2. HTTPDownloadClient.java
3. HTTPUploadClient.java
4. Experiment-7.docx

Compilation:
javac HTTPServer.java
javac HTTPDownloadClient.java
javac HTTPUploadClient.java

Run the server first:
java HTTPServer

Then, in separate terminals:
java HTTPDownloadClient
java HTTPUploadClient

The server listens on port 8080.

For two-computer LAN testing, replace localhost in both client programs
with the server computer's IP address, for example:
new Socket("192.168.1.100", 8080);

The server creates UploadedPage.html after a successful POST upload.
