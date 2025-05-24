const loginForm = document.getElementById('loginForm');
const signupForm = document.getElementById('signupForm');
const toggleDarkModeBtn = document.getElementById('toggleDarkMode');

// Handle login
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = loginForm.querySelector('input[type="email"]').value;
        const password = loginForm.querySelector('input[type="password"]').value;

        try {
            const response = await fetch('http://localhost:8085/users/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Login failed');
            }

            const data = await response.json();

            // Store tokens and user info
            localStorage.setItem('accessToken', data.accessToken);
            localStorage.setItem('username', data.userName);
            localStorage.setItem('email', data.email);

            // Redirect based on email (admin@admin.com is admin)
            if (data.email === 'admin@admin.com') {
                window.location.href = 'admin_page.html';
            } else {
                window.location.href = 'index.html';
            }
        } catch (error) {
            console.error('Error logging in:', error);
            alert(error.message || 'Invalid credentials. Try again.');
        }
    });
}

// Handle signup
if (signupForm) {
    signupForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const firstName = signupForm.querySelector('input[name="firstName"]').value;
        const lastName = signupForm.querySelector('input[name="lastName"]').value;
        const email = signupForm.querySelector('input[name="email"]').value;
        const password = signupForm.querySelector('input[name="password"]').value;
        const confirmPassword = signupForm.querySelector('input[name="confirmPassword"]').value;
        const gender = signupForm.querySelector('select[name="gender"]').value;

        if (password !== confirmPassword) {
            alert('Passwords do not match. Please try again.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8085/users/login/signup', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ firstName, lastName, email, password, gender })
            });

            if (!response.ok) {
                const text = await response.text();
                if (text.includes("Duplicate entry") && text.includes("users.email")) {
                    alert("Email already exists. Please use a different email.");
                } else {
                    alert("Signup failed. Please try again.");
                }
                throw new Error(text);
            }

            alert('Signup successful! Please login.');
            window.location.href = 'login.html';
        } catch (error) {
            console.error('Error signing up:', error);
        }
    });
}

// Dark Mode Toggle
if (toggleDarkModeBtn) {
    toggleDarkModeBtn.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
        localStorage.setItem('darkMode', document.body.classList.contains('dark-mode'));
        updateDarkModeButton();
    });

    function updateDarkModeButton() {
        if (document.body.classList.contains('dark-mode')) {
            toggleDarkModeBtn.textContent = '☀️';
        } else {
            toggleDarkModeBtn.textContent = '🌙';
        }
    }

    // Check for saved dark mode preference
    if (localStorage.getItem('darkMode') === 'true') {
        document.body.classList.add('dark-mode');
        updateDarkModeButton();
    }
}

// Helper function to check authentication
function checkAuth() {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        window.location.href = 'login.html';
        return false;
    }
    return true;
}

// Helper function to check if admin
function isAdmin() {
    const email = localStorage.getItem('email');
    return email === 'admin@admin.com';
}
