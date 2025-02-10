import axios from "axios";
import { BASE_API_URL } from "../common/constant";
import { AuthHeader } from "./auth.header";

const API_URL = BASE_API_URL;

class CartService {
  addCart(productId, quantity) {
    return axios.put(
      API_URL + "/shopping-carts",
      {
        productId: productId,
        quantity: quantity,
      },
      {
        headers: AuthHeader(),
      }
    );
  }

  checkCart(cart) {
    return axios.post(API_URL + "/check", cart);
  }

  getCart() {
    return axios.get(API_URL + "/shopping-carts", { headers: AuthHeader() });
  }

  updateCart(id, qu) {
    console.log(id + ", " + qu);
    return axios.put(
      API_URL + "/shopping-carts/",
      { productId: id, quantity: qu },
      {
        headers: AuthHeader(),
      }
    );
  }

  deleteCart(id) {
    return axios.get(API_URL + "/deleteCart/" + id, { headers: AuthHeader() });
  }
}

export default new CartService();
