# 🚆 IRCTC Train Booking System

A Java-based **Train Ticket Booking System** developed as a console application using **Java 17+, Gradle, Jackson, BCrypt, and JSON-based data persistence**.

The application provides core railway booking functionality including **user registration, authentication, train search, route validation, train selection, seat availability, seat booking, booking retrieval, and booking cancellation**.

The project follows a modular architecture with separate **Entity, Service, and Utility layers**, making the application easier to maintain, understand, and extend.

---

## 📌 Project Overview

The **IRCTC Train Booking System** is a console-based Java backend project that simulates the basic workflow of a railway ticket booking application.

Users can interact with the application through a command-line menu to perform operations such as:

- Create an account
- Login
- Search trains
- Select a train
- View available seats
- Book a seat
- View existing bookings
- Cancel bookings
- Exit the application

Train and user information is persisted locally using JSON files.

---

# 🛠️ Tech Stack

| Technology / Tool | Purpose |
|---|---|
| **Java 17+** | Core programming language |
| **Gradle** | Build automation and dependency management |
| **Jackson** | JSON serialization and deserialization |
| **BCrypt** | Password hashing and authentication |
| **UUID** | Unique user and ticket ID generation |
| **JSON** | Local data persistence |
| **Java Collections** | Managing users, trains, tickets, and seats |
| **Java Streams API** | Searching and filtering data |
| **Lambda Expressions** | Functional collection processing |
| **File I/O** | Reading and writing JSON files |
| **IntelliJ IDEA** | Development environment |
| **Git** | Version control |
| **GitHub** | Source code hosting |

---

# ✨ Key Features

### 👤 User Management

- User registration
- User login
- Unique UUID-based user IDs
- Password hashing using BCrypt
- Password verification
- User booking history

### 🚆 Train Management

- Search trains by source and destination
- Case-insensitive station search
- Route validation
- Station sequence validation
- Train information display
- Station timing display
- Train data persistence

### 💺 Seat Management

- Display available seats
- Row and column based seat selection
- Seat validation
- Prevent booking of already occupied seats
- Persist updated seat availability

### 🎫 Booking Management

- Book a train seat
- Fetch user bookings
- Cancel bookings
- Generate ticket IDs
- Store train and journey information

### 💾 Data Persistence

- JSON-based local database
- Jackson ObjectMapper
- Persistent train information
- Persistent user information
- Persistent seat availability

---

# 🏗️ Application Architecture

The project follows a simple layered architecture.

```text
                         ┌───────────────────────┐
                         │       App.java        │
                         │                       │
                         │ Console User Interface│
                         └───────────┬───────────┘
                                     │
                                     ▼
                       ┌─────────────────────────┐
                       │  UserBookingService    │
                       │                         │
                       │ User Management         │
                       │ Booking Management      │
                       │ Seat Booking            │
                       │ Train Search            │
                       └────────────┬────────────┘
                                    │
                         ┌──────────┴──────────┐
                         │                     │
                         ▼                     ▼
                ┌─────────────────┐   ┌──────────────────┐
                │  TrainService   │   │ UserServiceUtil  │
                │                 │   │                  │
                │ Train Search    │   │ BCrypt Hashing   │
                │ Route Validation│   │ Password Check   │
                │ Train Updates   │   │                  │
                │ Data Persistence│   │                  │
                └────────┬────────┘   └──────────────────┘
                         │
                         ▼
                ┌────────────────────┐
                │     Local JSON     │
                │      Storage       │
                │                    │
                │   trains.json      │
                │   users.json       │
                └────────────────────┘
