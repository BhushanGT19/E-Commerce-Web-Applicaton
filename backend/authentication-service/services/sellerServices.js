const bcrypt = require("bcrypt");
const { createToken } = require("../authentication/getToken");
const { hashPassword } = require("../utils/encrypt");

// import email related services
const { validateEmail } = require("./mailServices");

const register = async (req) => {
  try {
    // access the seller registration data
    
    let { firstName, lastName, panNumber, mobileNumber, email, passWord } =
      req.body;

    console.log(firstName, lastName, panNumber, mobileNumber, email, passWord);

    // verify credentials, email , mobileNumber

    // validate the credentials

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

    // Hash the password
    const hashedPassword = await hashPassword(passWord);

    // Create the seller
    const seller = {
      firstName,
      lastName,
      panNumber,
      mobileNumber,
      email,
      passWord: hashedPassword,
    };

    return {
      seller: seller,
      success: true,
      message: "Request is under processing(for seller registration)",
    };
  } catch (error) {
    throw new Error(error);
  }
};

const login = async (req) => {
  // email is validated in core application

  // access all the fields required

  // email is validated in core application
  const { sellerId, sellerPassWord, validationPassWord } = req.body;

  console.log(sellerId, sellerPassWord, validationPassWord);

  try {
    const isPasswordValid = await bcrypt.compare(
      validationPassWord,
      sellerPassWord
    );

    // Check if password matches
    if (!isPasswordValid) {
      throw new Error("Invalid password");
    }

    const token = await createToken(sellerId);

    console.log(token);
    // return token
    return { token };
  } catch (error) {
    console.log("error while login seller : " + error);
    throw new Error(error.message);
  }
};

module.exports = { register, login };
