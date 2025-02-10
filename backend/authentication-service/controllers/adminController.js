const { login } = require("../services/adminServices");
const { validateToken } = require("../authentication/validateToken");

exports.authenticateAdmin = async (req, res) => {
  try {
    const token = await login(req);
    return res.status(200).json({ admin: null, token: token });
  } catch (error) {
    return res.status(401).json({ message: error.message });
  }
};

exports.validateAdminToken = async (req, res) => {
  try {
    const validateTokenResponse = await validateToken(req);

    console.log(validateTokenResponse);

    if (validateTokenResponse.success) {
      return res
        .status(200)
        .send({ adminId: parseInt(validateTokenResponse.id) });
    } else {
      return res.status(401).send({ message: validateTokenResponse.message });
    }
  } catch (error) {
    console.log("error while validating admin");
    return res.status(401).send({ message: "error while validating admin" });
  }
};
