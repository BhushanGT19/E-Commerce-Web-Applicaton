import axios from "axios";
import { BASE_API_URL } from "../common/constant";

import { AuthHeader } from "./auth.header";

const API_URL = BASE_API_URL + "/users";

class userService {
  register(user) {
    // return axios.post(API_URL + "/user/signup", user);

    // register the user using user attributes
    // post request on authentication-service
    return axios.post(API_URL, {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      passWord: user.passWord,
      mobileNumber: user.mobileNumber,
    });
  }

  login(user) {
    return axios.post(API_URL + "/login", {
      email: user.email,
      passWord: user.passWord,
    });
  }

  // send request to update the user
  update(user, formData) {
    return axios.put(
      API_URL,
      {
        user: user,
        formData: formData,
      },
      {
        headers: {
          "Content-Type": "multipart/form-data", // Set the content type to multipart form data
          Authorization: localStorage.getItem("token"),
        },
      }
    );
  }


  
}

export default new userService();
