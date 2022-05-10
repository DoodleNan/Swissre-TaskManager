# SwissRe - Task Manager

## Problem
With Task Manager (TM) we refer to a software component that is designed for handling multiple processes inside an operating system. 
Each process is identified by 2 fields, a unique unmodifiable identifier (PID), and a priority (low, medium, high).

## What is it
TaskManager is a spring application to manipulate tasks. It is built with maven.

## How to use
### Prerequisites
- JDK 8 & JRE installed
- Maven installed

All dependencies/executables are packaged in the application. You should be able to build and run it from any unix-like systems.

### Start the application
To reduce starting time we don't run unit tests on start the server.
It takes around half a sec to start the application
We will cover unit test in section below.
1. From command line:
   Navigate to root location of application where we define pom.xml, and execute:
```
 mvn spring-boot:run -Drun.arguments=--queue.capacity={capacity}
 mvn spring-boot:run -Dspring-boot.run.arguments=--queue.capacity=2
```
You need to specify the capacity of this Task Manager, i.e, how many processes/tasks
it can hold. The default value is 4.

2. From IDE(Intellij):

   Add configurations of command line additional input, and simple click "run" button, or navigate to `TaskManagerApplication.java` class, 
   right click it and choose
   `run TaskManagerApplication`.

### Sending http request to application
Once the application is running, i.e, you see something like this from console
` Started TaskManagerApplication in 0.956 seconds (JVM running for 1.165)`
the application is ready to accept http request and serve the queries.

The application is integrated with post man, you can import into postman from {ProjectRoot}/postman/swissre-task-manager.postman_collection.json

There are 5 APIs: 1). addProcess 2). listProcesses. 3).killProcess 4).killAllProcess 5).killGroupProcesses

### addProcess
The application accepts process metadata and add process strategy as input, 
and will return process adding result. Example curl command:

#### Simple Add
We can addProcess until capacity is full

Example curl command:

```
curl --location --request POST 'http://localhost:8080/addProcess?strategy=simple' \
--header 'Content-Type: application/json' \
--data-raw '{
    "command": ["java", "-h"],
    "priority": "high"
}'
```
Example output:
```
Successfully added a new process: Process(PID=46502, priority=high, creationTimestamp=1652220038071, command=[java, -h], isLive=true)
```

If We have reached the capacity, we will skip the process and return a message:
```
Unable to add the new process because capacity {} is full.
```
where the {capacity} is set up when application starts.

#### Priority Based add
every call to the add() method, when the max size is reached, should result into an evaluation: 
if the new process passed in the add() call has a higher priority compared to any of the existing one, 
we remove the lowest priority that is the oldest, otherwise we skip it.

Example curl command:
````
curl --location --request POST 'http://localhost:8080/addProcess?strategy=priority' \
--header 'Content-Type: application/json' \
--data-raw '{
    "command": ["java", "-h"],
    "priority": "high"
}'

````

Example output:
```
Successfully added a new process: Process(PID=83104, priority=high, creationTimestamp=1652264980846, command=[java, -h], isLive=true)

```
If we have reached the capacity limit, the program will automatically kick out the lowest priority
one if it has lower priority than the newly added one, otherwise we do nothing.

#### FIFO Based add
Accept all new processes through the add() method, killing and removing from the TM list the oldest one (First-In, First- Out) when the max size is reached

Example curl command:
````
curl --location --request POST 'http://localhost:8080/addProcess?strategy=fifo' \
--header 'Content-Type: application/json' \
--data-raw '{
    "command": ["java", "-h"],
    "priority": "high"
}'

````

Example output:
```
Successfully added a new process: Process(PID=83251, priority=high, creationTimestamp=1652265045255, command=[java, -h], isLive=true)
```

If we have reached the capacity limit, the program will automatically kick out the oldest process
and add our new process

### listProcesses
The application accepts list strategy and return sorted list of all live running processes.

#### List by priority
Example curl command:
```
curl --location --request GET 'http://localhost:8080/listProcesses?strategy=priority' \
--header 'Content-Type: application/json'
```

Example output:
```
[
    {
        "priority": "high",
        "creationTimestamp": 1652220251360,
        "command": [
            "java",
            "-h"
        ],
        "pid": 46608,
        "live": true
    },
    {
        "priority": "low,
        "creationTimestamp": 1652220038071,
        "command": [
            "java",
            "-h"
        ],
        "pid": 46502,
        "live": true
    }
]
```
#### List by timestamp
Example curl command:
```
curl --location --request GET 'http://localhost:8080/listProcesses?strategy=time' \
--header 'Content-Type: application/json'
```

Example output:
```
[
    {
        "priority": "low,
        "creationTimestamp": 1652220038071,
        "command": [
            "java",
            "-h"
        ],
        "pid": 46502,
        "live": true
    },
    {
        "priority": "high",
        "creationTimestamp": 1652220251360,
        "command": [
            "java",
            "-h"
        ],
        "pid": 46608,
        "live": true
    }
]
```

#### List by PID
Example curl command:
```
curl --location --request GET 'http://localhost:8080/listProcesses?strategy=pid' \
--header 'Content-Type: application/json'
```

Example output:
```
[
    {
        "priority": "low,
        "creationTimestamp": 1652220038071,
        "command": [
            "java",
            "-h"
        ],
        "pid": 46502,
        "live": true
    },
    {
        "priority": "high",
        "creationTimestamp": 1652220251360,
        "command": [
            "java",
            "-h"
        ],
        "pid": 46608,
        "live": true
    }
]
```

### killProcess
The application accepts process to be killed as input, kill it, remove from livePrcoss list
under TaskManager. It will return the result of execution.

Example curl command:
```
curl --location --request POST 'http://localhost:8080/killProcess?PID=46502' \
--header 'Content-Type: application/json'

```

Example output:
```
Successfully kill process with PID 46502

```
Verify by calling listProcesses api again, the output is:
```
[
   {
      "priority":"high",
      "creationTimestamp":1652220251360,
      "command":[
         "java",
         "-h"
      ],
      "pid":46608,
      "live":true
   }
]

```

### killAllProcess
The application will try to kill all processes under TaskManager.

Example curl command:
```
curl --location --request POST 'http://localhost:8080/killAllProcesses' \
--header 'Content-Type: application/json'

```

Example output:
```
Killed all processes successfully
```

Verify by calling listProcesses api again, the output is empty list:
```
[]
```

### Kill GroupProcess
The application will try to kill processes with the given groupId

Example curl command
```
curl --location --request POST 'http://localhost:8080/killGroupProcesses?PGID=46608' \
--header 'Content-Type: application/json'

```
Example output:
```
Killed group processes successfully with GPID 46608.

```

### Terminate the application
You can terminate the application by keyboard input or IDE stop button. The processes 
under TaskManager will all be lost.

### Exception Handling
I implemented two custom exception for a better user experience -- 
`ProcessCreationException` and `ProcessKiller`. For development purpose it will display detailed error message
and trace of the execution. If we want to deploy the software to prod we should never expose
those detailed trace to customer! 

## Design ideas
1. I choose Spring boot framework. Spring has many 
nice features for dependent injection as well as restful binding, which fits into our use case. Also
I also choose it because I am familiar wit it and comfortable with it.
2. To fulfill different add process use case, I apply `strategy patter` where we ask client to
specify the strategy and we handle it separately. Add more patterns in the future would be as simple
as adding an implementation of `AddProcessManager` interface
3. The business logic is already complicated, for single responsibility concern I apply `factory pattern` that 
decouple object creation logic to factory so that we won't be bothered by concrete object creation changes later.
4. Similar to dynamic add process strategy, I apply strategy pattern to list process implementation too.
5. Use dependent injection for easier unit test and clean logic
6. Use command line input to specify capacity of Task Manager at runtime
7. Integrate Lomlok to save code from constructor/getter/setter etc
8. Integrate with post man to enable easier testing

## Unit test

### Run all unit test
```
mvn test
```

### Run a single/multiple unit test
```
mvn -Dtest=path1/to/test, path2/to/test test
```
### Run a single test method from a test class
```
mvn -Dtest=path1/to/test#methodname test
```
### Jacoco Integration
You can find Code coverage report under {projectRoot}/target/site/jacoco folder.

## Improvement Ideas
1. A better Input validation from command line for capacity, and input validation 
for request from client(process body, strategy passed etc)
2. We should have a better error handing model, categorize different errors(
e.g. add process timeout/command not valid/missing input, process not exist etc)
and return more info to client
3. Enable asending/descending sorting, ideally per user input.
4. Define timeout and establish an SLA, as the start/kill process may take unforeseeable time,
which is not a good customer experience.
5. We store everything in memory for this simple project. Once TaskManager grows we can consider connect to a
database to store the process info. Not only the PID/Priority/Timestamp but more process
related metadata. It is also useful for debugging and business analysis purpose
6. Once the application terminates all processes will be lost. If we want to persist the
session a data storage and automatic data recovery mechanism can be introduced
7. We can expose some APIs to tell client what's the performance/IOPS/other stats of our CPU
So that they can decide if they want to add more processes or kill some unused ones
8. We could even integrate with some machine learning model, scan and detect those processes
that consume lots of resources or running for a looooong time then inform our customer







    