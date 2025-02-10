import axios from "axios";
import { BASE_API_URL } from "../common/constant";

import { AuthHeader } from "./auth.header";

const API_URL = BASE_API_URL;

class adminService {
  login(user) {
    return axios.post(API_URL + "/admin/login", {
      userName: user.userName,
      passWord: user.passWord,
    });
  }

  
}

export default new adminService();
