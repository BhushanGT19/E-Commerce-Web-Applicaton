import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { BASE_API_URL } from "../../common/constant";
import orderService from "../../service/order.service";

const Orders = () => {
  const [orderList, setOrderList] = useState([]);

  useEffect(() => {
    

    orderService.getAllOrdersByUser().then(res=>{

      console.log("This is Order List "+ res.data);
      setOrderList(res.data);
    }).catch(error=>{
      console.log(error);
    });

    
  }, []);

  return (
    <div className="container">
      <div class="card paint-card">
        <div class="card-body">
          <h4 class="form-signin-heading text-center">Order Details</h4>

          <table class="table">
            <thead>
              <tr>
                <th scope="col">Image</th>
                <th scope="col">Order Id</th>
                <th scope="col">Book Details</th>
                <th scope="col">Quantity</th>
                <th scope="col">Price</th>
                <th scope="col">Payment Type</th>
                <th scope="col">Status</th>
              </tr>
            </thead>
            <tbody>
              {orderList.map((item, ind) => (
                <tr>
                  <th scope="row">
                    <img
                      //src={BASE_API_URL + "/" + item.book.img}
                      width="70px"
                      height="70px"
                    />
                  </th>

                  <th scope="row">{item.orderNumber}</th>

                  <td>
                    {item.book.bookName}
                    <br />
                    Author: {item.book.author}
                    <br />
                    ISBN :{item.book.isbnNo} <br />
                    Category: {item.book.category.categoryName}
                  </td>

                  <td>{item.quantity}</td>

                  <td>{item.book.price}</td>
                  <td>{item.paymentType}</td>
                  <td >{item.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Orders;