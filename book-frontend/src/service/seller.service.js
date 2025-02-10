import axios from "axios";
import { BASE_API_URL } from "../common/constant";
import { AuthHeader } from "./auth.header";

const API_URL = BASE_API_URL;

class sellerService {
  register(values) {
    return axios.post(API_URL + "/sellers", {
      firstName: values.firstName,
      lastName: values.lastName,
      email: values.email,
      passWord: values.passWord,
      panNumber: values.panNumber,
      mobileNumber: values.mobileNumber,
    });
  }

  login(values) {
    console.log(values);
    return axios.post(API_URL + "/sellers/login", {
      email: values.email,
      passWord: values.passWord,
    });
  }

  addBook(book) {
    return axios.post(
      API_URL + "/products",
      {
        book: book,
      },
      {
        headers: AuthHeader(),
      }
    );
  }

  getAllBooks() {
    return axios.get(API_URL + "/products/seller", {
      headers: AuthHeader(),
    });

    // return [
    //   { id: 1, title: "Book 1", author: "Author 1", price: 10, quantity: 5 },
    //   { id: 2, title: "Book 2", author: "Author 2", price: 15, quantity: 8 },
    //   { id: 3, title: "Book 3", author: "Author 3", price: 20, quantity: 3 },
    // ];
  }
  deleteBook(productId) {
    return axios.delete(API_URL + `/products/${productId}`, {
      headers: AuthHeader(),
    });
  }

  //admin will verify and to update verified seller this function is used
  updateSeller(seller) {}

  getAllUnverifiedSellers() {
    return axios.get(API_URL + "/sellers", { headers: AuthHeader() });

    // show an dummy data
    /*
    return new Promise((resolve, error) => {
      resolve({
        unverifiedSellers: [
          {
            sellerId: 1,
            firstName: "Seller 1",
            lastName: "lastName",
            email: "seller1@example.com",
            mobileNumber: "123-456-7890",
            panNumber: 3434,
          }
        ],
      });
      
    });
    */
  }

  // verify the admin using token and send seller id as query string
  verifySeller(sellerId) {
    // for axios.put , second paramter is data, and third is config
    return axios.put(
      API_URL + `/sellers/seller/${sellerId}`,
      {},
      {
        headers: AuthHeader(),
      }
    );
  }

  // verify the admin using token and send seller id as query string
  removeSeller(sellerId) {
    return axios.delete(API_URL + `/sellers/seller/${sellerId}`, {
      headers: AuthHeader(),
    });
  }

  addProduct(product) {
    alert("from add product");
    console.log(product);

    // set the content-type as "multipart/form-data" rather than application/json because product may be contain product image
    return axios.post(API_URL + "/products", product, {
      headers: { ...AuthHeader(), "Content-Type": "multipart/form-data" },
    });
  }
}

export default new sellerService();
