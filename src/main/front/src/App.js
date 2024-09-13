import React from "react";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import Header from "./components/Header";
import AddPostPage   from "./pages/AddPostPage";

function App() {
  if (process.env.NODE_ENV === "production") {
    // production에서만 사용할 수 없도록
    console = window.console || {};
    console.log = function no_console() {}; // console log 막기
    console.warn = function no_console() {}; // console warning 막기
    console.error = function () {}; // console error 막기
  }

  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<LoginPage />} />
          {/*<Route path="/addPost" element={<AddPostPage />} />*/}
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
