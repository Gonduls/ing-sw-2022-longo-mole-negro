# Eryantis Protocol Documentation

Virginia Longo, Marco Molè, Pierluigi Negro

Group 3

## Messages

### 1. Acknowledgment (ack)

This message is sent from the server to the client when a generic message has been acknowledged.

#### Arguments

This message has no arguments.

#### Possible responses

This message has no responses.

### 2. Negative Acknowledgment (nack)

This message is sent from the server to the client when a generic action was not permitted. It is interpreted from the network handler depending on scenarios.

#### Arguments

- ErrorMessages: the entity of the error

#### Possible responses

This message has no responses.

### 3. Login

This message is sent from the client to the server after establishing a connection to create a player session.

#### Arguments

 - Username: the unique name that will identify a pLayer

#### Possible responses

 - Ack: the name is unique and the user has been logged
 - Nack: the name is not unique and the user has not been logged


### 4. CreateRoom

This message is sent from the client to the server to create and access a new room.

#### Arguments

 - Number of players: the number of players that will participate in the game (2, 3, 4)
 - Expert: flag that sets the game to expert if true
 - Private: flag that sets the room to private if true

#### Possible responses

 - Ack: when the room was correctly created
 - Nack: when there were errors in the making of the room

### 5. RoomId

This message is sent from the server to the client that creates a game, in order to communicate the roomId. This information is necessary in order to being able to access a game.

#### Arguments

 - roomId: the room identifier

#### Possible responses

 This message has no responses


### 6. GetPublicRooms 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 7. PublicRooms 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 8. AccessRoom 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 9. AddPlayer 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 10. StartGame 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 11. MoveStudent 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 12. AddStudentTo 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 13. ActivateCard 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 14. SetProfessorTo 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 15. ChangePhase 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 16. EndGame 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 17. GameEvent 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 18. LeaveRoom 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


## Scenarios

### Name of the scenario

(Add sequence diagram picture here)
<img src="image_file_name.png">

Description of what happens in this scenario.

