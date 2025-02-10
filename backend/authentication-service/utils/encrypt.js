require("dotenv").config();
// import module to encrypt the user passwords
const bcrypt = require("bcrypt"); // For password hashing

// function to return, the hash of the plain password
async function hashPassword(password) {
  const saltRounds = parseInt(process.env.SALT_ROUNDS);

  return new Promise((resolve, reject) => {
    bcrypt.hash(password, saltRounds, (err, hash) => {
      if (err) {
        reject(err);
      } else {
        resolve(hash);
      }
    });
  });
}

module.exports = { hashPassword };
