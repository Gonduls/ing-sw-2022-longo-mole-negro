# ing-sw-2022-longo-mole-negro

## Grade
30 cum laude

## Gruppo 3
 Virginia Longo
 
 Marco Molè
 
 Pierluigi Negro
 
 
 
## Implemented Functionalities
- Basic rules
- Advanced rules
- CLI
- GUI
- Multiple Games
- 4 Player Games
- All cards implemented in "expert mode"

## How to run the game

### Server

``` bash
java -jar PSP3-eriantys.jar [-d] -s PORT
```

- The optional flag -d will start the "debug mode", which logs information in a "Server.txt" file in append mode, creating the file in the current working directory if not already present. The information logged regards all messages sent to any client.
- The parameter port must be a valid port number, we advise for port 9999. If no socket can be opened at the given port, the program will automatically halt.

### CLI

``` bash
java -jar PSP3-eriantys.jar [-d] -c -cli
```

### GUI

``` bash
java -jar PSP3-eriantys.jar [-d] -c -gui
```

### Alternative Methods

The jar file can also be run without specifying any flags, by running:

``` bash
java -jar PSP3-eriantys.jar 
```

In this case, the program itself will ask if a server instance or a client has to be run. The server instance will automatically start listening for a tcp connection at port 9999, the client instance will ask how to play the game, if from a CLI environment or a GUI environment.
