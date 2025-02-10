import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { AlertLink } from "react-bootstrap";

const PaymentForm = () => {
  const [paymentAmount, setPaymentAmount] = useState();
  const [orderId, setOrderId] = useState("");
  const [paymentSuccess, setPaymentSuccess] = useState(false);

  const { amount } = useParams();

  useEffect(() => {
    // Dynamically add Razorpay SDK script
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.async = true;
    document.body.appendChild(script);

    alert(amount);
    setPaymentAmount(amount);
    return () => {
      // Cleanup script on component unmount
      document.body.removeChild(script);
    };
  }, []);

  const handlePayment = async () => {
    try {
      // Make a request to your backend to create a Razorpay order
      const response = await axios.post(
        "http://localhost:8081/orders/create-razorpay-order",
        {
          totalAmount: Math.max(amount, 1),
          receipt: "receipt#1",
          // Add other necessary order details
        }
      );

      // Set the orderId received from the backend
      setOrderId(response.data);

      // Redirect the user to the Razorpay payment page
      const razorpayOrderId = response.data;
      const options = {
        key: "rzp_test_FQ4cpgtlLQsLA9",
        amount: paymentAmount * 100,
        currency: "INR",
        order_id: razorpayOrderId,
        handler: function (response) {
          // Handle success callback
          console.log("Payment success:", response);
          setPaymentSuccess(true);
        },
        prefill: {
          email: "patilkhagesh@gmail.com",
          contact: "+919900000000",
        },
        notes: {
          // Add any notes if needed
        },
        theme: {
          // Customize the payment page theme if needed
        },
      };
      const rzp1 = new window.Razorpay(options);
      rzp1.open();
    } catch (error) {
      console.error("Error initiating payment:", error);
      // Log the detailed error information
      console.error("Detailed error:", error);
    }
  };

  return (
    <div>
      {paymentSuccess ? (
        <div>
          <h2>Payment Successful!</h2>
          {/* Display any success message or redirect the user to a success page */}
        </div>
      ) : (
        <div>
          <h2>Payment Details</h2>
          <p>Amount: {paymentAmount} INR</p>
          <button onClick={handlePayment}>Pay Now</button>
        </div>
      )}
    </div>
  );
};

export default PaymentForm;
