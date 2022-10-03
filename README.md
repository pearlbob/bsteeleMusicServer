# bsteeleMusicServer

WebSocket server for the  bsteeleMusicApp.
See usage at http://www.bsteele.com or http://communityjams.org.
Open source code at https://github.com/pearlbob/bsteeleMusicApp.

The server provides the leader/follower functionality
for an Apache Tomcat based app server.  The leader's app
updates the websocket server on song, location, key and the like.
The server simply distributes this message to the other apps on the network.

Note: Compilation and execution currently requires Java 18.