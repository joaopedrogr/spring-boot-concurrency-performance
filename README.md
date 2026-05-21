# Spring Boot Concurrency Performance

Modern applications need to handle multiple tasks simultaneously: processing thousands of requests, consuming external APIs, reading large files, and executing parallel operations. Without understanding concurrency, your code will be slow, fragile, and hard to scale.

This project walks through the evolution of Java's threading model, from incorrect ways of creating threads to best practices, covering the most modern features the platform has to offer.

---

## What you will learn

Topics are presented in progression: each concept lays the groundwork for the next.

### 1. Manual Threads and why to avoid them

Understand how a Thread works internally, what problems arise when creating them manually (resource leaks, lack of control, monitoring difficulties), and why the recommendation is to never do this in production.

### 2. Thread Pools with ExecutorService

Learn how to manage threads with reusable pools. The project covers the four main types:

| Pool | When to use |
|---|---|
| FixedThreadPool | CPU-intensive tasks (fixed number of workers) |
| CachedThreadPool | Short tasks with many simultaneous requests |
| SingleThreadExecutor | Guaranteed sequential execution |
| CustomThreadPoolExecutor | Fine-grained control: pool size, queue, rejection policy |

### 3. Scheduled Tasks with ScheduledExecutorService

Execute tasks in the future or on a recurring basis. Understand the difference between:

- `scheduleAtFixedRate` — fires at fixed intervals regardless of execution time
- `scheduleWithFixedDelay` — waits for the task to finish before counting the next interval

### 4. Virtual Threads (Java 21+)

The biggest advancement in Java's concurrency model in decades. See in practice how Virtual Threads enable running 200,000 concurrent tasks with minimal memory consumption, making them ideal for I/O-bound workloads such as database calls and external API requests.
### 5. Async Programming with CompletableFuture

Write asynchronous code in a readable and functional style. The project covers two fundamental patterns:

- **thenCompose** — chaining dependent operations (fetch user → fetch profile)
- **thenCombine** — combining independent operations in parallel (order history + shipping preferences)

### 6. File Reading: I/O vs NIO vs NIO.2

Practical benchmark comparing three file-reading strategies with 1,000,000 lines:

| Strategy | API | Characteristic |
|---|---|---|
| Traditional I/O | BufferedReader | Simple, blocking |
| NIO | FileChannel + ByteBuffer | Efficient with memory buffers |
| NIO.2 | Files.lines() + Streams | Modern, functional, concise |

Each strategy processes a financial transactions CSV and returns performance metrics (execution time, throughput, fraud count).

---

## Prerequisites

- Java 21 or higher (the project uses Java 25)
- Maven 3.8+
- IDE of your choice (IntelliJ IDEA recommended)

---

## How to run

**1. Clone the repository**
```bash
git clone https://github.com/joaopedrogr/spring-boot-concurrency-performance.git
cd spring-boot-concurrency-performance
```

**2. Download the financial transactions dataset**

The file reading examples use a real fraud detection dataset. Download it at:

> [https://www.kaggle.com/datasets/ealaxi/paysim1](https://www.kaggle.com/datasets/ealaxi/paysim1)

After downloading, save the file as `transactions.csv` at:

```
src/main/resources/transactions.csv
```
**3. Run the application**
```bash
./mvnw spring-boot:run
```

The application starts on port **8080**. Access the interactive endpoint documentation at:

```
http://localhost:8080/swagger-ui.html
```

---

## Exploring the examples

Use Swagger UI (or any REST client such as Postman or curl) to trigger each example:

### Threads

| Method | Endpoint | Example |
|---|---|---|
| POST | `/threads/manual` | Manually created thread |
| POST | `/threads/fixed` | Fixed Thread Pool (CPU-bound) |
| POST | `/threads/schedule` | Scheduled tasks |
| POST | `/threads/virtual` | 200,000 Virtual Threads |

### CompletableFuture

| Method | Endpoint | Example |
|---|---|---|
| POST | `/completable-future/example1` | Chaining with thenCompose |
| POST | `/completable-future/example2` | Combining with thenCombine |

### File Reading

| Method | Endpoint | Strategy |
|---|---|---|
| POST | `/transactions/io` | Traditional I/O |
| POST | `/transactions/nio` | NIO with FileChannel |
| POST | `/transactions/nio2` | NIO.2 with Streams |

---

## Project structure

```
src/main/java/br/com/example/springbootconcurrencyperformance/
├── threads/              # Thread Pool and Virtual Threads examples
├── completableFuture/    # Asynchronous programming examples
├── service/              # File reading strategies (I/O, NIO, NIO.2)
├── controller/           # REST endpoints that expose the examples
└── model/                # DTOs and Records (Transaction, BenchmarkResultDTO)
```
