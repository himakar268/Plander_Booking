async function fetchWithAuth(url, options = {}) {
    const token = localStorage.getItem('accessToken');
    
    if (!token) {
        window.location.href = 'login.html';
        throw new Error('Authentication token missing');
    }

    // Set up headers
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        ...(options.headers || {})
    };

    try {
        const response = await fetch(url, {
            ...options,
            headers
        });

        // Handle 401 Unauthorized
        if (response.status === 401) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('username');
            localStorage.removeItem('email');
            window.location.href = 'login.html';
            throw new Error('Session expired. Please login again.');
        }

        // Handle 403 Forbidden
        if (response.status === 403) {
            throw new Error('You do not have permission to access this resource');
        }

        // Handle other error statuses
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `Request failed with status ${response.status}`);
        }

        return response;

    } catch (error) {
        console.error('API request failed:', error);
        alert(error.message || 'An error occurred. Please try again.');
        throw error;
    }
}

// Helper functions for common HTTP methods
const api = {
    get: (url) => fetchWithAuth(url),
    post: (url, body) => fetchWithAuth(url, {
        method: 'POST',
        body: JSON.stringify(body)
    }),
    put: (url, body) => fetchWithAuth(url, {
        method: 'PUT',
        body: JSON.stringify(body)
    }),
    delete: (url) => fetchWithAuth(url, {
        method: 'DELETE'
    })
};

// Expose the api object for easy access
window.api = api;