import { ADD_CART,REMOVE_CART, CLEAR_CURRENT_USER, SET_CURRENT_USER } from "../type";

const cartReducer = (state = {}, action) => {


    switch (action?.type) {
        case ADD_CART:
            localStorage.setItem('cart', JSON.stringify(action?.payload));
            return action?.payload;

        case REMOVE_CART:
            localStorage.removeItem('cart');
            return null;   

        


        default:
            return JSON.parse(localStorage.getItem('loginUser'));
    }


}

export default cartReducer;