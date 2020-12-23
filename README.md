# Tuition Reimbursement Management System (TRMS)

## Project Description
The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.

#Technologies Used
- PostgreSQL Database  - version 13.0
- DBeaver Database IDE - version 7.2.4
- Javalin              - version 3.12.0
- Java                 - version 1.8.217
- HTML and CSS
- Azure Virtual Machine

## Features
- Multiple Chain Trigger function on database, giving less heavy logic to the application.
- Implemented Timer to periodically update Database automatically.
- Used Javalin's CookiesStore to store and retrieve data for various function in the application.

To-do List:
- Polish Website aesthetics.
- Finish Implementing Email Notification feature to notify a specific User.
- Implement Hibernate to replace hardcoded Java pojos.

## Getting Started
- `git clone https://github.com/JoeDKoal94/Project1.git`
- Environmental Variables:
  - `DATABASE_URL` - PostgreSQL Database URL for JDBC.
  - `DATABASE_USERNAME` - Username for the database.
  - `DATABASE_PASSWORD` - Password for the database.
- change ip Adress to localhost to run at a local server.
- Open .sql file in src/resources amd execute script in DBeaver.

- Environment Variable Set Up:
  ![image](https://user-images.githubusercontent.com/51380575/103027780-25c4fe00-451c-11eb-9506-f9747eba945c.png)
 
## Usage
- After setting up the environment variable, run ServerDriver
- Open browser and go to `http://localhost:9090/login` to get to the log in screen.
- Log in using any credentials in the Database to access dashboard.
- Depending on the level of authority that the user has you have access to specific waitlist.
- Anyone can create and view ticket.
