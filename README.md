

# 🗓️ Plander – Online Booking Platform

**Plander** is a lightweight online booking platform built with **Spring Boot**, **MySQL**, and a classic **HTML/CSS/JavaScript** frontend. It allows users to book appointments or services and gives service providers full control over availability and listings.

---

## 🌟 Features

### 🔐 User Authentication

* Simple and secure login/signup flow.
* Role-based access control (users vs. admins).

### 📅 Booking System

* Real-time booking with availability management.
* Avoids double-booking conflicts.

### 📋 Service Management

* Admins can add, update, and remove services.
* Includes service name, description, price, and duration.

### 📊 Admin Dashboard

* Track bookings and users.
* Manage services and availability.

### 💻 Frontend (No Frameworks)

* Pure HTML, CSS, and JavaScript.
* Clean and responsive design.

---

## 🛠️ Technologies Used

* **Backend:** Spring Boot (Java)
* **Frontend:** HTML, CSS, JavaScript
* **Database:** MySQL
* **Build Tool:** Maven / Gradle

---

## 🚀 Getting Started

### 📥 Clone the Repository

```bash
git clone https://github.com/yourusername/plander.git
cd plander
```

---

## ⚙️ Backend Setup (Spring Boot + MySQL)

### 1. Create a MySQL Database

```sql
CREATE DATABASE plander_db;
```

### 2. Configure `application.properties`

Edit the file at `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/plander_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

> 🔐 **Important:** Replace `your_mysql_username` and `your_mysql_password` with your actual MySQL credentials.

### 3. Run the Application

If using Maven:

```bash
./mvnw spring-boot:run
```

If using Gradle:

```bash
./gradlew bootRun
```

Access your backend at: `http://localhost:8080`

---

## 🌐 Frontend Setup

1. All frontend files (HTML, CSS, JS) should go into:

   ```
   src/main/resources/static/
   ```

2. Launch the Spring Boot server and open:

   ```
   http://localhost:8080/index.html
   ```

---

## 🧪 Build and Package

To create a runnable `.jar` file:

```bash
./mvnw clean package
```

Run it with:

```bash
java -jar target/plander-0.0.1-SNAPSHOT.jar
```

---

## 📧 Contact

📬 Email: **[himakarmodukuri14@gmail.com](mailto:himakarmodukuri14@gmail.com)**
👤 Project by: **Himakar Modukuri**

---

## 🪪 License

Licensed under the [MIT License](LICENSE).

