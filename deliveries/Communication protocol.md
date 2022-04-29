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

This message is sent from the client to the server to get a list of all public rooms currently initializing, along with the number of players that that room will hold and if the game played in the room will be in expert mode.

#### Arguments

This message can have optional parameters to filter through all games:
 - number of players (2, 3, 4): asks for a public game of that many players
 - expert (boolean): asks for a public game in expert mode 

#### Possible responses

 - PublicRooms: if the parameters where not illegal
 - Nack: if number of player is not in (2, 3, 4)

### 7. PublicRooms 

This message is sent from the server to the client after he logs in or in response to a GetPublicRooms message, it holds a list of all public games (or the ones that meet the criterias of the request) and their relative information.

#### Arguments

 - publicRooms: a collection that holds the information about the rooms

#### Possible responses

This message has no responses. 


### 8. AccessRoom 

This message is sent from the client to the server, in order to access a room.

#### Arguments

 - roomId: The identifier of the room that the player wants to enter. 

#### Possible responses

 - Ack: if the player has been added to the room
 - Nack: if the room does not exist or if the room is already full


### 9. AddPlayer 

This message is sent from the server to the client, while the client is in a room, in response to players being added to the room.

#### Arguments

 - player: the username of the player added
 - Color: the TowerColor of the player added 
 - Position: the position of the player around the table

#### Possible responses

This message has no responses.

### 10. StartGame 

This message is sent from the server to the client when all playing players have been added to the room. to indicate that the game can start.

#### Arguments

This message has no arguments.

#### Possible responses

This message has no responses.

### 11. MoveStudent 

This message is sent from the server to the client in order to communicate the movement of a student, as a consequence of a GameEvent or an internal event.

#### Arguments

 - from: the place where the student has been taken from
 - to: the place where the student is added
 - color: the color of the student

#### Possible responses

This message has no responses.


### 12. AddStudentTo 

This message is sent from the server to the client in order to communicate the placement of a student, as a consequence of a GameEvent or an internal event.

#### Arguments

 - where: the place where the student is added
 - color: the color of the student

#### Possible responses

 - This message has no responses.


### 13. SetProfessorTo 

This message is sent from the server to the client when the ownership of a professor is changed.

#### Arguments

 - professor: the color of the professor to consider
 - player: the username of the player that now owns the professor

#### Possible responses

 - This message has no responses.


### 14. ActivateCard 

This message is sent from the server to the client in order to communicate the activation of a card.

#### Arguments

 - card: the card that is activated

#### Possible responses

 - This message has no responses.


### 15. AddCoin

This message is sent from the server to the client when a coin is given to a player.

#### Arguments

 - player: the username of the player that receives the coin

#### Possible responses

 - This message has no responses.


### 16. RemoveCoin

This message is sent from the server to the client when a coin is taken from a player.

#### Arguments

 - player: the username of the player that loses the coin

#### Possible responses

 - This message has no responses.


### 17. ChangePhase 

This message is sent from the server to the client when the phase changes.

#### Arguments

 - phase: the phase the game changes into

#### Possible responses

 - This message has no responses.


### 18. ChangeTurn

This message is sent from the server to the client when the turn changes.

#### Arguments

 - player: the username of the player that is now to play

#### Possible responses

 - This message has no responses.


### 19. GameEvent 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 20. EndGame 

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent

### 21. LeaveRoom 

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

