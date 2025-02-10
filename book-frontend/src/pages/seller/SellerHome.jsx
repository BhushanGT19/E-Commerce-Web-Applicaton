import React, { useEffect, useState } from "react";
import Book from "../../model/Book";
import sellerService from "../../service/seller.service";
//import Book from './Book'; // Assuming you have a Book component

import { useFormik } from "formik";
import { useNavigate } from "react-router-dom";

import { toast, ToastContainer } from "react-toastify";

import { RotatingLines } from "react-loader-spinner";

import genericbook from "../../generic-book.jpg";

const SellerHome = () => {
  const productValues = {
    productName: "",
    productDescription: "",
    stock: "",
    author: "",
    productPrice: "",
    isbn: "",
    category: "",
  };

  //  productName: "",
  //   productDescription: "",
  //   stock: "",
  //   author: "",
  //   productPrice: "",
  //   productImage: "",
  //   isbn: "",
  //   category: "BOOK",

  //storing the list of all books of seller
  const [books, setBooks] = useState([]);
  //for storing new new book that seller will add
  const [newBook, setNewBook] = useState(
    new Book("", "", "", "", "", "", "", "", "", "", "")
  );

  const [productImage, setProductImage] = useState(null);

  const navigate = useNavigate();

  // state for spinner button

  const [showSpinner, setShowSpinner] = useState(false);

  const [showAddForm, setShowAddForm] = useState(false);

  useEffect(() => {
    sellerService
      .getAllBooks()
      .then((res) => {
        console.log(res.data);

        // const products = res.data.map((product) => {
        //   product.productImage = `data:image/jpeg;base64,${product.productImage}`;
        // });

        setBooks(res.data);
      })
      .then((err) => {
        console.log(err);
      });
  }, []);

  const handleQuantityChange = (id, newQuantity) => {
    setBooks(
      books.map((book) =>
        book.productId === id ? { ...book, quantity: newQuantity } : book
      )
    );
  };

  const handleImageChange = (event) => {
    setProductImage(event.target.files[0]);
  };

  const handleDeleteBook = (id) => {
    setBooks(books.filter((book) => book.id !== id));
    sellerService.deleteBook(id);
  };

  const { values, errors, handleChange, handleSubmit, handleBlur, touched } =
    useFormik({
      initialValues: productValues,

      onSubmit: (values, action) => {
        setShowSpinner(true);

        const formData = new FormData();

        // append all the new book data in formData
        // for (let key in values) {
        //   formData.append(key, values[key]);
        // }

        formData.append("productName", values.productName);
        formData.append("productDescription", values.productDescription);
        formData.append("stock", values.stock);
        formData.append("author", values.author);
        formData.append("productPrice", values.productPrice);
        formData.append("isbn", values.isbn);
        formData.append("category", values.category);

        // append the productImage in formData to send it to the server
        formData.append("productImage", productImage);

        sellerService
          .addProduct(formData)
          .then((res) => {
            notify(res);
            notify("product added");

            setShowAddForm(true);
          })
          .catch((error) => {
            console.log(error);

            notifyError(error.response.data);
          });

        setShowSpinner(false);
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
    <div className="mb-5 container-fluid p-5">
      <div className="row">
        <div className="col-md-8">
          <table className="table">
            <thead className="text-center bg-light">
              <tr>
                <th scope="col">Title</th>
                <th scope="col">Author</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Action</th>
                <th scope="col">Image</th>
              </tr>
            </thead>
            <tbody className="text-center">
              {books.map((book) => (
                <tr key={book.id}>
                  <td>{book.productName}</td>
                  <td>{book.author}</td>
                  <td>{book.productPrice}</td>
                  <td>
                    <button
                      className="btn btn-sm btn-dark me-1"
                      onClick={() =>
                        handleQuantityChange(book.id, book.quantity - 1)
                      }
                    >
                      -
                    </button>
                    {book.quantity}
                    <button
                      className="btn btn-sm btn-dark ms-1"
                      onClick={() =>
                        handleQuantityChange(book.id, book.quantity + 1)
                      }
                    >
                      +
                    </button>
                  </td>
                  <td>
                    <button
                      className="btn btn-sm btn-danger"
                      onClick={() => handleDeleteBook(book.id)}
                    >
                      Delete
                    </button>
                  </td>
                  <td>
                    <img
                      src={
                        book.productImage
                          ? `data:image/jpeg;base64,${book.productImage}`
                          : genericbook
                      }
                      height="100px"
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          <button
            className="btn btn-primary"
            onClick={() => setShowAddForm(true)}
          >
            Add New Book
          </button>
          {showAddForm && (
            <div className="mt-3">
              <h2>Add New Book</h2>
              <div className="mb-3">
                <label>Title:</label>
                <input
                  type="text"
                  className="form-control"
                  name="productName"
                  value={values.productName}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label>Author:</label>
                <input
                  type="text"
                  className="form-control"
                  name="author"
                  value={values.author}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label>Price:</label>
                <input
                  type="text"
                  className="form-control"
                  name="productPrice"
                  value={values.productPrice}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label>Quantity:</label>
                <input
                  type="text"
                  className="form-control"
                  name="stock"
                  value={values.stock}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label>Add Description:</label>
                <input
                  type="text"
                  className="form-control"
                  name="productDescription"
                  value={values.productDescription}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label>isbn No:</label>
                <input
                  type="text"
                  className="form-control"
                  name="isbn"
                  value={values.isbn}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label>Upload Image:</label>
                <input
                  type="file"
                  className="form-control"
                  name="productImage"
                  onChange={handleImageChange}
                  required
                />
              </div>

              {showSpinner ? (
                <RotatingLines />
              ) : (
                <button className="btn btn-success" onClick={handleSubmit}>
                  Add Book
                </button>
              )}
            </div>
          )}
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

export default SellerHome;
