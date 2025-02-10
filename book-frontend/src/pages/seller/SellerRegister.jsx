import { useFormik } from "formik";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

import User from "../../model/User";
import sellerService from "../../service/seller.service";

import { toast, ToastContainer } from "react-toastify";

import { useDispatch, useSelector } from "react-redux";

// to change the state (current authenticated user)
import { setCurrentUser } from "../../store/action/user.action";

import { RotatingLines } from "react-loader-spinner";

const initialValues = {
  firstName: "",
  lastName: "",
  email: "",
  panNumber: "",
  passWord: "",
  mobileNumber: "",
  confirmPassWord: "",
};

const SellerRegister = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  // state for spinner button

  const [showSpinner, setShowSpinner] = useState(false);

  const [errorMsg, setErrorMsg] = useState("");
  const [succMsg, setSuccMsg] = useState("");

  const { values, errors, handleChange, handleSubmit, handleBlur, touched } =
    useFormik({
      initialValues: initialValues,

      onSubmit: (values, action) => {
        if (values.passWord !== values.confirmPassWord) {
          alert("password and confirm password should be same");
        } else {
          setShowSpinner(true);
          sellerService
            .register(values)
            .then((res) => {
              const navigateToHomePageTimeout = setTimeout(() => {
                // after successfully login register navigate to the home page of seller
                navigate("/");
              }, 5000);

              notify(res);
              notify(
                "Your request is under processing, we notify you on email"
              );
              setShowSpinner(false);
            })
            .catch((error) => {
              console.log(error);

              // if (error.response?.status === 409) {
              // notifyError(error.response.data);
              // }

              // global error notifier
              notifyError(error.response.data);
              setShowSpinner(false);
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
                    <label>Pan Card </label>
                    <input
                      type="string"
                      name="panNumber"
                      className="form-control form-control-sm"
                      value={values.panNumber}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    {errors.panNumber && touched.panNumber ? (
                      <p className="text-danger">{errors.panNumber}</p>
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
                  <div className="row">
                    <div className="col-md-6">
                      <div className="text-center mt-3">
                        {/* Use Link component with "to" prop to specify the target route */}
                        <Link
                          to="/sellerLogin"
                          className="btn btn-primary col-md-12"
                        >
                          Login
                        </Link>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="text-center mt-3">
                        <button
                          type="submit"
                          className="btn btn-primary col-md-12"
                        >
                          Register
                        </button>
                      </div>
                    </div>
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

export { SellerRegister };
