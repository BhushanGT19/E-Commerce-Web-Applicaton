const {
  sendWelcomeEmail,
  sendSellerRegistrationEmail,
} = require("../services/mailServices");

// send welcome mail to the end user

exports.sendWelcomeMailToNewUser = async (req, res) => {
  const { email, firstName, lastName } = req.body;

  console.log(email, firstName, lastName);
  await sendWelcomeEmail(email, firstName, lastName);

  return res.status(200);
};

// send email to the new seller
// msg -> you're request is under process, we've reached out to you once we finish verifying your identity

exports.sendRegistrationMailToSeller = async (req, res) => {
  const { email, firstName, lastName } = req.body;

  console.log(email, firstName, lastName);

  await sendSellerRegistrationEmail(email, firstName, lastName);

  return res.status(200);
};
