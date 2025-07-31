const API_BASE = "http://localhost:8081"; // Spring Boot backend

// LOGIN PAGE
if (document.getElementById("loginForm")) {
    const errorBox = document.getElementById("error");

    document.getElementById("loginForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();

        errorBox.classList.add("d-none");

        try {
            const response = await fetch(`${API_BASE}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) throw new Error("Invalid Email or Password");

            const data = await response.json();
            localStorage.setItem("token", data.jwtToken);
            window.location.href = "dashboard.html";
        } catch (err) {
            errorBox.textContent = err.message;
            errorBox.classList.remove("d-none");
        }
    });
}

// DASHBOARD
if (document.getElementById("userTable")) {
    const token = localStorage.getItem("token");
    if (!token) window.location.href = "index.html";

    fetch(`${API_BASE}/users`, {
        headers: { "Authorization": "Bearer " + token },
    })
    .then(res => {
        if (!res.ok) throw new Error("Unauthorized. Login again.");
        return res.json();
    })
    .then(users => {
        const table = document.getElementById("userTable");
        users.forEach(u => {
            const row = `<tr><td>${u.name}</td><td>${u.email}</td></tr>`;
            table.innerHTML += row;
        });
    })
    .catch(() => window.location.href = "index.html");

    document.getElementById("logoutBtn").addEventListener("click", () => {
        localStorage.removeItem("token");
        window.location.href = "index.html";
    });
}
