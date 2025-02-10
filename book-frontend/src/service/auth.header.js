import store from "../store/store";

export const AuthHeader = () => {
  const token = localStorage.getItem("token");

  return {
    "Content-Type": "application/json",
    Authorization: "Bearer " + token,
  };
};
