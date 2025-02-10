import { useEffect } from "react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import User from "../../model/User";
import adminService from "../../service/admin.service";

import { setCurrentAdmin } from "../../store/action/admin.action";

// importing spinner
import { RotatingLines } from "react-loader-spinner";

const AdminLogin = () => {
  const [user, setUser] = useState(
    new User("", "", "", "", "", "", "", "", "", "", "")
  );

  const [message, setMessage] = useState("");
  const [logMessage, setLogMessage] = useState("");

  // state for spinner (to show or not)
  const [showSpinner, setShowSpinner] = useState(false);

  const [login, adminLogin] = useState({
    userName: "",
    passWord: "",
  });

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const loginAdmin = useSelector((state) => state.admin);

  const handleChange = (e) => {
    const { name, value } = e.target;

    adminLogin((prevState) => {
      return {
        ...prevState,
        [name]: value,
      };
    });
  };

  useEffect(() => {
    if (loginAdmin) {
      navigate("/adminDashboard");
    }
    // console.log(loginAdmin);
  }, []);

  const loginSubmit = (e) => {
    e.preventDefault();
    // show spinner (to denote the processing is started)
    setShowSpinner(true);
    adminService
      .login(login)
      .then((res) => {
        // save jwt token in local storage
        console.log(res);

        localStorage.setItem("token", res.data.token);

        // dispatch(setCurrentUser({ user: res.data.user }));
        dispatch(setCurrentAdmin({ admin: res.data.admin }));

        // hide the spinner
        setShowSpinner(false);
        // navigate to the home page after user logs in successfully
        navigate("/adminDashboard");
      })
      .catch((error) => {
        setMessage("invalid email and password");
        console.log(error);
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
                    type="text"
                    className="form-control"
                    onChange={(e) => handleChange(e)}
                    name="userName"
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

export { AdminLogin };
