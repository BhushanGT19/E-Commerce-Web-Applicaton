import { CLEAR_CURRENT_ADMIN, SET_CURRENT_ADMIN } from "../type";

const adminReducer = (state = {}, action) => {
  switch (action?.type) {
    case SET_CURRENT_ADMIN:
      localStorage.setItem("loginUser", JSON.stringify(action?.payload));
      return action?.payload;

    case CLEAR_CURRENT_ADMIN:
      localStorage.removeItem("loginUser");
      localStorage.removeItem("token");
      return null;
    default:
      // if login admin exists as a admin
      if (localStorage.getItem("loginUser") !== null) {
        return JSON.parse(localStorage.getItem("loginUser")).admin === undefined
          ? null
          : JSON.parse(localStorage.getItem("loginUser"));
      } else {
        return null;
      }
  }
};

export default adminReducer;
