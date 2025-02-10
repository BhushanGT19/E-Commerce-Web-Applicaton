import axios from "axios";
import { BASE_API_URL } from "../common/constant";

const API_URL = BASE_API_URL + "/products";

class BookService {
  saveBook(book) {
    return axios.post(API_URL + "/", book);
  }
  getAllBook() {
    return axios.get(API_URL);
  }

  getBookById(productId) {
    // return {
    //   id: 1,
    //   productName: "bookName",
    //   productDescription: "description",
    //   author: "author",
    //   // categorysId: "categorysId",
    //   isbn: "isbnNo",
    //   // language: "language",
    //   productPrice: 200,
    //   productImage: null,
    //   category: {
    //     type: "BOOK",
    //   },
    // };
    return axios.get(`${API_URL}/${productId}`);
  }

  updateBook(book) {
    return axios.post(API_URL + "/update", book);
  }

  deleteBook(id) {
    return axios.get(API_URL + "/delete/" + id);
  }

  searchBook(ch) {
    return axios.get(API_URL + "/search?ch=" + ch);
  }
}

export default new BookService();
