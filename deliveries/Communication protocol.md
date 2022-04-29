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

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 5. GetPublicRooms

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 6. PublicRooms

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 7. AccessRoom

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 8. AddPlayer

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 9. StartGame

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 10. MoveStudent

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 11. AddStudentTo

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 12. ActivateCard

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 13. SetProfessorTo

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 14. ChangePhase

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 15. EndGame

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 16. GameEvent

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent


### 17. LeaveRoom

Description of the message goes here.

#### Arguments

 - Argument1: description of the argument
 - Argument2: description of the argument
 - Argument3: description of the argument

#### Possible responses

 - ResponseMessageName: condition in which this response is sent




### MessageName

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

