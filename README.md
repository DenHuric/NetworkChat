# NetworkChat
This project is a coursework during my Java development course.<br>
The application consists of 2 modules(Server and Client).

## Server
### Server requirements<br>
• Setting the port for connecting clients through the settings file (for example, settings.txt);<br>
• Ability to connect to the server at any time and join the chat;<br>
• Sending new messages to clients;<br>
• A record of all messages sent through the server, indicating the username and time of sending.<br>

### Classes
#### Server
Server class in which a server socket is created and messages are sent to clients.
#### ClientHAndler
Each user connected to the system is processed in a separate thread, implemented in the ClientHandler class.<br>
This class first message receives from the user his name and writes it to the corresponding field of the class, which is then used when receiving messages from the user.<br>
Further in the loop, the client's messages are read and sent to other chat participants.<br>
If the client sends an exit message, he will be removed from the chat, other users will receive a message that the user has exited.
#### Logger
The class creates a folder in the user's home directory, where it puts file.log, where all events are logged.
#### Settings
The class parses the settings file "Settings.xml" and puts the values into variables.


## Client
### Client Requirements<br>
• Choosing a name to participate in the chat;<br>
• Read application settings from settings file - for example, server port number;<br>
• Connection to the server specified in the settings;<br>
• To exit the chat, you need to type the exit command - “/exit”;<br>
• Each participant's message must be written to a text file - a logging file. Each time the application is launched, the file must be updated.<br>

### Classes
#### Client
Creates a class object that connects to the server and sends messages to it.<br>
Reception of messages from the server is implemented in a separate thread ClientInputThread.<br>
#### ClientInputThread
Separate class for the thread listening to the server.
#### Logger
The class creates a folder in the user's home directory, where it puts file.log, where all events are logged.
#### Settings
The class parses the settings file "Settings.xml" and puts the values into variables.
