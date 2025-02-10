import { useFormik } from "formik";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
//import back from "../img/back2.jpg";
import User from "../model/User";
import userService from "../service/user.service";
//import { signupUpSchema } from "../schemas";
import { toast, ToastContainer } from "react-toastify";

import { useDispatch, useSelector } from "react-redux";

// to change the state (current authenticated user)
import { setCurrentUser } from "../store/action/user.action";

import { RotatingLines } from "react-loader-spinner";

const Signup = () => {
  const dispatch = useDispatch();

  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    passWord: "",
    confirmPassWord: "",
    mobileNumber: "",
    address: "",
    city: "",
    state: "",
    pincode: "",
  });

  // state for spinner button

  const [showSpinner, setShowSpinner] = useState(false);

  const [errorMsg, setErrorMsg] = useState("");
  const [succMsg, setSuccMsg] = useState("");
  const navigate = useNavigate();

  // reference object for useFormik
  const initialValues = {
    firstName: "",
    lastName: "",
    email: "",
    passWord: "",
    mobileNumber: "",
    address: "",
    city: "",
    state: "",
    pincode: "",
    confirmPassWord: "",
  };

  const { values, errors, handleChange, handleSubmit, handleBlur, touched } =
    useFormik({
      initialValues: initialValues,
      //validationSchema: signupUpSchema,
      onSubmit: (values, action) => {
        console.log(values);

        if (values.passWord !== values.confirmPassWord) {
          alert("password and confirm password should be same");
        } else {
          // show spinner
          setShowSpinner(true);

          userService
            .register(values)
            .then((data) => {
              const navigateToHomePageTimeout = setTimeout(() => {
                navigate("/");
              }, 2000);

              notify("Register sucessfully");
              // save jwt token in local storage
              // set the JWT token for authenticated user
              localStorage.setItem("token", data.data.token);

              // set the current authenticated user as new registered user using dispatcher of react-redux
              dispatch(setCurrentUser({ user: data.data.user }));
              action.resetForm();
              // clear the time out settled for before navigate to home page
            })
            .catch((error) => {
              console.log(error);
              if (error.response?.status === 409) {
                notifyError(error.response.data);
                setShowSpinner(false);
              }
            });
        }
      },
    });

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
      className="container-fluid p-2"
      style={{
        //backgroundImage: `url(${back})`,
        backgroundPosition: "center",
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
      }}
    >
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card paint-card">
            <div className="card-header">
              <h3 className="text-center text-dark">Signup</h3>
              {succMsg && (
                <p className="fs-4 text-success text-center">{succMsg}</p>
              )}
              {errorMsg && (
                <p className="fs-4 text-danger text-center">{errorMsg}</p>
              )}
            </div>

            <div className="card-body">
              <form onSubmit={handleSubmit}>
                <div className="row mt-3">
                  <div className="col">
                    <label>First Name</label>
                    <input
                      type="text"
                      name="firstName"
                      className="form-control form-control-sm"
                      value={values.firstName}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.firstName && touched.firstName ? (
                      <p className="text-danger">{errors.firstName}</p>
                    ) : null}
                  </div>
                  <div className="col">
                    <label>Last Name</label>
                    <input
                      type="text"
                      name="lastName"
                      className="form-control form-control-sm"
                      value={values.lastName}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.lastName && touched.lastName ? (
                      <p className="text-danger">{errors.lastName}</p>
                    ) : null}
                  </div>
                </div>
                <div className="row mt-3">
                  <div className="col">
                    <label>Email Id</label>
                    <input
                      type="email"
                      name="email"
                      className="form-control form-control-sm"
                      value={values.email}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.email && touched.email ? (
                      <p className="text-danger">{errors.email}</p>
                    ) : null}
                  </div>
                  <div className="col">
                    <label>Mobile No</label>
                    <input
                      type="string"
                      name="mobileNumber"
                      className="form-control form-control-sm"
                      value={values.mobileNumber}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.mobileNumber && touched.mobileNumber ? (
                      <p className="text-danger">{errors.mobileNumber}</p>
                    ) : null}
                  </div>
                </div>

                <div className="row mt-3">
                  <div className="col">
                    <label>Password</label>
                    <input
                      type="password"
                      name="passWord"
                      id="psw"
                      className="form-control form-control-sm"
                      value={values.passWord}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.passWord && touched.passWord ? (
                      <p className="text-danger">{errors.passWord}</p>
                    ) : null}
                  </div>
                  <div className="col">
                    <label>Confirm Password</label>
                    <input
                      type="password"
                      name="confirmPassWord"
                      className="form-control form-control-sm"
                      value={values.confirmPassWord}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.confirmPassWord && touched.confirmPassWord ? (
                      <p className="text-danger">{errors.confirmPassWord}</p>
                    ) : null}
                  </div>
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
                  <div className="text-center mt-3">
                    <button type="submit" className="btn btn-primary col-md-12">
                      Register
                    </button>
                  </div>
                )}
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

export { Signup };
