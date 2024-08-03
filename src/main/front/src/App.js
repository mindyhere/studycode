import React from "react";
import { Routes, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";
import logo from "./logo.svg";
import "./App.css";
import axios from "axios";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import Header from "./components/Header";

// function selectTestData() {
//     axios.post('/test', ["가", "나", "다"])
//         .then(function (res) {
//             console.log(res);
//         });
// }

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
          <Route path="/" element={<LoginPage />} />
          <Route path="/main" element={<MainPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
