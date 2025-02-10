import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { toast, ToastContainer } from "react-toastify";

import { setCurrentUser } from "../../store/action/user.action";
import userService from "../../service/user.service";

import { useFormik } from "formik";

// import css for this file
import "../../css/userProfile.css";

const Profile = () => {
  // reference object for useFormik as well acting as a data for Profile component
  const [user, setUser] = useState({
    id: "",
    firstName: "",
    email: "",
    password: "",
    mobNo: "",
    address: "",
    city: "",
    state: "",
    pincode: "",
    profileUrl: "",
  });

  // state for profile image
  const [selectedFile, setSelectedFile] = useState(null);

  // access the user state
  const loginUser = useSelector((state) => state.user);

  // profile image

  const [image, setImage] = useState(null);

  // state for show address form

  const [showAddressForm, setShowAddressForm] = useState(false);

  // to dispatch the redux actions to the reducers to update the state
  const dispatch = useDispatch();

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

  useEffect(() => {
    setImage(loginUser.user.profileUrl);
    setUser(loginUser.user);
  }, []);

  // handle upload for file
  const handleUpload = async () => {
    try {
      const formData = new FormData();
      formData.append("image", selectedFile);

      console.log(formData);

      // const response = await axios.post("/upload", formData, {
      //   headers: {
      //     "Content-Type": "multipart/form-data",
      //   },
      // });

      // console.log("Image uploaded successfully:", response.data);
    } catch (error) {
      console.error("Error uploading image:", error);
    }
  };

  // handle file change for input type
  const handleFileChange = (event) => {
    console.log(URL.createObjectURL(event.target.files[0]));
    setImage(URL.createObjectURL(event.target.files[0]));
    setSelectedFile(event.target.files[0]);
  };

  const { values, errors, handleChange, handleSubmit, handleBlur, touched } =
    useFormik({
      initialValues: loginUser.user,

      onSubmit: async (values, action) => {
        const formData = await handleUpload();
        console.log(values);

        // show spinner
        // setShowSpinner(true);

        // execute the userService method to update the state of user with new state

        userService
          .update(values, formData)
          .then((data) => {
            notify("Profile updated sucessfully");

            // set the current authenticated user as new registered user using dispatcher of react-redux
            dispatch(setCurrentUser({ user: data.data.user }));

            // clear the time out settled for before navigate to home page
          })
          .catch((error) => {
            console.log(error);

            // handle the corresponding erros
            if (error.response?.status === 400) {
              notifyError(error.response.data);
              // setShowSpinner(false);
            }
          });
      },
    });

  return (
    <>
      <form onSubmit={handleSubmit}>
        <div id="user-profile-section">
          <div id="profile-icon">
            <div>
              {/* profile icon */}

              {image ? (
                <img src={image} className="profile-img" alt={image} />
              ) : (
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="100"
                  height="100"
                  fill="currentColor"
                  class="bi bi-person-circle"
                  viewBox="0 0 16 16"
                >
                  <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
                  <path
                    fill-rule="evenodd"
                    d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1"
                  />
                </svg>
                // show svg dummy, image if user not selected any image
              )}
            </div>
          </div>
          <div>
            <input
              type="file"
              id="fileInput"
              className="file-input"
              accept="image/*"
              name={values.profileUrl}
              onChange={handleFileChange}
            />
            <label for="fileInput" className="file-label">
              Choose File
            </label>
          </div>

          <div className="email-section">
            <div>
              <label htmlFor="emailName" class="styled-label">
                Email
              </label>
              <input
                type="text"
                className="styled-input w-425"
                id="email"
                value={values.email}
              />
            </div>
          </div>
          <div id="name-section">
            <div className="name">
              <label htmlFor="firstName" class="styled-label">
                First Name
              </label>
              <input
                type="text"
                className="styled-input w-200"
                id="firstName"
                value={values.firstName}
                onChange={handleChange}
                placeholder="Enter First Name"
                required
              />
            </div>
            <div className="name">
              <label htmlFor="lastName" class="styled-label">
                Last Name
              </label>
              <input
                type="text"
                className="styled-input w-200"
                id="lastName"
                value={values.lastName}
                onChange={handleChange}
                placeholder="Enter Last Name"
                required
              />
            </div>
          </div>
          <div className="mobile-number-section">
            <div>
              <label htmlFor="mobileNumber" class="styled-label">
                Mobile Number
              </label>
              <input
                type="text"
                className="styled-input w-425"
                id="mobileNumber"
                value={values.mobileNumber}
                onChange={handleChange}
                placeholder="Enter Mobile Number"
                required
              />
            </div>
          </div>

          <div id="address-section">
            <div className="w-425">
              <p
                className="btn btn-light"
                onClick={() => setShowAddressForm(!showAddressForm)}
              >
                Update Address
              </p>
            </div>

            <div>
              {showAddressForm && (
                <div id="address-form">
                  <div id="add-address-form">
                    <div>
                      <label htmlFor="city" class="styled-label">
                        City
                      </label>
                      <input
                        type="text"
                        className="styled-input w-425"
                        id="city"
                        value={values.city}
                        placeholder="Add City"
                        handleChange
                      />
                    </div>

                    <div>
                      <label htmlFor="state" class="styled-label">
                        State
                      </label>
                      <input
                        type="text"
                        className="styled-input w-425"
                        id="state"
                        value={values.state}
                        handleChange
                        placeholder="State"
                      />
                    </div>

                    <div>
                      <label htmlFor="postalCode" class="styled-label">
                        Postal Code
                      </label>
                      <input
                        type="text"
                        className="styled-input w-425"
                        id="postalCode"
                        value={values.postalCode}
                        handleChange
                        placeholder="postalCode"
                      />
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>

          <div>
            <button className="btn btn-lg btn-outline-dark">
              update changes
            </button>
          </div>
        </div>
      </form>

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
    </>
  );
};

export default Profile;
