const { createToken } = require("../authentication/getToken");
const { validateToken } = require("../authentication/validateToken");
const { createUser, loginUser } = require("../services/userServices");

// endpoint register the user in the system
// validate the email of user
// validate whether email does not exists before
// validate password
// register user

exports.registerUser = async (req, res) => {
  try {
    // if user is validated successfully then return the validated user and jwt token

    const registrationResult = await createUser(req);

    // if user is already exists
    if (!registrationResult.success) {
      return res.status(409).json({ message: registrationResult.message });
    }

    // new user created , create jwt token and return

    const token = await createToken(registrationResult.user.userId);
    console.log("this is newUser id "+registrationResult.user.userId);


    console.log(registrationResult.user);

    return res.status(201).json({
      userRegistrationDto: registrationResult.user,
    });
  } catch (error) {
    console.log(error);
    return res.status(500).json({
      error: "Unable to create user",
      message: error.message,
    });
  }
};

exports.authenticateUser = async (req, res) => {
  // access the email and passWord from req
  // verify that user exists or not with that credentials
  // if not then return , status code not found
  // else return jwt token, user

  try {
    // Call the loginUser function from the authService
    const { token } = await loginUser(req);

    // Return a success response with the user object and token
    return res.status(200).json({ token: token });
  } catch (error) {
    // Handle any errors that occur during login
    console.error("Error during login:", error);
    return res.status(401).json({ error: "Login failed: " + error.message });
  }
};

exports.validateUserToken = async (req, res) => {
  // validate the token here

  try {
    const validateTokenResponse = await validateToken(req);

    console.log(validateTokenResponse);

    if (validateTokenResponse.success) {
      return res
        .status(200)
        .send({ userId: parseInt(validateTokenResponse.id) });
    } else {
      return res.status(401).send({ message: validateTokenResponse.message });
    }
  } catch (error) {
    console.log("error while validating user");
    return res.status(401).send({ message: "error while validating user" });
  }
};
