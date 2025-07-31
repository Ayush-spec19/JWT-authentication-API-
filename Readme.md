# 🔐 JWT Authentication API (Spring Boot)

A **Spring Boot JWT Authentication API** with **In-Memory Users**, secure endpoints, and a simple **HTML/Bootstrap frontend** for testing.

---

## 🚀 Features
✅ JWT-based authentication (Login & Token Validation)  
✅ In-memory users (no database needed)  
✅ Secure endpoints (Protected APIs)  
✅ Simple HTML frontend (login & user dashboard)  
✅ Tested using **Insomnia/Postman**

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot 3, Spring Security 6
- **Authentication:** JWT (JSON Web Token)
- **Frontend:** HTML + Bootstrap (Static pages)
- **Testing:** Insomnia / Postman

---

## 🔑 **In-Memory Users** (for login)
| Email             | Password    | Role  |
|--------------------|------------|-------|
| ayush@gmail.com    | abc        | ADMIN |
| vishesh@gmail.com  | vishesh    | ADMIN |

---

## 🖥️ **API Endpoints**

### 1️⃣ **Login (Generate JWT Token)**
```http
POST /auth/login
```

📩 **Request Body:**
```json
{
  "email": "ayush@gmail.com",
  "password": "abc"
}
```

📤 **Response:**
```json
{
  "jwtToken": "<your-jwt-token>",
  "email": "ayush@gmail.com"
}
```

---

### 2️⃣ **Fetch Users (Protected Endpoint)**
```http
GET /users
Authorization: Bearer <jwt-token>
```

📤 **Response:**
```json
[
  {
    "id": "14567e67-34ff-45ef-ac5f-221dc52bac6e",
    "name": "Ayush Ojha",
    "email": "ayush@gmail.com",
    "password": "$2a$10$..."
  }
]
```

---

## 🌐 **Frontend Pages**
- **`/index.html`** → Landing page (API intro & login button)
- **`/login.html`** → Login form to authenticate and fetch token
- **Users fetched via JWT token** displayed dynamically

---

## ▶️ **How to Run**
1. Clone the repo:
   ```bash
   git clone https://github.com/Ayush-spec19/JWT-authentication-API-.git
   ```
2. Run Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
3. Access Frontend:
    - `http://localhost:8081/index.html`
    - `http://localhost:8081/login.html`

4. Test APIs (Insomnia/Postman):
    - `POST http://localhost:8081/auth/login` (Login)
    - `GET http://localhost:8081/users` (with JWT)

---

## 🎨 Stickers & Badges

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green?style=for-the-badge&logo=springboot)
![JWT](https://img.shields.io/badge/JWT-Security-orange?style=for-the-badge&logo=jsonwebtokens)
![Insomnia](https://img.shields.io/badge/Insomnia-Test-blue?style=for-the-badge&logo=insomnia)
![Bootstrap](https://img.shields.io/badge/Bootstrap-Frontend-purple?style=for-the-badge&logo=bootstrap)

---

## ✨ Author
👨‍💻 **Ayush Ojha**  
📧 Email: [saryuparinayush@gmail.com](mailto:ayush@gmail.com)  
🚀 Built with ❤️ using **Spring Boot & JWT**
