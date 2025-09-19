Train Reservation System
Overview
This project is a simple web-based train reservation system. It allows users to register, log in, search for available trains, book reservations, and view their booking history. The system demonstrates a full-stack application with a Java backend handling business logic and data persistence, and a JavaScript-based frontend providing the user interface.

Key Features
User Authentication: Secure login and registration for users.

    Train Search: Users can search for trains based on their origin and destination.

    Reservation Management:

        Book a reservation on an available train.

        View a list of all personal reservations.

        Cancel an existing reservation.

    Real-time Updates: Reservations and seat availability are updated in the database.

Technologies Used
Frontend
HTML, CSS, JavaScript: The core building blocks for the user interface and functionality.

        Fetch API: Used to make asynchronous requests to the backend API.

    Backend
        Java: The primary language for the server-side logic.

        Spring Boot: A powerful framework for building robust, stand-alone Java applications.

        Spring Data JPA: Used for database interaction and data management.

        MySQL: The relational database used to store user, train, and reservation data.

Getting Started

Prerequisites
To run this project, you will need to have the following installed on your machine:

        Java Development Kit (JDK) 11 or later

        Maven

        A MySQL database server

        An IDE like IntelliJ IDEA or VS Code

1.  Backend Setup
    Clone the repository:

        git clone [your-repository-url]

        Configure the database:

        Create a MySQL database named train_reservation_db.

        Update the database connection properties in src/main/resources/application.properties with your MySQL username and password.

        Run the application:

        Open the project in your IDE.

        Run the main class (e.g., com.example.reservation.TrainReservationApplication).

        The backend will start on http://localhost:8080.

2.  Frontend Setup
    Open the HTML file:

        Navigate to the frontend directory.

        Open index.html in your preferred web browser.

        Configure API URL:

        Ensure that the API_BASE_URL variable in your JavaScript file is set to the correct backend URL (e.g., http://localhost:8080).

Usage
Open index.html in your browser.

        Use the "Register" button to create a new account.

        Log in with your new credentials.

        Use the search form to find available trains.

        Select a train to book a reservation.

        Navigate to "My Reservations" to view and manage your bookings.

Enjoy your train journey!
