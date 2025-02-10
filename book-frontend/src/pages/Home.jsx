import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Footer } from "../component/footer/Footer";
import BookService from "../service/book.service";

import BookCard from "./BookCard";

import "../css/homePage.css";

const Home = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    // Fetch books data from the server
    try {
      BookService.getAllBook()
        .then((res) => {
          console.log(res.data);
          setBooks(res.data);
        })
        .catch((error) => {
          console.error(error);
        });
    } catch (error) {
      console.log(error);
    }
  }, []); // Empty dependency array ensures useEffect runs only once

  return (
    <div>
      <div className="book-cards">
        {books.map((book) => (
          <>
            <BookCard key={book.productId} product={book} />
          </>
        ))}
      </div>
    </div>
  );
};

export { Home };
