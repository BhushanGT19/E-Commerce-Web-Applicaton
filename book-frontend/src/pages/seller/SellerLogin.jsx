import { useEffect } from "react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import User from "../../model/User";
import sellerService from "../../service/seller.service";

import { setCurrentSeller } from "../../store/action/seller.action";

import { RotatingLines } from "react-loader-spinner";

import { toast, ToastContainer } from "react-toastify";

const SellerLogin = () => {
  // state for spinner button

  const [showSpinner, setShowSpinner] = useState(false);

  const [message, setMessage] = useState("");
  const [logMessage, setLogMessage] = useState("");

  const loginSeller = useSelector((state) => state.seller);

  const [login, userLogin] = useState({
    email: "",
    passWord: "",
  });

  const navigate = useNavigate();
  const dispatch = useDispatch();

  // const loginSeller = useSelector((state) => state.seller);

  const handleChange = (e) => {
    setMessage("");
    const { name, value } = e.target;

    userLogin((prevState) => {
      return {
        ...prevState,
        [name]: value,
      };
    });
  };

  useEffect(() => {
    if (loginSeller !== null && loginSeller.seller) {
      navigate("/sellerHome");
    }
  }, []);

  const loginSubmit = (e) => {
    e.preventDefault();
    setShowSpinner(true);
    sellerService
      .login(login)
      .then((res) => {
        console.log(res);

        // save jwt token in local storage
        localStorage.setItem("token", res.data.token);

        dispatch(setCurrentSeller({ seller: res.data.seller }));

        // navigate to the home page after user logs in successfully
        navigate("/sellerHome");
        setShowSpinner(false);
      })
      .catch((error) => {
        console.log(error);
        setMessage("invalid email or password");
        // notifyError("invalid emil or password");
        setShowSpinner(false);
      });
  };

  // toast buttons
  const notifyError = (msg) => {
    toast.error(msg, {
      position: "top-center",
      autoClose: 2000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
  };

  const notify = (msg) => {
    toast.success(msg, {
      position: "top-center",
      autoClose: 2000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
  };

  return (
    <div
      className="container-fluid p-5 bg-img"
      style={{
        //backgroundImage: `url(${back})`,
        backgroundPosition: "center",
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
      }}
    >
      <div className="row">
        <div className="col-md-5 offset-md-3">
          <div className="card paint-card">
            <div className="card-header ">
              <h4 className="text-dark text-center">Seller Login</h4>
              {message && (
                <p className="text-center text-danger fs-5">{message}</p>
              )}

              {logMessage && (
                <p className="text-center text-danger fs-5">{logMessage}</p>
              )}
            </div>
            <div className="card-body">
              <form onSubmit={(e) => loginSubmit(e)}>
                <div className="mb-3">
                  <label className="form-label">Email address</label>
                  <input
                    type="email"
                    className="form-control"
                    onChange={(e) => handleChange(e)}
                    name="email"
                    required
                  />
                </div>
                <div className="mb-3">
                  <label className="form-label">Password</label>
                  <input
                    type="password"
                    className="form-control"
                    onChange={(e) => handleChange(e)}
                    name="passWord"
                    required
                  />
                </div>

                {showSpinner ? (
                  <div className="text-center mt-3">
                    <RotatingLines
                      visible={true}
                      height="57"
                      width="57"
                      color="grey"
                      strokeWidth="5"
                      animationDuration="0.75"
                      ariaLabel="rotating-lines-loading"
                      wrapperStyle={{}}
                      wrapperClass=""
                    />
                  </div>
                ) : (
                  <button type="submit" className="btn btn-primary col-md-12">
                    Login
                  </button>
                )}

                {/* <div className="text-center p-3">
                  <a href="loadforgotPassword" className="text-decoration-none">
                    Forgot Password
                  </a>
                </div> */}
              </form>
            </div>
          </div>
        </div>
      </div>

      <ToastContainer
        position="top-center"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  );
};

export default SellerLogin;
