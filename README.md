💳 Online Banking System (Java + JDBC)
📌 Project Overview

This is a console-based Online Banking System developed using Core Java and JDBC.
The application simulates basic banking operations such as account creation, deposits, withdrawals, and balance checking.

This project demonstrates strong understanding of:
Object-Oriented Programming (OOP)
Database connectivity using JDBC
Clean code structure with layered architecture

🚀 Features
🧑‍💼 Create new bank account
💰 Deposit money
💸 Withdraw money
📊 Check account balance
🔐 Basic validation and error handling
🗄️ Database integration using MySQL
🛠️ Technologies Used
Java (Core Java)
JDBC (Java Database Connectivity)
MySQL Database
Eclipse IDE

📂 Project Structure
Online_Banking_System/
│── src/
│   ├── com.rohan.accounts/
│   │   └── AccountHolders.java
│   ├── com.rohan.bankservice/
│   │   └── BankingServices.java
│   ├── com.rohan.database/
│   │   └── DatabaseConnection.java
│   ├── com.rohan.main/
│       └── Main.java
│
│── bin/
│── .project
│── .classpath

⚙️ Setup Instructions
1️⃣ Clone the Repository
git clone https://github.com/your-username/your-repo-name.git

2️⃣ Open in IDE
Import the project into Eclipse or any Java IDE

3️⃣ Setup Database
Create a MySQL database (example: bank_db)
Create required tables (example structure):
CREATE TABLE accounts (
    account_number INT PRIMARY KEY,
    name VARCHAR(100),
    balance DOUBLE
);

4️⃣ Configure Database Connection

Update your database credentials in:

DatabaseConnection.java

Example:

Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/bank_db",
    "root",
    "password"
);

5️⃣ Run the Project
Run Main.java
Use console menu to perform operations
🎯 Learning Outcomes
Hands-on experience with JDBC operations (CRUD)
Understanding of modular Java project structure
Implementation of real-world banking logic
Improved problem-solving and debugging skills
🔮 Future Improvements
Add GUI (JavaFX / Swing)
Implement user authentication (Login/Register)
Add transaction history
Convert to web app using Servlets & JSP / Spring Boot

👨‍💻 Author
Rohan Mane
Final Year Computer Engineering Student
