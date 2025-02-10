import axios from "axios";
import { BASE_API_URL } from "../common/constant";
import { AuthHeader } from "./auth.header";
const API_URL = BASE_API_URL + "/addresses";


class addressService{

  getAddress(){
    return axios.get(
      API_URL,
      {headers:AuthHeader()},);
  }

}

export default new addressService();

