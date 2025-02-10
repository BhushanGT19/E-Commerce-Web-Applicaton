require("dotenv").config();
const nodemailer = require("nodemailer");
const axios = require("axios");

// send mail to seller while registering the user

const sendSellerRegistrationEmail = async (
  sellerEmail,
  sellerFirstName,
  sellerLastName
) => {
  // transporter is a object, which holds the SMTP connection info for email communication
  const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
      user: process.env.GMAIL,
      pass: process.env.GMAIL_PASSWORD,
    },
  });

  // mailData holds the, meta data and information need to be shared in the email

  const mailData = {
    from: process.env.GMAIL, // sender address
    to: sellerEmail, // receiver
    subject: "Registration Form Submission Received",
    html: `
      <h3>Dear ${sellerFirstName} ${sellerLastName},</h3>
      <p>Thank you for submitting your registration form to become a seller on <strong>BookCharm Platform</strong>. We have received your information and appreciate your interest in joining our platform.</p>
      <p>Your registration form is currently under review by our team. We are working diligently to verify the information provided and ensure compliance with our guidelines and policies.</p>
      <p>Thank you for your patience and understanding. We look forward to potentially welcoming you as a seller on our platform!</p>
      <p>Best regards, BookCharm Team</p>
      <button><a href="${process.env.WEBAPP_URL}">View Platform</a></button>
    `,
  };

  try {
    // send email to the user using transporter object
    // mailInfor contains the information about the sended mail
    const mailInfo = await transporter.sendMail(mailData);
    return {
      success: true,
      message: "email send successfully",
    };
  } catch (error) {
    console.log(error);
    throw new Error("Error while Sending email to the user");
  }
};

// send welcome email to the user, when user registerd on to the system

const sendWelcomeEmail = async (userEmail, userFirstName, userLastName) => {
  // transporter is a object, which holds the SMTP connection info for email communication
  const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
      user: process.env.GMAIL,
      pass: process.env.GMAIL_PASSWORD,
    },
  });

  // mailData holds the, meta data and information need to be shared in the email

  const mailData = {
    from: process.env.GMAIL, // sender address
    to: userEmail, // list of receivers
    subject: "Welcome To BookCharm",
    html: `
      <h3>Hey 👋, ${userFirstName} ${userLastName}</h3>
      <p>Welcome to Our Platform!</p>
      <p>Thank you for signing up. We're excited to have you on board!</p>
      <button><a href="${process.env.WEBAPP_URL}">View Platform</a></button>
    `,
  };

  try {
    // send email to the user using transporter object
    // mailInfor contains the information about the sended mail
    const mailInfo = await transporter.sendMail(mailData);
    return {
      success: true,
      message: "email send successfully",
    };
  } catch (error) {
    console.log(error);
    throw new Error("Error while Sending email to the user");
  }
};

// function to verify the user email whether it is valid email or not
// returns boolean
const validateEmail = async (userEmail) => {
  // verify whether email is valid or not, and return status accordingly

  // use abstract api to verify email valid or not

  /*
  it returns json data inside 
  deliverability as

  'DELIVERABLE' means it is valid 
  'UNDELIVERABLE' means it is not valid
  'UNKNOWN' means it is not valid


  deliverability: 'DELIVERABLE',
  */

  try {
    const response = await axios.get(
      `https://emailvalidation.abstractapi.com/v1/?api_key=${process.env.ABSTRACTAPI_API_KEY}&email=${userEmail}`
    );

    console.log(response.data)

    
    if (response.deliverability === "DELIVERABLE" || response.data.deliverability ==="DELIVERABLE") {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    console.log(error);
    throw new Error("error while validating email");
  }
};

module.exports = {
  validateEmail,
  sendWelcomeEmail,
  sendSellerRegistrationEmail,
};
