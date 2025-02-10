const { createToken } = require("../authentication/getToken");
const { hashPassword } = require("../utils/encrypt");

// import email related services
const { validateEmail, sendWelcomeEmail } = require("./mailServices");

// import utils methods
const { capitalize } = require("../utils/capitalize");

// import module to encrypt the user passwords
const bcrypt = require("bcrypt"); // For password hashiōng

// Controller or Service function to create a new user
async function createUser(req) {
  // validate the credentials

  let { email, firstName, lastName, passWord, mobileNumber, profileUrl } =
    req.body;

  console.log(email, passWord);

  // validate email
  const emailValidationResult = await validateEmail(email);
  console.log(emailValidationResult);

  // if email is not valid, return message as email is not valid
  if (!emailValidationResult) {
    return {
      success: false,
      message: "Invalid email address",
    };
  }

  // validate mobileNumber if exists

  // validate address if exists

  // capitalize firstName , lastName

  // profile url
  if (profileUrl === undefined) {
    profileUrl = null;
  }

  // instead of storing the plain text password in database
  // encrypt a password using a hashing function and stored

  const hashedPassword = await hashPassword(passWord);

  // Create a new user object

  const newUser = {
    email,
    firstName,
    lastName,
    passWord: hashedPassword,
    mobileNumber,
    profileUrl,
  };

  // send the welcome email to the user
  // sendWelcomeEmail(email, firstName, lastName);

  return {
    success: true,
    message: "User registered successfully",
    user: newUser,
  };
}

// service function to allow user to login into the system

async function loginUser(req) {
  // access all the fields required

  // email is validated in core application
  const { userId, userPassword, validationPassword } = req.body;

  try {
    const isPasswordValid = await bcrypt.compare(
      validationPassword,
      userPassword
    );

    //   // Check if user exists and if password matches
    if (!isPasswordValid) {
      throw new Error("Invalid password");
    }

    const token = await createToken(userId);

    console.log(token);
    // return token
    return { token };
  } catch (error) {
    console.log("error while login user : " + error);
    throw new Error(error.message);
  }
}

// export user services

module.exports = {
  createUser,
  loginUser,
};
