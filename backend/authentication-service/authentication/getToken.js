require("dotenv").config();
const jwt = require("jsonwebtoken");

const createToken = async (id) => {
  const JWT_SECRET_KEY = process.env.JWT_SECRET_KEY;

  //   default algorithm is (HMAC SHA256)

  const token = jwt.sign({ id: id }, JWT_SECRET_KEY);

  return token;
};

module.exports = { createToken };
