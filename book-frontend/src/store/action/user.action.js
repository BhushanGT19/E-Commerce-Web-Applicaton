import { ADD_CART, CLEAR_CURRENT_USER, SET_CURRENT_USER } from "../type";

export const setCurrentUser = (user) => {
  return {
    type: SET_CURRENT_USER,
    payload: user,
  };
};

export const clearCurrentUser = () => {
  return {
    type: CLEAR_CURRENT_USER,
  };
};

export const addCart = (cart) => {
  return {
    type: ADD_CART,
    payload: cart,
  };
};
