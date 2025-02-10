import { SET_CURRENT_SELLER, CLEAR_CURRENT_SELLER } from "../type";

export const setCurrentSeller = (seller) => {
  return {
    type: SET_CURRENT_SELLER,
    payload: seller,
  };
};

export const clearCurrentSeller = () => {
  return {
    type: CLEAR_CURRENT_SELLER,
  };
};
