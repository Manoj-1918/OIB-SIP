const API_BASE_URL = 'http://localhost:8080';
const contentDiv = document.getElementById('content');
const navLogin = document.getElementById('nav-login');
const navRegister = document.getElementById('nav-register');
const navSearch = document.getElementById('nav-search');
const navReservations = document.getElementById('nav-reservations');
const navLogout = document.getElementById('nav-logout');

const messageBox = document.getElementById('message-box');
const messageText = document.getElementById('message-text');
const messageCloseBtn = document.getElementById('message-close-btn');

let currentUser = null; // Stores the logged-in user's username

// --- Helper Functions ---

function showMessage(message) {
    messageText.textContent = message;
    messageBox.classList.remove('hidden');
}

function hideMessage() {
    messageBox.classList.add('hidden');
}

function updateNav() {
    const hiddenClass = 'hidden';
    if (currentUser) {
        navLogin.classList.add(hiddenClass);
        navRegister.classList.add(hiddenClass);
        navSearch.classList.remove(hiddenClass);
        navReservations.classList.remove(hiddenClass);
        navLogout.classList.remove(hiddenClass);
    } else {
        navLogin.classList.remove(hiddenClass);
        navRegister.classList.remove(hiddenClass);
        navSearch.classList.add(hiddenClass);
        navReservations.classList.add(hiddenClass);
        navLogout.classList.add(hiddenClass);
    }
}

// --- View Rendering Functions ---

function renderAuthForm() {
    const formHtml = `
        <h2 class="form-title">Login to Your Account</h2>
        <form id="loginForm">
            <div class="form-group">
                <label for="loginUsername">Username:</label>
                <input type="text" id="loginUsername" required>
            </div>
            <div class="form-group">
                <label for="loginPassword">Password:</label>
                <input type="password" id="loginPassword" required>
            </div>
            <button type="submit" class="btn">Login</button>
        </form>
        <p style="margin-top: 1rem;">New user? <a href="#" id="link-to-register">Register here</a></p>
    `;
    contentDiv.innerHTML = formHtml;
    document.getElementById('loginForm').addEventListener('submit', handleLogin);
    document.getElementById('link-to-register').addEventListener('click', renderRegisterForm);
}

function renderRegisterForm() {
    const formHtml = `
        <h2 class="form-title">Create a New Account</h2>
        <form id="registerForm">
            <div class="form-group">
                <label for="regFullName">Full Name:</label>
                <input type="text" id="regFullName" required>
            </div>
            <div class="form-group">
                <label for="regUsername">Username:</label>
                <input type="text" id="regUsername" required>
            </div>
            <div class="form-group">
                <label for="regEmail">Email:</label>
                <input type="email" id="regEmail" required>
            </div>
            <div class="form-group">
                <label for="regPhone">Phone Number:</label>
                <input type="tel" id="regPhone" required>
            </div>
            <div class="form-group">
                <label for="regPassword">Password:</label>
                <input type="password" id="regPassword" required>
            </div>
            <button type="submit" class="btn">Register</button>
        </form>
        <p style="margin-top: 1rem;">Already have an account? <a href="#" id="link-to-login">Login here</a></p>
    `;
    contentDiv.innerHTML = formHtml;
    document.getElementById('registerForm').addEventListener('submit', handleRegister);
    document.getElementById('link-to-login').addEventListener('click', renderAuthForm);
}

async function renderTrainsSearchView() {
    if (!currentUser) return renderAuthForm();

    const searchHtml = `
        <h2 class="form-title">Search for Trains</h2>
        <form id="searchForm">
            <div class="form-group">
                <label for="source">Source:</label>
                <input type="text" id="source" placeholder="Enter source city" required>
            </div>
            <div class="form-group">
                <label for="destination">Destination:</label>
                <input type="text" id="destination" placeholder="Enter destination city" required>
            </div>
            <div class="form-group">
                <label for="journeyDate">Journey Date:</label>
                <input type="date" id="journeyDate" required>
            </div>
            <button type="submit" class="btn">Search</button>
        </form>
        <div id="searchResults" style="margin-top: 2rem; text-align: left;"></div>
    `;
    contentDiv.innerHTML = searchHtml;
    document.getElementById('searchForm').addEventListener('submit', handleTrainSearch);
}

async function renderReservationsView() {
    if (!currentUser) return renderAuthForm();

    const reservationsHtml = `<h2 class="form-title">My Reservations</h2><ul id="reservation-list" class="result-list"></ul>`;
    contentDiv.innerHTML = reservationsHtml;
    const reservationList = document.getElementById('reservation-list');

    try {
        const response = await fetch(`${API_BASE_URL}/reservation/my-reservation/${currentUser}`);
        if (!response.ok) throw new Error('Failed to load reservations.');
        const reservations = await response.json();
        
        if (reservations.length === 0) {
            reservationList.innerHTML = `<p style="text-align: center;">You have no reservations.</p>`;
        } else {
            reservationList.innerHTML = reservations.map(res => `
                <li class="result-item">
                    <h4>PNR: ${res.pnr}</h4>
                    <p>Train: ${res.trainNumber} - ${res.trainName}</p>
                    <p>Route: ${res.fromPlace} to ${res.toPlace}</p>
                    <p>Date: ${res.journeyDate}</p>
                    <button class="btn" onclick="handleCancel('${res.pnr}')">Cancel</button>
                </li>
            `).join('');
        }
    } catch (error) {
        showMessage(error.message);
    }
}

// --- Form and Button Handlers ---

async function handleLogin(event) {
    event.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        const text = await response.text();
        if (response.ok && text === "Login successful") {
            currentUser = username;
            updateNav();
            renderTrainsSearchView();
        } else {
            showMessage(text);
        }
    } catch (error) {
        showMessage('An error occurred. Please check the server connection.');
    }
}

async function handleRegister(event) {
    event.preventDefault();
    const fullName = document.getElementById('regFullName').value;
    const username = document.getElementById('regUsername').value;
    const email = document.getElementById('regEmail').value;
    const phoneNumber = document.getElementById('regPhone').value;
    const password = document.getElementById('regPassword').value;

    const newUser = { fullName, username, email, phoneNumber, password };

    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newUser)
        });
        if (response.ok) {
            showMessage(`User '${username}' registered successfully! Please log in.`);
            renderAuthForm();
        } else {
            showMessage('Registration failed. Username may already exist.');
        }
    } catch (error) {
        showMessage('An error occurred during registration.');
    }
}

async function handleTrainSearch(event) {
    event.preventDefault();
    const source = document.getElementById('source').value;
    const destination = document.getElementById('destination').value;
    const journeyDate = document.getElementById('journeyDate').value;

    try {
        const response = await fetch(`${API_BASE_URL}/reservation/search?source=${source}&destination=${destination}`);
        if (!response.ok) throw new Error('No trains found for this route.');
        const trains = await response.json();
        const resultsDiv = document.getElementById('searchResults');

        if (trains.length === 0) {
            resultsDiv.innerHTML = `<p style="text-align: center;">No trains found matching your search criteria.</p>`;
        } else {
            resultsDiv.innerHTML = `
                <h3>Matching Trains</h3>
                <ul class="result-list">
                    ${trains.map(train => `
                        <li class="result-item">
                            <h4>${train.trainName} (${train.trainNumber})</h4>
                            <p>From: ${train.source} to ${train.destination}</p>
                            <p>Departure: ${train.departureTime} | Arrival: ${train.arrivalTime}</p>
                            <p>Class: ${train.classType} | Available Seats: ${train.availableSeats}</p>
                            <button class="btn" onclick="handleBook('${train.trainNumber}', '${train.classType}', '${train.source}', '${train.destination}', '${journeyDate}')">Book</button>
                        </li>
                    `).join('')}
                </ul>
            `;
        }
    } catch (error) {
        showMessage(error.message);
    }
}

async function handleBook(trainNumber, classType, fromPlace, toPlace, journeyDate) {
    const reservation = {
        username: currentUser,
        trainNumber: trainNumber,
        classType: classType,
        fromPlace: fromPlace,
        toPlace: toPlace,
        journeyDate: journeyDate,
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/reservation/book`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reservation)
        });

        // First, check if the response was successful (status code 200-299)
        if (!response.ok) {
            // If it's a non-successful response, parse the error message if available
            // Note: The backend's error messages are likely JSON, so this is safe.
            const errorData = await response.json();
            throw new Error(errorData.error || 'Failed to book reservation');
        }

        // Since we are not changing the backend, we read the response as plain text
// Since backend sends plain text like "Reservation successful! Your PNR is: KITNCUPD"
const successMessage = await response.text();

const pnrMatch = successMessage.match(/PNR is:\s*([A-Z0-9]+)/i);
let pnr = pnrMatch ? pnrMatch[1] : 'N/A';

showMessage(`Reservation successful! Your PNR is: ${pnr}`);


        renderReservationsView();
    } catch (error) {
        // The error message from the throw will be available here
        showMessage('Failed to book reservation. ' + error.message);
    }
}

async function handleCancel(pnr) {

    // For now, let's keep it simple.
    if (!confirm(`Are you sure you want to cancel reservation with PNR ${pnr}?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/cancel/${pnr}`, {
            method: 'DELETE',
        });
        const message = await response.text();
        showMessage(message);
        renderReservationsView();
    } catch (error) {
        showMessage('Failed to cancel reservation.');
    }
}

// --- Event Listeners and Initial Load ---

messageCloseBtn.addEventListener('click', hideMessage);
navLogin.addEventListener('click', () => renderAuthForm());
navRegister.addEventListener('click', () => renderRegisterForm());
navSearch.addEventListener('click', () => renderTrainsSearchView());
navReservations.addEventListener('click', () => renderReservationsView());
navLogout.addEventListener('click', () => {
    currentUser = null;
    updateNav();
    renderAuthForm();
});

document.addEventListener('DOMContentLoaded', () => {
    updateNav();
    renderAuthForm();
});