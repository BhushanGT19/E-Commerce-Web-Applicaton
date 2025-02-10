require("dotenv").config();

// connect to the database
require("./database/db.js");
const express = require("express");

const cors = require("cors");

// importing the routes

const apiRoute = require("./routes/apiRoute.js");
const mailRoute = require("./routes/mailRoute.js");

// creating instance of express
const app = express();

// middleware function
// body parser
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// allow cross origin resource sharing
app.use(cors());

// routes
app.use("/authenticate", apiRoute);
app.use("/mail", mailRoute);

// home route of api
app.get("/", (req, res) => {
  res.send({
    message: "API is working",
  });
});

// app started on listening on this port
app.listen(process.env.PORT, () => {
  console.log(`user ${process.env.USER}`);
  console.log(`server started on http://localhost:${process.env.PORT}`);
});
