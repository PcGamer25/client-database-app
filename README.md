# Client Database App
The client-database-app is an appointment scheduling program that manages appointments, customers, and generates reports. It is to be connected to a MySQL database through the Java Database Connectivity API where customer and appointment data is stored.
## Features
### Login Form
<img width="452" alt="login" src="https://github.com/PcGamer25/client-database-app/assets/24723469/8750e78c-f9e1-489a-b861-046a93d71f9d">

- Accepts a username and password and provides an appropriate error message
- Determines the user’s location and displays it in the form
- Displays the form in English or French based on the user’s computer language setting
- Translates error messages into English or French based on the user’s computer language setting
- Records all user logins and login attempts with timestamps in login_activity.txt
### Appointment Reminder
<img width="272" alt="appointmentspopup" src="https://github.com/PcGamer25/client-database-app/assets/24723469/0953dab0-d26e-4cfb-a881-3d3874c89d32">

- Provides an alert with the appointment ID, date, and time if there is an appointment within 15 minutes of the local time of the user’s login
### Directory
<img width="452" alt="directory" src="https://github.com/PcGamer25/client-database-app/assets/24723469/7a336f2b-5c15-4b42-add9-f627d53a657d">

- Presents navigation to the Appointments, Customers, and Reports windows
- Allows the user to log out
### Appointments
<img width="452" alt="appointments" src="https://github.com/PcGamer25/client-database-app/assets/24723469/62014ee0-0ec2-420d-9af5-3577a660158f">

- Displays all current appointments in a table in the current time zone
- Allows adding, updating, and deleting appointments
- Enables the user to filter appointments by month or week
### Add/Edit Appointments
<img width="452" alt="addappointment" src="https://github.com/PcGamer25/client-database-app/assets/24723469/50b5f0d0-18d4-4ee2-be87-a874ce3f05c6">

- Performs input validation and blank field checks and generates an error message
- Prevents scheduling an appointment outside of business hours (currently defined as 8:00 a.m. to 10:00 p.m. ET including weekends)
- Prevents scheduling an overlapping appointment for customers
- Auto-populates the form when modifying an appointment
### Customers
<img width="452" alt="customers" src="https://github.com/PcGamer25/client-database-app/assets/24723469/200824f6-cdcc-4747-8a1a-6f70e33e1529">

- Displays all customer data in a table
- Allows adding, updating, and deleting customers
- Respects foreign key constraints by prompting the user to remove all the customer’s associated appointments first
### Add/Edit Customers
<img width="452" alt="addcustomer" src="https://github.com/PcGamer25/client-database-app/assets/24723469/c5d0bcac-824b-4e70-b124-6d63fb7c6773">

- Performs input validation and blank field checks and generates an error message
- Auto-populates the form when modifying a customer
### Reports
<img width="452" alt="reports" src="https://github.com/PcGamer25/client-database-app/assets/24723469/57d482e3-4fdc-498a-8d9d-bb9ee1178e02">

- Displays the appointment schedule of each contact, customer, or user in a table
- Displays the number of customer appointments by type and month
