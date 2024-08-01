import React from "react";
import { Routes, Route } from "react-router";
import { BrowserRouter } from "react-router-dom";
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import MainPage from "./pages/MainPage";
import LoginPage from "./pages/LoginPage";

// function selectTestData() {
//     axios.post('/test', ["가", "나", "다"])
//         .then(function (res) {
//             console.log(res);
//         });
// }

function App() {
    if (process.env.NODE_ENV === "production") { // production에서만 사용할 수 없도록
        console = window.console || {};
        console.log = function no_console() {
        }; // console log 막기
        console.warn = function no_console() {
        }; // console warning 막기
        console.error = function () {
        }; // console error 막기
    }

    return (
        <div className="App">
            <BrowserRouter>
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    {/*<div>*/}
                    {/*  <button onClick={()=>selectTestData()}>조회</button>*/}
                    {/*</div>*/}
                </header>
                <Routes>
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/signIn" element={<LoginPage />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
