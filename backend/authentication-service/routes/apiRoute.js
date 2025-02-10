const router = require("express").Router();
const userController = require("../controllers/userController");
const adminController = require("../controllers/adminController");
const sellerController = require("../controllers/sellerController");
// user routes

router.post("/user/register", userController.registerUser);

router.post("/user/login", userController.authenticateUser);

// admin related routes

router.post("/admin/login", adminController.authenticateAdmin);

router.post("/admin/validate-token", adminController.validateAdminToken);

// seller related routes

router.post("/seller/register", sellerController.registerSeller);

router.post("/seller/login", sellerController.authenticateSeller);

router.post("/seller/validate-token", sellerController.validateSellerToken);

// new routes

router.post("/user/validate-token", userController.validateUserToken);

// export the express router
module.exports = router;
