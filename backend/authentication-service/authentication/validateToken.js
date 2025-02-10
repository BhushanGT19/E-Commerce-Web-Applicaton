require("dotenv").config();
const jwt = require("jsonwebtoken");

const validateToken = async (req) => {
  const JWT_SECRET_KEY = process.env.JWT_SECRET_KEY;

  const jwtToken = req.header("Authorization");

  console.log(jwtToken);

  if (!jwtToken) {
    return {
      success: false,
      message: "JWT token required",
    };
  }

  // Remove 'Bearer ' prefix from token
  const tokenWithoutPrefix = jwtToken.replace("Bearer ", "");

  // insert the id into incoming req if (id is valid)
  try {
    const { id, iat } = jwt.verify(tokenWithoutPrefix, JWT_SECRET_KEY);
    if (id) {
      return {
        success: true,
        message: "user found",
        id: id,
      };
    }
  } catch (error) {
    return {
      success: false,
      message: "invalid token",
    };
  }
};

module.exports = { validateToken };
