import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { Navbar } from "./component/Navbar/Navbar";
import { Footer } from "./component/footer/Footer";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { Home } from "./pages/Home";
import { ViewBook } from "./pages/ViewBook";
import { Login } from "./pages/Login";
import { Signup } from "./pages/Signup";
import { Cart } from "./pages/user/Cart";
import EditProfile from "./pages/user/Profile";
import SellerLogin from "./pages/seller/SellerLogin";
import Orders from "./pages/user/Orders";
import { SellerRegister } from "./pages/seller/SellerRegister";
import SellerHome from "./pages/seller/SellerHome";
import OrderSuccessful from "./pages/user/OrderSuccessful";
/*import { Home } from './pages/Home';


*/
import "react-toastify/dist/ReactToastify.css";
import AdminDashboard from "./pages/admin/AdminDashboard";
import { AdminLogin } from "./pages/admin/AdminLogin";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import PaymentForm from "./pages/user/PaymentForm";

function App() {
  const loginUser = useSelector((state) => state.user);
  const loginSeller = useSelector((state) => state.seller);
  const loginAdmin = useSelector((state) => state.admin);

  const [isUser, setIsUser] = useState(false);
  const [isSeller, setIsSeller] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    // set the variables according to the type of user logged other wise all are false means visitor is performing actions

    console.log(loginUser);
    console.log(loginSeller);
    console.log(loginAdmin);

    if (loginUser) {
      setIsUser(true);
    } else if (loginSeller) {
      setIsSeller(true);
    } else if (loginAdmin) {
      setIsAdmin(true);
    }
  }, []);

  return (
    <BrowserRouter>
      <Navbar />
      <div style={{ "margin-top": "100px" }}></div>
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/home" element={<Home />}></Route>
        <Route path="/book/:bookname/:id" element={<ViewBook />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
        <Route path="/cart" element={<Cart />}></Route>
        <Route path="/EditProfile" element={<EditProfile />}></Route>
        <Route path="/orders" element={<Orders />}></Route>
        <Route path="/sellerRegister" element={<SellerRegister />}></Route>
        <Route path="/sellerHome" element={<SellerHome />}></Route>
        <Route path="/sellerLogin" element={<SellerLogin />}></Route>
        <Route path="/orderSuccessful" element={<OrderSuccessful />}></Route>
        <Route path="/PaymentForm/:amount" element={<PaymentForm />}></Route>

        <Route
          path="/adminDashboard"
          // element={
          //   !isSeller && !isUser ? (
          //     isAdmin ? (
          //       <AdminDashboard />
          //     ) : (
          //       <AdminLogin />
          //     )
          //   ) : (
          //     <Navigate to="/Home" />
          //   )
          // }
          element={<AdminDashboard />}
        ></Route>

        {/* navigate the admin to the login page only if there is no user or admin is logged in */}

        {/* if user || seller has logged in to the applicaton then person should not be able to view the admin login page */}
        {/* and if admin is already stored in browser then directly show him dashboard */}
        <Route
          path="/admin-login"
          element={
            !isSeller && !isUser ? (
              isAdmin ? (
                <AdminDashboard />
              ) : (
                <AdminLogin />
              )
            ) : (
              <Navigate to="/Home" />
            )
          }
        ></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
