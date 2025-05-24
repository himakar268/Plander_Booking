async function fetchVenues() {
    try {
        const gameId = localStorage.getItem('selectedGameId');
        if (!gameId) {
            throw new Error('No game selected');
        }
        
        const response = await fetch(`http://localhost:8085/api/venues/game/${gameId}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch venues: ${response.status} ${response.statusText}`);
        }
        const venues = await response.json();
        return venues;
    } catch (error) {
        console.error('Error fetching venues:', error);
        return [];
    }
}

function createVenueCard(venue) {
    return `
        <div class="venue-card" data-venue-id="${venue.id}">
            <img src="${venue.image}" alt="${venue.name}">
            <div class="venue-card-content">
                <h3>${venue.name}</h3>
                <p><strong>Available Slots:</strong> <span class="capacity" data-venue-id="${venue.id}">${venue.availableCapacity}</span></p>
                <p><strong>Rate per Hour:</strong> ₹${venue.ratePerHour}</p>
                <div class="date-time-picker">
                    <label>Date: <input type="date" id="date-${venue.id}" onchange="updateTimeOptions(${venue.id})"></label>
                    <label>Time: <select id="time-${venue.id}"></select></label>
                    <label>Duration (hours): 
                        <select id="duration-${venue.id}">
                            <option value="1">1 hour</option>
                            <option value="2">2 hours</option>
                            <option value="3">3 hours</option>
                        </select>
                    </label>
                </div>
                <div class="quantity-control">
                    <button onclick="decreaseSeats(${venue.id})">-</button>
                    <span id="seatsBooked-${venue.id}">0</span>
                    <button onclick="increaseSeats(${venue.id}, ${venue.availableCapacity})">+</button>
                </div>
                <button class="btn btn-primary" onclick="showEmailModal(${venue.id}, '${venue.name}', ${venue.availableCapacity}, ${venue.ratePerHour})">
                    Proceed to Payment
                </button>
            </div>
        </div>`;
}

async function renderVenues() {
    const venues = await fetchVenues();
    if (venues.length === 0) {
        document.getElementById('venuesContainer').innerHTML = '<p>No venues available for this sport.</p>';
        return;
    }
    document.getElementById('venuesContainer').innerHTML = venues.map(createVenueCard).join('');

    setMinDateForInputs();
    
    venues.forEach(venue => {
        updateTimeOptions(venue.id);
    });
}

document.addEventListener("DOMContentLoaded", renderVenues);

function setMinDateForInputs() {
    const today = new Date().toISOString().split('T')[0];
    document.querySelectorAll('input[type="date"]').forEach(dateInput => {
        dateInput.setAttribute("min", today);
    });
}

function updateTimeOptions(venueId) {
    const dateInput = document.getElementById(`date-${venueId}`);
    const timeSelect = document.getElementById(`time-${venueId}`);

    if (!dateInput || !timeSelect) return;

    const selectedDate = new Date(dateInput.value);
    const today = new Date();

    timeSelect.innerHTML = "";

    let startHour = 8; // Starting from 8 AM
    const closingHour = 22; // Until 10 PM

    if (selectedDate.toDateString() === today.toDateString()) {
        const currentHour = today.getHours();
        startHour = currentHour < 8 ? 8 : currentHour + 1; // Start from next hour if today
    }

    for (let hour = startHour; hour <= closingHour; hour++) {
        const timeStr = `${hour.toString().padStart(2, '0')}:00`;
        const option = document.createElement("option");
        option.value = timeStr;
        option.textContent = timeStr;
        timeSelect.appendChild(option);
    }
}

function increaseSeats(venueId, availableCapacity) {
    const seatsElement = document.getElementById(`seatsBooked-${venueId}`);
    let seats = parseInt(seatsElement.textContent);
    if (seats < availableCapacity) {
        seats++;
        seatsElement.textContent = seats;
    }
}

function decreaseSeats(venueId) {
    const seatsElement = document.getElementById(`seatsBooked-${venueId}`);
    let seats = parseInt(seatsElement.textContent);
    if (seats > 0) {
        seats--;
        seatsElement.textContent = seats;
    }
}

function showEmailModal(venueId, venueName, availableCapacity, ratePerHour) {
    const emailModal = document.getElementById("emailModal");
    emailModal.dataset.venueId = venueId;
    emailModal.dataset.venueName = venueName;
    emailModal.dataset.availableCapacity = availableCapacity;
    emailModal.dataset.ratePerHour = ratePerHour;
    emailModal.style.display = "flex";
}

function submitEmail() {
    const userEmailInput = document.getElementById("userEmail");
    const userEmail = userEmailInput.value.trim();
    const emailModal = document.getElementById("emailModal");

    if (userEmail === "") {
        alert("Please enter your email.");
        return;
    }

    const venueId = emailModal.dataset.venueId;
    const venueName = emailModal.dataset.venueName;
    const ratePerHour = parseFloat(emailModal.dataset.ratePerHour);
    const seatsBooked = parseInt(document.getElementById(`seatsBooked-${venueId}`).textContent);
    const date = document.getElementById(`date-${venueId}`).value;
    const time = document.getElementById(`time-${venueId}`).value;
    const duration = parseInt(document.getElementById(`duration-${venueId}`).value);
    const gameId = localStorage.getItem('selectedGameId');

    if (seatsBooked === 0) {
        alert("Please select at least one seat.");
        return;
    }

    if (!date || !time) {
        alert("Please select a date and time.");
        return;
    }

    const selectedGame = JSON.parse(localStorage.getItem('selectedGame'));
    const gameName = selectedGame ? selectedGame.name : 'Unknown Game';

    const bookingDetails = {
        venueId,
        venue: venueName,
        ratePerHour,
        seatsBooked,
        date,
        time,
        duration,
        userEmail,
        gameId,
        gameName,
        amount: ratePerHour * duration * seatsBooked
    };

    localStorage.setItem('selectedGameName', gameName);
    localStorage.setItem("bookingDetails", JSON.stringify(bookingDetails));
    emailModal.style.display = "none";

    window.location.href = "payments.html";
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#emailModal button").addEventListener("click", submitEmail);
});

// Dark Mode Toggle
const toggleDarkModeBtn = document.getElementById('toggleDarkMode');
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

if (localStorage.getItem('darkMode') === 'true') {
    document.body.classList.add('dark-mode');
    updateDarkModeButton();
}