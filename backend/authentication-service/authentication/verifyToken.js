// verify the token here,
// using next() middleware
const jwt = require("jsonwebtoken");

const verifyJWT = async (req, res, next) => {
  const JWT_SECRET_KEY = process.env.JWT_SECRET_KEY;

  const jwtToken = req.header("Authorization");

  if (!jwtToken) {
    return res
      .status(404)
      .send({ message: "Access denied, JWT token required" });
  }

  // Remove 'Bearer ' prefix from token
  const tokenWithoutPrefix = jwtToken.replace("Bearer ", "");

  try {
    const user = jwt.verify(tokenWithoutPrefix, JWT_SECRET_KEY);
    if (user) {
      req.user = user;
      next();
    }
  } catch (error) {
    return res.status(401).send({ message: "invalid token" });
  }
};

module.exports = { verifyJWT };
