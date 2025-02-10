// Navbar.jsx

import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { clearCurrentUser } from "../../store/action/user.action";
import { clearCurrentAdmin } from "../../store/action/admin.action";
import { clearCurrentSeller } from "../../store/action/seller.action";
import cartService from "../../service/cart.service";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBook, faCartShopping, faCircleUser } from "@fortawesome/free-solid-svg-icons";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "./Navbar.css";
import Logo from "./logo.png";


const Navbar = () => {
  const loginUser = useSelector((u) => u.user);
  const loginSeller = useSelector((state) => state.seller);
  const loginAdmin = useSelector((state) => state.admin);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [cartNo, setCartNo] = useState();

  useEffect(() => {
    init();
  }, []);

  const logout = () => {
    if (loginUser) {
      dispatch(clearCurrentUser());
    } else if (loginAdmin) {
      dispatch(clearCurrentAdmin());
    } else if (loginSeller) {
      dispatch(clearCurrentSeller());
    }

    navigate("/");
  };

  const init = async () => {
    // let cart = await cartService.getCart();
    // setCartNo(cart.data.length);
  };

  return (
    <div className="App-header">
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark p-3">
        <div className="container-fluid">
        <Link to="/" className="navbar-brand">
            <img src={Logo} alt="Book Charm Logo" className="logo-image" />
            {/* <FontAwesomeIcon icon={faBook} /> Book Charm */}
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link to="/home" className="nav-link" aria-current="page">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link to="/books" className="nav-link">
                  Books
                </Link>
              </li>
              {/* Uncomment and complete the search form section */}
              {/* <li className="nav-item search-form">
                <form className="d-flex">
                  <input className="form-control me-2 col-md-6" type="search" placeholder="Search" aria-label="Search" />
                  <button className="btn btn-light" type="submit">
                    Search
                  </button>
                </form>
              </li> */}
            </ul>

            {loginSeller || loginAdmin ? (
              <button className="btn btn-outline-warning" onClick={() => logout()}>
                Logout
              </button>
            ) : (
              !loginUser && (
                <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                  <li className="nav-item">
                    <Link to="/login" className="nav-link">
                      Login
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to="/signup" className="nav-link">
                      Signup
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/sellerRegister" className="nav-link">
                      Become a Seller
                    </Link>
                  </li>
                </ul>
              )
            )}

            {loginUser && (
              <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <Link to="/cart" className="nav-link">
                    <FontAwesomeIcon icon={faCartShopping} /> Cart
                  </Link>
                </li>

                <li className="nav-item dropdown">
                  <a
                    className="nav-link dropdown-toggle"
                    href="#"
                    id="navbarDropdown"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    <FontAwesomeIcon icon={faCircleUser} /> {loginUser.user.firstName}
                  </a>
                  <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li>
                      <Link to="editProfile" className="dropdown-item">
                        Edit Profile
                      </Link>
                    </li>

                    <li>
                      <Link to="/orders" className="dropdown-item">
                        Orders
                      </Link>
                    </li>
                    <li>
                      <button className="dropdown-item" onClick={() => logout()}>
                        Logout
                      </button>
                    </li>
                  </ul>
                </li>
              </ul>
            )}
          </div>
        </div>
      </nav>
    </div>
  );
};

export { Navbar };
