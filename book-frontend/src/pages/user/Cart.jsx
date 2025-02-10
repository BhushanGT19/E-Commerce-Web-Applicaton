import { useRef, useState } from "react";
import { useEffect } from "react";
import cartService from "../../service/cart.service";
import { ToastContainer, toast } from "react-toastify";
import { BASE_API_URL } from "../../common/constant";
import { Link, useNavigate } from "react-router-dom";
import orderService from "../../service/order.service";
import { useSelector } from "react-redux";
import userService from "../../service/user.service";
import addressService from "../../service/address.service";



const Cart = () => {
  const user = useSelector((u) => u.user.user);

  const [address, setAddress] = useState();
  const [cartList, setCartList] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [pymtType, setPymtType] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    init();
  }, []);

  useEffect(() => {
    //code for calcuating totalPrice
    var calculateTotalPrice = 0;

    cartList.forEach((item) => {
      console.log(totalPrice);
      console.log(item.product);
      console.log(item.product.productPrice);
      console.log(item.quantity);
      calculateTotalPrice += item.product.productPrice * item.quantity;
    });
    setTotalPrice(calculateTotalPrice);
    console.log(totalPrice);
  }, [cartList]);

  const init = async () => {
    let cart = await cartService.getCart();
    console.log(cart);
    setCartList(cart.data);

    // let address = await addressService.getAddress();
    // console.log(address);
    // setAddress(address);
  };

  const plusCart = (id, qu) => {
    qu += 1;

    if (qu > 1) {
      cartService
        .updateCart(id, qu)
        .then((res) => {
          init();
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const minusCart = (id, qu) => {
    qu = qu - 1;
    if (qu < 1) {
      cartService
        .deleteCart(id, qu)
        .then((res) => {
          init();
          notify();
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      cartService
        .updateCart(id, qu)
        .then((res) => {
          init();
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const handleType = (e) => {
    setPymtType(e.target.value);
  };

  const orderPage = (e) => {
    e.preventDefault();

    if (pymtType === "COD") {
      // orderService
      //   .createOrder(pymtType)
      //   .then((res) => {
      //     console.log(res.data);
      //   })
      //   .catch((error) => {
      //     console.log(error);
      //   });
      // navigate("/orderSuccessful");
      alert("This service is under maintenance");
    } else {
      alert(totalPrice);
      navigate(`/PaymentForm/${totalPrice}`);
    }
  };

  const notify = () =>
    toast.success("Item Removed", {
      position: "bottom-center",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });

  return (
    <div className="container-fluid p-5">
      <div className="row">
        <div className="col-md-8">
          <table className="table ">
            <thead className="text-center bg-light">
              <tr>
                <th scope="col">Image</th>
                <th scope="col">Name</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Total Price</th>
                <th scope="col">Action</th>
              </tr>
            </thead>
            <tbody className="text-center">
              {cartList.map((item, ind) => (
                <tr key={item.productId}>
                  <th scope="row">
                    <img
                      src={item.product.productImage}
                      width="70px"
                      height="70px"
                    />
                  </th>
                  <td>{item.product.productName}</td>
                  <td>{item.product.productPrice}</td>
                  <td>{item.quantity}</td>
                  <td>{item.quantity * item.product.productPrice}</td>
                  <td className="text-center">
                    <a
                      onClick={() =>
                        plusCart(item.product.productId, item.quantity)
                      }
                      className="text-dark"
                    >
                      <i class="fa-solid fa-plus"></i>
                      Plus
                    </a>
                    <button className="btn btn-sm btn-dark ms-2 me-2">
                      {item.quantity}{" "}
                    </button>
                    <a
                      onClick={() =>
                        minusCart(item.product.productId, item.quantity)
                      }
                      className="text-dark ms-1"
                    >
                      <i class="fa-solid fa-minus"></i>
                      minus
                    </a>
                  </td>
                </tr>
              ))}

              {/* <tr>
                <td colSpan={4}>Total Price</td>
                <td>{totalPrice}</td>
                <td></td>
              </tr> */}
            </tbody>
          </table>
        </div>

        <div className="col-md-4">
          <div className="col-md-12">
            <div className="card paint-card">
              <div className="card-body">
                {/* <p className="fs-6 text-Secondary text-center">
                  Delivery Address
                </p> */}
                <p>
                  Name : {user.firstName} {user.lastName}
                </p>
                <p style={{ color: "black" }}>Mobile No: {user.mobileNumber}</p>
                <Link className="fs-5 text-decoration-none" to="/editProfile">
                  Change Address
                </Link>
              </div>
            </div>
          </div>

          <div className="col-md-12">
            <div className="card paint-card">
              <div className="card-body">
                <p className="fs-6 text-Secondary text-center">Payment</p>
                <p className="fw-bold" style={{ color: "black" }}>
                  Amount: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
                  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                  <i className="fas fa-rupee-sign"></i> {totalPrice}
                  <br /> Shipping Charge:&nbsp; &nbsp; &nbsp; &nbsp;
                  &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
                  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                  <i className="fas fa-rupee-sign"></i> 60 <br /> Tax :&nbsp;
                  &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
                  &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
                  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
                  &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
                  <i className="fas fa-rupee-sign"></i> 30
                </p>
                <hr />
                <p className="fw-bold">
                  Total Amount:&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
                  <i className="fas fa-rupee-sign"></i> {totalPrice + 30 + 60}
                  <br />
                </p>

                <form
                  className="row g-3"
                  onSubmit={(e) => orderPage(e)}
                  method="post"
                >
                  <div className="form-group">
                    <label className="form-label">Payment Mode</label>
                    <select
                      name="type"
                      className="form-control form-control-sm"
                      onChange={(e) => handleType(e)}
                    >
                      <option>--select--</option>
                      <option value="Debit Card">Debit Card</option>
                      <option value="COD">Cash On Delivary</option>
                    </select>
                  </div>
                  {/* <input type="hidden" name="amt" value="690" />
                  <input type="hidden" value="43" name="uid" /> */}

                  {cartList.length !== 0 && (
                    <button className="btn btn-success col-md-12 text-white">
                      Place Order
                    </button>
                  )}

                  {cartList.length === 0 && (
                    <button
                      className="btn btn-success col-md-12 text-white"
                      disabled
                    >
                      Place Order
                    </button>
                  )}
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
      <ToastContainer
        position="bottom-center"
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

export { Cart };
