require("dotenv");
const { Sequelize } = require("sequelize");

// Create a Sequelize instance
const sequelize = new Sequelize(
  process.env.DATABASE,
  process.env.USER,
  process.env.PASSWORD,
  {
    host: process.env.HOST,
    port: process.env.DB_PORT,
    dialect: "mysql",
  }
);

module.exports = sequelize;
