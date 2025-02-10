import axios from "axios";
import { BASE_API_URL } from "../common/constant";
import { AuthHeader } from "./auth.header";

const API_URL = BASE_API_URL + "/users"

class OrderService {

    createOrder(type) {
        return axios.get(API_URL + "/" + type, { headers: AuthHeader() });
    }

    getAllOrdersByUser() {

        return axios.get(API_URL + "/orders", { headers: AuthHeader() });
    }

    


}

export default new OrderService();