const sellerService = require("../services/sellerServices");
const { validateToken } = require("../authentication/validateToken");

// function to register the seller
exports.registerSeller = async (req, res) => {
  try {
    const result = await sellerService.register(req);
    if (result.success) {
      return res.status(200).json({
        sellerRegistrationDto: result.seller,
        message: result.message,
      });
    } else {
      return res.status(409).json({ message: result.message });
    }
  } catch (error) {
    console.log("Error Occured : ", error);
    return res
      .status(422)
      .json({ message: "error while while registering seller" });
  }
};

exports.authenticateSeller = async (req, res) => {
  try {
    const { token } = await sellerService.login(req);
    // Return a success response with the user object and token
    return res.status(200).json({ token: token });
  } catch (error) {
    // Handle any errors that occur during login
    console.error("Error during login:", error);
    return res.status(401).json({ error: "Login failed: " + error.message });
  }
};

// validate seller based on jwt token and return sellerId

exports.validateSellerToken = async (req, res) => {
  // validate the token here

  try {
    const validateTokenResponse = await validateToken(req);

    console.log(validateTokenResponse);

    if (validateTokenResponse.success) {
      return res
        .status(200)
        .send({ sellerId: parseInt(validateTokenResponse.id) });
    } else {
      return res.status(401).send({ message: validateTokenResponse.message });
    }
  } catch (error) {
    console.log("error while validating seller");
    return res.status(401).send({ message: "error while validating seller" });
  }
};
