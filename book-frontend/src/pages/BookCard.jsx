import React, { useEffect } from "react";
import "../css/bookCard.css";
import genericbook from "../generic-book.jpg";

import { useNavigate } from "react-router-dom";

const Card = ({ product }) => {
  const navigate = useNavigate();

  const handleOnClick = async (productId, productName) => {
    // alert(productId)
    console.log(productId);
    const id = productId;
    navigate(`/book/${productName}/${id}`);
    // navigate("/admin-login");
  };

  return (
    <div
      className="book-card"
      onClick={() => handleOnClick(product.productId, product.productName)}
    >
      <img
        src={
          product.productImage !== null
            ? `data:image/jpeg;base64, ${product.productImage}`
            : genericbook
        }
        alt={product.productName}
      />
      <div className="book-card-body">
        <h2>{product.productName}</h2>
        <p>{product.productDescription}</p>
        <p>
          <strong>Price:</strong> ${product.productPrice}
        </p>
        <p>
          <strong>Category:</strong> {product.category.type}
        </p>
        <p>
          <strong>Author:</strong> {product.author}
        </p>
        <p>
          <strong>ISBN:</strong> {product.isbn}
        </p>
      </div>
    </div>
  );
};

export default Card;
