
💳 ATM Banking Service (Spring Boot & Vanilla JS)
🌟 Project Overview
This is a modern, token-based RESTful API service simulating core ATM operations: Authentication, Balance Check, Deposit, Withdrawal, Transfer, and Transaction History.

The backend is built with Spring Boot and Spring Data JPA, utilizing a service-repository pattern with transactional safety. The frontend is a simple, single-page application built with HTML, CSS, and vanilla JavaScript served directly as static resources by the Spring Boot application.

### Key Features

* **Token-Based Session Management:** Custom `X-ATMTOKEN` header for secure, stateless API access (managed by `SessionService`).
* **Transactional Integrity:** All financial operations (`deposit`, `withdraw`, `transfer`) are marked `@Transactional` to ensure atomicity.
* **Centralized Error Handling:** Uses `GlobalExceptionHandler` to catch business logic errors (`IllegalArgumentException` like insufficient balance) and return a standardized `400 Bad Request` JSON response.
* **Localized Frontend:** All financial displays use the **Rupee (₹)** currency format.

### Key Technologies

| Category | Technology | Description |
| :--- | :--- | :--- |
| **Backend** | Spring Boot 3+ | Core framework for REST API |
| **Persistence** | Spring Data JPA | Data access layer |
| **Database** | H2 (Default) | In-memory database for quick setup |
| **Language** | Java 17+ | Programming language |
| **Frontend** | HTML5, CSS3, Vanilla JavaScript | Simple, single-page client served statically |

---

## 🛠️ Setup and Installation

### Prerequisites

1.  Java 17 or higher
2.  Maven

### Running the Application

1.  **Clone the repository** (or ensure all files are in your local directory).
2.  **Configuration**: The application uses an in-memory **H2 database** by default.
3.  **Build the Project**:
    ```bash
    mvn clean install
    ```
4.  **Run the Application**:
    ```bash
    mvn spring-boot:run
    ```
    The application will start, serving the API on `http://localhost:8080/api` and the frontend client on `http://localhost:8080/`.

### Initial Data (For Testing)

For testing purposes, you will need at least one user in your database. Ensure a user exists with:

| Username | PIN | Initial Balance |
| :--- | :--- | :--- |
| **`testuser`** | **`1234`** | (e.g., `1000.00`) |

---

## 🚀 Usage (Frontend Client)

1.  Open your browser and navigate to: **`http://localhost:8080/`**
2.  **Login**: Enter the username and PIN.
3.  **Operations**: Once logged in, the UI displays your current balance (in **₹**) and allows you to switch between Deposit, Withdraw, Transfer, and History panels.

### Frontend Structure

The client is kept simple, residing in the static directory: