import { CLEAR_CURRENT_USER, SET_CURRENT_USER } from "../type";

const userReducer = (state = {}, action) => {
  switch (action?.type) {
    case SET_CURRENT_USER:
      localStorage.setItem("loginUser", JSON.stringify(action?.payload));
      return action?.payload;

    case CLEAR_CURRENT_USER:
      localStorage.removeItem("loginUser");
      localStorage.removeItem("token");
      return null;
    default:
      // if login user exists as a user
      if (localStorage.getItem("loginUser") !== null) {
        return JSON.parse(localStorage.getItem("loginUser")).user === undefined
          ? null
          : JSON.parse(localStorage.getItem("loginUser"));
      } else {
        return null;
      }
  }
};

export default userReducer;
