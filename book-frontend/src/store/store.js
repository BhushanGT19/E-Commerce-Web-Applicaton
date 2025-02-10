import { combineReducers, createStore } from "redux";
import userReducer from "./reducer/user.reducer";
import sellerReducer from "./reducer/seller.reducer";
import adminReducer from "./reducer/admin.reducer";

const allReducer = combineReducers({
  user: userReducer,
  seller: sellerReducer,
  admin: adminReducer,
});

const store = createStore(allReducer);

export default store;
