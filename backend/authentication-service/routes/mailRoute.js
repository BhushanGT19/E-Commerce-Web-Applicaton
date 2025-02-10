// creating router instance
const router = require("express").Router();
const mailController = require("../controllers/mailController");

// endpoint for sending welcome mail to newly register user

router.post("/user/send-welcome", mailController.sendWelcomeMailToNewUser);

router.post(
  "/seller/registration",
  mailController.sendRegistrationMailToSeller
);

module.exports = router;
