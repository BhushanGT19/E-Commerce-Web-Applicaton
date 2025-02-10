import { SET_CURRENT_SELLER, CLEAR_CURRENT_SELLER } from "../type";

const sellerReducer = (state = {}, action) => {
  switch (action?.type) {
    case SET_CURRENT_SELLER:
      localStorage.setItem("loginUser", JSON.stringify(action?.payload));
      return action?.payload;

    case CLEAR_CURRENT_SELLER:
      localStorage.removeItem("loginUser");
      localStorage.removeItem("token");
      return null;
    default:
      // if login admin exists as a admin
      if (localStorage.getItem("loginUser") !== null) {
        return JSON.parse(localStorage.getItem("loginUser")).seller ===
          undefined
          ? null
          : JSON.parse(localStorage.getItem("loginUser"));
      } else {
        return null;
      }
  }
};

export default sellerReducer;
