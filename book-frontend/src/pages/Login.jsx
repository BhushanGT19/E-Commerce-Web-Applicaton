import { useEffect } from "react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
//import back from "../img/back2.jpg";
import User from "../model/User";
import userService from "../service/user.service";

import { setCurrentUser } from "../store/action/user.action";

// importing spinner
import { RotatingLines } from "react-loader-spinner";

const Login = () => {
  const [user, setUser] = useState(
    new User("", "", "", "", "", "", "", "", "", "", "")
  );

  const [message, setMessage] = useState("");
  const [logMessage, setLogMessage] = useState("");

  // state for spinner (to show or not)
  const [showSpinner, setShowSpinner] = useState(false);

  const [login, userLogin] = useState({
    email: "",
    passWord: "",
  });

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const loginUser = useSelector((u) => u.user);
  const loginSeller = useSelector((state) => state.seller);
  const loginAdmin = useSelector((state) => state.admin);

  const handleChange = (e) => {
    const { name, value } = e.target;

    userLogin((prevState) => {
      return {
        ...prevState,
        [name]: value,
      };
    });
  };

  useEffect(() => {
    if (loginUser || loginAdmin || loginSeller) {
      navigate("/");
    }
  }, []);

  const loginSubmit = (e) => {
    e.preventDefault();
    // show spinner (to denote the processing is started)
    setShowSpinner(true);
    userService
      .login(login)
      .then((res) => {
        // save jwt token in local storage
        console.log(res.data);
        console.log(res.data.token);

        localStorage.setItem("token", res.data.token);

        dispatch(setCurrentUser({ user: res.data.user }));
        // hide the spinner
        setShowSpinner(false);
        // navigate to the home page after user logs in successfully
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
        setMessage("invalid email or password");
        setShowSpinner(false);
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
              <h4 className="text-dark text-center">Login</h4>
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
                  />
                </div>
                <div className="mb-3">
                  <label className="form-label">Password</label>
                  <input
                    type="password"
                    className="form-control"
                    onChange={(e) => handleChange(e)}
                    name="passWord"
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
    </div>
  );
};

export { Login };
