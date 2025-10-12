const API_BASE = '/api';
let currentToken = null;
let currentUsername = null;

// Helper function to format numbers as Indian Rupees
function formatRupee(amount) {
    if (typeof amount !== 'number') {
        // Attempt to parse if it's a string, otherwise default
        amount = parseFloat(amount) || 0.00;
    }
    // Use Intl.NumberFormat for Indian Rupee format
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR'
    }).format(amount);
}

// --- UI HELPERS ---

/** Switches between application panels (Deposit, Withdraw, Transfer, History) */
function showPanel(panelId, button) {
    // Hide all panels
    document.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
    // Show the target panel
    document.getElementById(panelId).classList.add('active');

    // Update active tab button style
    document.querySelectorAll('.tab-button').forEach(b => b.classList.remove('active'));
    button.classList.add('active');
}

/** Updates the main UI visibility based on login state */
function updateUI(isLoggedIn) {
    document.getElementById('auth-section').classList.toggle('active', !isLoggedIn);
    document.getElementById('app-content').classList.toggle('active', isLoggedIn);

    if (isLoggedIn) {
        document.getElementById('current-user').textContent = currentUsername;
        // Reset and show the default panel (Deposit)
        document.querySelector('.tab-button').click(); 
        getBalance();
    } else {
        document.getElementById('current-user').textContent = 'Guest';
        displayBalance(0); // Reset balance to 0.00
    }
}

/** Updates the current balance display */
function displayBalance(balance) {
    // *** CHANGE HERE: Use formatRupee helper ***
    document.getElementById('current-balance').textContent = formatRupee(balance);
}

/** Displays success/error messages in a specific output area */
function displayOutput(elementId, message, type) {
    const output = document.getElementById(elementId);
    output.className = `output ${type}`;
    if (type === 'success') {
        output.innerHTML = `Success! ${message}`;
    } else if (type === 'error') {
        output.innerHTML = `Error: ${message}`;
    } else {
        output.innerHTML = message;
    }
}

// --- API CALLER ---

/**
 * Helper to make API calls with the token header.
 */
async function apiCall(endpoint, method = 'GET', bodyData = null, needsToken = true) {
    const headers = { 'Content-Type': 'application/json' };
    if (needsToken && currentToken) {
        headers['X-ATMTOKEN'] = currentToken;
    }

    const config = { method: method, headers: headers };

    if (bodyData) {
        config.body = JSON.stringify(bodyData);
    }

    try {
        const response = await fetch(API_BASE + endpoint, config);
        // If 204 No Content, return a dummy object
        if (response.status === 204) {
            return { ok: true, json: async () => ({ message: "Operation successful" }) };
        }
        return response;
    } catch (error) {
        console.error('Network Error:', error);
        // Return a custom error response for network issues
        return { 
            ok: false, 
            status: 503, 
            json: async () => ({ message: "Network connection failed or server is down." })
        };
    }
}

// --- AUTHENTICATION FUNCTIONS ---

async function login() {
    const username = document.getElementById('login-username').value;
    const pin = document.getElementById('login-pin').value;
    displayOutput('auth-output', 'Attempting login...', '');

    const response = await apiCall('/auth/login', 'POST', { username, pin }, false);
    const data = await response.json();

    if (response.ok) {
        currentToken = data.token;
        currentUsername = data.username;
        displayOutput('auth-output', `Logged in as ${currentUsername}`, 'success');
        updateUI(true);
    } else {
        currentToken = null;
        displayOutput('auth-output', data.message || 'Invalid credentials', 'error');
        updateUI(false);
    }
}

async function logout() {
    if (!currentToken) return;

    displayOutput('auth-output', 'Logging out...', '');
    
    // Send logout request (token is sent in the header)
    await apiCall('/auth/logout', 'POST'); 
    
    currentToken = null;
    currentUsername = null;
    displayOutput('auth-output', 'Successfully logged out.', '');
    updateUI(false);
}

// --- ACCOUNT OPERATION FUNCTIONS ---

async function getBalance() {
    // No output display needed for silent refresh
    if (!currentToken) return;
    
    const response = await apiCall('/account/balance');

    if (response.ok) {
        const balance = await response.json();
        displayBalance(balance);
    } else {
        // Handle unauthorized or other errors silently/via console for a cleaner UI
        console.error('Failed to fetch balance:', await response.json());
        displayBalance(0);
    }
}

async function deposit() {
    const amount = parseFloat(document.getElementById('deposit-amount').value);
    displayOutput('deposit-output', 'Processing deposit...', '');
    
    const response = await apiCall('/account/deposit', 'POST', { token: currentToken, amount });
    const data = await response.json();

    if (response.ok) {
        displayOutput('deposit-output', `Deposited ${formatRupee(amount)}.`, 'success');
        getBalance(); 
    } else {
        displayOutput('deposit-output', data.message || 'Deposit failed.', 'error');
    }
}

async function withdraw() {
    const amount = parseFloat(document.getElementById('withdraw-amount').value);
    displayOutput('withdraw-output', 'Processing withdrawal...', '');

    const response = await apiCall('/account/withdraw', 'POST', { token: currentToken, amount });
    const data = await response.json();

    if (response.ok) {
        displayOutput('withdraw-output', `Withdrew ${formatRupee(amount)}.`, 'success');
        getBalance();
    } else {
        const errorMessage = data.message || data; 
        displayOutput('withdraw-output', errorMessage || 'Withdrawal failed.', 'error');
    }
}

async function transfer() {
    const toUsername = document.getElementById('transfer-recipient').value;
    const amount = parseFloat(document.getElementById('transfer-amount').value);
    displayOutput('transfer-output', 'Processing transfer...', '');

    const response = await apiCall('/account/transfer', 'POST', { token: currentToken, toUsername, amount });
    const data = await response.json();

    if (response.ok) {
        displayOutput('transfer-output', `Transferred ${formatRupee(amount)} to ${toUsername}.`, 'success');
        getBalance();
    } else {
        const errorMessage = data.message || data; 
        displayOutput('transfer-output', errorMessage || 'Transfer failed.', 'error');
    }
}

async function getHistory() {
    const output = document.getElementById('history-body');
    output.innerHTML = '<tr><td colspan="4">Loading transaction history...</td></tr>';
    
    const response = await apiCall('/transactions/history');
    
    if (response.ok) {
        const transactions = await response.json();
        
        if (transactions.length === 0) {
            output.innerHTML = '<tr><td colspan="4">No transactions found.</td></tr>';
            return;
        }

        let historyHtml = '';
        transactions.forEach(tx => {
            const time = new Date(tx.timestamp).toLocaleString();
            const counterparty = tx.counterparty || '-';
            let rowClass = tx.type.includes('IN') || tx.type.includes('DEPOSIT') ? 'tx-in' : 'tx-out';
            
            historyHtml += `<tr class="${rowClass}">
                <td>${tx.type}</td>
                <td>${formatRupee(tx.amount)}</td>
                <td>${counterparty}</td>
                <td>${time}</td>
            </tr>`;
        });
        output.innerHTML = historyHtml;
    } else {
        const data = await response.json();
        document.getElementById('history-output').className = 'output error';
        document.getElementById('history-output').innerHTML = data.message || 'Failed to load history.';
        output.innerHTML = `<tr><td colspan="4" class="error-cell">Error fetching history.</td></tr>`;
    }
}

// Initial setup
document.addEventListener('DOMContentLoaded', () => {
    updateUI(false);
});