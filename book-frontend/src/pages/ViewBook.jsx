import { useEffect } from "react";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import bookService from "../service/book.service";
import { useDispatch, useSelector } from "react-redux";
import cartService from "../service/cart.service";
import { ToastContainer, toast } from "react-toastify";

import ProductDetails from "./ProductDetails";

const ViewBook = () => {
  const [product, setProduct] = useState({
    productName: "",
    productPrice: "",
    productImage: null,
    productDescription: "",
    category: "",
    author: "",
    isbn: "",
  });

  const { id } = useParams();
  const user = useSelector((st) => st.user);
  const navigate = useNavigate();

  useEffect(() => {
    init();
  }, []);

  const init = async () => {
    try {
      let res = await bookService.getBookById(id);
      console.log(res.data);
      setProduct(res.data);
    } catch (error) {
      console.log(error);
    }
  };

  const notify = () =>
    toast.success("Added to Cart", {
      position: "bottom-center",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });

  const addToCart = (productId, quantity) => {
    if (!user) {
      navigate("/login");
    } else {
      cartService
        .addCart(productId, quantity)
        .then((res) => {
          console.log(res);
          notify();
          init();
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const placeOrder = () => {
    if (!user) {
      navigate("/login");
    } else {
      // cartService
      //   .addCart(productId, quantity)
      //   .then((res) => {
      //     console.log(res);
      //     notify();
      //     init();
      //   })
      //   .catch((error) => {
      //     console.log(error);
      //   });
    }
  };

  return (
    <>
      <ProductDetails
        product={product}
        addToCart={addToCart}
        placeOrder={placeOrder}
      />

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

export { ViewBook };
