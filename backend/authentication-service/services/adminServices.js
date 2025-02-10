// adminService.js

require("dotenv");

const { createToken } = require("../authentication/getToken");

async function login(req) {
  const { adminId, passWord, validationPassWord } = req.body;

  console.log(adminId, passWord, validationPassWord);

  try {
    // verify passwords
    const isPasswordValid = passWord === validationPassWord;

    if (!isPasswordValid) {
      throw new Error("Invalid credentials");
    }

    const token = await createToken(adminId);

    return token;
  } catch (error) {
    console.log(error);
    throw new Error(error.message);
  }
}

async function validateToken(req) {}

module.exports = { login };
