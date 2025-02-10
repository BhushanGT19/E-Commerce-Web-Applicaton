# Book Charm (E-Commerce Web Application)

## Deployment

### Hosted Application

The application is hosted on Netlify. You can access it using the following URL:
[https://bookcharm.netlify.app/](https://bookcharm.netlify.app/)

# Introduction

Welcome to BookCharm, an e-commerce web application developed by our team during our journey at CDAC. BookCharm aims to provide users with a seamless shopping experience, offering a wide range of products in Books categories.

The project is divided into two main services, each serving a distinct purpose:

## 1. Node.js Service

The Node.js service handles crucial functionalities such as authentication, authorization, and notification delivery. It ensures secure access to the Book Charm platform, manages user authentication sessions using JWT tokens, and facilitates email notifications using Nodemailer.

## 2. Spring Boot Service

The Spring Boot service serves as the backbone of the application, housing the core logic that drives the functionalities of Book Charm. It manages operations related to book listings, user orders, and overall business logic essential for the smooth functioning of the platform.

By dividing the project into these two services, Book Charm achieves modularity, scalability, and maintainability, allowing for efficient development and future enhancements.

## Features

- User Authentication: Secure user authentication system allowing users to register, login, and manage their profiles.
- Seller Registration: Sellers can register on the platform providing necessary details including PAN card ID.
- Admin Verification: Admin verifies seller registration based on PAN card ID, ensuring authenticity and trustworthiness.
- Book Listings: Browse through an extensive collection of books sorted into different genres and categories for easy navigation.
- Shopping Cart: Add desired books to the cart for easy checkout and purchase.
- Payment Integration: Secure payment gateway integration for seamless transactions.
- Responsive Design: Book Charm is designed to be responsive, ensuring a consistent experience across various devices and screen sizes.

## Installation

download project using git clone

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/BhushanGT19/E-Commerce-Web-Applicaton

```

### 2. Backend Setup

#### 1. Node.js Service (Authentication,Authorization & Mail)

a. Navigate to the Node.js service directory:

```bash
    cd backend/authentication-service

```

b. Environment Variables

Feel free to contact any of team member for to obtain the .env file for configuring environment variables. Place this file in the root of the nodejs-service directory.

### .env file structure

- app configuration
  `PORT=8080`

- db configuration

`HOST`

`DB_PORT=19919`

`PASSWORD`

`USER`

`DATABASE=defaultdb`

`JWT_SECRET_KEY=thisisjwtsecretkey`

- security configuration

`SALT_ROUNDS=10`

- mail services configuration

`GMAIL`

`GMAIL_PASSWORD`

- to validate the email

`ABSTRACTAPI_API_KEY`

c. Installing Dependencies

```bash
    npm install

```

d. Running the Service

```bash
    npm start

```

#### 2. Spring Boot Service (Core Logic)

Navigate to the Spring Boot service directory:

```bash
    cd backend/myapp-backend

```

a. Database Configuration

Ensure that you have configured the database connection details in the application.properties file.

b. Running the Service
You can run the Spring Boot service using your IDE or with Maven:

```bash
    ./mvnw spring-boot:run

```

#### 3. Frontend Setup

Navigate to the React frontend directory:

```bash
    cd book-frontend/
```

a. Installing Dependencies

```bash
    npm install
```

b. Starting the Frontend

```bash
    npm start
```

## Accessing the Application

Once all services are running, you can access the Book Charm application by navigating to http://localhost:3000 in your web browser.


## Future Plans

- Full Payment Integration
- Enhance the UI/UX
- AWS to store Images

## Additional Notes

- Ensure that backend services are running before starting the frontend.
- Make sure all necessary dependencies are installed and configured before starting each service.
