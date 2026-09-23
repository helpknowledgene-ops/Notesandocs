Experiment 6 - Remote Command Execution Using Java

Files:
- RemoteCommandServer.java
- RemoteCommandClient.java
- Experiment-6.docx

Compile:
  javac RemoteCommandServer.java
  javac RemoteCommandClient.java

Run server first:
  java RemoteCommandServer

Then run client:
  java RemoteCommandClient

Default connection: localhost:5000
For LAN testing, replace localhost in RemoteCommandClient.java with the server IP address.

Educational/laboratory use only. Do not expose arbitrary remote command execution to untrusted networks.
