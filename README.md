
---

# Task Management REST API

This is a **Java Spring Boot** application that provides a **REST API** for managing and executing “Task” objects.
Each Task represents a **shell command** that can be executed, tracked, and stored along with its execution history in **MongoDB**.

---

## Features

* **Create a Task** – Add new tasks with ID, name, owner, and command.
* **Get All Tasks** – Retrieve all stored tasks.
* **Get Task by ID** – Retrieve a specific task by its ID.
* **Search Tasks by Name** – Find tasks whose names contain a given substring.
* **Delete Task** – Remove a task using its ID.
* **Execute a Task Command** – Run the shell command of a task and store execution details (start time, end time, output).
* **MongoDB Integration** – All data is stored in a MongoDB database.

---

## Technologies Used

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Web**
* **Spring Data MongoDB**
* **Lombok**
* **Maven**
* **Postman / curl (for testing)**

---

## Project Structure

```
src/main/java/com/example/taskmanager
│
├── controller
│   └── TaskController.java
│
├── model
│   ├── Task.java
│   └── TaskExecution.java
│
├── repository
│   └── TaskRepository.java
│
├── service
│   └── TaskService.java
│
└── TaskManagerApplication.java
```

---

## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/<your-username>/<your-repo-name>.git
cd <your-repo-name>
```

### Configure MongoDB

Make sure MongoDB is running locally or in the cloud (e.g., MongoDB Atlas).
Update your `application.properties` (or `application.yml`) file:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/taskdb
spring.data.mongodb.database=taskdb
server.port=8080
```

### Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on **[http://localhost:8080](http://localhost:8080)**

---

## API Endpoints

### 1. **GET /tasks**

Retrieve all tasks or a single task by ID.

**Examples:**

```bash
# Get all tasks
curl -X GET http://localhost:8080/tasks

# Get task by ID
curl -X GET "http://localhost:8080/tasks?id=123"
```

---

### 2. **PUT /tasks**

Create or update a task.

**Request Body:**

```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!"
}
```

**Example:**

```bash
curl -X PUT http://localhost:8080/tasks \
-H "Content-Type: application/json" \
-d '{"id":"123","name":"Print Hello","owner":"John Smith","command":"echo Hello World!"}'
```

---

### 3. **DELETE /tasks/{id}**

Delete a task by its ID.

**Example:**

```bash
curl -X DELETE http://localhost:8080/tasks/123
```

---

### 4. **GET /tasks/search?name=Print**

Search tasks by partial name.

**Example:**

```bash
curl -X GET "http://localhost:8080/tasks/search?name=Print"
```

---

### 5. **PUT /tasks/{id}/execute**

Execute the task’s command and record its output and timestamps.

**Example:**

```bash
curl -X PUT http://localhost:8080/tasks/123/execute
```

**Sample Response:**

```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!",
  "taskExecutions": [
    {
      "startTime": "2023-04-21T15:51:42.276Z",
      "endTime": "2023-04-21T15:51:43.276Z",
      "output": "Hello World!"
    }
  ]
}
```

---

## 🔒 Command Validation

Before executing any command, the application **validates** it to prevent unsafe or malicious commands.
Commands like `rm -rf`, `shutdown`, `reboot`, or any file manipulation commands are **rejected**.

---

## Example JSON (Sample Task)

```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World again!",
  "taskExecutions": [
    {
      "startTime": "2023-04-21T15:51:42.276Z",
      "endTime": "2023-04-21T15:51:43.276Z",
      "output": "Hello World!"
    },
    {
      "startTime": "2023-04-21T15:52:42.276Z",
      "endTime": "2023-04-21T15:52:43.276Z",
      "output": "Hello World again!"
    }
  ]
}
```

---

## Testing

You can test using:

* **Postman**
* **curl**
* **HTTPie**

Include screenshots of your Postman requests/responses in your project’s GitHub repo under `/screenshots`.

---

## Example Screenshots

---

| **Endpoint**                | **Method** | **Description / Functionality**                                                        | **Screenshot**                                             |
| --------------------------- | ---------- | -------------------------------------------------------------------------------------- | ---------------------------------------------------------- |
| `/tasks`                    | **GET**    | Returns all tasks if no parameters are passed.                                         | ![Get All Tasks](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/Get%20All.png)                |
| `/tasks/{id}`               | **GET**    | <span style="color:green;font-weight:bold;"> Task found – returns task details</span> | ![Get Task By Id](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/Get%20By%20Id.png)          |
| `/tasks/{id}`               | **GET**    | <span style="color:red;font-weight:bold;"> 404 Not Found – task doesn’t exist</span>  | ![Get Task By Id](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/id%20not%20found.png) |
| `/tasks`                    | **PUT**    | Creates or updates a task with safe command validation.                                | ![Create Task](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/ADD.png)                   |
| `/tasks/{id}/execute`       | **PUT**    | Executes a task’s shell command and stores the result.                                 | ![Execute Task](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/Exected.png)              |
| `/tasks/{id}`               | **DELETE** | Deletes a task by its ID.                                                              | ![Delete Task](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/Delete.png)                |
| `/tasks/find?name={string}` | **GET**    | Finds tasks whose names contain the given string.                                      | ![Find Tasks By Name](https://github.com/purnachandu12/Kaiburr-task-1/blob/main/Search%20By%20Name.png)           |

---

